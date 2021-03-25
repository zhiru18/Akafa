package database;

import java.util.List;

import model.Employee;
import model.Post;

public interface PostDBIF {
	
	public List<Post> findAllPosts(boolean fullAssociation) throws DataAccessException;
	public Post findPostById(int id, boolean fullasociation) throws DataAccessException;
	public void updatePostCount(Employee employee, Post post)throws DataAccessException;
}
