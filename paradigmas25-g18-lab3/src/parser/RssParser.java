package parser;

import feed.*;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;
import javax.xml.parsers.DocumentBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/* Esta clase implementa el parser de feed de tipo rss (xml)
 * https://www.tutorialspoint.com/java_xml/java_dom_parse_document.htm 
 * */

public class RssParser extends GeneralParser <Feed>{

	public RssParser(){}

	public Feed parse (String feedRssXml) {
		try {
            Feed feed = new Feed(null);

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = factory.newDocumentBuilder();
            
            ByteArrayInputStream input = new ByteArrayInputStream(feedRssXml.getBytes("UTF-8"));
        	Document xmldoc = docBuilder.parse(input);
        
            Element rootElement = xmldoc.getDocumentElement();
            if (rootElement.getTagName().equals("rss")) {
                parseRss(feed, rootElement, xmldoc);
            } else {
                parseAtom(feed, rootElement, xmldoc);
            }
            return feed;
        } catch (Exception e) {
            e.printStackTrace();    
            return null;
        }      
	}

    public String getParseredSiteName() {
        return this.parseredSiteName;
    }

    public void parseRss(Feed feed, Element rootElement, Document xmldoc) {
        try {
            Element childElement = (Element) rootElement.getElementsByTagName("channel").item(0);
            
            Element siteNameElement = (Element) childElement.getElementsByTagName("title").item(0);
            String siteName = siteNameElement.getTextContent();
            parseredSiteName = siteName;

            NodeList nList = xmldoc.getElementsByTagName("item");
            
            for (int i = 0; i < nList.getLength(); i++) {
                String title = null; 
                String description = null; 
                String pubDateStr = null; 
                String link = null;

                Node nNodeItem = nList.item(i);
                
                
                if (nNodeItem.getNodeType() == Node.ELEMENT_NODE) {
                    Element elementItem = (Element) nNodeItem; 
                    
                    Node titleNode = elementItem.getElementsByTagName("title").item(0);
                    if (titleNode != null) title = titleNode.getTextContent();
                    Node descriptionNode = elementItem.getElementsByTagName("description").item(0);
                    if (descriptionNode != null) description = descriptionNode.getTextContent();
                    Node pubDateNode = elementItem.getElementsByTagName("pubDate").item(0);
                    if (pubDateNode != null) pubDateStr = pubDateNode.getTextContent();
                    Node linkNode = elementItem.getElementsByTagName("link").item(0);
                    if (linkNode != null) link = linkNode.getTextContent();
                    
                    SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
                    Date pubDateDate = null;
                    
                    if (pubDateStr != null) {
                        pubDateDate = formatter.parse(pubDateStr);
                    }

                    Article article = new Article(title, 
                                                    description,
                                                    pubDateDate,
                                                    link
                                                    );
                    feed.addArticle(article);   
                }
            } 
        } catch (Exception e) {
            e.printStackTrace();    
            }                        
        }

    public void parseAtom(Feed feed, Element rootElement,Document xmldoc) {
        try {
            Element siteNameElement = (Element) rootElement.getElementsByTagName("title").item(0);
            String siteName = siteNameElement.getTextContent();
            parseredSiteName = siteName;

            NodeList nList = xmldoc.getElementsByTagName("entry");
            
            for (int i = 0; i < nList.getLength(); i++) {
                String title = null; 
                String description = null; 
                String pubDateStr = null; 
                String link = null;

                Node nNodeItem = nList.item(i);
                
                
                if (nNodeItem.getNodeType() == Node.ELEMENT_NODE) {
                    Element elementItem = (Element) nNodeItem; 
                    
                    Node titleNode = elementItem.getElementsByTagName("title").item(0);
                    if (titleNode != null) title = titleNode.getTextContent();
                    Node descriptionNode = elementItem.getElementsByTagName("content").item(0);
                    if (descriptionNode != null) description = descriptionNode.getTextContent();
                    Node pubDateNode = elementItem.getElementsByTagName("published").item(0);
                    if (pubDateNode != null) pubDateStr = pubDateNode.getTextContent();
                    Node linkNode = elementItem.getElementsByTagName("link").item(0);
                    if (linkNode != null) link = linkNode.getTextContent();
                    
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.ENGLISH);
                    Date pubDateDate = null;
                    
                    if (pubDateStr != null) {
                        pubDateDate = formatter.parse(pubDateStr);
                    }

                    Article article = new Article(title, 
                                                    description,
                                                    pubDateDate,
                                                    link
                                                    );
                    feed.addArticle(article);   
                }                         
        } 
    } catch (Exception e) {
        e.printStackTrace();    
        } 

}
}
