package edu.tamu.srl.object.shape;

import java.util.ArrayList;

import edu.tamu.srl.object.SRL_Object;
import edu.tamu.srl.object.shape.primitive.SRL_Point;
import edu.tamu.srl.object.shape.primitive.SRL_Rectangle;
import edu.tamu.srl.object.shape.stroke.SRL_Stroke;


/**
 * Shape data class
 * @author hammond
 * @copyright Tracy Hammond, Sketch Recognition Lab, Texas A&M University
 */
public abstract class SRL_Shape extends SRL_Object{


	public SRL_Shape(){super();}
	
	/**
	 * Returns the center x of a shape.
	 * @return center x of a shape
	 */
	public double getCenterX(){
		return (getMinX() + getMaxX())/2.0;
	}
	
	/**
	 * Returns the center y of a shape
	 * @return center y of a shape
	 */
	public double getCenterY(){
		return (getMinY() + getMaxY())/2.0;
	}
	
	/**
	 * Get the bounding box of the stroke
	 * This returns an awt shape. 
	 * Use getBoundingSRLRectangle to get the SRL shape
	 * @return the bounding box of the stroke
	 */
	public SRL_Rectangle getBoundingBox() {
		return new SRL_Rectangle(new SRL_Point(getMinX(), getMinY()), 
				new SRL_Point(getMinX() + getWidth(), getMinY() + getHeight()));
	}
	
	public abstract double getMinX();
	public abstract double getMinY();
	public abstract double getMaxX();
	public abstract double getMaxY();
	
	/**
	 * Returns the width of the object
	 * @return the width of the object
	 */
	public double getWidth(){
		return getMaxX() - getMinX();
	}
	
	/**
	 * Returns the height of the object
	 * @return the height of the object
	 */
	public double getHeight(){
		return getMaxY() - getMinY();
	}
	
	/**
	 * Returns the length times the height
	 * See also getLengthOfDiagonal()
	 * return area of shape
	 */
	public double getArea(){
		return getHeight() * getWidth();
	}
	
	/**
	 * This returns the length of the diagonal of the bounding box. 
	 * This might be a better measure of perceptual size than area
	 * @return Euclidean distance of bounding box diagonal
	 */
	public double getLengthOfDiagonal(){
		return Math.sqrt(getHeight() * getHeight() + getWidth() * getWidth());
	}
	
	/**
	 * This function just returns the same thing as the length of the diagonal
	 * as it is a good measure of size.
	 * @return size of the object.
	 */
	public double getSize(){
		return getLengthOfDiagonal();
	}
	
	/**
	 * Returns the angle of the diagonal of the bounding box of the shape
	 * @return angle of the diagonal of the bounding box of the shape
	 */
	public double getBoundingBoxDiagonalAngle() {
		return Math.atan(getHeight()/getWidth());
	}

	
}
