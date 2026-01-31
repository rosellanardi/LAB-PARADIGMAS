package entityHierarchy;
import namedEntity.NamedEntity;

public class Place extends NamedEntity {

	public Place(String name, String category, int frequency) {
		super(name, category, frequency);
	}

	public void prettyPrint(){
		System.out.println(this.getName() + " " + this.getFrequency());
	}
	
	
}



