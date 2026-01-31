package parser;

import feed.*;
import java.text.SimpleDateFormat;
import java.util.*;
import org.json.*;


/*
 * Esta clase implementa el parser de feed de tipo reddit (json)
 * pero no es necesario su implemntacion 
 * */

public class RedditParser extends GeneralParser {
	public RedditParser(){}

	public Feed parse (String jsonText) {
		try {
            Feed feed = new Feed(null);

	        JSONObject root = new JSONObject(jsonText);
	        JSONObject data = root.getJSONObject("data");
	        JSONArray children = data.getJSONArray("children");

	        for (int i = 0; i < children.length(); i++) {
	            JSONObject child = children.getJSONObject(i);
	            JSONObject postData = child.getJSONObject("data");

	            Date date;
	            long timestamp = postData.optLong("created_utc", 0);
	            if (timestamp > 0) {
	                date = new Date(timestamp * 1000); 
	            } else {
	                date = new Date();
	            }
	            Article article = new Article(postData.optString("title"), 
                                              postData.optString("selftext"),
                                              date,
                                          	  postData.optString("url")
                                          	 );
                feed.addArticle(article);   
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
}
