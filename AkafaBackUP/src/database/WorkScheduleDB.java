package database;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.List;

import model.WorkSchedule;

/**this class is handles database functionality for WorkSchedules. 
 * 
 * @author Group 1
 * @version 1
 */
public class WorkScheduleDB implements WorkScheduleDBIF {

	@Override
	public void insertWorkSchedule(WorkSchedule workSchedule) throws DataAccessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateWorkSchedule(WorkSchedule workSchedule) throws DataAccessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<WorkSchedule> findAll() throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WorkSchedule findScheduleById(int id) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WorkSchedule> findScheduleByDate(LocalDate date) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	public WorkSchedule buildObject(ResultSet resultSet) {
		//TODO
		return null;
	}
	public List<WorkSchedule> buildObjects(ResultSet resultSet){
		//TODO
		return null;
	}

}
