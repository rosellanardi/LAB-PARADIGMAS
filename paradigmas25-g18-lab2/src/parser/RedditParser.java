package parser;

import java.text.SimpleDateFormat;
import java.util.*;
import org.json.*;

public class RedditParser extends GeneralParser {

    public List<HashMap<String, Object>> parse(String jsonText) {
        List<HashMap<String, Object>> feedList = new ArrayList<>();

        JSONObject root = new JSONObject(jsonText);
        JSONObject data = root.getJSONObject("data");
        JSONArray children = data.getJSONArray("children");

        for (int i = 0; i < children.length(); i++) {
            JSONObject child = children.getJSONObject(i);
            JSONObject postData = child.getJSONObject("data");

            HashMap<String, Object> postMap = new HashMap<>();

            postMap.put("title", postData.optString("title"));
            postMap.put("description", postData.optString("selftext"));
            postMap.put("link", postData.optString("url"));

            long timestamp = postData.optLong("created_utc", 0);
            if (timestamp > 0) {
                Date date = new Date(timestamp * 1000); 
                postMap.put("pubDate", date);
            } else {
                postMap.put("pubDate", new Date());
            }

            feedList.add(postMap);
        }

        return feedList;
    }

    public String getParseredSiteName() {
        return this.parseredSiteName;
    }
}

