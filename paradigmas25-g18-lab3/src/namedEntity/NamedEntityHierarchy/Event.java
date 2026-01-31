package entityHierarchy;
import namedEntity.NamedEntity;
import java.util.Date;


public class Event extends NamedEntity {
	String canonicalForm;
	Date date;
	Boolean recurring;
	
	public Event(String name, String category, int frequency, String canonicalForm, Date date, Boolean recurring){
		super(name, category, frequency);
		this.canonicalForm = canonicalForm;
		this.recurring = recurring;
		this.date = date;
	}

	public String getcanonicalForm(){
		return canonicalForm;
	}

	public void setcanonicalForm(String canonicalForm){
		this.canonicalForm = canonicalForm;
	}

	public boolean getrecurring(){
		return recurring;
	}

	public void setrecurring(boolean recurring){
		this.recurring = recurring;
	}

	public Date getdate(){
		return date;
	}

	public void setdate(Date date){
		this.date = date;
	}

	public void prettyPrint(){
		System.out.println(this.toString());
	}
	
	
}



