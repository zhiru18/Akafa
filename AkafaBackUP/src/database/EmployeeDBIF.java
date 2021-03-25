package database;

import java.util.List;

import model.Employee;

public interface EmployeeDBIF {
	
	public List<Employee> findAllEmployees() throws DataAccessException;
	public Employee findEmployeeById(int id, boolean fullAsociation) throws DataAccessException;
	public List<Employee> findEmployeesByPostId(int postId, boolean fullAssociation) throws DataAccessException;

}
