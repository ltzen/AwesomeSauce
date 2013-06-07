/**
 * 
 */
package edu.tamu.srl.recognition.primitives;

import java.util.ArrayList;

import edu.tamu.srl.object.SRL_Interpretation;
import edu.tamu.srl.object.shape.SRL_Shape;
import edu.tamu.srl.object.shape.primitive.SRL_Line;
import edu.tamu.srl.object.shape.primitive.SRL_Point;
import edu.tamu.srl.object.shape.stroke.SRL_Stroke;
import edu.tamu.srl.recognition.SRL_StrokeRecognizer;
import edu.tamu.srl.recognition.primitives.corner.ShortStraw;

/**
 * @author hammond
 * @copyright Tracy Hammond, Sketch Recognition Lab, Texas A&M University
 */
public class LineRecognizer extends SRL_StrokeRecognizer {

	/* 
	 * This function currently takes in a bunch of strokes, 
	 * and then returns a bunch of lines with a single stroke inside
	 * @param shapes a list of strokes
	 * (non-Javadoc)
	 * @see edu.tamu.srl.recognition.domain.SRL_Recognizer#recognize(SRL_Stroke)
	 */
	@Override
	public ArrayList<SRL_Shape> recognize(SRL_Stroke stroke) {
		ArrayList<SRL_Shape> newshapes = new ArrayList<SRL_Shape>();
		newshapes.add(stroke.getEndpointLine());
		return newshapes;
	}

	public ArrayList<SRL_Shape> recognizeCorners(SRL_Stroke stroke) {
		return (new ShortStraw()).recognize(stroke);
	}

	
}
