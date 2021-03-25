package controller;



import java.time.LocalDate;
import java.util.List;

import database.DataAccessException;
import database.PeriodDB;
import database.PeriodDBIF;
import model.Employee;
import model.Period;
import model.Post;
import model.ScheduleType;

/**
 * The PeriodController class contains all the methods regarding Periods. 
 *
 * @author (Group 1)
 * @version (1)
 */
public class PeriodController {

	private PeriodDBIF periodDB;
	private PostController postController;
	private EmployeeController employeeController;
	
	public PeriodController() throws DataAccessException {
		postController = new PostController();
		employeeController = new EmployeeController();
		periodDB = new PeriodDB();
	}
	
	/**
	 * this method calls findPeriodById on PeriodDB which finds periods by their id from the database
	 * @param id
	 * @return the found Period 
	 * @throws DataAccessException
	 */
	public Period findPeriodById(int id) throws DataAccessException {
		return periodDB.findPeriodById(id, true);
	}
	
	/***
	 * this method creates new periods
	 * @param employee
	 * @param post
	 * @param startDate 
	 * @return period returns the created Period
	 * @throws DataAccessException 
	 */
	public Period createPeriod(Employee employee, Post post, LocalDate startDate, int scheduleId, ScheduleType scheduleType) throws DataAccessException {
		Period period = new Period(employee,post, startDate, scheduleType);
		return period;
	}
	public void insertPeriod(Period period, int scheduleId, ScheduleType scheduleType) throws DataAccessException {
		periodDB.insertPeriod(period, scheduleId, scheduleType);
	}
	public void insertPeriods(List<Period> periods, int scheduleId, ScheduleType scheduleType) throws DataAccessException {
		periodDB.insertPeriods(periods, scheduleId, scheduleType);
	}
	
	/**
	 * this method updates periods
	 * @param employee
	 * @param post
	 * @param startDate
	 * @param endDate
	 * @throws DataAccessException
	 */
	public void updatePeriod(int periodId, int postId, int employeeId) throws DataAccessException {
		Period period = periodDB.findPeriodById(periodId, true);
		Post post = postController.findPostById(postId);
		Employee employee = employeeController.findEmployeeById(employeeId);
		period.setPost(post);
		period.setEmployee(employee);
		periodDB.updatePeriod(period);
	}
	/**
	 * this method calls findPeriodsByScheduleId on PeriodDB which finds a list of periods from the database
	 * @param scheduleId
	 * @param fullAssociation
	 * @return the found Periods
	 * @throws DataAccessException
	 */
	public List<Period> findPeriodsByScheduleId(int scheduleId, boolean fullAssociation) throws DataAccessException {
		return periodDB.findPeriodsByScheduleId(scheduleId, fullAssociation);
	}
	/**
	 * this method calls findAllPosts on PostController which finds a list of posts 
	 * @return the found Posts
	 * @throws DataAccessException
	 */
	public List<Post> findAllPosts() throws DataAccessException{
		return postController.findAllPosts();
	}
	/**
	 * this method calls findAllEmployees on EmployeeController which finds a list of Employees 
	 * @return the found Employees
	 * @throws DataAccessException
	 */
	public List<Employee> findAllEmployees() throws DataAccessException{
		return employeeController.findAllEmployees();
	}
	/**
	 * this method calls findEmployeesByPostId on EmployeeController which finds a list of Employees 
	 * @return the found Employees
	 * @throws DataAccessException
	 */
	public List<Employee> findEmployeesByPostId(int postId) throws DataAccessException {
		return employeeController.findEmployeesByPostId(postId);
	}

	/**
	 * this method create a period and insert the period 
	 * @param employeeId
	 * @param postId
	 * @param startDate
	 * @param scheduleId
	 * @param scheduleType
	 * @throws DataAccessException
	 */
	public Period createInsertPeriod(int employeeId, int postId,  LocalDate startDate,  int scheduleId,
			ScheduleType scheduleType) throws DataAccessException {
		Post post = postController.findPostById(postId);
		Employee employee = employeeController.findEmployeeById(employeeId);
		Period period = createPeriod(employee,post,startDate,scheduleId,scheduleType);
		insertPeriod(period, scheduleId, scheduleType);
		return period;
	} 
}
