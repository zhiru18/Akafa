package test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controller.EmployeeController;
import database.DataAccessException;
import database.PostDB;
import database.PostDBIF;
import model.Employee;
import model.Post;

class PostDBTest {
	PostDBIF postDB;
	EmployeeController employeeController;
	
	@BeforeEach
	void setUp() {
		try {
			postDB = new PostDB();
			employeeController = new EmployeeController();
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void findAllPostsTest() {
		//Arrange
		List<Post> posts = new ArrayList<>();
		
		//Act
		try {
			posts = postDB.findAllPosts(false);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		
		//Assert
		assertFalse(posts.isEmpty());
	}
	
	@Test
	void findPostByIdTest() {
		//Arrange
		Post post = null;
		
		//Act
		try {
			post = postDB.findPostById(4, false);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		
		//Assert
		assertEquals(4, post.getId());
	}
	@Test
	void updatePostCountTest() throws DataAccessException {
		//Arrange 
		Post post = postDB.findPostById(4, true);
		List<Employee> employees = employeeController.findAllEmployees();
		int value = 0;
		Employee employee = employees.get(3);
		Employee employee2 = employees.get(5);
		if(post.getEmployees().containsKey(employee)){
			value = post.getEmployees().get(employee2);
		}
		
		//Act
		postDB.updatePostCount(employee, post);
		int updatedValue = post.getEmployees().get(employee);
		int updatedValue2 = post.getEmployees().get(employee2);
		
		//Assert
		assertEquals(1, updatedValue);
		assertEquals(++value, updatedValue2);
	}

}
