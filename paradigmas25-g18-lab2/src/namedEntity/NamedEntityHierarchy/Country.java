package entityHierarchy;

public class Country extends Place {
	String officialLanguage;
	Integer population;

	public Country(String name, String category, int frequency, String officialLanguage, Integer population) {
		super(name, category, frequency);
		this.officialLanguage = officialLanguage;
		this.population = population;

	}

	public String getOfficialLanguage(){
		return officialLanguage;
	}

	public void setOfficialLanguage(String officialLanguage){
		this.officialLanguage = officialLanguage;
	}

	public int getPopulation(){
		return population;
	}

	public void setPopulation(int population){
		this.population = population;
	}

	public void prettyPrint(){
		System.out.println(this.getName() + " " + this.getFrequency());
	}
	
	
}



