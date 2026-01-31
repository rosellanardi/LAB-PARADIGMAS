package entityHierarchy;
import namedEntity.NamedEntity;

public class Surname extends Person {
    String canonicalForm;
    String origin;
    
	
	public Surname(String name, String category, int frequency, Integer ID, String canonicalForm, String origin) {
		super(name, category, frequency, ID);
		this.canonicalForm = canonicalForm;
		this.origin = origin;
	}
	
	public String getCanonicalForm() {
		return canonicalForm;
	}

    public void setCanonicalForm(String canonicalForm) {
		this.canonicalForm = canonicalForm;
	}    
	
	public String getOrigin() {
		return origin;
	}

    public void setOrigin(String origin) {
		this.origin = origin;
	}

	public void prettyPrint(){
		System.out.println(this.toString());
	}
}
