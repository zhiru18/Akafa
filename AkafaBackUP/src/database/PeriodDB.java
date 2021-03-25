package database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Employee;
import model.Period;
import model.Post;
import model.ScheduleType;


/**
 * The PeriodDB class contains all the functionality for the PeriodDBIF.
 *
 * @author (Group 1)
 * @version (1)
 */
public class PeriodDB implements PeriodDBIF{
	private EmployeeDBIF employeeDB;
	private PostDBIF postDB;
	private Connection connection;
	
	private static final String FIND_ALL_PERIODS="select id, startDate, endDate, post_id, employee_id, schedule_id from PeriodTable";
	private PreparedStatement findAllPeriods;
	private static final String FIND_BY_PERIOD_ID =FIND_ALL_PERIODS + " where id = ?";
	private PreparedStatement findPeriodById;
	private static final String INSERT_PERIOD="insert into PeriodTable (startDate, endDate, post_id, employee_id, schedule_id) values(?, ?, ?, ?, ?)";
	private PreparedStatement insertPeriod;
	private static final String UPDATE_PERIOD ="update PeriodTable set startDate = ?, endDate = ?, employee_id=?, post_id = ? where id = ?";
	private PreparedStatement updatePeriod;
	private static final String FIND_PERIODS_BY_SCHEDULE_ID = FIND_ALL_PERIODS + " where schedule_id = ?";
	private PreparedStatement findPeriodsByScheduleId;
	
	public PeriodDB() throws DataAccessException {
		init();
	}
	
	/**
	 * This method initializes the various preparedstatements, creates a new PeriodDB and establishes the connection
	 * to the database.
	 * @throws SQLException 
	 * 
	 */
	
	private void init() throws DataAccessException {
		connection = DBConnection.getInstance().getConnection();
		employeeDB=new EmployeeDB();
		postDB=new PostDB();
		try {
			//connection.setTransactionIsolation(connection.TRANSACTION_SERIALIZABLE);
			String generatedColumns[] = {"id"};
			findPeriodById=connection.prepareStatement(FIND_BY_PERIOD_ID);
			insertPeriod=connection.prepareStatement(INSERT_PERIOD, generatedColumns);
			updatePeriod=connection.prepareStatement(UPDATE_PERIOD);
			findPeriodsByScheduleId = connection.prepareStatement(FIND_PERIODS_BY_SCHEDULE_ID);
		} catch (SQLException e) {
			throw new DataAccessException("Could not create Period DB", e);
		}
	}
	
	/**
	 * This method overrides the same method from PeriodDBIF and inserts a period into the database
	 * 
	 * @param(the period you wish insert into the database)
	 */
	@Override
	public void insertPeriod(Period period, int scheduleId, ScheduleType scheduleType)throws DataAccessException{
		try {
			insertPeriod.setDate(1, Date.valueOf(period.getStartDate()));
			insertPeriod.setDate(2, Date.valueOf(period.getEndDate()));
			insertPeriod.setInt(3,period.getPost().getId());
			insertPeriod.setInt(4,period.getEmployee().getId());
			insertPeriod.setInt(5, scheduleId);	
			int id = DBConnection.getInstance().executeInsertWithIdentity(insertPeriod);
			period.setId(id);
		}catch (SQLException e) {
			throw new DataAccessException("Unable to insert period", e);
		}		
	}
	
	/**
	 * This method overrides the same method from PeriodDBIF and inserts a period into the database
	 * 
	 * @param(the period you wish update in the database)
	 */
	@Override
	public void updatePeriod(Period period) throws DataAccessException{
		try {
			updatePeriod.setDate(1,Date.valueOf(period.getStartDate()));
			updatePeriod.setDate(2,Date.valueOf(period.getEndDate()));
			updatePeriod.setInt(3,period.getEmployee().getId());
			updatePeriod.setInt(4,period.getPost().getId());
			updatePeriod.setInt(5, period.getId());
			updatePeriod.executeUpdate();
		}catch (SQLException e) {
			throw new DataAccessException("Unable to update period", e);
		}				
	}
	
	/**
	 * This method overrides the same method from PeriodDBIF and find a period by id of the period
	 * 
	 * @param(the period id)
	 */
	@Override
	public Period findPeriodById(int id, boolean fullAssociation) throws DataAccessException{
		ResultSet resultSet;
		Period res = null;
		try {
			findPeriodById.setInt(1, id);
			resultSet = this.findPeriodById.executeQuery();
			if(resultSet.next()) {
				res = buildObject(resultSet, fullAssociation);
			}
		} catch (SQLException e) {		
			throw new DataAccessException("Unable to find period", e);
		}
		return res;
	}
	
	/**
	 * This method builds and returns a period
	 * 
	 * @param(ResultSet resultSet, boolean:fullAssociation)
	 */
	private Period buildObject(ResultSet resultSet, boolean fullAssociation) throws DataAccessException {
		Period period = new Period();
		try {
			period.setId(resultSet.getInt("id"));
			period.setStartDate(resultSet.getDate("startDate").toLocalDate());
			period.setEndDate(resultSet.getDate("endDate").toLocalDate(), null);
			if(fullAssociation) {
				int employee_id = resultSet.getInt("employee_id");
				Employee employee = employeeDB.findEmployeeById(employee_id, false);
				period.setEmployee(employee);
				
				int post_id = resultSet.getInt("post_id");
				Post post = postDB.findPostById(post_id, false);
				period.setPost(post);
			}
		} catch (SQLException e) {
			throw new DataAccessException("Unable to build period", e);
		}
		return period;
	}
	/**
	 * This method builds a list of Periods and returns it.
	 * @param resultSet
	 * @param fullAssociation
	 * @return List<Period>
	 * @throws DataAccessException
	 */
	private List<Period> buildObjects(ResultSet resultSet, boolean fullAssociation) throws DataAccessException{
		List<Period> res = new ArrayList<>(); 
		try {
			while (resultSet.next()) {
				Period period = buildObject(resultSet, fullAssociation);
				res.add(period);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException("Could not build objects", e);
		}
		return res;
	}
	
	/**This methods returns a list of periods with a specific ScheduleId.
	 * @param id
	 * @param fullAssociation
	 * @return List<Period>
	 */
	@Override
	public List<Period> findPeriodsByScheduleId(int id, boolean fullAssociation) throws DataAccessException {
		ResultSet resultSet;
		List<Period> res = new ArrayList<>(); 
		try {
			findPeriodsByScheduleId.setInt(1, id);
			resultSet = findPeriodsByScheduleId.executeQuery();
			res = buildObjects(resultSet, true);
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new DataAccessException("Could not retrieve periods by ScheduleId", e);
		}
		return res;
	}

	@Override
	public void insertPeriods(List<Period> periods, int scheduleId, ScheduleType scheduleType) throws DataAccessException {
			DBConnection.getInstance().startTransaction();
			for(Period period : periods) {
				insertPeriod(period, scheduleId, scheduleType);
			}
			DBConnection.getInstance().commitTransaction();
		
		
	}
		
}


