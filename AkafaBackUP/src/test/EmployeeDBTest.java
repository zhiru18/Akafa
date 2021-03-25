package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import database.DataAccessException;
import database.EmployeeDB;
import model.Employee;

class EmployeeDBTest {
	EmployeeDB employeeDB;
	Employee employee1;

	@BeforeEach
	public void setUp() throws Exception {
		employeeDB = new EmployeeDB();
		employee1 = new Employee("Heidi", "Kim", "Thomsen");
	}

	@Test
	public void findEmployeeByIdTest() {
		Employee employee = null;
		int id = 0;
		try {
			employee = employeeDB.findEmployeeById(1, false);
			id = employee.getId();
	    } catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    assertEquals(1,id);
	}
	
	@Test
	public void findAllEmployeesTest() {
		List<Employee> employees = new ArrayList<>();
		try {
			employees = employeeDB.findAllEmployees();
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertFalse(employees.isEmpty());
	}
}
