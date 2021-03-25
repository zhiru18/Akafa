package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import model.HungarianAlgorithm;
import model.MatrixNotSquareException;

class HungarianAlgorithmTest {
	HungarianAlgorithm hungarianAlgorithm;
	
	@BeforeAll
	static void setUp() {
	}
	
	@Test
	void testFindAssignment() {
		//Arrange
	      int[][] dataMatrix = {
	    		
	    		  {46, 	16, 87, 76},
	    		  {79, 29, 39, 50},
	    		  {10, 75, 59, 34},
	    		  {27, 5, 19, 96}
	    	      };
	      
		int [][] copiedMatrix = {
			
				  {46, 	16, 87, 76},
	    		  {79, 29, 39, 50},
	    		  {10, 75, 59, 34},
	    		  {27, 5, 19, 96}
	    	      };
		try {
			hungarianAlgorithm = new HungarianAlgorithm(dataMatrix);
		} catch (MatrixNotSquareException e) {
			e.printStackTrace();
		}
		//Act
		int[][] assignment = hungarianAlgorithm.findAssignment();
		
		//Assert
		
	    assertEquals(79, copiedMatrix[assignment[0][1]][assignment[0][0]]);
	    assertEquals(75, copiedMatrix[assignment[1][1]][assignment[1][0]]);
	    assertEquals(87, copiedMatrix[assignment[2][1]][assignment[2][0]]);
	    assertEquals(96, copiedMatrix[assignment[3][1]][assignment[3][0]]);
	   }


}
