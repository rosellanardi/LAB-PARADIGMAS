import subscription.Subscription;
import subscription.SingleSubscription;
import parser.*;
import feed.Feed;
import feed.Article;
import httpRequest.httpRequester;
import parser.GeneralParser;
import parser.RssParser;
import parser.RedditParser;
import namedEntity.heuristic.*;
import namedEntity.*;
import sparkFeedFetcher.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

import scala.Tuple2;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.api.java.JavaSparkContext;

public class FeedReaderMain {

	private static void printHelp(){
		System.out.println("Please, call this program in correct way: FeedReader [-ne]");
	}
	
	public static void main(String[] args) {
		List<String> urls = new ArrayList<>();
	    List<Article> allArticles;
	    List<NamedEntity> allNamedEntities;
	    List<Tuple2<String, Integer>> countsNamedEntites;

		System.out.println("************* FeedReader version 1.0 *************");

		// INICIALIZAR SPARK

		SparkSession spark = SparkSession
                .builder()
                .appName("FeedFetcher")
                .master("local[*]") // Ejecuta en modo local usando todos los núcleos
                .getOrCreate();

		// PARSEAR LISTA DE SUBSCRIPTIONS

		GeneralParser subscriptionParser = new SubscriptionParser();
		Subscription subscription = (Subscription) subscriptionParser.parse("config/subscriptions.json");

		for (SingleSubscription s : subscription.getSubscriptionsList()) {
			for (int i = 0; i < s.getUlrParams().size(); i++) {
				String fullUrl = s.getFeedToRequest(i); 
				urls.add(fullUrl);
			}
	    }
	    
	    JavaSparkContext jsc = new JavaSparkContext(spark.sparkContext());
	    SparkFeedFetcher sparkFeedFetcher = new SparkFeedFetcher();

	    allArticles = sparkFeedFetcher.parallelizeFeeds(jsc, urls);

        if (args.length == 0) {
        	for (int i = 0; i < allArticles.size(); i++) {
        		Article article = allArticles.get(i);
        		article.prettyPrint();
        	}
		} else if (args.length == 1) {
			
			Scanner scanner = new Scanner(System.in);
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

            allNamedEntities = sparkFeedFetcher.parallelizeComputedEntities(jsc, heuristic, allArticles);
            countsNamedEntites = sparkFeedFetcher.parallelizeCountedEntities(jsc, allNamedEntities);

    	    for (Tuple2<?, ?> tuple : countsNamedEntites) {
            	System.out.println("[" + tuple._1() +"]" + ": " + "[" + tuple._2() +"]");
	        }
	        System.out.println("FIN CÓMPUTO DE HEURISTICAS \n");
	        scanner.close();
	    } else {
			printHelp();
		}
		spark.stop();	
    }
}

