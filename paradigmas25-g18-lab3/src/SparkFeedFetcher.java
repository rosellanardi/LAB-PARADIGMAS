package sparkFeedFetcher;
import parser.*;
import subscription.Subscription;
import subscription.SingleSubscription;
import feed.Feed;
import feed.Article;
import namedEntity.NamedEntity;
import httpRequest.httpRequester;
import namedEntity.heuristic.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

import scala.Tuple2;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.api.java.JavaSparkContext;


public class SparkFeedFetcher {

	public SparkFeedFetcher(){};

	public List<Article> parallelizeFeeds (JavaSparkContext jsc, List<String> urls) {
		JavaRDD<String> urlsRDD = jsc.parallelize(urls);

		JavaRDD<Article> articlesRDD = urlsRDD.flatMap(url -> {
        	httpRequester requester = new httpRequester();
        	String feedText = requester.getFeedRss(url);
        	GeneralParser parser = new RssParser();          
        	Feed feed = (Feed) parser.parse(feedText);
        	if (feed == null){
        		return new ArrayList<Article>().iterator();
        	} else {
        		return feed.getArticleList().iterator();
        	}
		});
		List<Article> allArticles = articlesRDD.collect();
		return allArticles;
	}	

	public List<NamedEntity> parallelizeComputedEntities (JavaSparkContext jsc, 
													      Heuristic heuristic, 
													      List<Article> allArticles) {
		JavaRDD<Article> allArticlesRDD = jsc.parallelize(allArticles);

		JavaRDD<NamedEntity> namedEntitiesRDD = allArticlesRDD.flatMap(article -> {
        	article.computeNamedEntities(heuristic);
        	article.countersToNamedEntities();
        	return article.getNamedEntityList().iterator();
		});
		List<NamedEntity> allNamedEntities = namedEntitiesRDD.collect();
		return allNamedEntities;
	}

	public List<Tuple2<String, Integer>> parallelizeCountedEntities (JavaSparkContext jsc, 
		                                                             List<NamedEntity> allNamedEntities) {
		JavaRDD<NamedEntity> allNamedEntitiesRDD = jsc.parallelize(allNamedEntities);
		JavaPairRDD<String, Integer> mapNamedEntities = allNamedEntitiesRDD.mapToPair(namedEntity -> {
			return new Tuple2(namedEntity.getName(), namedEntity.getFrequency());
		});

		JavaPairRDD<String, Integer> countsNamedEntites = mapNamedEntities.reduceByKey((i1, i2) -> i1 + i2);
		List<Tuple2<String, Integer>> output = countsNamedEntites.collect();
		return output;
	}
}
