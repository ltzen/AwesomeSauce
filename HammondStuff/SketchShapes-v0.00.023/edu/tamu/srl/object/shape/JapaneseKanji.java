/**
 * 
 */
package edu.tamu.srl.object.shape;

import java.util.ArrayList;

import edu.tamu.srl.object.SRL_Object;

/**
 * @author hammond
 * @copyright Tracy Hammond, Sketch Recognition Lab, Texas A&M University
 */
public class JapaneseKanji extends SRL_Shape {

	public JapaneseKanji(ArrayList<SRL_Shape> shapes){
		for(SRL_Object o : shapes){
			addSubObject(o);
		}
	}
	
	/* (non-Javadoc)
	 * @see edu.tamu.srl.object.shape.SRL_Shape#getMinX()
	 */
	@Override
	public double getMinX() {
		double min = Double.POSITIVE_INFINITY;
		for(SRL_Shape shape : getSubShapes("shape")){
			min = Math.min(min, shape.getMinX());
		}
		return min;
	}

	/* (non-Javadoc)
	 * @see edu.tamu.srl.object.shape.SRL_Shape#getMinY()
	 */
	@Override
	public double getMinY() {
		double min = Double.POSITIVE_INFINITY;
		for(SRL_Shape shape : getSubShapes("shape")){
			min = Math.min(min, shape.getMinY());
		}
		return min;
	}

	/* (non-Javadoc)
	 * @see edu.tamu.srl.object.shape.SRL_Shape#getMaxX()
	 */
	@Override
	public double getMaxX() {
		double max = Double.NEGATIVE_INFINITY;
		for(SRL_Shape shape : getSubShapes("shape")){
			max = Math.max(max, shape.getMaxX());
		}
		return max;
	}

	/* (non-Javadoc)
	 * @see edu.tamu.srl.object.shape.SRL_Shape#getMaxY()
	 */
	@Override
	public double getMaxY() {
		double max = Double.NEGATIVE_INFINITY;
		for(SRL_Shape shape : getSubShapes("shape")){
			max = Math.max(max, shape.getMaxY());
		}
		return max;
	}

	/* (non-Javadoc)
	 * @see edu.tamu.srl.object.SRL_Object#clone()
	 */
	@Override
	public JapaneseKanji clone() {
		JapaneseKanji cloned = new JapaneseKanji(getSubShapes("shape"));
		super.clone(cloned);
		return cloned;
	}


}
