package topicMap;

import topicsHierarchy.*;

public class TopicMap {

	public static Topic matchTopicMap(String topic) {

		Topic returnTopic;

		switch (topic) {
		    case "Sports":
		        returnTopic = new Sports();
		        break;
		    case "Soccer":
		        returnTopic = new Soccer();
		        break;
		    case "Basketball":
		        returnTopic = new Basketball();
		        break;
		    case "Tennis":
		        returnTopic = new Tennis();
		        break;
		    case "F1":
		        returnTopic = new F1();
		        break;
		    case "Culture":
		        returnTopic = new Culture();
		        break;
		    case "Cinema":
		        returnTopic = new Cinema();
		        break;
		    case "Music":
		        returnTopic = new Music();
		        break;
		    case "OthersCulture":
		        returnTopic = new OthersCulture();
		        break;
		    case "Politics":
		        returnTopic = new Politics();
		        break;
		    case "International":
		        returnTopic = new International();
		        break;
		    case "National":
		        returnTopic = new National();
		        break;
		    case "OtherPolitics":
		        returnTopic = new OtherPolitics();
		        break;
		    default:
		        returnTopic = new OtherTopic();
		        break;
		}

		return returnTopic;
	}
}
