require 'calabash-android/abase'

class AddPortalScreen < Calabash::ABase
  include SharePlusTests::AndroidHelpers 

  # A page object provides a method called trait. 
  # This method returns a Calabash query string that in turn returns an object unique to this screen. 
  # The page object in Calabash calls this method when it needs to determine if the current screen 
  # corresponds to a given page object
  def trait
    "android.widget.ScrollView id:'add_site_scrollview'"
  end

  def site_name
    "android.widget.EditText id:'site_name_text'"    
  end

  def site_url
    "android.widget.EditText id:'site_url_text'"    
  end

  def site_auth_method
    "android.widget.Spinner id:'site_auth_method_spinner'"    
  end

  def account_username
    "android.widget.EditText id:'account_username_text'"    
  end

  def account_password
    "android.widget.EditText id:'account_password_text'"    
  end

  def account_domain
    "android.widget.EditText id:'account_domain_text'"    
  end

  def test_connection_button
    "com.android.internal.view.menu.ActionMenuItemView id:'test'"
  end

  def cancel_button
    "com.android.internal.view.menu.ActionMenuItemView id:'cancel'"
  end
  
  def done_button
    "com.android.internal.view.menu.ActionMenuItemView id:'done'"
  end

  def add_new_portal(portal_name, portal_url, username, password)
    enter_text(site_name, portal_name)
    enter_text(site_url, portal_url)
    enter_text(account_username, username)
    enter_text(account_password, password)
    touch(done_button)
    page(SitesHubScreen).await
  end

end