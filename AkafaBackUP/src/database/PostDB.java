package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Employee;
import model.Post;

/**
 * The PostDB class contains all the functionality for the PostDBIF.
 *
 * @author (Group 1)
 * @version (1)
 */

public class PostDB implements PostDBIF{
	private Connection connection;
	private static final String FIND_ALL_POSTS = "select post.id, pname, ppriority, isSpecial from post";
	private PreparedStatement findAllPosts; 
	private static final String FIND_POST_BY_ID = FIND_ALL_POSTS + " where id=?";
	private PreparedStatement findPostById;
	private static final String FIND_EPCOUNT = "select epcount from employeePost where employee_id = ? and post_id = ?";
	private PreparedStatement findEpcount;
	private static final String UPDATE_EPCOUNT = "update employeePost set epcount = ? where post_id = ? and employee_id = ?";
	private PreparedStatement updateEPCount;
	
	private EmployeeDBIF employeeDB;
	
	public PostDB() throws DataAccessException {
		init();
	}
	/**
	 * This method initializes the various preparedstatements,establishes the connection
	 * to the database.
	 * @throws SQLException 
	 * 
	 */
	private void init() throws DataAccessException {
		employeeDB = new EmployeeDB();
		connection = DBConnection.getInstance().getConnection();
		try {
			findAllPosts = connection.prepareStatement(FIND_ALL_POSTS);
			findPostById = connection.prepareStatement(FIND_POST_BY_ID);
			findEpcount = connection.prepareStatement(FIND_EPCOUNT);
			updateEPCount = connection.prepareStatement(UPDATE_EPCOUNT);
			String generatedColumns[] = {"id"};			
		}catch (SQLException e) {
			throw new DataAccessException("could not create Post DB", e);
		}	
	}
	
	/**
	 * This method creates a list of posts from the database by using the buildObject method. 
	 * 
	 * @param resultSet is ResultSet and gives the values from the database. 
	 * @param fullAssociation is a boolean and that controls the amount of information. 
	 */
	private List<Post> buildObjects(ResultSet resultSet, boolean fullAssociation) throws DataAccessException {
		List<Post> result = new ArrayList<>();
		Post post = new Post();
		try {
			while (resultSet.next()) {
				post = buildObject(resultSet, fullAssociation);
				result.add(post);
			}
		} catch (SQLException e) {
			throw new DataAccessException("unable to build post", e);
		}
		return result;	
	}
	
	/**
	 * This method creates a post object from the database by using prepared statements. 
	 * 
	 * @param resultSet is ResultSet and gives the values from the database. 
	 * @param fullAssociation is a boolean and that controls the amount of information. 
	 * @return
	 * @throws DataAccessException
	 */
	private Post buildObject(ResultSet resultSet, boolean fullAssociation) throws DataAccessException {
		Post post = new Post();
		List<Employee> postEmployees = new ArrayList<>();
		Map<Employee, Integer> employees = new HashMap<>();
		try {
				post.setId(resultSet.getInt("id"));
				post.setName(resultSet.getString("pname"));
				post.setPriority(resultSet.getInt("ppriority"));
				post.setSpecial(Boolean.valueOf(resultSet.getString("isSpecial")));
				if(fullAssociation) {
					postEmployees = employeeDB.findEmployeesByPostId(post.getId(), fullAssociation);
					for(Employee employee : postEmployees) {
						   int count = findEmployeePostCount(post.getId(), employee.getId());
						   employees.put(employee,count);
					}
			post.setEmployees(employees);
			}
		} catch (SQLException e) {
			throw new DataAccessException("unable to build post object", e);
		}
		return post;
	}

	/**
	 * This method finds the employee post count. 
	 * @param postId postId is the id on the post
	 * @param employeeId employeeId is the id on the employee
	 * @return result result is an int that is the employee post count. 
	 * @throws DataAccessException
	 */
	private int findEmployeePostCount(int postId, int employeeId) throws DataAccessException {
		ResultSet resultSet = null;
		int result = 0;
		try {
			findEpcount.setInt(1, employeeId);
			findEpcount.setInt(2, postId);
			resultSet = findEpcount.executeQuery();
			if(resultSet.next()) {
				result = resultSet.getInt("epcount");
			}
		} catch (SQLException e) {
			throw new DataAccessException("findEmployeePostCount failed", e);
		}
		return result;	
	}



	/**
	 * This method overrides the same method from PostDBIF and finds all the post from the database
	 * @return a post list.
	 */
	@Override
	public List<Post> findAllPosts(boolean fullAssociation) throws DataAccessException {
		ResultSet rs = null;
		try {
		     rs = findAllPosts.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace(); 
		}
		return buildObjects(rs, fullAssociation);
	}
	/**
	 * This method overrides the same method from PostDBIF and finds a post by id from the database
	 * @return a post.
	 */
	@Override
	public Post findPostById(int id,boolean fullAssociation) throws DataAccessException {
		ResultSet rs = null;
		Post post = null;
		try {
			findPostById.setInt(1, id);
			rs = this.findPostById.executeQuery();
			if(rs.next()) {
				post = buildObject(rs, fullAssociation); 
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return post;
	}
	
	/**
	 * this method updates the post count for all employees on a given Post.
	 * The employee that is currently on the post gets their counter set to 0 and then all counters are incremented with 1.
	 * @param employee
	 * @param post
	 * @throws DataAccessException
	 */
	@Override
	public void updatePostCount(Employee employee, Post post) throws DataAccessException {
		DBConnection.getInstance().startTransaction();
		post.getEmployees().forEach((k, v) -> {
			if(k.equals(employee)){
				if(!(post.getPriority() == 2 && v == 1)) {
					v = 0;
					post.getEmployees().put(k, v);
				}
			} 
			post.getEmployees().put(k, ++v);
			try {
				updateEPCount.setInt(1, v);
				updateEPCount.setInt(2, post.getId());
				updateEPCount.setInt(3, k.getId());
				updateEPCount.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
		DBConnection.getInstance().commitTransaction();
	}
}
