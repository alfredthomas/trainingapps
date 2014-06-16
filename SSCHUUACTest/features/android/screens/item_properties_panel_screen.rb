require_relative 'shareplus_base_screen'

class ItemPropertiesPanelScreen < SharePlusBaseScreen

  def item_name_label(name)
    "TextView id:'item_name' text:'#{name}'"
  end

  def item_icon
    "ImageView id:'item_thumbnail'"
  end

  def item_action_button_label(action)
    item_action_button + " text:'#{action}'"
  end

  def item_action_button
    "TextView id:'item_default_action'"
  end

  # A page object provides a method called trait. 
  # This method returns a Calabash query string that in turn returns an object unique to this screen. 
  # The page object in Calabash calls this method when it needs to determine if the current screen 
  # corresponds to a given page object
  def trait
    "FrameLayout id:'properties_drawer' descendant * id:'properties_panel_container'"
  end

  def assert_item_name(actual_item_name)
    uiquery = item_name_label(actual_item_name)
    check_element_exists(uiquery)
  end

  def assert_item_icon()
    check_element_exists(item_icon)
  end

  def assert_item_action(actual_item_action)
    uiquery = item_action_button_label(actual_item_action)
    check_element_exists(uiquery)
  end

  def tap_item_action_button()
    touch(item_action_button)
  end

end