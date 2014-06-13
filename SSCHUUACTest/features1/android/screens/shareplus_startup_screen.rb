require_relative 'shareplus_base_screen'

class SharePlusStartupScreen < SharePlusBaseScreen
  
  def trait
    "android.widget.TextView id:'action_bar_title' text:'SharePlus'"
  end

end