package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Employee;
import model.SpecialNeeds;

/**
 * The employeeDB class contains all of the functionality for EmployeeDBIF
 * 
 * @author (group 1)
 * @version (1)
 */
public class EmployeeDB implements EmployeeDBIF {
	private Connection connection;
	private static final String FIND_ALL_EMPLOYEES = "select employee.id, firstName, middleName, lastName, specialNeeds from Employee";
	private PreparedStatement findAllEmployees;
	private static final String FIND_EMPLOYEE_BY_ID = FIND_ALL_EMPLOYEES + " where employee.id = ?";
	private PreparedStatement findEmployeeById;
	private static final String FIND_EMPLOYEES_BY_POST_ID = "select employee.id, firstName, middleName, lastName, specialNeeds from Employee \r\n" + 
			"inner join EmployeePost \r\n" + 
			"on employee.id = EmployeePost.employee_id\r\n" + 
			"inner join Post\r\n" + 
			"on Post.id = EmployeePost.post_id\r\n" + 
			"where Post.id = ?";
private PreparedStatement findEmployeesByPostId;
	
	public EmployeeDB() throws DataAccessException {
		init();

	}

	private void init() throws DataAccessException {
		connection = DBConnection.getInstance().getConnection();
		try {
			findEmployeeById = connection.prepareStatement(FIND_EMPLOYEE_BY_ID);
			findAllEmployees = connection.prepareStatement(FIND_ALL_EMPLOYEES);
			findEmployeesByPostId = connection.prepareStatement(FIND_EMPLOYEES_BY_POST_ID);
		} catch (SQLException e) {
			throw new DataAccessException("Could not Initialise the prepared statemens", e);
		}

	}

/**
 * This method builds a list of employees from the database by using the method buildObject
 * @param resultSet
 * @param fullAssociation
 * @return
 * @throws DataAccessException
 */
	private List<Employee> buildObjects(ResultSet resultSet, boolean fullAssociation) throws DataAccessException{
		List<Employee> res = new ArrayList<>();
		Employee employee;
		try {
			while (resultSet.next()) {
				employee = buildObject(resultSet, fullAssociation);
				res.add(employee);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException("Database not accessible", e);
		}
		return res;
	}
	
	/**
	 * This method builds an employee object by using the preparedstatement
	 * @param (resultSet)
	 * @param (fullAssociation)
	 * @return
	 * @throws DataAccessException
	 */
	private Employee buildObject(ResultSet resultSet, boolean fullAssociation) throws DataAccessException {
		Employee employee = new Employee();
		try {
				employee.setId(resultSet.getInt("id"));
				employee.setFirstName(resultSet.getString("firstName"));
				employee.setMiddleName(resultSet.getString("middleName"));
				employee.setLastName(resultSet.getString("lastName"));
				employee.setSpecialNeeds(SpecialNeeds.valueOf(resultSet.getString("specialNeeds")));
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException("Database not accessible", e);
		}
		return employee;
	}
	
	/**
	 * This method creates a list of employees (By using the method buildObjects) to a specific post. 
	 * @param postId postId is the ID on the post. 
	 * @throws DataAccessException
	 */
	@Override
	public List<Employee> findEmployeesByPostId(int postId, boolean fullAssociation) throws DataAccessException {
		ResultSet resultSet = null;
		try {
			findEmployeesByPostId.setInt(1, postId);
			resultSet = findEmployeesByPostId.executeQuery();
		} catch (SQLException e) {
			throw new DataAccessException("findEmployees failed", e);
		}
		return buildObjects(resultSet, fullAssociation);
	}
	
	/**
	 * This method finds all employees and is implemented from the EmployeeDBIF
	 */
	@Override
	public List<Employee> findAllEmployees() throws DataAccessException {
		ResultSet rs;
		try {
			rs = this.findAllEmployees.executeQuery();
		}catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException("Error couldn't find all employees", e);
		}
		return buildObjects(rs, false);
	}
	
	/**
	 * The method finds an employee from it's id and it is implemented from EmployeeDBIF
	 * @param(The id of the employee)
	 */
	@Override
	public Employee findEmployeeById(int id, boolean fullAssociation) throws DataAccessException {
		ResultSet rs;
		Employee employee = null;
		try {
			findEmployeeById.setInt(1, id);
			rs = this.findEmployeeById.executeQuery();
			if(rs.next()) {
				employee = buildObject(rs, fullAssociation);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException("Error couldn't find the desired employee", e);
		}

		return employee;
	}

}
