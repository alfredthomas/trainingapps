require 'calabash-android/abase'

class SharePlusSideBarScreen < SharePlusBaseScreen
  include SharePlusTests::AndroidHelpers 
  
  public
  def trait
    "android.widget.TextView id:'action_bar_title' text:'SharePlus'"
  end
  
  def search_option
    "TextView id:'action_text' text:'Search'"
  end
 
  def tap_in_search_option
      wait_for_elements_exist([search_option])
      touch(search_option) 
  end

end
  
