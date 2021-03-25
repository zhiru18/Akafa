package database;

import java.time.LocalDate;
import java.util.List;

import model.WorkSchedule;

public interface WorkScheduleDBIF {
	
	public void insertWorkSchedule(WorkSchedule workSchedule) throws DataAccessException;
	public void updateWorkSchedule(WorkSchedule workSchedule) throws DataAccessException;
	public List<WorkSchedule> findAll() throws DataAccessException;
	public WorkSchedule findScheduleById(int id) throws DataAccessException;
	public List<WorkSchedule> findScheduleByDate(LocalDate date) throws DataAccessException;

}
