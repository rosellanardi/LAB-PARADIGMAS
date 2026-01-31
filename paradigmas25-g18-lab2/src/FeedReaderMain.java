import subscription.Subscription;
import subscription.SingleSubscription;
import feed.Feed;
import feed.Article;
import httpRequest.httpRequester;
import parser.GeneralParser;
import parser.RssParser;
import parser.RedditParser;
import namedEntity.heuristic.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class FeedReaderMain {

	private static void printHelp(){
		System.out.println("Please, call this program in correct way: FeedReader [-ne]");
	}
	
	public static void main(String[] args) {
		System.out.println("************* FeedReader version 1.0 *************");
			
		Subscription subscription = new Subscription(null);
		subscription.createParseredSubscription(subscription);
			
		List<String> urls = new ArrayList<>();
		List<String> urlsType = new ArrayList<>();
	    List<String> feedDescriptions = new ArrayList<>();
	      
		for (SingleSubscription s : subscription.getSubscriptionsList()) {
			for (int i = 0; i < s.getUrlParams().size(); i++) {
				String fullUrl = s.getFeedToRequest(i); 
				urls.add(fullUrl);
				feedDescriptions.add(s.getUrlParams().get(i));
				urlsType.add(s.getUrlType());
			}
	    }
	    
		Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el número del feed a consultar: " + "\n");
        for (int i = 0; i < urls.size(); i++) {
    		System.out.println("[" + i + "] " + feedDescriptions.get(i) + " - " + urls.get(i));
      	}
        
        int choice = scanner.nextInt();
        if (choice < 0 || choice >= urls.size()) {
    		System.out.println("Opción inválida.");
    		return;
        }
	    
        String siteName = feedDescriptions.get(choice);
        String url = urls.get(choice);
    	String urlType = urlsType.get(choice);
        httpRequester requester = new httpRequester();
        
    	Feed feed = new Feed(siteName);
    	GeneralParser parser;
    	String feedText;
    		
        if (urlType.equalsIgnoreCase("rss")) {
            feedText = requester.getFeedRss(url);
            parser = new RssParser();          
    	} else {
            feedText = requester.getFeedReedit(url);
            parser = new RedditParser();
        }
    	    
        if (args.length == 0) {
        	feed.createParseredFeed(feed, feedText, parser, null);
	        feed.prettyPrint();
		} else if (args.length == 1) {
            System.out.print("Ingrese el número de la heurística: "  + "\n");
            System.out.println("[0] RandomHeuristic");
            System.out.println("[1] QuickHeuristic");
      
            Heuristic heuristic;
            int seleccion = scanner.nextInt();
            
            if (seleccion == 0) {
                heuristic = new RandomHeuristic();
                System.out.println("Heurística seleccionada: RandomHeuristic");
            } else {
                heuristic = new QuickHeuristic();
                System.out.println("Heurística seleccionada: QuickHeuristic");
            }
            feed.createParseredFeed(feed, feedText, parser, heuristic);
    		
    	    for (int i = 0; i < feed.getNumberOfArticles(); i++) {
    	        Article article = feed.getArticle(i);
    	        article.prettyPrintHeuristic();
    	    }
	      
	    } else {
			printHelp();
		}
		scanner.close();	
    }
}

