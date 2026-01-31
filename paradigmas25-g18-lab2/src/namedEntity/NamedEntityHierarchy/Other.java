package entityHierarchy;
import namedEntity.NamedEntity;


/*Esta clase modela la nocion de entidad nombrada*/

public class Other extends NamedEntity {
	String comments;
	
	public Other(String name, String category, int frequency, String comments){
		super(name, category, frequency);
		this.comments = comments;
	}

	public String getComments(){
		return comments;
	}

	public void setComments(String comments){
		this.comments = comments;
	}

	public void prettyPrint(){
		System.out.println(this.toString());
	}
	
	
}



