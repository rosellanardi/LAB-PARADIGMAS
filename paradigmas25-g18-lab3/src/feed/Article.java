package feed;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.Serializable;


import topicMap.TopicMap;
import subscription.Counter;
import entityHierarchy.*;
import namedEntity.NamedEntity;
import namedEntity.heuristic.Heuristic;
import namedEntity.heuristic.QuickHeuristic;


/*Esta clase modela el contenido de un articulo (ie, un item en el caso del rss feed) */

public class Article implements Serializable {
	private static final long serialVersionUID = 1L;
	private String title;
	private String text;
	private Date publicationDate;
	private String link;

	private Counter nameEntitiesCounter;
	private Counter personCounter;
	private Counter placeCounter;

	
	private List<NamedEntity> namedEntityList = new ArrayList<NamedEntity>();
	
	
	public Article(String title, String text, Date publicationDate, String link) {
		super();
		this.title = title;
		this.text = text;
		this.publicationDate = publicationDate;
		this.link = link;
		this.nameEntitiesCounter = new Counter();
		this.personCounter = new Counter();
		this.placeCounter = new Counter();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	
	public List<NamedEntity> getNamedEntityList() {
		return namedEntityList;
	}

	public void countersToNamedEntities() {
		List<NamedEntity> namedEntities = new ArrayList<>();
		int nameEntitiesValue = nameEntitiesCounter.getValue();
		int personValue = personCounter.getValue();
		int placeValue = placeCounter.getValue();

		if (nameEntitiesValue > 0){
			namedEntities.add(new NamedEntity("NameEntityCounter", "NameEntity", nameEntitiesValue));
		}
		if (personValue > 0){
			namedEntities.add(new Person("PersonCounter", "Person", personValue, null));
		}
		if (placeValue > 0){
			namedEntities.add(new Place("PlaceCounter", "Place", placeValue));
		} 
		namedEntityList.addAll(namedEntities);
	}


	@Override
	public String toString() {
		return "Article [title=" + title + ", text=" + text + ", publicationDate=" + publicationDate + ", link=" + link
				+ "]";
	}
	
	
	
	public NamedEntity getNamedEntity(String namedEntity){
		for (NamedEntity n: namedEntityList){
			if (n.getName().compareTo(namedEntity) == 0){				
				return n;
			}
		}
		return null;
	}
	
	public void computeNamedEntities(Heuristic h){
		String text = this.getTitle() + " " +  this.getText();  
			
		String charsToRemove = ".,;:()'!?\n";
		for (char c : charsToRemove.toCharArray()) {
			text = text.replace(String.valueOf(c), "");
		}
			
		for (String s: text.split(" ")) {
			if (h.isEntity(s)){
				NamedEntity ne = this.getNamedEntity(s);
				if (ne == null) {
					NamedEntity objectNamedEntity;
					String categoryEntity = h.getCategory(s);
					String topicEntity = h.getTopic(s);

					if (categoryEntity != null) {
						objectNamedEntity = matchCategoryMap(s, categoryEntity);
					} else {
						objectNamedEntity = matchCategoryMap(s, "Other");
					}

					if (topicEntity != null) {
						objectNamedEntity.setTopic(TopicMap.matchTopicMap(topicEntity));
					} else {
						objectNamedEntity.setTopic(TopicMap.matchTopicMap("Other"));
					}

					nameEntitiesCounter.increment();
					this.namedEntityList.add(objectNamedEntity);
				}else {
					ne.incFrequency();
				}
			}
		} 
	}

	
	public void prettyPrintHeuristic(){
		System.out.println("\n");
		System.out.println("Name Entities counter: " + nameEntitiesCounter.getValue());
		System.out.println("Person counter: " + personCounter.getValue());
		System.out.println("Place counter: " + placeCounter.getValue());
		for (int i = 0; i < namedEntityList.size(); i++) {
			NamedEntity n = namedEntityList.get(i);
			n.prettyPrint();
		}
	}

	public NamedEntity matchCategoryMap(String name, String category) {
	    NamedEntity namedEntity;

	    switch (category) {
	        case "Person":
	            namedEntity = new Person(name, category, 1, null);
	            personCounter.increment();
	            break;
	        case "Surname":
	            namedEntity = new Surname(name, category, 1, null, name, null);
	            personCounter.increment();
	            break;
	        case "Name":
	            namedEntity = new Name(name, category, 1, null, name, null, null);
	            personCounter.increment();
	            break;
	        case "Title":
	            namedEntity = new Title(name, category, 1, null, name, null);
	            personCounter.increment();
	            break;
	        case "Organization":
	            namedEntity = new Organization(name, category, 1, name, null, null);
	            break;
	        case "Product":
	            namedEntity = new Product(name, category, 1, null, null);
	            break;
	        case "Event":
	            namedEntity = new Event(name, category, 1, name, null, null);
	            break;
	        case "NamedEntityDate":
	            namedEntity = new NamedEntityDate(name, category, 1, null, null);
	            break;
	        case "Place":
	            namedEntity = new Place(name, category, 1);
	            placeCounter.increment();
	            break;
	        case "Country":
	            namedEntity = new Country(name, category, 1, null, null);
	            placeCounter.increment();
	            break;
	        case "City":
	            namedEntity = new City(name, category, 1, null, null);
	            placeCounter.increment();
	            break;
	        case "Address":
	            namedEntity = new Address(name, category, 1, null);
	            placeCounter.increment();
	            break;
	        case "OtherPlace":
	            namedEntity = new OtherPlace(name, category, 1, null);
	            placeCounter.increment();
	            break;
	        default:
	            namedEntity = new Other(name, category, 1, null);
	            break;
	    }

	    return namedEntity;
	}

	public void prettyPrint() {
		System.out.println("**********************************************************************************************");
		System.out.println("Title: " + this.getTitle());
		System.out.println("Publication Date: " + this.getPublicationDate());
		System.out.println("Link: " + this.getLink());
		System.out.println("Text: " + this.getText());
		System.out.println("**********************************************************************************************");
		
	}

	public static void main(String[] args) {
		  Article a = new Article("This Historically Black University Created Its Own Tech Intern Pipeline",
			  "A new program at Bowie State connects computing students directly with companies, bypassing an often harsh Silicon Valley vetting process",
			  new Date(),
			  "https://www.nytimes.com/2023/04/05/technology/bowie-hbcu-tech-intern-pipeline.html"
			  );
		 
		  a.prettyPrint();
	}
	
	
}



