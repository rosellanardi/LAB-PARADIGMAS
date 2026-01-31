package parser;

import subscription.*;


import org.json.JSONObject;

import org.json.JSONTokener;
import org.json.JSONObject;
import org.json.JSONArray;

import java.io.FileReader;
import java.io.FileNotFoundException;
/*
 * Esta clase implementa el parser del  archivo de suscripcion (json)
 * Leer https://www.w3docs.com/snippets/java/how-to-parse-json-in-java.html
 * */

public class SubscriptionParser extends GeneralParser <Subscription>{

	public SubscriptionParser() {}

	public Subscription parse (String config) {
		try {
			FileReader reader = new FileReader(config);
            JSONArray array = new JSONArray(new JSONTokener(reader));
            Subscription subscription = new Subscription(null);


            for (int i = 0; i < array.length(); i++) {
            	
            	JSONObject object = array.getJSONObject(i);

                String url = object.getString("url");
                JSONArray paramsArray = object.getJSONArray("urlParams");
                String urlType = object.getString("urlType");
                SingleSubscription singleSubscription = new SingleSubscription(url, null, urlType);

                for (int j = 0; j < paramsArray.length(); j++) {
                    singleSubscription.setUlrParams(paramsArray.getString(j));
		    	}
                subscription.addSingleSubscription(singleSubscription);
            }
            return subscription;
		} catch (Exception e) {
			System.err.println("Error: El archivo no se encontró.");
            e.printStackTrace();
            return null;
		}
	}

    public String getParseredSiteName() {
        return this.parseredSiteName;
    }
}
