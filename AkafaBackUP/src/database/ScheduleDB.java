package database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.Period;
import model.Schedule;
import model.ScheduleType;

/**this class is handles database functionality for WeekSchedules. 
 * 
 * @author Group 1
 * @version 1
 */
public class ScheduleDB implements ScheduleDBIF {
	private Connection connection;
	private PeriodDBIF periodDB;
	
	private static final String INSERT_SCHEDULE = "insert into Schedule (startDate, endDate, ScheduleType) values(?, ?, ?)";
	private PreparedStatement insertSchedule;
	private static final String UPDATE_SCHEDULE = "update Schedule set startDate = ?, endDate = ? where id = ?";
	private PreparedStatement updateSchedule;
	private static final String FIND_ALL = "select id, startDate, endDate, ScheduleType from Schedule";
	private PreparedStatement findAll;
	private static final String FIND_SCHEDULE_BY_ID = FIND_ALL + " where id = ?";
	private PreparedStatement findScheduleById;
	private static final String FIND_SCHEDULES_BY_DATE = FIND_ALL + " where startdate between ? and ? or enddate between ? and ?";
	private PreparedStatement findSchedulesByDate;
	private static final String FIND_SCHEDULES_BY_DATE_AND_TYPE = FIND_ALL + " where (startdate between ? and ? or enddate between ? and ?) and ScheduleType = ?";
	private PreparedStatement findSchedulesByDateAndType;
	
	
	public ScheduleDB() throws DataAccessException {
		init();
	}
	private void init() throws DataAccessException {
		connection = DBConnection.getInstance().getConnection();
		try {
			periodDB = new PeriodDB();
			String generatedColumns[] = {"id"};
			insertSchedule = connection.prepareStatement(INSERT_SCHEDULE, generatedColumns);
			updateSchedule = connection.prepareStatement(UPDATE_SCHEDULE);
			findAll = connection.prepareStatement(FIND_ALL);
			findScheduleById = connection.prepareStatement(FIND_SCHEDULE_BY_ID);
			findSchedulesByDate = connection.prepareStatement(FIND_SCHEDULES_BY_DATE);
			findSchedulesByDateAndType = connection.prepareStatement(FIND_SCHEDULES_BY_DATE_AND_TYPE);
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new DataAccessException("Unable to initialise Prepared Statements", e);
		}
		
	}
	
	/**
	 * this method inserts Schedules into the database
	 * @param schedule the Schedule to be inserted into the database
	 */
	@Override
	public void insertSchedule(Schedule schedule) throws DataAccessException {
		try {
			insertSchedule.setDate(1, Date.valueOf(schedule.getStartDate()));
			insertSchedule.setDate(2, Date.valueOf(schedule.getEndDate()));
			insertSchedule.setString(3, String.valueOf(schedule.getScheduleType()));
			int id = DBConnection.getInstance().executeInsertWithIdentity(insertSchedule);
			schedule.setId(id);
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new DataAccessException("Could not insert Schedule", e);
		}
	}

	/**
	 * this method updates a schedule
	 * @param schedule the updated Schedule to be put into the database
	 * @return res returns the updated Schedule
	 */
	@Override
	public int updateSchedule(Schedule schedule) throws DataAccessException {
		int res = 0;
		try {
			updateSchedule.setDate(1, Date.valueOf(schedule.getStartDate()));
			updateSchedule.setDate(2, Date.valueOf(schedule.getEndDate()));
			updateSchedule.setInt(3, schedule.getId());
			res = updateSchedule.executeUpdate();
		} catch (SQLException e) {
			throw new DataAccessException("Could not update Schedule", e);
		}
		return res;
	}
	
	/**
	 * this method finds all Schedules
	 * @param fullAssociation
	 * @return returns a list of the found Schedules
	 */
	@Override
	public List<Schedule> findAll(boolean fullAssociation) throws DataAccessException {
		ResultSet resultSet = null;
		try {
			resultSet = findAll.executeQuery();
		} catch (SQLException e) {
			throw new DataAccessException("Could not find any Week Schedules", e);
		}
		return buildObjects(resultSet, fullAssociation);
	}

	/**
	 * this method finds a Schedule based on its id
	 * @param id 
	 * @param fullAssociation
	 * @return schedule the schedule that was found
	 */
	@Override
	public Schedule findScheduleById(int id, boolean fullAssociation) throws DataAccessException {
		ResultSet resultSet = null;
		Schedule schedule = null;
		try {
			findScheduleById.setInt(1, id);
			resultSet = findScheduleById.executeQuery();
			if(resultSet.next()) {
				schedule = buildObject(resultSet, fullAssociation);
			}
		} catch (SQLException e) {
			throw new DataAccessException("Could not find any Week Schedules by ID", e);
		}
		return schedule;
	}

	/**
	 * this method finds Schedules based on their startdate and enddate
	 * @param startDate
	 * @param endDate
	 * @param fullAssociation
	 * @return returns a list of the found Schedules
	 */
	@Override
	public List<Schedule> findSchedulesByDate(LocalDate startDate, LocalDate endDate, boolean fullAssociation) throws DataAccessException {
		ResultSet resultSet = null;
		try {
			findSchedulesByDate.setDate(1, Date.valueOf(startDate));
			findSchedulesByDate.setDate(2, Date.valueOf(endDate));
			findSchedulesByDate.setDate(3, Date.valueOf(startDate));
			findSchedulesByDate.setDate(4, Date.valueOf(endDate));
			resultSet = findSchedulesByDate.executeQuery();
		} catch (SQLException e) {
			throw new DataAccessException("Could not find any Week Schedules by Date", e);
		}
		return buildObjects(resultSet, fullAssociation);
	}
	
	/**
	 * this method finds a Schedule based on its startdate, enddate and type
	 * @param startDate
	 * @param endDate
	 * @param scheduleType the type of the Schedule 
	 * @param fullAssociation
	 * @return returns a list of the found Schedules
	 */
	@Override
	public List<Schedule> findSchedulesByDateAndType(LocalDate startDate, LocalDate endDate, ScheduleType scheduleType, boolean fullAssociation) throws DataAccessException {
		ResultSet resultSet = null;
		try {
			findSchedulesByDateAndType.setDate(1, Date.valueOf(startDate));
			findSchedulesByDateAndType.setDate(2, Date.valueOf(endDate));
			findSchedulesByDateAndType.setDate(3, Date.valueOf(startDate));
			findSchedulesByDateAndType.setDate(4, Date.valueOf(endDate));
			findSchedulesByDateAndType.setString(5, scheduleType.toString());
			resultSet = findSchedulesByDateAndType.executeQuery();
		} catch (SQLException e) {
			throw new DataAccessException("Could not find any Week Schedules by Date", e);
		}
		return buildObjects(resultSet, fullAssociation);
	}
	
		
	/**
	 * this method builds a Schedule
	 * @param resultSet
	 * @param fullAssociation
	 * @return schedule the Schedule object that was built from the information from the database
	 * @throws DataAccessException
	 */
	public Schedule buildObject(ResultSet resultSet, boolean fullAssociation) throws DataAccessException {
		Schedule schedule = new Schedule();
		try {
			schedule.setStartDate(resultSet.getDate("startDate").toLocalDate());
			schedule.setEndDate(resultSet.getDate("endDate").toLocalDate());
			schedule.setId(resultSet.getInt("id"));
			schedule.setScheduleType(ScheduleType.valueOf(resultSet.getString("ScheduleType")));
			if(fullAssociation) {
				schedule.setPeriods(periodDB.findPeriodsByScheduleId(schedule.getId(), fullAssociation));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException("Unable to build Schedules", e);
		}
		return schedule;
	}
	
	/**
	 * this method builds a list of Schedules
	 * @param resultSet
	 * @param fullAssociation
	 * @return res a list of the built Schedule objects
	 * @throws DataAccessException
	 */
	public List<Schedule> buildObjects(ResultSet resultSet, boolean fullAssociation) throws DataAccessException{
		List<Schedule> schedules = new ArrayList<>();
		try {
			while(resultSet.next()) {
				schedules.add(buildObject(resultSet, fullAssociation));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException("Unable to build objects", e);
		}
		return schedules;
	}
	
	/**
	 * this method calls findPeriodsByScheduleId on PeriodDB
	 */
	@Override
	public List<Period> findPeriodsByScheduleId(int scheduleId, boolean fullAssociation)
			throws DataAccessException {
		return periodDB.findPeriodsByScheduleId(scheduleId, fullAssociation);
	}

}
