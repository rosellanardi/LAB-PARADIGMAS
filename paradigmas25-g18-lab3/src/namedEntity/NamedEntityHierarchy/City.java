package entityHierarchy;

public class City extends Place {
	String country;
	String capital;

	public City(String name, String category, int frequency, String country, String capital) {
		super(name, category, frequency);
		this.country = country;
		this.capital = capital;
	}

	public String getCountry(){
		return country;
	}

	public void setCountry(String country){
		this.country = country;
	}

	public String getCapital(){
		return capital;
	}

	public void setCapital(String capital){
		this.capital = capital;
	}

	public void prettyPrint(){
		System.out.println(this.getName() + " " + this.getFrequency());
	}
	
	
}



