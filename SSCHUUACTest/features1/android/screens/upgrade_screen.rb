require 'calabash-android/abase'

class UpgradeScreen < Calabash::ABase
  include SSCHUUACTests::AndroidHelpers 
  def trait
    "ListView id:'upgrade_list'"
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
  
  
end
