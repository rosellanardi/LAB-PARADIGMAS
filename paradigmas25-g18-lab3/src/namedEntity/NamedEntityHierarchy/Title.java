package entityHierarchy;
import namedEntity.NamedEntity;

public class Title extends Person {
    String canonicalForm;
    Boolean profesional;
    
	
	public Title(String name, String category, int frequency, Integer ID, String canonicalForm, Boolean profesional) {
		super(name, category, frequency, ID);
		this.canonicalForm = canonicalForm;
		this.profesional = profesional;
	}
	
	public String getCanonicalForm() {
		return canonicalForm;
	}

    public void setCanonicalForm(String canonicalForm) {
		this.canonicalForm = canonicalForm;
	}    
	  
	public boolean getProfesional() {
		return profesional;
	}

    public void setProfesional(boolean profesional) {
		this.profesional = profesional;
	}
	
	public void prettyPrint(){
		System.out.println(this.toString());
	}
}
