package entityHierarchy;
import namedEntity.NamedEntity;

public class Product extends NamedEntity {
	String commercial;
	String producer;
	
	public Product(String name, String category, int frequency, String producer, String commercial){
		super(name, category, frequency);
		this.producer = producer;
		this.commercial = commercial;
	}

	public String getproducer(){
		return producer;
	}

	public void stproducer(String producer){
		this.producer = producer;
	}

	public String getcommercial(){
		return commercial;
	}

	public void setcommercial(String commercial){
		this.commercial = commercial;
	}

	public void prettyPrint(){
		System.out.println(this.toString());
	}
	
	
}



