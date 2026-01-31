package entityHierarchy;
import namedEntity.NamedEntity;

public class Person extends NamedEntity {
    Integer ID;
	
	public Person(String name, String category, int frequency, Integer ID) {
		super(name, category, frequency);
		this.ID = ID;
	}
	
	public int getID() {
		return ID;
	}

    public void setID(int ID) {
		this.ID = ID;
	}    
	
	public void prettyPrint(){
		System.out.println(this.toString());
	}
}
