package parser;

import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;
import javax.xml.parsers.DocumentBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/* Esta clase implementa el parser de feed de tipo rss (xml)
 * https://www.tutorialspoint.com/java_xml/java_dom_parse_document.htm 
 * */

public class RssParser extends GeneralParser {
    
    public RssParser() {
    
    }
 
    public List<HashMap<String, Object>> parse(String feedRssXml) {
        
        try {
            List<HashMap<String, Object>> feedList = new ArrayList<>();
        
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = factory.newDocumentBuilder();
            
            ByteArrayInputStream input = new ByteArrayInputStream(feedRssXml.getBytes("UTF-8"));
        	Document xmldoc = docBuilder.parse(input);
        
            Element rootElement = xmldoc.getDocumentElement();
            Element childElement = (Element) rootElement.getElementsByTagName("channel").item(0);
            
            Element siteNameElement = (Element) childElement.getElementsByTagName("title").item(0);
            String siteName = siteNameElement.getTextContent();
            parseredSiteName = siteName;

            NodeList nList = xmldoc.getElementsByTagName("item");
            
            for (int i = 0; i < nList.getLength(); i++) {
                Node nNodeItem = nList.item(i);
                HashMap<String, Object> mapItem = new HashMap<>();
                
                if (nNodeItem.getNodeType() == Node.ELEMENT_NODE) {
                    Element elementItem = (Element) nNodeItem;
                    
                    Element title = (Element) elementItem.getElementsByTagName("title").item(0);
                    Element description = (Element) elementItem.getElementsByTagName("description").item(0);
                    Element pubDate = (Element) elementItem.getElementsByTagName("pubDate").item(0);
                    Element link = (Element) elementItem.getElementsByTagName("link").item(0);
                    
                    String pubDateStr = pubDate.getTextContent();

                    SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
                    Date pubDateDate = null;
                    try {
                        pubDateDate = formatter.parse(pubDateStr);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    mapItem.put(title.getNodeName(), title.getTextContent());
                    mapItem.put(description.getNodeName(), description.getTextContent());
                    mapItem.put(pubDate.getNodeName(), pubDateDate);
                    mapItem.put(link.getNodeName(), link.getTextContent());
                    
                    feedList.add(mapItem);      
                }                         
            } 
            return feedList;
        } catch (Exception e) {
            e.printStackTrace();    
            return new ArrayList<>();
        }      
    }
    
    public String getParseredSiteName() {
        return this.parseredSiteName;
    }
    
}
