/**
 * 
 */
package edu.tamu.srl.object.shape.primitive;

import edu.tamu.srl.object.SRL_Object;
import edu.tamu.srl.object.shape.SRL_Shape;


/**
 * @author hammond
 * @copyright Tracy Hammond, Sketch Recognition Lab, Texas A&M University
 */
public class SRL_Rectangle extends SRL_Shape {

	private SRL_Point m_topLeftCorner = new SRL_Point(0,0);
	private SRL_Point m_bottomRightCorner = new SRL_Point(0,0);
	
	/**
	 * Constructor takes two points, and constructs a horizontal rectangle from this.
	 * @param topLeftCorner
	 * @param bottomRightCorner
	 */
	public SRL_Rectangle(SRL_Point topLeftCorner, SRL_Point bottomRightCorner){
		setTopLeftCorner(topLeftCorner);
		setBottomRightCorner(bottomRightCorner);
	}

	/**
	 * Gets the bottom right corner
	 * @return
	 */
	public SRL_Point getBottomRightCorner() {
		return m_bottomRightCorner;
	}

	/**
	 * Sets the bottomright corner
	 * @param bottomRightCorner
	 */
	public void setBottomRightCorner(SRL_Point bottomRightCorner) {
		m_bottomRightCorner = bottomRightCorner;
	}

	/* 
	 * Returns the minimum x. This works even if the topleft and bottomright are switched
	 * @see edu.tamu.srl.object.shape.SRL_Shape#getMinX()
	 */
	@Override
	public double getMinX() {
		return Math.min(m_topLeftCorner.getX(), m_bottomRightCorner.getX());
	}

	/* 
	 * Returns the minimum y. This works even if the topleft and bottomright are switched
	 * @see edu.tamu.srl.object.shape.SRL_Shape#getMinY()
	 */
	@Override
	public double getMinY() {
		return Math.min(m_topLeftCorner.getY(), m_bottomRightCorner.getY());
	}

	/* 
	 * Returns the maximum x. This works even if the topleft and bottomright are switched
	 * (non-Javadoc)
	 * @see edu.tamu.srl.object.shape.SRL_Shape#getMaxX()
	 */
	@Override
	public double getMaxX() {
		return Math.max(m_topLeftCorner.getX(), m_bottomRightCorner.getX());
	}

	/* (non-Javadoc)
     * Returns the maximum y. This works even if the topleft and bottomright are switched
	 * @see edu.tamu.srl.object.shape.SRL_Shape#getMaxY()
	 */
	@Override
	public double getMaxY() {
		return Math.max(m_topLeftCorner.getY(), m_bottomRightCorner.getY());
	}

	/* (non-Javadoc)
	 * @see edu.tamu.srl.object.SRL_Object#clone()
	 */
	@Override
	public SRL_Object clone() {
		return new SRL_Rectangle(m_topLeftCorner.clone(), m_bottomRightCorner.clone());
	}

	/**
	 * Gets the top left corner point
	 * @return
	 */
	public SRL_Point getTopLeftCorner() {
		return m_topLeftCorner;
	}

	/**
	 * Sets the top left corner
	 * @param topLeftCorner
	 */
	public void setTopLeftCorner(SRL_Point topLeftCorner) {
		m_topLeftCorner = topLeftCorner;
	}

}
