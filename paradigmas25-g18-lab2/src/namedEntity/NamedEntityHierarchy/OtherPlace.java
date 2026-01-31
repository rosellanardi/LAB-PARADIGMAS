package entityHierarchy;

public class OtherPlace extends Place {
	String comments;

	public OtherPlace(String name, String category, int frequency, String comments) {
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
		System.out.println(this.getName() + " " + this.getFrequency());
	}
	
	
}



