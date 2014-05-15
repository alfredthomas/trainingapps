package com.example.madlib.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.storage.StorageManager;

/**
 * Helper class for providing utilities involving XML
 */
public class Utils {

    /**
     * Generate list of filenames from path within assets folder
     */
	public static String[] AssetFilenameList(Context appContext, String path){
		String[] list;
		try {
			list = appContext.getAssets().list(path);
		}
		catch (IOException io){return null;}
		return list;
	}
	/**
	 * Read in an XML Story from a given path.
	 *  
	 */
    public static ArrayList<UIElement> LoadStory(AssetManager am,int index)
    {
    	try{
    	ArrayList<UIElement> storyUI =new ArrayList<UIElement>();
    	InputStream xmlFile = GetFile(am, index);
    	if(xmlFile.available()>0)
    	{
    		try {
    		
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc;
			
            doc = dBuilder.parse(xmlFile);
			

            doc.getDocumentElement().normalize();

            Node story = doc.getFirstChild();
            //now perform DFS to iterate over each node in proper order
            Node next= story.getFirstChild();
            while(next!=null){
            	if(!(next.getNodeType() == Node.ELEMENT_NODE))
            	{
            		next = next.getNextSibling();
            		continue;
            	}
            	//get content
            	storyUI.add(TranslateXML(next));
            	//go to children
            	Node child = next.getFirstChild();
            	while(child!=null)
            	{
            		if(!(child.getNodeType() == Node.ELEMENT_NODE))
                	{
                		child = child.getNextSibling();
                		continue;
                	}
            		//check for child content
            		storyUI.add(TranslateXML(child));
            		//go to next sibling
            		child = child.getNextSibling();
            	}
            	//go to next sibling
            	next = next.getNextSibling();
            }
            
            int i = 12;
            i= i+1;
    		} catch (Exception e) {
				e.printStackTrace();
			}

    	}
    	return storyUI;
    	}catch (IOException io){io.printStackTrace();}
    	return null;
    }
    public static InputStream GetFile(AssetManager assets,int id){
    
    try {
		return assets.open("templates/"+assets.list("templates")[id-1]);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return null;
	}
    	
    }
    public static UIElement TranslateXML(Node node){
    if(node.getNodeName().equals("title"))
    	return new UIElement("title", node.getTextContent());
    else if(node.getNodeName().equals("static"))
    	return new UIElement("text",node.getAttributes().getNamedItem("content").getNodeValue());
    else 
    	return new UIElement("input",node.getNodeName());
    }
	
}
