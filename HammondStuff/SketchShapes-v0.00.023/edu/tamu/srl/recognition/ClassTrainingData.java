/**
 * 
 */
package edu.tamu.srl.recognition;

import java.util.ArrayList;

import edu.tamu.srl.object.SRL_Sketch;

/**
 * @author hammond
 * @copyright Tracy Hammond, Sketch Recognition Lab, Texas A&M University
 */
public class ClassTrainingData {
	
	private String m_classname = "";
	private ArrayList<SRL_Sketch> m_sketches= null;
	
	/**
	 * Returns the name of the class for the supplied training data
	 * @return the classname
	 */
	public String getClassname() {
		return m_classname;
	}

	/**
	 * Returns the sketches that serve as the training data
	 * @return the sketches
	 */
	public ArrayList<SRL_Sketch> getSketches() {
		return m_sketches;
	}

	/**
	 * Creates a class that holds training data and the classname
	 * @param classname name of the class
	 * @param sketches list of sketches that serve as the training data
	 */
	public ClassTrainingData(String classname, ArrayList<SRL_Sketch> sketches){
		m_classname = classname;
		m_sketches = sketches;
	}
}
