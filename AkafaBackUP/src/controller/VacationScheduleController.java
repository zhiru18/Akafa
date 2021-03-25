package controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import database.DataAccessException;
import database.ScheduleDB;
import database.ScheduleDBIF;
import model.Period;
import model.Schedule;
import model.ScheduleType;

/** This class handles VacationScheduling.
 * 
 * @author Group 1
 * @version 1
 */
public class VacationScheduleController {
	private ScheduleDBIF scheduleDB;
	private PeriodController periodController;
	
	public VacationScheduleController() throws DataAccessException {
		scheduleDB = new ScheduleDB();
		periodController = new PeriodController();
	}
	/**
	 * this method calls findSchedulesByDateAndType on ScheduleDB which finds a schedule from the database
	 * @param startDate
	 * @param endDate
	 * @param scheduleType
	 * @return the found schedules
	 * @throws DataAccessException
	 */
	
	public List<Schedule> findVacationScheduleByDate(LocalDate startDate, LocalDate endDate, ScheduleType scheduleType) throws DataAccessException {
		return scheduleDB.findSchedulesByDateAndType(startDate, endDate, scheduleType, true);
	}
	
	
	/**
	 * this method method finds the Periods associated with a vacationschedule based on the startdate and enddate
	 * @param startDate
	 * @param endDate
	 * @return periods returns the list of found periods
	 * @throws DataAccessException
	 */
	public List<Period> findPeriodsByDate(LocalDate startDate, LocalDate endDate) throws DataAccessException{
		List<Schedule>schedules = findVacationScheduleByDate(startDate, endDate, ScheduleType.VACATION);
		List<Period> periods = new ArrayList<>();
		for(Schedule schedule: schedules) {
			periods.addAll(periodController.findPeriodsByScheduleId(schedule.getId(), true));
		}
		return periods;
	}
	
}
