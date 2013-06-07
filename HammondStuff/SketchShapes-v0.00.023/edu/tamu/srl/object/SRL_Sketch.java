/**
 * 
 */
package edu.tamu.srl.object;

import java.util.ArrayList;

/**
 * Collection of shapes (and possibly speech, edit, objects et al.?) 
 * @author hammond
 * @copyright Tracy Hammond, Sketch Recognition Lab, Texas A&M University
 */
public class SRL_Sketch extends SRL_Object{

	/**
	 * List of objects in the sketch
	 */
//	private ArrayList<SRL_Object> m_objects = new ArrayList<SRL_Object>();

	/**
	 * Gets the list of objects
	 * @return list of objects
	 */
//	public ArrayList<SRL_Object> getObjects() {
//		return m_objects;
//	}

	/**
	 * Sets the list of objects
	 * @param objects list of objects
	 */
//	public void setObjects(ArrayList<SRL_Object> objects) {
//		m_objects = objects;
//	}
	
	/**
	 * Adds an object
	 * @param object
	 */
//	public void addObject(SRL_Object object){
//		m_objects.add(object);
//	}

	/* (non-Javadoc)
	 * @see edu.tamu.srl.object.SRL_Object#clone()
	 */
	@Override
	public SRL_Object clone() {
		// TODO Auto-generated method stub
		return (SRL_Sketch)super.clone(new SRL_Sketch());
	}
	
}
