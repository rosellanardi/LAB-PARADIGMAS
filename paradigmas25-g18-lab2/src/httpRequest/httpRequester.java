package httpRequest;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
/* Esta clase se encarga de realizar efectivamente el pedido de feed al servidor de noticias
 * Leer sobre como hacer una http request en java
 * https://www.baeldung.com/java-http-request
 * */

public class httpRequester {

	public String getFeedRss(String urlFeed){
		String feedRssXml = "";
		try {
		    URL url = new URL(urlFeed);
		    HttpURLConnection con = (HttpURLConnection) url.openConnection();
		    con.setRequestMethod("GET");
		    int status = con.getResponseCode();

		    InputStreamReader streamReader;
		    if (status > 299) {
		        streamReader = new InputStreamReader(con.getErrorStream());
		    } else {
		        streamReader = new InputStreamReader(con.getInputStream());
		    }

		    try (BufferedReader in = new BufferedReader(streamReader)) {
		        StringBuilder content = new StringBuilder();
		        String line;
		        while ((line = in.readLine()) != null) {
		            content.append(line);
		        }
		        feedRssXml = content.toString();
		    }

		    con.disconnect();

		} catch (Exception e) {
		    e.printStackTrace();
		}

		return feedRssXml;
    }


	public String getFeedReedit(String urlFeed) {
		String feedReeditJson = "";
		try {
		    URL url = new URL(urlFeed);
		    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		    conn.setRequestProperty("User-Agent", "MyRedditReader/1.0");
		    conn.setRequestMethod("GET");
		    int status = conn.getResponseCode();

		    InputStreamReader streamReader;
		    if (status > 299) {
		        streamReader = new InputStreamReader(conn.getErrorStream());
		    } else {
		        streamReader = new InputStreamReader(conn.getInputStream());
		    }

		    try (BufferedReader in = new BufferedReader(streamReader)) {
		        StringBuilder content = new StringBuilder();
		        String line;
		        while ((line = in.readLine()) != null) {
		            content.append(line);
		        }
		        feedReeditJson = content.toString();
		    }
		    conn.disconnect();

		} catch (Exception e) {
		    e.printStackTrace();
		}

		return feedReeditJson;
	}
}


