require_relative 'shareplus_base_screen'
class WebViewerScreen < SharePlusBaseScreen
  def trait
    "WebView id:'web_view'"
  end

  public
  def web_view_html_element(selector)
    "WebView id:'web_view' css:'#{selector}'"
  end
  
end
