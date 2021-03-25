package controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import database.DataAccessException;
import database.ScheduleDB;
import database.ScheduleDBIF;
import model.Schedule;

/**This class manages WeekendSchedules
 * 
 * 
 * @author Group 1
 * @version 1
 */


public class WeekendScheduleController {
	private ScheduleDBIF scheduleDB;
	private Schedule currentSchedule;
	
	public WeekendScheduleController() throws DataAccessException{
		scheduleDB = new ScheduleDB();
		currentSchedule = new Schedule();
	}
	
	/**
     * This method creates a new weekendSchedule
     * @return a new weekendSchedule
     */
	public Schedule createWeekendSchedule() {
		Schedule weekendSchedule = new Schedule();
		return weekendSchedule;
	}
	
	/**
     * This method updates a weekendSchedule
     * @throws DataAccessException
     * @return a the weekendSchedule
     */
	public Schedule updateWeekendSchedule() throws DataAccessException {
		scheduleDB.updateSchedule(currentSchedule);
		return currentSchedule;
	}
	
	/**
     * This method calls the findAll method in the weekendScheduleDB .
     * @return a weekendSchedule list.
	 * @throws DataAccessException 
     */
	public List<Schedule> findAllWeekendSchedules() throws DataAccessException{
		return new ArrayList<Schedule>(scheduleDB.findAll(false));
	}
	
	/**
     * This method calls the findScheduleById method in the weekendScheduleDB .
     * @param id is the Id of the weekendSchedule.
     * @return a weekendSchedule 
	 * @throws DataAccessException 
     */
	public Schedule findScheduleById(int id) throws DataAccessException {
		return scheduleDB.findScheduleById(id, true);
	}
	
	/**
     * This method calls the findScheduleByDate method in the weekendScheduleDB .
     * @param date is the start date of the weekendSchedule.
     * @return a weekendSchedule list.
	 * @throws DataAccessException 
     */
	public List <Schedule> findSchedulesByDate(LocalDate startDate, LocalDate endDate) throws DataAccessException{
		return new ArrayList<Schedule>(scheduleDB.findSchedulesByDate(startDate, endDate, true));
	} 
}
