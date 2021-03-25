package database;

import java.time.LocalDate;
import java.util.List;

import model.Period;
import model.Schedule;
import model.ScheduleType;

public interface ScheduleDBIF {
	
	public void insertSchedule(Schedule schedule) throws DataAccessException;
	public int updateSchedule(Schedule schedule) throws DataAccessException;
	public List<Schedule> findAll(boolean fullAssociation) throws DataAccessException;
	public Schedule findScheduleById(int id, boolean fullAssociation) throws DataAccessException;
	public List<Schedule> findSchedulesByDate(LocalDate startDate, LocalDate endDate, boolean fullAssociation) throws DataAccessException;
	public List<Period> findPeriodsByScheduleId(int scheduleId, boolean fullAssociation) throws DataAccessException;
	public List<Schedule> findSchedulesByDateAndType(LocalDate startDate, LocalDate endDate, ScheduleType scheduleType, boolean fullAssociation) throws DataAccessException;

}
