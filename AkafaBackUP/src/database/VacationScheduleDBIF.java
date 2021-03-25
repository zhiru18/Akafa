package database;

import model.VacationSchedule;

public interface VacationScheduleDBIF {

	public void insertVacationSchedule(VacationSchedule vacationSchedule) throws DataAccessException;
	public void updateVacationSchedule(VacationSchedule vacationSchedule) throws DataAccessException;
	public VacationSchedule findVacationScheduleById(int id) throws DataAccessException;
}
