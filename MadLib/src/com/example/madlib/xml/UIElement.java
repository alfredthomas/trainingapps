package com.example.madlib.xml;

public class UIElement {
	private String component;
	private String content;
	
	public UIElement(String component, String content){
		this.setComponent(component);
		this.setContent(content);
	}
	public String getComponent() {
		return component;
	}
	public void setComponent(String component) {
		this.component = component;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

}
