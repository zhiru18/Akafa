package database;

import java.util.List;

import model.Period;
import model.ScheduleType;

public interface PeriodDBIF {
	
	public void insertPeriod(Period period, int scheduleId, ScheduleType scheduleType) throws DataAccessException;
	public void updatePeriod(Period period) throws DataAccessException;
	public Period findPeriodById(int id, boolean fullAssociation) throws DataAccessException;
	public List<Period> findPeriodsByScheduleId(int id, boolean fullAssociation) throws DataAccessException;
	public void insertPeriods(List<Period> periods, int scheduleId, ScheduleType scheduleType) throws DataAccessException;

}
