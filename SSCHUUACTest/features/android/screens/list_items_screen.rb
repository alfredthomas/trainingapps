require_relative 'shareplus_base_screen'

class ListItemsScreen < SharePlusBaseScreen

  def trait
    "android.widget.ListView id:'list'"
  end

  private
  def doc_item_name(doc)
    "TextView id:'docItemName' text:'#{doc}'"
  end
  
  private
  def item_title(item)
    "TextView id:'item_title' text:'#{item}'"
  end

  private
  def document_title(doc)
    "TextView id:'docItemName' text:'#{doc}'"
  end

  private
  def item_information_icon(item)
    "TextView text:'#{item}' parent CheckableRelativeLayout descendant ImageButton"
  end

  public
  def tap_folder(folder_name)
    uiquery = doc_item_name(folder_name)
    tap_content(uiquery)
    wait_for_elements_exist([action_bar_title_label(folder_name)])
  end
  
  public
  def tap_item_information_icon(item)
    tap_document_information_icon(item)
  end
  
  public 
  def assert_item_exists(arg1)
    if (arg1.to_s.length > 0 && !element_exists("* id:'docItemName' text:'" + arg1 +"'"))
     raise("item with name " + arg1 + " not found")
    end
  end

  public
  def tap_document_information_icon(doc)
    wait_for_elements_exist([document_title(doc)])
    touch(item_information_icon(doc))
  end

end