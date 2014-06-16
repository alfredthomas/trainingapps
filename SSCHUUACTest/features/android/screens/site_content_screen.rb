require_relative 'shareplus_base_screen'

class SiteContentScreen < SharePlusBaseScreen
  
  def trait
    "com.infragistics.shareplus.ui.BetterColumnsStickyGridView id:'web_items'"
  end

  private
  def item_title(title)
    "TextView id:'item_title' text:'#{title}'"
  end
  
  public
  def tap_library(library_name)
    uiquery = item_title(library_name)
    tap_content(uiquery)
    page(ListItemsScreen).await
  end
  
  public
  def assert_subsite_exists(arg1)
   wait_for_elements_exist(["* id:'sites_home_item_text'"])
   if (arg1.to_s.length > 0 && !element_exists("* id:'sites_home_item_text' text:'" + arg1 +"'"))
       raise("subsite with name " + arg1 + "not found")
   end
  end
  
  public
  def assert_list_exists(arg1)
    if (arg1.to_s.length > 0 && !element_exists("* id:'item_title' text:'" + arg1 +"'"))
     raise("list with name " + arg1 + " not found")
    end
  end
  
  public
  def tap_list(list_name)
    uiquery = item_title(list_name)
    tap_content(uiquery)
    page(ListItemsScreen).await
  end
    
  public
  def list_has_sync_icon(list_name)
    check_element_exists('TextView text:"' + list_name + '" parent CheckableRelativeLayout descendant ImageView id:"sync_icon"')
  end
    
  public
  def list_does_not_have_sync_icon(list_name)
    check_element_does_not_exist('TextView text:"' + list_name + '" parent CheckableRelativeLayout descendant ImageView id:"sync_icon"')
  end

end