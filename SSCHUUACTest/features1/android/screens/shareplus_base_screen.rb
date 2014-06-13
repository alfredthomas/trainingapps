require 'calabash-android/abase'

class SharePlusBaseScreen < Calabash::ABase
  include SharePlusTests::AndroidHelpers 
  
  protected
  def action_bar_title_label(content)
    action_bar_title + " text:'#{content}'"
  end
  
  def action_bar_title
    "TextView id:'action_bar_title'"
  end
  
  protected 
  def action_bar_overflow_button
    "com.android.internal.view.menu.ActionMenuPresenter$OverflowMenuButton"
  end
  
  protected 
  def action_bar_overflow_button_menu_item(menu_option)
    "com.android.internal.view.menu.ListMenuItemView descendant TextView text:'#{menu_option}'"
  end
  
  public 
  def tap_content(uiquery)
    wait_for_elements_exist([uiquery])
    touch(uiquery)
  end 
  
  public
  def assert_action_bar_title(expected_title)
    check_element_exists(action_bar_title_label(expected_title))
  end
  
  public
  def go_back
    performAction('go_back')
  end  
  
  public
  def tap_action_bar_overflow_button
    touch(action_bar_overflow_button)
    wait_for_elements_exist(["ListMenuItemView"])
  end
  
  def tap_menu_option_from_action_bar_overflow_button(menu_option)
    action_uiquery = action_bar_overflow_button_menu_item(menu_option)
    wait_for_elements_exist([action_uiquery])
    touch(action_uiquery)
  end
  
  public
  def go_up
    touch("ImageView id:'up'")
  end
  
  public
  def scroll_down
    performAction('scroll_down')
  end
  
  public
  def action_bar_has_option(value)
    check_element_exists('ListMenuItemView descendant TextView text:"' + value + '"')
  end
  
  public
  def action_bar_does_not_have_option(value)
    check_element_does_not_exist('ListMenuItemView descendant TextView text:"' + value + '"')
  end

  public
  def wait_for_progress_bar_do_not_exist
    wait_for_elements_do_not_exist(["ProgressBar"], :timeout => 40)
  end

  public
  def rotate_to_position(position)
    performAction('set_activity_orientation', position)
    performAction('wait', 3)
  end

# PRE: List does not have pagination.
# POS: Touch the row with the specific text, if it does not found, throws an error.  
  public
  def try_tap_row_with_text(text)
    wait_for_progress_bar_do_not_exist
    uiquery = "TextView text:'" + text + "'"
    foundedRows = query(uiquery)
    canScroll = true
    while (foundedRows.size() == 0 && canScroll)
      array = query("TextView")
      scroll_down
      foundedRows = query(uiquery)
      elements_after_scroll = query("TextView")
      canScroll = elements_after_scroll[0]["description"] != array[0]["description"]
    end
    if (foundedRows.size() > 0)
      touch(foundedRows[0])
    else
      screenshot_and_raise "Row with title " + text + " not found"
    end
  end
  
  public
  def show_drawer
    if !page(SharePlusSideBarScreen).current_page?
      touch(action_bar_title)
      @current_page = page(SharePlusSideBarScreen).await
    end
  end
  
  public
  def hide_drawer
    if page(SharePlusSideBarScreen).current_page?
      go_back()
    end
  end
  
# navigation_steps:
# https://github.com/calabash/calabash-android/blob/master/ruby-gem/lib/calabash-android/steps/navigation_steps.rb

end
