package entityHierarchy;
import namedEntity.NamedEntity;
import java.util.Date;

public class NamedEntityDate extends NamedEntity {
	Date canonicalForm;
	Boolean precise;

	public NamedEntityDate(String name, String category, int frequency, Date canonicalForm, Boolean precise) {
		super(name, category, frequency);
		this.canonicalForm = canonicalForm;
		this.precise = precise;
	}

	public Date getCanonicalForm(){
		return canonicalForm;
	}

	public void setCanonialForm(Date canonicalForm){
		this.canonicalForm = canonicalForm;
	}

	public boolean getPrecise(){
		return precise;
	}

	public void setPrecise(boolean precise){
		this.precise = precise;
	}

	public void prettyPrint(){
		System.out.println(this.getName() + " " + this.getFrequency());
	}
	
}



