package model;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public class HungarianAlgorithm {
	private int [][] matrix;
	private int[] zeroInRow, zeroInColumn, rowIsCovered, columnIsCovered, staredZeroesInRow;
	
	public HungarianAlgorithm(int [][] matrix) throws MatrixNotSquareException, ArrayIndexOutOfBoundsException {
		//Checks if the matrix is square
		if(matrix.length != matrix[0].length) {
			throw new MatrixNotSquareException("The Matrix is sadly not square");
		}
		this.matrix = matrix;
        zeroInRow = new int[matrix.length];       // zeroInRow & zeroInCol indicate the position
        zeroInColumn = new int[matrix[0].length];    // of the marked zeroes

        rowIsCovered = new int[matrix.length];      // indicates whether a row is covered
        columnIsCovered = new int[matrix[0].length];   // indicates whether a column is covered
        staredZeroesInRow = new int[matrix.length]; // storage for the 0*
        Arrays.fill(staredZeroesInRow, -1);
        Arrays.fill(zeroInRow, -1);
        Arrays.fill(zeroInColumn, -1);
		}
	
	/**
	 * This method finds the optimal assignment for the matrix. 
	 * @return optimalAssignment optimalAssignment is a matrix with positions of the optimal values. 
	 */
	public int[][] findAssignment(){
		step0();
		step1();
		step2();
		step3();
		 while (!allColumnsAreCovered()) {
	            int[] mainZero = step4();		// mainZero is an array where the first value is row and
	            								// the second value is column.
	            while (mainZero == null) {      // while no zeroes found in step4.
	                step7();
	                mainZero = step4();
	            }
	            if (zeroInRow[mainZero[0]] == -1) {
	                // there is no marked zero in the mainZero row
	                step6(mainZero);
	                step3();    // cover columns which contain a marked zero
	            } else {
	                // there is marked zero in the mainZero row
	            	step5(mainZero[0]);
	            	step7();
	            }
	        }
		 	//Creates a new matrix that is the same size as the original matrix. 
	        int[][] optimalAssignment = new int[matrix.length][];
	        for (int i = 0; i < zeroInColumn.length; i++) {
	            optimalAssignment[i] = new int[]{i, zeroInColumn[i]};
	        }
	        return optimalAssignment;
	    }
	
	/**
	 * This method checks if all columns are covered.  
	 * @return isCovered isCovered is true if all columns are covered. 
	 */
	private boolean allColumnsAreCovered() {
		boolean isCovered = true;
        for (int i : columnIsCovered) {
            if (i == 0) {
                isCovered = false;
            }
        }
        return isCovered;
    }
	
	/**
	 * Step 0: This method flips the algorithm so we find the biggest instead of the smallest value. 
	 */
	private void step0() {
		//Since we want the maximum, we first find the maximum value and then negate all elements.
				int maximumValue = 0;
				for(int i = 0; i < matrix.length; i++) {
					for(int j = 0; j < matrix[i].length; j++) {
						if(matrix[i][j] > maximumValue) {
							maximumValue = matrix[i][j];
						}
						matrix[i][j] *= -1;
					}
				}
				//To avoid negative elements we add maximumValue to all elements.
				for(int i = 0; i < matrix.length; i++) {
					for(int j = 0; j < matrix[i].length; j++) {
						matrix[i][j] += maximumValue;
					}
				}
	}
	/**
	 * Step 1: Subtracts row minimum, and column minimum. 
	 */
	private void step1() {
		// rows
        for (int i = 0; i < matrix.length; i++) {
            // find the minimum value of the current row
            int currentRowMinimum = Integer.MAX_VALUE;
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] < currentRowMinimum) {
                    currentRowMinimum = matrix[i][j];
                }
            }
            // subtract minimum value from each element of the current row
            for (int k = 0; k < matrix[i].length; k++) {
                matrix[i][k] -= currentRowMinimum;
            }
        }

        // columns
        for (int i = 0; i < matrix[0].length; i++) {
            // find the minimum value of the current column
            int currentColumnMinimum = Integer.MAX_VALUE;
            for (int j = 0; j < matrix.length; j++) {
                if (matrix[j][i] < currentColumnMinimum) {
                    currentColumnMinimum = matrix[j][i];
                }
            }
            // subtract minimum value from each element of the current column
            for (int k = 0; k < matrix.length; k++) {
                matrix[k][i] -= currentColumnMinimum;
            }
        }
	}
	
	/**
	 * Step 2: Marks all zeroes in the matrix
	 */
	private void step2() {
		int[] rowHasZero = new int[matrix.length];
        int[] columnHasZero = new int[matrix[0].length];
        
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                // mark if current value == 0 & there are no other marked zeroes in the same row or column
                if (matrix[i][j] == 0 && rowHasZero[i] == 0 && columnHasZero[j] == 0) {
                    rowHasZero[i] = 1;
                    columnHasZero[j] = 1;
                    zeroInRow[i] = j; // save the row-position of the zero
                    zeroInColumn[j] = i; // save the column-position of the zero
                }
            }
        }
	}
	
	/**
	 * Step 3: Cover all columns with a zero
	 */
	private void step3() {
		 for (int i = 0; i < zeroInColumn.length; i++) {
			 if(zeroInColumn[i] != -1) {
				 columnIsCovered[i] = 1;
			 }else {
				 columnIsCovered[i] = 0;
			 }
		 }
	}
	
	/**
	 * Step 4: Finds a zero that isn't covered, stars it and returns its position.
	 * @return result result is an int [] 
	 */
	 private int[] step4() {
	     int [] result = null;  
		 for (int i = 0; i < matrix.length && result == null; i++) {
	            if (rowIsCovered[i] == 0) {
	                for (int j = 0; j < matrix.length; j++) {
	                    if (matrix[i][j] == 0 && columnIsCovered[j] == 0) {
	                        staredZeroesInRow[i] = j; // mark as 0*
	                        result = new int[]{i, j};
	                    }
	                }
	            }
	        }
	        return result;
	    }

	 /**
	  * Step 5: Covers the row where mainZero is positioned and uncovers its column.
	  * @param mainZero mainZero is an Array with a position of a 0*. 
	  */
	 private void step5(int mainZero) {
		rowIsCovered[mainZero] = 1;
		columnIsCovered[zeroInRow[mainZero]] = 0;
	 }
	 
	 /**
	  * 
	  * Step 6: Takes all staredZeroes (0*) and turns them into marked.
	  * @param mainZero mainZero is an Array with a position of a 0*. 
	  */
	 private void step6(int[] mainZero) {
	        int i = mainZero[0];
	        int j = mainZero[1];

	        Set<int[]> zeroLocationSet = new LinkedHashSet<>();
	        //add mainZero[] to zeroSet
	        zeroLocationSet.add(mainZero);
	        boolean found = false;
	        do {
	            /* If there exists more than one zero in a row/column the first will be named zero0
	             * and the next will be zero1.
	             * Add location for zero to zeroLocationSet if
	             * there is a zero1 which is marked in the column of zero0
	             */
	            if (zeroInColumn[j] != -1) {
	                zeroLocationSet.add(new int[]{zeroInColumn[j], j});
	                found = true;
	            } else {
	                found = false;
	            }

	            // if zero1 is marked in the column of zero0 run the following
	            if (found) {
	            	// replace zero0 with the 0* in the row of zero1
		            i = zeroInColumn[j];
		            j = staredZeroesInRow[i];
		            // add the new zero0 to zeroLocationSet
		            if (j != -1) {
		                zeroLocationSet.add(new int[]{i, j});
		                found = true;
		            } else {
		                found = false;
		            }
	            }
	        } while (found); // as long as new marked zeroes are found

	        for (int[] zero : zeroLocationSet) {
	            // remove all marked zeroes in zeroLocationSet
	        	// If the second position is equal to the first position then zeros will be unassigned
	            if (zeroInColumn[zero[1]] == zero[0]) {
	                zeroInColumn[zero[1]] = -1;
	                zeroInRow[zero[0]] = -1;
	            }
	            // replace the 0* marks in zeroLocationSet with marked zeroes. 
	            if (staredZeroesInRow[zero[0]] == zero[1]) {
	                zeroInRow[zero[0]] = zero[1];
	                zeroInColumn[zero[1]] = zero[0];
	            }
	        }

	        // remove all 0* and all covered rows and columns. 
	        Arrays.fill(staredZeroesInRow, -1);
	        Arrays.fill(rowIsCovered, 0);
	        Arrays.fill(columnIsCovered, 0);
	    }
	 
	 /**
	  * Step 7: Finds the smallest uncovered value, adds it to all twice covered values and 
	  * subtracts it from uncovered values.
	  */
	 private void step7() {
	        // Find the smallest uncovered value in the matrix
	        int minimumUncoveredValue = Integer.MAX_VALUE;
	        for (int i = 0; i < matrix.length; i++) {
	            if (rowIsCovered[i] != 1) {
	                for (int j = 0; j < matrix[0].length; j++) {
		                if (columnIsCovered[j] == 0 && matrix[i][j] < minimumUncoveredValue) {
		                    minimumUncoveredValue = matrix[i][j];
		                }
		            }
	            }     
	        }

	        if (minimumUncoveredValue > 0) {
	            for (int i = 0; i < matrix.length; i++) {
	                for (int j = 0; j < matrix[0].length; j++) {
	                    if (rowIsCovered[i] == 1 && columnIsCovered[j] == 1) {
	                        // Add minimum to all twice-covered values
	                        matrix[i][j] += minimumUncoveredValue;
	                    } else if (rowIsCovered[i] == 0 && columnIsCovered[j] == 0) {
	                        // Subtract minimum from all uncovered values
	                        matrix[i][j] -= minimumUncoveredValue;
	                    }
	                }
	            }
	        }
	    }
}
