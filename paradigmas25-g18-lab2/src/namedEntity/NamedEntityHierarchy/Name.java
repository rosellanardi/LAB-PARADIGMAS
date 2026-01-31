package entityHierarchy;
import namedEntity.NamedEntity;

public class Name extends Person {
    String canonicalForm;
    String origin;
    String alternativeForm;
    
	
	public Name(String name, String category, int frequency, Integer ID, String canonicalForm, String origin, String alternativeForm) {
		super(name, category, frequency, ID);
		this.canonicalForm = canonicalForm;
		this.origin = origin;
		this.alternativeForm = alternativeForm;
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
	
	public String getAlternativeForm(String alternativeForm) {
		return alternativeForm;
	}

    public void setAlternativeForm(String alternativeForm) {
		this.alternativeForm = alternativeForm;
	}

	public void prettyPrint(){
		System.out.println(this.toString());
	}
}
