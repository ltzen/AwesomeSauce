/**
 * 
 */
package edu.tamu.srl.util.math.stat;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author hammond
 * @copyright Tracy Hammond, Sketch Recognition Lab, Texas A&M University
 */
public class Stat1DTest {

	/**
	 * Test method for {@link edu.tamu.srl.util.math.stat.Stat1D#getSum(double[])}.
	 */
	@Test
	public void testGetSum() {
		double[] values = {1,2,3,4,5,6,7,8,9,10, .5};
		double sum = Stat1D.getSum(values);
		assertEquals("Stat1D.getSum has error", sum, 55.5, .00000001);
	}

	/**
	 * Test method for {@link edu.tamu.srl.util.math.stat.Stat1D#getMean(double[])}.
	 */
	@Test
	public void testGetMean() {
		double[] values = {1,2,3,4,5,6,7,8,9,10};
		double mean = Stat1D.getMean(values);
		assertEquals("Stat1D.getMean has error", mean, 5.5, .00000001);
	}

	/**
	 * Test method for {@link edu.tamu.srl.util.math.stat.Stat1D#getDifferenceFromMean(double[])}.
	 */
	@Test
	public void testGetDifferenceFromMean() {
		double[] values = {1,2,3,4,5,6,7,8,9,10};
		double[] diff = Stat1D.getDifferenceFromMean(values);
		assertEquals("Stat1D.getDifferencFromMean has error", diff[0], -4.5, .00000001);
		assertEquals("Stat1D.getDifferencFromMean has error", diff[9], 4.5, .00000001);
	}

	/**
	 * Test method for {@link edu.tamu.srl.util.math.stat.Stat1D#squareValues(double[])}.
	 */
	@Test
	public void testSquareValues() {
		double[] values = {1,2,3,4,5,6,7,8,9,10};
		double[] square = Stat1D.squareValues(values);
		assertEquals("Stat1D.squareValues has error", square[0], 1, .00000001);
		assertEquals("Stat1D.squareValues has error", square[9], 100, .00000001);
	}

	/**
	 * Test method for {@link edu.tamu.srl.util.math.stat.Stat1D#multiplyValues(double[], double[])}.
	 */
	@Test
	public void testMultiplyValues() {
		double[] values = {1,2,3,4,5,6,7,8,9,10};
		double[] multiply = Stat1D.multiplyValues(values, values);
		assertEquals("Stat1D.multiplyValues has error", multiply[0], 1, .00000001);
		assertEquals("Stat1D.multiplyValues has error", multiply[9], 100, .00000001);
	}

	/**
	 * Test method for {@link edu.tamu.srl.util.math.stat.Stat1D#getSampleVariance(double[])}.
	 */
	@Test
	public void testGetSampleVariance() {
		double[] values = {1,2,3,4,5,6,7,8,9,10};
		double variance = Stat1D.getSampleVariance(values);
		assertEquals("Stat1D.getSampleVariance has error", variance, 9.16666666666, .00000001);
	}

	/**
	 * Test method for {@link edu.tamu.srl.util.math.stat.Stat1D#getSampleStdDev(double[])}.
	 */
	@Test
	public void testGetSampleStdDev() {
		double[] values = {1,2,3,4,5,6,7,8,9,10};
		double stddev = Stat1D.getSampleStdDev(values);
		assertEquals("Stat1D.getSampleVariance has error", stddev, 3.027650354, .00000001);

	}

}
