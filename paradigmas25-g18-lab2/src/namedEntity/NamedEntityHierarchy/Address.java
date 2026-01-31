package entityHierarchy;

public class Address extends Place {
	String city;
	
	public Address(String name, String category, int frequency, String city) {
		super(name, category, frequency);
		this.city = city;
	}

	public String getCity(){
		return city;
	}

	public void setAddress(String city){
		this.city = city;
	}

	public void prettyPrint(){
		System.out.println(this.getName() + " " + this.getFrequency());
	}
	
	
}



