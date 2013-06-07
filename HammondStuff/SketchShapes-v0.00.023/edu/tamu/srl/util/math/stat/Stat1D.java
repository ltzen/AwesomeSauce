/**
 * 
 */
package edu.tamu.srl.util.math.stat;

/**
 * @author hammond
 * @copyright Tracy Hammond, Sketch Recognition Lab, Texas A&M University
 */
public class Stat1D {
	
	/**
	 * Returns the sum of all of the values
	 * @param values list of values to be summed
	 * @return sum of the values
	 */
	public static double getSum(double[] values){
		double sum = 0;
		for(double value : values){
			sum += value;
		}
		return sum;
	}
	
	/**
	 * Returns the mean (average) of the list of numbers
	 * @param the list of values to get the mean of
	 * @return the mean of the values
	 */
	public static double getMean(double[] values){
		return getSum(values)/(1.0*values.length);
	}
	
	/**
	 * Computes the distance from the mean for each of the values
	 * @param the original data
	 * @return an array listing the difference from the mean
	 */
	public static double[] getDifferenceFromMean(double values[]){
		double mean = getMean(values);
		double[] diffFromMean = new double[values.length];
		for(int i = 0; i < values.length; i++){
			diffFromMean[i] = values[i] - mean;
		}
		return diffFromMean;
	}
	
	/**
	 * Square the values in the array
	 * @param values in the original array
	 * @return squared values
	 */
	public static double[] squareValues(double[] values){
		return multiplyValues(values, values);
	}
	
	/**
	 * Square the values in the array
	 * @param values in the original array
	 * @return squared values
	 */
	public static double[] multiplyValues(double[] values1, double[] values2){
		if(values1.length != values2.length) return null;
		double[] multipliedValues = new double[values1.length];
		for(int i = 0; i < values1.length; i++){
			multipliedValues[i] = values1[i]*values2[i];
		}
		return multipliedValues;
	}
	
	/**
	 * Computes the variance of the list of values
	 * Note that this divides by n-1, because we are working with sample data
	 * and not the real data
	 * @param values the original array
	 * @return the variance of the list
	 */
	public static double getSampleVariance(double[] values){
		return getSum(squareValues(getDifferenceFromMean(values)))/(values.length - 1.0);
	}
	
	/**
	 * Computes the standard deviation of the list of values
	 * Note that this divides by n-1, because we are working with sample data
	 * and not the real data
	 * @param values the original array
	 * @return the standard deviation of the list
	 */
	public static double getSampleStdDev(double[] values){
		return Math.sqrt(getSampleVariance(values));
	}
	
	/**
	 * String representing the array list
	 * @param values list of values
	 * @return string representing the array
	 */
	public static String toString(double[] values){
		String s = "";
		for(double value : values){
			s += value + " ";
		}
		s += "\n";
		return s;
	}

	/**
	 * String representing the array list
	 * @param values list of values
	 * @return string representing the array
	 */
	public static String toString(int[] values) {
		String s = "";
		for(int value : values){
			s += value + " ";
		}
		s += "\n";
		return s;
	}
}
