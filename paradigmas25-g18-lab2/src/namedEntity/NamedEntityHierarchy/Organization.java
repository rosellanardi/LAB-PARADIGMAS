package entityHierarchy;
import namedEntity.NamedEntity;

public class Organization extends NamedEntity {
	String canonicalForm;
	String typeOrganization;
	Integer membershipNumber;
	
	public Organization(String name, String category, int frequency, String canonicalForm, String typeOrganization, Integer membershipNumber){
		super(name, category, frequency);
		this.canonicalForm = canonicalForm;
		this.typeOrganization = typeOrganization;
		this.membershipNumber = membershipNumber;
	}

	public String getcanonicalForm(){
		return canonicalForm;
	}

	public void setcanonicalForm(String canonicalForm){
		this.canonicalForm = canonicalForm;
	}

	public String gettypeOrganization(){
		return typeOrganization;
	}

	public void settypeOrganization(String typeOrganization){
		this.typeOrganization = typeOrganization;
	}

	public int getmembershipNumber(){
		return membershipNumber;
	}

	public void setmembershipNumber(int membershipNumber){
		this.membershipNumber = membershipNumber;
	}

	public void prettyPrint(){
		System.out.println(this.toString());
	}
	
	
}



