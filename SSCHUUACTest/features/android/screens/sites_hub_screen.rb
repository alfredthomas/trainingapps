require 'calabash-android/abase'

class SitesHubScreen < SharePlusBaseScreen
  include SharePlusTests::AndroidHelpers 
  
  def trait
    "com.infragistics.shareplus.ui.BetterColumnsStickyGridView id:'sites_home_items'"
  end

  def add_portal_button
    "com.android.internal.view.menu.ActionMenuItemView id:'action_add_site'"
  end

  def portal_label(portal_name)
    "TextView id:'sites_home_item_text' text:'#{portal_name}'"
  end

  def tap_add_portal_button
    touch(add_portal_button)
    page(AddPortalScreen).await
  end
  
  def portal_exists?(portal_name)
    element_exists(portal_label(portal_name))
  end
  
  def tap_portal(portal_name)
    portal_uiquery = portal_label(portal_name)
    wait_for_elements_exist([portal_uiquery])
    touch(portal_uiquery)
    page(SiteContentScreen).await
  end
  
  def delete_portal_option
    query("ActionMenuItemView id:'action_delete'")
  end
  
  def delete_confirmation_button
    "* text:'Yes'"
  end
  
  def portal_name_textview
    "TextView id:'sites_home_item_text'"
  end
  
  def long_press_portal(portal_name)
     performAction('press_long_on_text', portal_name)
  end
  
  
  def remove_portal_if_exists(portal_name) 
    if element_exists(portal_label(portal_name))
        long_press_portal(portal_name)
        touch(delete_portal_option)
        wait_for_elements_exist([delete_confirmation_button])
        touch(delete_confirmation_button)
        wait_for_elements_exist([action_bar_title_label("Sites")])
    end
  end
  
  def get_portal_names 
    portalNames = []
    siteTextViews = query(portal_name_textview) 
    for item in siteTextViews
       portalNames.push item["text"]
     end
     portalNames
  end

end