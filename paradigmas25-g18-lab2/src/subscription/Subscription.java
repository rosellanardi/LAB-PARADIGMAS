package subscription;

import parser.SubscriptionParser;
import org.json.JSONArray;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

/*Esta clse abstrae el contenido del archivo  de suscripcion(json)*/
public class Subscription {
	private List<SingleSubscription> suscriptionsList;
	
	
	public Subscription(String subscriptionFilePath) {
		super();
		this.suscriptionsList = new ArrayList<SingleSubscription>();
	}

	public List<SingleSubscription> getSubscriptionsList(){
		return this.suscriptionsList;
	}

	public void addSingleSubscription(SingleSubscription s) {
		this.suscriptionsList.add(s);
	}
	
	public SingleSubscription getSingleSubscription(int i){
		return this.suscriptionsList.get(i);
	}

	@Override
	public String toString() {
		String str ="";
		for (SingleSubscription s: getSubscriptionsList()){
			str += s.toString();
		}
		return "[" + str + "]";
	}	
	
	public void prettyPrint(){
		System.out.println(this.toString());
	}

    public void createParseredSubscription(Subscription subscription) {
        SubscriptionParser parserJson = new SubscriptionParser();
        List<HashMap<String, Object>> subscriptionsList = parserJson.parse("config/subscriptions.json");

        for (int i = 0; i < subscriptionsList.size(); i++) {
        
            HashMap<String, Object> hash = subscriptionsList.get(i);
            String url = (String) hash.get("url");
            JSONArray urlParams = (JSONArray) hash.get("urlParams");
            String urlType = (String) hash.get("urlType");
            SingleSubscription singleSubscription = new SingleSubscription(url, null, urlType);

            for (int j = 0; j < urlParams.length(); j++) {
                    singleSubscription.setUrlParams(urlParams.getString(j));
		    }
            subscription.addSingleSubscription(singleSubscription);
        }
    
    }
	
	public static void main(String[] args) {
		Subscription a = new Subscription(null);
	
		SingleSubscription s0 = new SingleSubscription("https://www.chicagotribune.com/arcio/rss/category/%s/?query=display_date:[now-2d+TO+now]&sort=display_date:desc", null, "rss");
		s0.setUrlParams("business");		
		
		SingleSubscription s1 = new SingleSubscription("https://rss.nytimes.com/services/xml/rss/nyt/%s.xml", null, "rss");
		s1.setUrlParams("Business");
		s1.setUrlParams("Technology");

		a.addSingleSubscription(s0);		
		a.addSingleSubscription(s1);
		a.prettyPrint();
	}
}
