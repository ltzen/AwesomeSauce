/**
 * 
 */
package edu.tamu.srl.util.math.stat;

import java.util.ArrayList;

import Jama.Matrix;

/**
 * @author hammond
 * @copyright Tracy Hammond, Sketch Recognition Lab, Texas A&M University
 */
public class Stat2D {

	/**
	 * Tests if two matrices are filled with equal values
	 * @param matrix1 first matrix
	 * @param matrix2 second matrix
	 * @return true if they are equal, else false
	 */
	public static boolean equals(double[][] matrix1, double[][]matrix2, double epsilon){
		if(matrix1.length != matrix2.length){
				return false;
		}
		if(matrix1[0].length != matrix2[0].length){
			return false;
		}
		for(int i = 0; i < matrix1.length; i++){
			for(int j = 0; j < matrix1[0].length; j++){
				if(Math.abs(matrix1[i][j] - matrix2[i][j]) > epsilon) {
						return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Get the the mean for each of the features from the list of examples
	 * @param listOfExamples a list of examples, with each example consisting of a number of features
	 * @return an array containing the mean for each of the features
	 */
	public static double[] getFeaturesMean(double[][] listOfExamples){
		double[][] listOfFeatures = transpose(listOfExamples);
		int count = 0;
		double[] mean = new double[listOfExamples[0].length];
		for(double[] feature : listOfFeatures){
			mean[count++] = Stat1D.getMean(feature);
		}
		return mean;
	}
	
	/**
	 * Computes the covariance matrix of a list of features
	 * @param listOfExamples array of features
	 * @return covariance matrix
	 */
	public static double [][] computeCovarianceMatrix(double[][] listOfExamples){
		double[][] listOfFeatures = transpose(listOfExamples);
		int count = 0;
		double[][] diff = new double[listOfExamples[0].length][listOfExamples.length];
		for(double[] feature : listOfFeatures){
			diff[count++] = Stat1D.getDifferenceFromMean(feature);
		}
		double covSum[][] = new double[diff.length][diff.length];
		for(int i = 0; i < diff.length; i++){
			for(int j = 0; j < diff.length; j++){
				covSum[i][j] = Stat1D.getSum(Stat1D.multiplyValues(diff[i], diff[j])) / (listOfExamples.length - 1);
			}
		}
		return covSum;
	}
	
	/**
	 * Computes the average of a list of matrices. 
	 * This might be used to create a simple common covariance matrix 
	 * (which assumes all standard deviations are equal which is probably not the case).
	 * @param matrices list of matrices
	 * @return matrix returning the average value for each index
	 */
	public static double[][] computeAverageMatrix(ArrayList<double[][]> matrices){
		double[][] averageMatrix = new double[matrices.get(0).length][matrices.get(0)[0].length];
		for(int i = 0; i < matrices.get(0).length; i++){
			for(int j = 0; j < matrices.get(0)[0].length; j++){
				double sum = 0;
				for(double[][] matrix : matrices){
					sum += matrix[i][j];
				}
				sum /= matrices.size();
				averageMatrix[i][j] = sum;
			}
		}
		return averageMatrix;
	}
	
	
	
	/**
	 * Create the transpose of a matrix 
	 * (essentially flips the rows and columns)
	 * @param matrix matrix to be transposed
	 * @return transposed matrix
	 */
	public static double[][] transpose(double[][] matrix) {
	    double[][] transposedMatrix = new double[matrix[0].length][matrix.length];
	    for (int i=0;i<matrix.length;i++) {
	        for (int j=0;j<matrix[0].length;j++) {
	            transposedMatrix[j][i] = matrix[i][j];
	        }
	    }
	    return transposedMatrix;
	}
	
	/**
	 * Checks if the number of columns are the same as the number of rows
	 * @return true if the matrix is square, else false
	 */
	public static boolean isSquare(double[][] matrix){
		return matrix[0].length == matrix.length;
	}
	
	
	/**
	 * Creates a submatrix with one row and column crossed out and the data removed.
	 * It returns a matrix with one fewer rows and one fewer columns
	 * @param matrix initial matrix
	 * @param excludeRow row to remove
	 * @param excludeCol column to remove
	 * @return
	 */
	public static double[][] createSubMatrix(double[][] matrix, int excludeRow, int excludeCol) {
		double[][] mat = new double[matrix[0].length - 1][matrix.length - 1];
	    int r = -1;
	    for (int i=0;i<matrix.length;i++) {
	        if (i==excludeRow)
	            continue;
	            r++;
	            int c = -1;
	        for (int j=0;j<matrix[0].length;j++) {
	            if (j==excludeCol)
	                continue;
	            mat[r][++c] = matrix[i][j];
	        }
	    }
	    return mat;
	}
	
	/**
	 * Calculates the determinant of the matrix
	 * @param matrix
	 * @return
	 */
	public static double determinantSlow(double[][] matrix) {
		if (!isSquare(matrix))
	        return Double.NaN;
	    if (matrix.length == 1){return matrix[0][0];}
	    if (matrix[0].length == 2) {
	        return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
	    }
	    double sum = 0.0;
	    for (int i=0; i< matrix.length; i++) {
	    	if (matrix.length > 11){
	    		System.out.print(matrix.length + " ");}
	    	//System.out.println(Stat2D.toString(createSubMatrix(matrix,0,i)));
	        sum += Math.pow(-1, i) * matrix[0][i] * determinant(createSubMatrix(matrix, 0, i));
	    }
	    return sum;
	}
	
	/**
	 * Calculates the determinant of the matrix
	 * @param matrix
	 * @return
	 */
	public static double determinant(double[][] matrix) {
		return (new Matrix(matrix)).det();
	}
	
	/**
	 * Creates the cofactor of a matrix
	 * @param matrix the initial matrix
	 * @return the cofactor of a matrix
	 */
	public static double[][] cofactor(double[][] matrix){
		if(!isSquare(matrix)){
			return null;
		}
	    double[][] newMatrix = new double[matrix[0].length][matrix.length];
	    for (int i=0;i<matrix[0].length;i++) {
	        for (int j=0; j<matrix.length;j++) {
	            newMatrix[i][j] = Math.pow(-1, i+j) * determinant(createSubMatrix(matrix, i, j));
	        }
	    }
	    return newMatrix;
	}
	
	/**
	 * Multiply the matrix by a constant.
	 * @param constant the constant to multiply a matrix by
	 * @return the new matrix
	 */
	public static double[][] multiplyByConstant(double[][] matrix, double constant){
		double[][] newMatrix = new double[matrix[0].length][matrix.length];
	    for (int i=0;i< matrix[0].length;i++) {
	        for (int j=0; j< matrix.length;j++) {
	        	newMatrix[i][j] = matrix[i][j]*constant;
	        }
	    }
		return newMatrix;
	}
	
	/**
	 * Computes the inverse of a matrix
	 * If matrix is not square this returns null
	 * @param matrix the initial matrix
	 * @return the inverted matrix
	 */
	public static double[][] inverse(double[][] matrix) {
		return (new Matrix(matrix)).inverse().getArray();
	}
	
	/**
	 * Computes the inverse of a matrix
	 * If matrix is not square this returns null
	 * @param matrix the initial matrix
	 * @return the inverted matrix
	 */
	public static double[][] inverseSlow(double[][] matrix) {
		if(!isSquare(matrix)) return null;
		if(matrix.length == 1) {
			return new double[][]{{1.0/matrix[0][0]}};
		}		
		//System.out.println(determinant(matrix));
	    return multiplyByConstant(transpose(cofactor(matrix)), 1.0/determinant(matrix));
	}
	
	/**
	 * Printout of the array
	 * @param array
	 * @return a string printing out the array.
	 */
	public static String toString(double[][] array){
		String s = "";
		for(int i = 0; i < array.length; i++){
			for(int j = 0; j < array[0].length; j++){
				s += array[i][j] + " \t";
			}
			s += "\n";
		}
		return s;
	}
	
}
