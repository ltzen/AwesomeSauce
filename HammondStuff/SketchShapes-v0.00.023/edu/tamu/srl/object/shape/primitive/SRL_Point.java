package edu.tamu.srl.object.shape.primitive;

import java.awt.Point;
import java.util.ArrayList;

import edu.tamu.srl.object.shape.SRL_Shape;

/**
 * Point data class
 * @author hammond
 * @copyright Tracy Hammond, Sketch Recognition Lab, Texas A&M University
 */
public class SRL_Point extends SRL_Shape{

	/**
	 * Points can have pressure depending on the input device
	 */
	private int m_pressure = 1;

	/**
	 * Holds an history list of the x points 
	 * Purpose is so that we can redo and undo and go back to the original points
	 */
	private ArrayList<Double> m_xList = new ArrayList<Double>();

	/**
	 * Holds a history list of the y points 
	 * Purpose is so that we can redo and undo and go back to the original points
	 */
	private ArrayList<Double> m_yList = new ArrayList<Double>();
	
	/**
	 * A counter that keeps track of where you are in the history of points
	 */
	private int m_currentElement = -1;
	
	/**
	 * Points can have pressure depending on the input device
	 * @return the pressure of the point
	 */
	public int getPressure() {
		return m_pressure;
	}

	/**
	 * Points can have pressure depending on the input device
	 * @param pressure
	 */
	public void setPressure(int pressure) {
		m_pressure = pressure;
	}

	/**
	 * Updates the location of the point
	 * Also add this point to the history of the points 
	 * so this can be undone.
	 * @param x the new x location for the point
	 * @param y the new y location for the point
	 */
	  public void setP(double x, double y) {
	    m_xList.add(x);
	    m_yList.add(y);
	    m_currentElement = m_xList.size() - 1;
	  }
	
	/**
	 * Creates a point with the initial points at x,y
	 * @param x the initial x point
	 * @param y the initial y point
	 */
	public SRL_Point(double x, double y) {
		setP(x,y);
	    addInterpretation("Point", 1, 1);
	}

	/**
	 * Creates a point with the initial points at x,y
	 * @param x the initial x point
	 * @param y the initial y point
	 * @param time the time the point was made
	 */
	public SRL_Point(double x, double y, long time) {
		setP(x,y);
		setTime(time);
	    addInterpretation("Point", 1, 1);	    
	}
	
	 /**
	   * Return the distance from point rp to this point.
	   * @param rp the other point
	   * @return the distance
	   */
	  public double distance(SRL_Point rp) {
	    return distance(rp.getX(), rp.getY());
	  }
	  
	  /**
	   * Return the distance from the point specified by (x,y) to this point
	   * @param x the x value of the other point
	   * @param y the y value of the other point
	   * @return the distance
	   */
	  public double distance(double x, double y) {
	    double xdiff = x - getX();
	    double ydiff = y - getY();
	    return Math.sqrt(xdiff*xdiff + ydiff*ydiff);
	  }

	  /**
	   * Return the distance from the point specified by (x,y) to this point
	   * @param x the x value of the other point
	   * @param y the y value of the other point
	   * @return the distance
	   */
	  public static double distance(double x1, double y1 , double x2, double y2) {
	    double xdiff = x1 -x2;
	    double ydiff = y1 - y2;
	    return Math.sqrt(xdiff*xdiff + ydiff*ydiff);
	  }
  
	  
	/**
	 * Delete the entire point history and 
	 * use these values as the starting point
	 * @param x new initial x location
	 * @param y new initial y location 
	 */
	public void setOrigP(double x, double y) {
	  m_xList = new ArrayList<Double>();
	  m_yList = new ArrayList<Double>();
	  setP(x, y);
	}
	
	/**
	 * Remove last point update
	 * If there is only one x,y value in the history,
	 * then it does nothing
	 * Returns the updated shape (this)
	 */
	public SRL_Point undoLastChange() {
	  if (m_xList.size() < 2) { return this; }
	  if (m_yList.size() < 2) { return this; }
	  m_xList.remove(m_xList.size() - 1);
	  m_yList.remove(m_yList.size() - 1);
	  m_currentElement -= 1;
	  return this;
	}
	
	/**
	 * Get the original value of the point
	 * @return a point where getx and gety return the first values that were added to the history
	 */
	public SRL_Point goBackToInitial(){
		if(m_currentElement >= 0){
			m_currentElement = 0;
		}
		return this;
	}
	
	/**
	 * Get the x value for the first point in the history
	 * @return
	 */
	public double getInitialX(){
		if(m_xList.size() == 0){
			return Double.NaN;
		}
		return m_xList.get(0);
	}
	
	/**
	 * Get the y value for the first point in the history
	 * @return
	 */
	public double getInitialY(){
		if(m_yList.size() == 0){
			return Double.NaN;
		}
		return m_yList.get(0);
	}
	
	/**
	 * Get the current x value of the point
	 * @return current x value of the point
	 */
	public double getX(){
		return m_xList.get(m_currentElement);
	}

	/**
	 * Get the current y value of the point
	 * @return current y value of the point
	 */
	public double getY(){
		return m_yList.get(m_currentElement);
	}
	  
	/**
	 * Clone the point
	 * return an exact copy of this point
	 */
	@Override
	public SRL_Point clone() {
		
		ArrayList<Double> xlist = new ArrayList<Double>();
	    ArrayList<Double> ylist = new ArrayList<Double>();
	    for(int i = 0; i < m_xList.size(); i++){
	      xlist.add((double)m_xList.get(i));
	      ylist.add((double)m_yList.get(i));
	    }
	    SRL_Point p = new SRL_Point(getX(), getY());
	    p = (SRL_Point)super.clone(p);
	    p.setTime(getTime());
	    p.setPressure(getPressure());
	    p.setName(getName());
	    p.m_xList = xlist;
	    p.m_yList = ylist;
	    p.m_currentElement = m_currentElement;
	    p.addInterpretation("Point", 1, 1);
	    return p;
	  }
	
	/**
	 * Return an object drawable by AWT
	 * return awt point
	 */
	public Point getAWT(){
		return new Point((int)getX(),(int)getY());
	}

	@Override
	/**
	 * Just returns the x value with is obviously the same as the min
	 * return x value
	 */
	public double getMinX() {
		return getX();
	}

	@Override
	/**
	 * Just returns the y value with is obviously the same as the min
	 * return y value
	 */
	public double getMinY() {
		return getY();
	}

	@Override
	/**
	 * Just returns the x value with is obviously the same as the max
	 * return x value
	 */
	public double getMaxX() {
		return getX();
	}

	@Override
	/**
	 * Just returns the y value with is obviously the same as the max
	 * return y value
	 */
	public double getMaxY() {
		return getY();
	}
	
	/**
	 * Returns a string of [x,y,time]
	 * @return a string of [x,t,time]
	 */
	public String toString(){
		return "["+ getX() + "," + getY() + "," + getTime() + "]";
	}
	
}
