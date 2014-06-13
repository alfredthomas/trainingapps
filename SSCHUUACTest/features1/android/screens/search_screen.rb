require 'calabash-android/abase'

class GlobalSearchScreen < SharePlusBaseScreen
  include SharePlusTests::AndroidHelpers
  #the global search screen have 2 different states,
  #the initial : when a SearchView is showed and a placeholder is present in the middle of the view
  #the results : when no SearchView is present and the results are showed (List or Grid)
  #they don't have any component in common , so a different trait must be used in order to check if the screen exists
  SEARCH_SCREEN_STATE_INITIAL = "SearchView id:'search'"
  SEARCH_SCREEN_STATE_RESULTS = "* id:'action_bar_title' {text CONTAINS 'Search:'}"

  @currentTextViewCount = 0;
  
  def search_button
    "com.android.internal.view.menu.ActionMenuItemView id:'search_results'"
  end
  
  def initialize(args)
    super
    @currentTrait = GlobalSearchScreen::SEARCH_SCREEN_STATE_INITIAL
  end

  def trait
    @currentTrait
  end

  def updateTrait(newTrait)
    @currentTrait = newTrait
  end
  
  def tapSearchButton()
    touch(search_button)
    wait_for_elements_exist(["SearchView"])
  end

  def searchForText(arg1)
 	if (element_exists(search_button)) then
		tapSearchButton() 	
 	end
    query("SearchView id:'search' descendant * id:'search_src_text'", setText: arg1)
    performAction('send_key_enter')
    @currentTrait = GlobalSearchScreen::SEARCH_SCREEN_STATE_RESULTS
  end

  def scroll_top_sticky_grid_view()
    canScroll = true
    while (canScroll)
      array = query("TextView")
      last_tested_item_description = array.last["description"]
      performAction('scroll_up')
      elements_after_scroll = query("TextView")
      canScroll = elements_after_scroll[0]["description"] != array[0]["description"]
    end
    #the last scroll segment cannot be scrolled in the sticky grid because of a "scroll_up" issue , the last part is scolled manually
    performAction('drag', 50, 50, 20, 50, 2)
    sleep(1)
  end

  def multipleSearchResultsViewExists
    element_exists("StickyGridHeadersGridView")
  end

  def wait_until_loading_begins
    if (!(element_exists("ProgressBar")))
      wait_for_elements_exist(["ProgressBar"])
    end
  end

  def wait_until_loading_finishes
    wait_until_loading_begins()
    if (multipleSearchResultsViewExists())
      canScroll = true
      while (canScroll)
        if (element_exists("ProgressBar"))
          wait_for_elements_do_not_exist(["ProgressBar"])
        end
        array = query("TextView")
        last_tested_item_description = array.last["description"]
        performAction('scroll_down')
        elements_after_scroll = query("TextView")
        canScroll = elements_after_scroll[0]["description"] != array[0]["description"]
      end
    else
      wait_for_elements_do_not_exist(["ProgressBar"])
    end
  end

  def assert_no_results_found()
    check_element_exists("* text:'No results found'")
  end

  def get_result_count()
    query("* id:'iconview'").size()
  end

  def is_cell_with_content_visible(query)
    canScroll = true
    cellVisible = false
    while (canScroll && !cellVisible)
      queryElements =  query(query);
      if (queryElements.size() > 0)
      cellVisible = true
      else
        array = query("TextView")
        last_tested_item_description = array.last["description"]
        performAction('scroll_down')
        elements_after_scroll = query("TextView")
        canScroll = elements_after_scroll[0]["description"] != array[0]["description"]
      end
    end
    cellVisible
  end

  def is_no_results_message_visible()
    is_cell_with_content_visible("* text:'No results found'")
  end

  def is_more_results_message_visible()
    is_cell_with_content_visible("* text:'View more results'")
  end

  def assert_no_results_visible()
    if (!is_no_results_message_visible())
      raise("The no results message should be visible")
    end
  end

  def assert_no_results_hidden()
    if (is_no_results_message_visible())
      raise("The no results message should be hidden")
    end
  end

  def assert_more_results_visible()
    if (!is_more_results_message_visible())
      raise("The load more results message should be visible")
    end
  end

  def assert_more_results_hidden()
    if (is_more_results_message_visible())
      raise("The load more results message should be hidden")
    end
  end

  def assert_noise_error_displayed()
    if (!is_cell_with_content_visible("TextView {text BEGINSWITH 'Server error'}"))
      raise("The noise error message should be visible")
    end
  end

  def get_items_in_view_with_query(query, loadingInProgress)
    if (loadingInProgress)
      sleep(2)
    end
    foundedCells = query(query)
    canScroll = true
    maxRetryCount = 5
    retryNumber = 0
    while (foundedCells.size() == 0 && canScroll  && retryNumber < maxRetryCount)
      array = query("TextView")
      performAction('scroll_down')
      foundedCells = query(query)
      elements_after_scroll = query("TextView")
      canScroll = elements_after_scroll[0]["description"] != array[0]["description"]
      if (!canScroll && loadingInProgress)
        scroll_top_sticky_grid_view()
        foundedCells = query(query)
      canScroll = true
      retryNumber += 1
      end
    end
    if (retryNumber >= maxRetryCount)
      raise "Maximum amount of searchs reached in query" + query
    end
    foundedCells
  end

  def navigate_to_item_with_description(desc, saveState, loadingInProgress)
    foundedCells = get_items_in_view_with_query("* {text CONTAINS '" + desc + "'}", loadingInProgress)
    if (foundedCells.size() > 0)
      if (saveState)
        save_current_search_state()
      end
      touch(foundedCells[0])
    else
      screenshot_and_raise "Cell with description " + desc + " not found"
    end
  end

  def save_current_search_state()
    @currentTextViewCount = get_result_count();
  end

  def assert_view_consistency()
    if (@currentTextViewCount != get_result_count())
      raise("The view is not showing the same amount of item as before " + @currentTextViewCount.to_s + " " + get_result_count().to_s)
    end
  end

  def assert_view_initialState()
    if (!element_exists(screen_purpose_description))
      raise("The view is not showing the placeholder text in the initial state")
    end
  end

  def assert_results_still_loading
    loadings = get_items_in_view_with_query("ProgressBar", true)
    if (loadings.size() <= 0)
      raise("The view should be in loading state")
    end
  end

  def assert_connection_error_exists
    error_messages = get_items_in_view_with_query("* {text CONTAINS 'Connection error'}", false)
    if (error_messages.size <= 0)
      raise("A connection error should be present")
    end
  end
  
  def assert_timeout_error_exists
    error_messages = get_items_in_view_with_query("* {text CONTAINS 'Error'}", false)
    if (error_messages.size <= 0)
      raise("A timeout error should be present")
    end
  end
  
  def screen_purpose_description
    "* {text CONTAINS 'Search for content in all portals'}"
  end

  def error_details_query
    "* {text CONTAINS 'Tap to get more info'}"
  end

  def assert_error_details_exists
    error_details = get_items_in_view_with_query(error_details_query(), false)
    if (error_details.size <= 0)
      raise("The error detail option should be present")
    end
  end
  
  def open_error_details
    error_details = get_items_in_view_with_query(error_details_query(), false)
    touch(error_details[0])
  end
  
  def exists_result_with_description(desc)
    items = get_items_in_view_with_query("* {text CONTAINS '" + desc + "'}", false)
    if (items.size <= 0)
      raise("The item could not be found")
    end
  end

end