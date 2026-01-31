package parser;

import subscription.Subscription;
import subscription.SingleSubscription;

import org.json.JSONTokener;
import org.json.JSONObject;
import org.json.JSONArray;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.io.FileNotFoundException;

/*
 * Esta clase implementa el parser del  archivo de suscripcion (json)
 * Leer https://www.w3docs.com/snippets/java/how-to-parse-json-in-java.html
 * */

public class SubscriptionParser extends GeneralParser {
	
	public SubscriptionParser() {

	}

    public List<HashMap<String, Object>> parse (String config){
          
        try {
            FileReader reader = new FileReader(config);
            JSONArray array = new JSONArray(new JSONTokener(reader));

            List<HashMap<String, Object>> list = new ArrayList<>();

            for (int i = 0; i < array.length(); i++) {		   
                HashMap<String, Object> map = new HashMap<>();

                JSONObject object = array.getJSONObject(i);

                String url = object.getString("url");
                JSONArray paramsArray = object.getJSONArray("urlParams");
                String urlType = object.getString("urlType");
                
                map.put("url", url);
                map.put("urlParams", paramsArray);
                map.put("urlType", urlType);

                list.add(map);                     
            }
            
            return list;  
        } catch (FileNotFoundException e) {
            System.err.println("Error: El archivo no se encontró.");
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public String getParseredSiteName() {
        return this.parseredSiteName;
    }

}

