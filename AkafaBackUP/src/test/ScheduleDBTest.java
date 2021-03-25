package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import database.DataAccessException;
import database.ScheduleDB;
import database.ScheduleDBIF;
import model.Period;
import model.Schedule;
import model.ScheduleType;

class ScheduleDBTest {
	private static ScheduleDBIF scheduleDB;
	private static Schedule schedule;
	private static LocalDate startDate, endDate, lateDate;

	@BeforeAll
	static void setUp() {
		try {
			scheduleDB = new ScheduleDB();
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		String date = "13-05-2019";
		startDate = LocalDate.parse(date, formatter);
		endDate = startDate.plusDays(5);
		lateDate = endDate.plusMonths(2);
		schedule = new Schedule(startDate, endDate, ScheduleType.WEEK);
	}
	
	@Test
	void insertScheduleTest() {
		//Arrange
		Schedule result = null;
		
		//Act
		try {
			scheduleDB.insertSchedule(schedule);
			result = scheduleDB.findScheduleById(schedule.getId(), false);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		
		//Assert
		assertEquals(schedule, result);
	}


	@Test
	void findAllTest() {
		//Arrange
		List<Schedule> result = new ArrayList<>();
		
		//Act
		try {
			result = scheduleDB.findAll(false);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		
		//Assert
		assertFalse(result.isEmpty());
	}

	@Test
	void findScheduleByIdTest() {
		//Arrange
		Schedule result = null;
		
		//Act
		try {
			result = scheduleDB.findScheduleById(1, true);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		
		//Assert
		assertEquals(1, result.getId());
	}

	@Test
	void findSchedulesByDateTest() {
		//Arrange
		List<Schedule> result = new ArrayList<>();
		
		//Act
		try {
			result = scheduleDB.findSchedulesByDate(startDate, lateDate, true);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		
		//Assert
		assertFalse(result.isEmpty());;
	}
	
	@Test
	void findSchedulesByDateAndType() {
		//Arrange
		List<Schedule> result = new ArrayList<>();
		
		//Act
		try {
			result = scheduleDB.findSchedulesByDateAndType(startDate, endDate, ScheduleType.WEEK, true);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Assert
		assertFalse(result.isEmpty());
	}


	@Test
	void findPeriodsByScheduleIdTest() {
		//Arrange
		List<Period> result = new ArrayList<>();
		
		//Act
		try {
			result = scheduleDB.findPeriodsByScheduleId(1, false);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		
		//Assert
		assertFalse(result.isEmpty());;
	}
	@Test
	void fullAssociationTest() {
		//Arrange
		boolean found = false;
		String name = null;
		List<Period> periods = null;
		try {
			periods = scheduleDB.findPeriodsByScheduleId(1, true);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		//Act
		for(int i = 0; i < periods.size() && !found; i++) {
			if(periods.get(i) != null) {
				name = periods.get(i).getEmployee().getFirstName();
				found = true;
			}
		}
		
		//Assert
		assertNotNull(name);
	}

}
