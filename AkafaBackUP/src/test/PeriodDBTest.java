package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import database.EmployeeDB;
import database.PeriodDB;
import database.PostDB;
import model.Employee;
import model.Period;
import model.Post;
import model.ScheduleType;


class PeriodDBTest {
	private static PeriodDB periodDB;
	private static EmployeeDB employeeDB;
	private static PostDB postDB;
	private static Period period1;
	private static Period period2;
	
	
	public PeriodDBTest() {
		
	}


	@BeforeAll
	public static void setUp() throws database.DataAccessException{	
		periodDB= new PeriodDB();
		employeeDB= new EmployeeDB();
		postDB= new PostDB();
		
		Employee e1= employeeDB.findEmployeeById(1, false);
		Employee e2= employeeDB.findEmployeeById(2, false);
		
		Post p1= postDB.findPostById(1, false);
		Post p2= postDB.findPostById(2, false);
		
		period1=new Period(e1,p1, LocalDate.now(), ScheduleType.WEEK);
		period2=new Period(e2,p2, LocalDate.now(), ScheduleType.WEEK);
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		String date1 = "19-05-2019";
		String date2 = "30-05-2019";
		String date3 = "13-05-2019";
		LocalDate endDate1 = LocalDate.parse(date1, formatter);
		LocalDate endDate2 = LocalDate.parse(date2, formatter);
		LocalDate startDate = LocalDate.parse(date3, formatter);
		
		period1.setStartDate(startDate);
		period1.setEndDate(endDate1, null);
		period2.setStartDate(startDate);
		period2.setEndDate(endDate2, null);
		periodDB.insertPeriod(period2, 1, ScheduleType.WEEK);		
	}
	
	@Test
	public void testInsertPeriod(){
		//Arrange
		Period p1=null;
		
		//Act		
		try {
			  periodDB.insertPeriod(period1, 1, ScheduleType.WEEK);
			  p1 = periodDB.findPeriodById(period1.getId(), false);
			} catch (database.DataAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	   //Assert
		 assertEquals(p1,period1);				
	}
	
	@Test
	public void testFindPeriodById() {
		//Arrange
		Period p2=null;
		
		//Act	
		try {
			p2 = periodDB.findPeriodById(period2.getId(), false);
		} catch (database.DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Assert
		 assertEquals(p2,period2);			
	}
	
	@Test
	public void testUpdatePeriod() {
		//Arrange
		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		 String date = "12-12-2019";
		 LocalDate endDate = LocalDate.parse(date, formatter);
		 period2.setEndDate(endDate, null);
		 Period p2=null;
		 
		//Act	
		 try {
			periodDB.updatePeriod(period2);
			p2 = periodDB.findPeriodById(period2.getId(), false);
		} catch (database.DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		//Assert
		 assertEquals(period2,p2);		 		 		
	}
	
	@Test
	public void testfindPeriodsByScheduleId() {
		//Arrange
		List<Period> periods = new ArrayList<>(); 
		
		//Act	
		try {
			periodDB.insertPeriod(period1, 1, ScheduleType.WEEK);
			periods = periodDB.findPeriodsByScheduleId(1, false);
		} catch (database.DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Assert
		 assertFalse(periods.isEmpty());	
	}

}
