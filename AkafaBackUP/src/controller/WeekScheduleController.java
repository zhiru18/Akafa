package controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import database.DBConnection;
import database.DataAccessException;
import database.ScheduleDB;
import database.ScheduleDBIF;
import gui.WeekScheduleGUI;
import model.Employee;
import model.HungarianAlgorithm;
import model.MatrixNotSquareException;
import model.Period;
import model.Post;
import model.Schedule;
import model.ScheduleType;
import model.SpecialNeeds;

/**This class manages WeekSchedules.
 * 
 * @author Group 1
 * @version 1
 */
public class WeekScheduleController {
	private ScheduleDBIF scheduleDB;
	private VacationScheduleController vacationScheduleController;
	private PeriodController periodController;
	private PostController postController;
	private EmployeeController employeeController;
	private List<Employee> employees;
	private List<Post> posts;
	
	public WeekScheduleController() throws DataAccessException {
		scheduleDB = new ScheduleDB();
		vacationScheduleController = new VacationScheduleController();
		periodController = new PeriodController();
		postController = new PostController();
		employeeController = new EmployeeController();
		employees = new ArrayList<>();
		posts = new ArrayList<>();
	}
	
	/**
	 * This method filters all employees and puts them into either the special or normal employees list. 
	 * @param employees employees is a List of the type Employee
	 * @return List<List<Employee>> returns both the normal and the special employees lists. 
	 */
	private List<List<Employee>> filterEmployees(List<Employee> employees){
		List<Employee> specialEmployees = new ArrayList<>();
		List<Employee> normalEmployees = new ArrayList<>();
		//The list that contains the EmployeeLists.
		List<List<Employee>> masterList = new ArrayList<>();
		for (Employee employee : employees) {
			if(employee.getSpecialNeeds() == SpecialNeeds.NONE || 
					employee.getSpecialNeeds() == SpecialNeeds.EVEN_WEEKENDS ||
					employee.getSpecialNeeds() == SpecialNeeds.ODD_WEEKENDS) {
				normalEmployees.add(employee);
				//Finds all employees with no special needs. 
			}else{					
					specialEmployees.add(employee);
			}
		}
		masterList.add(normalEmployees);
		masterList.add(specialEmployees);
		return masterList;
	}
	/**
	 * This method filters all posts and puts them in either the normal or the special posts list. 
	 * @param posts posts is a List of the type Post. 
	 * @return List<List<Post>> returns a list of both the special and normal posts. 
	 */
	private List<List<Post>> filterPosts(List<Post> posts){
		List<Post> specialPosts = new ArrayList<>();
		List<Post> normalPosts = new ArrayList<>();
		//The list that contains both Post lists. 
		List<List<Post>> masterList = new ArrayList<>();
		
		for (Post post : posts) {
			if(post.isSpecial()) {
				if(post.getPriority() < 4) {
					specialPosts.add(post);
				}
			}else {
				normalPosts.add(post);
			}
		}
		masterList.add(normalPosts);
		masterList.add(specialPosts);
		return masterList;
	}
	
	/**
     * This method creates a new weekSchedule
     * @return a new weekSchedule
	 * @throws DataAccessException 
	 * @throws MatrixNotSquareException 
     */
	public Schedule createWeekSchedule(LocalDate startDate) throws DataAccessException, MatrixNotSquareException, ArrayIndexOutOfBoundsException{
		Schedule weekSchedule = null;
		List<Schedule> schedules = scheduleDB.findSchedulesByDateAndType(startDate, startDate.plusDays(7), ScheduleType.WEEK, true);
		if(schedules.isEmpty()) {
			LocalDate endDate = startDate.plusDays(4);
			weekSchedule = new Schedule(startDate, endDate, ScheduleType.WEEK);
			scheduleDB.insertSchedule(weekSchedule);
			
			//Transaction to get all employees and posts.
			DBConnection.getInstance().startTransaction();
			employees = employeeController.findAllEmployees();
			posts = postController.findAllPosts();
			DBConnection.getInstance().commitTransaction();
			
			//Filtering all employees and posts into special and normal.
			List<List<Employee>>  masterEmployees = filterEmployees(employees);
			List<List<Post>> masterPosts = filterPosts(posts);
			
			List<Employee> specialEmployees = masterEmployees.get(1);
			employees = masterEmployees.get(0);
			List<Post> specialPosts = masterPosts.get(1);
			posts = masterPosts.get(0);
			
			//Removing employees on vacation before the automatic assignment.
			excludeVacation(startDate, endDate);
			
			//Assign people to special posts
			specialPosts = assignSpecial(specialPosts, weekSchedule, specialEmployees);
			
			//Move the special employees from the employees list to the specialEmployees list with the resizeEmployees method.
			specialEmployees.addAll(resizeEmployees()); 
			//Move the special posts from the posts list to the specialPosts list with the resizePosts method.
			specialPosts.addAll(resizePosts());
			
			//Creating the matrix with the posts and employees from the database.
			int[][] matrix = createMatrix();
			
			HungarianAlgorithm hungarian = new HungarianAlgorithm(matrix);
			
			int [][] assignments = hungarian.findAssignment();
			
			for(int i = 0 ; i < assignments.length ; i++) {
				Period period = periodController.createPeriod(employees.get(assignments[i][1]), posts.get(assignments[i][0]), startDate, weekSchedule.getId(), ScheduleType.WEEK);
				weekSchedule.addPeriod(period);
			}
			
			//Assigning special people to special posts after resizing.
			specialPosts = assignRest(specialPosts, weekSchedule, specialEmployees);
			
			//Inserting all periods into the database.
			DBConnection.getInstance().startTransaction();
			periodController.insertPeriods(weekSchedule.getPeriods(), weekSchedule.getId(), weekSchedule.getScheduleType());			
			for(Period period : weekSchedule.getPeriods()) {
				postController.updatePostCount(period.getEmployee(), period.getPost());
			}
			DBConnection.getInstance().commitTransaction();
		}else {
			weekSchedule = schedules.get(0);
		}
		return weekSchedule;
	}
	/**
	 * This method assigns the last posts or employees based on the ones left after findAssignment() has run.
	 * @param specialPosts a list of all special posts.
	 * @param weekSchedule the weekschedule we are creating.
	 * @param specialEmployees a list of special employees.
	 * @return specialPosts returns a modified specialPosts list.
	 * @throws DataAccessException
	 */
	private List<Post> assignRest(List<Post> specialPosts, Schedule weekSchedule, List<Employee> specialEmployees) throws DataAccessException {
		Employee employee = null;
		int value = 0;
		List<Post> deletePosts = new ArrayList<>();
		for(Post post : specialPosts) {
			for(Map.Entry<Employee, Integer> entry : post.getEmployees().entrySet()) {
				if(specialEmployees.contains(entry.getKey())) {
					if(entry.getValue() > value) {
						employee = entry.getKey();
						value = entry.getValue();								
					}
				}
			}
			if(employee != null) {
				createPeriod(employee, post, weekSchedule, specialEmployees, deletePosts);
			}
		}
		specialPosts.removeAll(deletePosts);
		return specialPosts;
	}
	/**
	 * This method assign all the special employees to the special posts. All the people who need to be assigned
	 * before the automatic assignment happens.
	 * @param specialPosts a list of all special posts.
	 * @param weekSchedule the weekschedule we are creating.
	 * @param specialEmployees a list of special employees.
	 * @return specialPosts returns a modified specialPosts list.
	 * @throws DataAccessException
	 */
	private List<Post> assignSpecial(List<Post> specialPosts, Schedule weekSchedule, List<Employee> specialEmployees) throws DataAccessException {
		Employee employee = null;
		int value = 0;
		boolean extraWeek = false;
		List<Post> deletePosts = new ArrayList<>();
		for(Post post : specialPosts) {
			value = 0;
			extraWeek = false;
			for(Map.Entry<Employee, Integer> entry : post.getEmployees().entrySet()) {
				if(post.getPriority() < 4) {
					if(post.getPriority() == 2 && entry.getValue() == 1 ) {
						employee = entry.getKey();
						value = entry.getValue();
						extraWeek = true;
					}else if(entry.getValue() > value && !extraWeek) {
						employee = entry.getKey();
						value = entry.getValue();								
					}
				}
			}
			if(employee != null) {
				createPeriod(employee, post, weekSchedule, specialEmployees, deletePosts);
			}
		}
		specialPosts.removeAll(deletePosts);
		return specialPosts;
	}
	/**
	 * This method creates a period, saves it on the weekSchedule and inserts it into the database.
	 * @param employee the employee to be assigned to this period.
	 * @param post the post the employee is assigned to in this period.
	 * @param weekSchedule the weekschedule we are creating.
	 * @param specialEmployees specialEmployees a list of special employees.
	 * @param deletePosts a list of the posts that need to be removed from specialPosts.
	 * @throws DataAccessException
	 */
	private void createPeriod(Employee employee, Post post, Schedule weekSchedule, List<Employee> specialEmployees, List<Post> deletePosts) throws DataAccessException {
		Period period = periodController.createPeriod(employee, post, weekSchedule.getStartDate(), weekSchedule.getId(), weekSchedule.getScheduleType());
		weekSchedule.addPeriod(period);
		employees.remove(employee);
		specialEmployees.remove(employee);
		deletePosts.add(post);
	}
	/**
	 * This method removes all the employees currently on vacation from the pool of employees to be assigned.
	 * @param startDate the start date for the schedule.
	 * @param endDate the end date for the schedule.
	 * @throws DataAccessException
	 */
	private void excludeVacation(LocalDate startDate, LocalDate endDate) throws DataAccessException {
		List<Employee> vacation = new ArrayList<>();
		for(Employee employee : employees) {
			if(isOnVacation(startDate, endDate, employee)) {
				vacation.add(employee);
			}
		}
		employees.removeAll(vacation);
	}
	/**
	 * This method check if a given employee is on vacation.
	 * @param startDate the start date for the schedule.
	 * @param endDate the end date for the schedule.
	 * @param employee the employee which is looked up.
	 * @return boolean true if the employee is on vacation.
	 * @throws DataAccessException
	 */
	private boolean isOnVacation(LocalDate startDate, LocalDate endDate, Employee employee) throws DataAccessException {
		List<Period> periods = vacationScheduleController.findPeriodsByDate(startDate, endDate);
		boolean result = false;
		for(Period period : periods) {
			if(employee.equals(period.getEmployee())) {
				result = true;
			}
		}
		return result;
	}
	/**
	 * This method creates the matrix for the automatic assignment.
	 * @return int [][] returns the matrix with the epcount values for all employees and posts.
	 */
	private int[][] createMatrix() {
		int[][] matrix = new int[posts.size()][employees.size()];
		for(int i = 0; i < posts.size(); i++) {
			for(int j = 0; j < employees.size(); j++) {
				if(posts.get(i).getEmployees().containsKey(employees.get(j))) {
					matrix[i][j] = posts.get(i).getEmployees().get(employees.get(j));
				}
				
			}
		}
		
		return matrix;
	}
	/**
	 * This method resizes the employees list, before the matrix is created if there are more employees than posts.
	 * @return List<Employee> returns a list of the removed employees.
	 */
	private List<Employee> resizeEmployees(){
		List <Employee> removedEmployees = new ArrayList<>();
		if(this.employees.size() > posts.size()) {
			for (int i = 0 ; i < employees.size() - posts.size() ; i++) {
				removedEmployees.add(employees.get(i));
			}
			
		}
		employees.removeAll(removedEmployees);
		return removedEmployees;
	}
	/**
	 * This method resizes the posts list, before the matrix is created if there are more posts than employees.
	 * @return list<Post> returns a list of the removed posts.
	 */
	private List<Post> resizePosts(){
		List <Post> removedPosts = new ArrayList<>();
		if(employees.size() < posts.size()) {
			for(int i = 0 ; i < posts.size() - employees.size() ; i++) {
				removedPosts.add(posts.get(i));
			}
		
		}
		posts.removeAll(removedPosts);
		return removedPosts;
	}
	
	/**
     * This method updates a weekSchedule
     * @throws DataAccessException
     * @return a the weekSchedule
     */
	public Schedule updateWeekSchedule(Schedule weekSchedule) throws DataAccessException {
		scheduleDB.updateSchedule(weekSchedule);
		return weekSchedule;
	}
	
	/**
     * This method calls the findAll method in the weekScheduleDB .
     * @return a weekSchedule list.
	 * @throws DataAccessException 
     */
	public List<Schedule> findAllWeekSchedules() throws DataAccessException{
		return new ArrayList<Schedule>(scheduleDB.findAll(false));
	}
	
	/**
     * This method calls the findScheduleById method in the weekScheduleDB .
     * @param id is the Id of the weekSchedule.
     * @return a weekSchedule 
	 * @throws DataAccessException 
     */
	public Schedule findScheduleById(int id) throws DataAccessException {
		return scheduleDB.findScheduleById(id, true);
	}
	
	/**
     * This method calls the findScheduleByDate method in the weekScheduleDB .
     * @param date is the start date of the weekSchedule.
     * @return a weekSchedule list.
	 * @throws DataAccessException 
     */
	public List<Schedule> findScheduleByDate(LocalDate startDate, LocalDate endDate) throws DataAccessException{
		return new ArrayList<Schedule>(scheduleDB.findSchedulesByDate(startDate, endDate, false));
	}
	/**
	 * 
	 * @param id schedule id, the id of the schedule which periods we are finding.
	 * @param fullAssociation a boolean for use at the database layer.
	 * @return List<Period> returns a list of periods.
	 * @throws DataAccessException
	 */
	public List<Period> findPeriodsByScheduleId(int id, boolean fullAssociation) throws DataAccessException {
		return periodController.findPeriodsByScheduleId(id, fullAssociation);
	}
}
