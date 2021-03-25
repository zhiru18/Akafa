package controller;

import java.util.List;

import database.DataAccessException;
import database.PostDB;
import database.PostDBIF;
import model.Employee;
import model.Post;

/**
 * The PostController class contains all the methods regarding posts. 
 *
 * @author (Group 1)
 * @version (1)
 */

public class PostController {
	private PostDBIF postDB;
	
	public PostController() throws DataAccessException {
		postDB = new PostDB();	
	}
	
	/**
	 * This method calls the findAllPosts method on the PostDB class. 
	 *
	 * @throws DataAccessException
	 */
	public List<Post> findAllPosts() throws DataAccessException{
		return postDB.findAllPosts(true);
		
	}
	
	/**
	 * this method calls the updatePostCount method on the database
	 * @param employee
	 * @param post
	 * @throws DataAccessException
	 */
	public void updatePostCount(Employee employee, Post post) throws DataAccessException {
		postDB.updatePostCount(employee, post);
	}
	/**
	 * This method find a post form its Id
	 * @return a post.
	 */
	public Post findPostById(int postId) throws DataAccessException {
		return postDB.findPostById(postId, true);
	}
}
