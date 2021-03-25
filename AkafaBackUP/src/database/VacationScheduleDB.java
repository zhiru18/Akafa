package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import database.DataAccessException;
import model.VacationSchedule;

/**
 * The VacationScheduleDB class contains all the functionality for the VacationScheduleDBIF.
 *
 * @author (Group 1)
 * @version (1)
 */

public class VacationScheduleDB implements VacationScheduleDBIF{
	private static final String FIND_VACATION_SCHEDULE_BY_ID = " where vacationSchedule.id = ?"; 
	private PreparedStatement findVacationScheduleById; 
	
	public VacationScheduleDB() throws database.DataAccessException {
		init();
	}

	private void init() throws database.DataAccessException {
		Connection con = DBConnection.getInstance().getConnection();
		try {
			findVacationScheduleById = con.prepareStatement(FIND_VACATION_SCHEDULE_BY_ID);
		}catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	/**
	 * This method creates a list of vacationschedules from the database by using the buildObject method. 
	 * 
	 * @param resultSet is ResultSet and gives the values from the database. 
	 * @param fullAssociation is a boolean and that controls the amount of information. 
	 */
	private List<VacationSchedule> buildObjects(ResultSet resultSet, boolean fullAssociation) throws DataAccessException {
		//TODO
		return null;
	}
	
	/**
	 * This method creates a vacationSchedule object from the database by using prepared statements. 
	 * 
	 * @param resultSet
	 * @param fullAssociation
	 * @return
	 * @throws DataAccessException
	 */
	private VacationSchedule buildObject(ResultSet resultSet, boolean fullAssociation) throws DataAccessException {
		//TODO
		return null;
	}
		

	@Override
	public void insertVacationSchedule(VacationSchedule vacationSchedule) throws DataAccessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateVacationSchedule(VacationSchedule vacationSchedule) throws DataAccessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public VacationSchedule findVacationScheduleById(int id) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

}
