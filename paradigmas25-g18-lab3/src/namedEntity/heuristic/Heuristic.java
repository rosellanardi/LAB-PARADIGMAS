package namedEntity.heuristic;
import java.io.Serializable;
import java.util.Map;

public abstract class Heuristic implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private static Map<String, String> categoryMap = Map.of(
			"Microsft", "Organization", 
			"Apple", "Organization", 
			"Google", "Organization",
			"Musk", "Surname",
			"Biden", "Surname",
			"Trump", "Surname",
			"Messi", "Surname",
			"Federer", "Surname",
			"USA", "Country",
			"Russia", "Country"
			);
	
	private static Map<String, String> topicMap = Map.of(
			"Microsft", "International", 
			"Apple", "International", 
			"Google", "International",
			"Musk", "International",
			"Biden", "International",
			"Trump", "International",
			"Messi", "Soccer",
			"Federer", "Tennis",
			"USA", "International",
			"Russia", "International"
			);
	

	public String getCategory(String entity){
		return categoryMap.get(entity);
	}
	
	public String getTopic(String topic){
		return topicMap.get(topic);
	}
	
	
	public abstract boolean isEntity(String word);
		
}
