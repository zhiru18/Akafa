package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controller.WeekScheduleController;
import database.DataAccessException;
import model.MatrixNotSquareException;
import model.Period;
import model.Schedule;

class WeekScheduleControllerTest {
	WeekScheduleController weekScheduleController;
	
	@BeforeEach
	void setUp(){
		try {
			weekScheduleController = new WeekScheduleController();
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void CreateWeekScheduleTest() throws MatrixNotSquareException {
		//Arrange
		Schedule weekSchedule = null;
		int id = 0;
		int id2 = 0;
		int count = 0;
		List<Period> periods = new ArrayList<>();
		LocalDate startDate = LocalDate.now();
		//Act
		try {
			while(weekSchedule == null) {
				weekSchedule = weekScheduleController.createWeekSchedule(startDate.plusDays(count));
				count += 7;
			}
			id = weekSchedule.getId();
			id2 = weekScheduleController.findScheduleById(id).getId();
			periods = weekSchedule.getPeriods();
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		// Assert
		assertEquals(id, id2);
		assertFalse(periods.isEmpty());
	}
	
	

}
