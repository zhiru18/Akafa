package model;

/**
 * The Employee class contains all the information about the employee and the getters and setters related to it
 * 
 * @author (group 1)
 * @version (1)
 */
public class Employee {
	private int id;
	private String firstName;
	private String middleName;
	private String lastName;
	private SpecialNeeds specialNeeds; 
	
	public Employee(String firstName, String middleName, String lastName) {
		setFirstName(firstName);
		setMiddleName(middleName);
		setLastName(lastName);
	}
	
	public Employee() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public SpecialNeeds getSpecialNeeds() {
		return specialNeeds;
	}
	
	public void setSpecialNeeds(SpecialNeeds specialNeeds) {
		this.specialNeeds = specialNeeds;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
