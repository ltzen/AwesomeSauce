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
public class Stat2DTest {

	/**
	 * Test method for {@link edu.tamu.srl.util.math.stat.Stat2D#computeCovarianceMatrix(double[][])}.
	 */
	@Test
	public void testComputeCovarianceMatrix() {
		double[][] values = new double[][]{{60, 32, 12}, {61, 72, 2}, {64, 14, 15}, {67, 53, 8},
				{68, 48, 5}, {62, 26, 24}, {61, 36, 18}, {64, 54, 5}, {63, 68, 12}, {63, 35, 14}};
		double[][] answers = new double[][]{{6.677777777777776, 5.622222222222222, -6.388888888888889},
				{5.622222222222222, 341.0666666666668, -87.44444444444443},	
				{-6.388888888888889, -87.44444444444443, 44.94444444444444}};
		assertTrue("Stat2D.computeCovarianceMatrix has error", Stat2D.equals(Stat2D.computeCovarianceMatrix(values) ,answers, .000001));
	}

	/**
	 * Test method for {@link edu.tamu.srl.util.math.stat.Stat2D#transpose(double[][])}.
	 */
	@Test
	public void testTranspose() {
		double[][] values1 = {{11.0, 1.0, 3.0}, {2.0, 4.0, 1.0},{3.0, 1.0, 2.0}};
		double[][] answer1 = {{11.0, 2.0, 3.0}, {1.0, 4.0, 1.0},{3.0, 1.0, 2.0}};
		assertTrue("Stat2D.transpose has error", Stat2D.equals(Stat2D.transpose(values1),answer1, .000001));
	}

	/**
	 * Test method for {@link edu.tamu.srl.util.math.stat.Stat2D#isSquare(double[][])}.
	 */
	@Test
	public void testIsSquare() {
		double[][] values1 = {{11.0, 1.0, 3.0}, {2.0, 4.0, 1.0},{3.0, 1.0, 2.0}};
		double[][] values2 = {{11.0, 1.0, 3.0}, {2.0, 4.0, 1.0},{3.0, 1.0, 2.0}, {25, 26, 27}};		
		double[][] values3 = {{11.0, 1.0, 3.0, 24}, {2.0, 4.0, 1.0,24},{3.0, 1.0, 2.0, 24}, {25, 26, 27, 28}};
		assertEquals("Stat2D.isSquare has error", Stat2D.isSquare(values1), true);
		assertEquals("Stat2D.isSquare has error", Stat2D.isSquare(values2), false);
		assertEquals("Stat2D.isSquare has error", Stat2D.isSquare(values3), true);
	}

	/**
	 * Test method for {@link edu.tamu.srl.util.math.stat.Stat2D#createSubMatrix(double[][], int, int)}.
	 */
	@Test
	public void testCreateSubMatrix() {
		double[][] values1 = {{11.0, 1.0, 3.0}, {2.0, 4.0, 1.0},{3.0, 1.0, 2.0}};
		double[][] answer1 = {{11, 1}, {2, 4.0}}; 
		assertTrue("Stat2D.cofactor has error", Stat2D.equals(Stat2D.createSubMatrix(values1,2,2),answer1, .000001));

	}

	/**
	 * Test method for {@link edu.tamu.srl.util.math.stat.Stat2D#determinant(double[][])}.
	 */
	@Test
	public void testDeterminant() {
		double[][] values1 = {{11.0, 1.0, 3.0}, {2.0, 4.0, 1.0},{3.0, 1.0, 2.0}};
		double[][] values3 = {{11.0, 1.0, 3.0, 24}, {2.0, 4.0, 1.0,24},{3.0, 1.0, 2.0, 24}, {25, 26, 27, 28}};
		
		assertEquals("Stat2D.determinant has error", Stat2D.determinant(values1), 46, .00000001);
		assertEquals("Stat2D.determinant has error", Stat2D.determinant(values3), -16832, .00000001);

	}

	/**
	 * Test method for {@link edu.tamu.srl.util.math.stat.Stat2D#cofactor(double[][])}.
	 */
	@Test
	public void testCofactor() {
		double[][] values1 = {{11.0, 1.0, 3.0}, {2.0, 4.0, 1.0},{3.0, 1.0, 2.0}};
		double[][] answer1 = {{7.0, -1.0, -10.0}, {1.0, 13.0, -8.0},{-11.0, -5.0, 42.0}}; 
		assertTrue("Stat2D.cofactor has error", Stat2D.equals(Stat2D.cofactor(values1),answer1, .000001));
	}

	
	/**
	 * Test method for {@link edu.tamu.srl.util.math.stat.Stat2D#equals(double[][], double)}.
	 */
	@Test
	public void testEquals() {
		double[][] values1 = {{11.0, 1.0, 3.0}, {2.0, 4.0, 1.0},{3.0, 1.0, 2.0}};
		double[][] values2 = {{11.0, 1.0, 3.0}, {2.0, 4.0, 1.0},{3.0, 1.0, 2.0}, {25, 26, 27}};		
		double[][] values3 = {{11.0, 1.0, 3.0, 24}, {2.0, 4.0, 1.0,24},{3.0, 1.0, 2.0, 24}, {25, 26, 27, 28}};
		double[][] values4 = Stat2D.transpose(values2);		
		assertTrue("Stat2D.equals has problem 1", Stat2D.equals(values1, values1, .000001));
		assertTrue("Stat2D.equals has problem 2", Stat2D.equals(values2, values2, .000001));
		assertTrue("Stat2D.equals has problem 3", Stat2D.equals(values3, values3, .000001));
		assertTrue("Stat2D.equals has problem 4", Stat2D.equals(values4, values4, .000001));
		assertFalse("Stat2D.equals has problem 5", Stat2D.equals(values1, values2, .000001));
		assertFalse("Stat2D.equals has problem 6", Stat2D.equals(values2, values4, .000001));
		assertFalse("Stat2D.equals has problem 7", Stat2D.equals(values3, values1, .000001));
		assertFalse("Stat2D.equals has problem 8", Stat2D.equals(values4, values2, .000001));
	}
	
	/**
	 * Test method for {@link edu.tamu.srl.util.math.stat.Stat2D#multiplyByConstant(double[][], double)}.
	 */
	@Test
	public void testMultiplyByConstant() {
		double[][] values1 = {{11.0, 1.0, 3.0}, {2.0, 4.0, 1.0},{3.0, 1.0, 2.0}};
		double[][] multvalue1 = Stat2D.multiplyByConstant(values1, 2);
		double[][] values2 = {{22,2,6},{4,8,2},{6,2,4}};
		assertTrue("Stat2D.multiplyByConstant has error", Stat2D.equals(values2, multvalue1, .00001)); 
	}

	/**
	 * Test method for {@link edu.tamu.srl.util.math.stat.Stat2D#inverse(double[][])}.
	 */
	@Test
	public void testInverse() {
		double[][] values1 = {{11.0, 1.0, 3.0}, {2.0, 4.0, 1.0},{3.0, 1.0, 2.0}};
		double[][] values3 = {{11.0, 1.0, 3.0, 24}, {2.0, 4.0, 1.0,24},{3.0, 1.0, 2.0, 24}, {25, 26, 27, 28}};
		double[][] answer1 = {{0.152174, 0.021739, -0.239130},
				{-0.021739, 0.282609, -0.108696}, 
				{-0.217391, -0.173913,  0.913043}};
		double[][] answer3 = {{0.14092205323193915,	0.035408745247148286, -0.1713403041825095, -0.0042775665399239545}, 
				{0.004515209125475285, 0.250712927756654, -0.2668726235741445, 0.009980988593155894}, 
				{-0.12737642585551331, -0.2832699619771863, 0.37072243346007605, 0.034220532319391636}, 
				{-0.0071886882129277565, 0.008733365019011407, 0.043310361216730035, -0.002732889733840304}}; 
		assertTrue("Stat2D.inverse has error 1", Stat2D.equals(Stat2D.inverse(values1), answer1, .000001)); 
		assertTrue("Stat2D.inverse has error 2", Stat2D.equals(Stat2D.inverse(values3), answer3, .000001)); 
	}

}
