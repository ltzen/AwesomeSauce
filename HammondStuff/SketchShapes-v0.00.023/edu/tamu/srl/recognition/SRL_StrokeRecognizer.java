/**
 * 
 */
package edu.tamu.srl.recognition;

import java.util.ArrayList;

import edu.tamu.srl.object.shape.SRL_Shape;
import edu.tamu.srl.object.shape.primitive.SRL_Point;
import edu.tamu.srl.object.shape.stroke.SRL_Stroke;
import edu.tamu.srl.recognition.primitives.LineRecognizer;

/**
 * @author hammond
 * @copyright Tracy Hammond, Sketch Recognition Lab, Texas A&M University
 */
public abstract class SRL_StrokeRecognizer extends SRL_Recognizer {

	/* (non-Javadoc)
	 * @see edu.tamu.srl.recognition.SRL_Recognizer#recognize(java.util.ArrayList)
	 */
	@Override
	public ArrayList<SRL_Shape> recognize(ArrayList<SRL_Shape> shapes) {
		ArrayList<SRL_Shape> newshapes = new ArrayList<SRL_Shape>();
		for(SRL_Shape shape : shapes){
			if(shape.hasInterpretation("stroke")){
				SRL_Stroke stroke = (SRL_Stroke)shape;
				newshapes.addAll(recognize(stroke));
			}	else {
				newshapes.add(shape);
			}
		}
		return newshapes;
	}
	

	/**
	 * temporary method for testing
	 * @param shapes
	 * @return
	 */
	public ArrayList<SRL_Shape> recognizeCorners(ArrayList<SRL_Shape> shapes) {
		ArrayList<SRL_Shape> newshapes = new ArrayList<SRL_Shape>();
		for(SRL_Shape shape : shapes){
			if(shape.hasInterpretation("stroke")){
				SRL_Stroke stroke = (SRL_Stroke)shape;
				newshapes.addAll((new LineRecognizer()).recognizeCorners(stroke));
			}	else {
				newshapes.add(shape);
			}
		}
		return newshapes;
	}
	
	/**
	 * Recognizes a stroke and returns a list of shapes of interpretations
	 * A single stroke may be recognized as several objects (pen not lifted)
	 * @param stroke a stroke to be recognized
	 * @return list of shapes that the stroke was recognized as
	 */
	public abstract ArrayList<SRL_Shape> recognize(SRL_Stroke stroke); 


	
}
