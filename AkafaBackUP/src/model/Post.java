package model;

import java.util.HashMap;
import java.util.Map;

/**
 * The Post class contains all the information about Post and all the getters and setters for it
 * @author (group 1)
 * @version (1)
 */

public class Post {

	private int id;
	private String name;
	private int priority;
	private boolean isSpecial;
	private Map<Employee, Integer> employees;
	
	public Post() {
		employees = new HashMap<>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<Employee, Integer> getEmployees() {
		return employees;
	}

	public void setEmployees(Map<Employee, Integer> employees) {
		this.employees = employees;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public boolean isSpecial() {
		return isSpecial;
	}

	public void setSpecial(boolean isSpecial) {
		this.isSpecial = isSpecial;
	}
	
	public void setEmployeeAndCount(Employee employee, int count) {
		employees.put(employee, count);
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
		Post other = (Post) obj;
		if (id != other.id)
			return false;
		return true;
	}
		
}
