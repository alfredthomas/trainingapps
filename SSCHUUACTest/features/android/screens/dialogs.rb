require 'calabash-android/abase'

class ProgressBarScreen < Calabash::ABase
  include SharePlusTests::AndroidHelpers 

  # A page object provides a method called trait. 
  # This method returns a Calabash query string that in turn returns an object unique to this screen. 
  # The page object in Calabash calls this method when it needs to determine if the current screen 
  # corresponds to a given page object
  def trait
    "android.widget.ProgressBar id:'download_progress'"
  end

  def await
    wait_for_elements_exist([trait], :timeout_message => "ProgressBar with id:'download_progress' was not displayed.")
    self
  end

  def download_progress_detail
    "TextView id:'download_progress_detail'"
  end
  
  def wait_until_download_finishes
    wait_for_elements_do_not_exist([download_progress_detail])
  end
  
end


class AlertScreen < Calabash::ABase

  def trait
    "DialogTitle id:'alertTitle'"
     #"com.android.internal.widget.DialogTitle id:'alertTitle'"
     #"com.infragistics.shareplus.ui.util.DialogTitle id:'alertTitle'"
  end

  def alert_message(msg)
    "android.widget.TextView id:'message' text:'#{msg}'" 
  end
  
  def alert_title(title)
    trait + " text:'#{title}'" 
  end
  
  def assert_alert_title(title)
    check_element_exists(alert_title(title))  
  end
  
  def assert_alert_message(msg)
    check_element_exists(alert_message(msg))  
  end
  
  def assert_alert_title_and_message(title, msg)
    assert_alert_title(title)
    assert_alert_message(msg)
  end
  
  def alert_cancel_button
    "Button text:'Cancel'"
  end
  
  def alert_retry_button
    "Button text:'Retry'"
  end
  
  def tap_cancel_button
    touch(alert_cancel_button)
  end
  
  def tap_retry_button
    touch(alert_retry_button)
  end
  
end
