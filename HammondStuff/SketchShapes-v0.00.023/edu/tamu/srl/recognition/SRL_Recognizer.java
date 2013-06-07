/**
 * 
 */
package edu.tamu.srl.recognition;

import java.util.ArrayList;

import edu.tamu.srl.object.SRL_Interpretation;
import edu.tamu.srl.object.shape.SRL_Shape;

/**
 * @author hammond
 * @copyright Tracy Hammond, Sketch Recognition Lab, Texas A&M University
 */
public abstract class SRL_Recognizer {
	public abstract ArrayList<SRL_Shape> recognize(ArrayList<SRL_Shape> shapes);
}
