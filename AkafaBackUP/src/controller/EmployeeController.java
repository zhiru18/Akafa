package controller;

import java.util.List;

import database.DataAccessException;
import database.EmployeeDB;
import database.EmployeeDBIF;
import model.Employee;
/**
 * EmployeeController contains all of the methods regarding employee 
 * 
 * @author (Group 1)
 * @version (1)
 */
public class EmployeeController {
	private EmployeeDBIF employeeDB;
	
	public EmployeeController() throws DataAccessException {
		employeeDB = new EmployeeDB();
	}
	
	/**
     * This method calls the findAll method in the employeeDB .
     * @return a employee list.
	 * @throws DataAccessException 
     */
	public List<Employee> findAllEmployees() throws DataAccessException{
		return employeeDB.findAllEmployees();
	}
	
	/**
	 * The method finds an employee from it's id 
	 * @param(The id of the employee)
	 */
	public Employee findEmployeeById(int employeeId) throws DataAccessException {
		return employeeDB.findEmployeeById(employeeId, true);
	}
	/**
     * This method calls the findEmployeesByPostId method in the employeeDB .
     * @return a employee list.
	 * @throws DataAccessException 
     */
	public List<Employee> findEmployeesByPostId(int postId) throws DataAccessException {
		return employeeDB.findEmployeesByPostId(postId, true);
	}
}
