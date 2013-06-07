/**
 * 
 */
package edu.tamu.srl.recognition.features.stroke;

import edu.tamu.srl.object.shape.stroke.SRL_Stroke;
import edu.tamu.srl.recognition.features.FeatureList;

/**
 * @author hammond
 * @copyright Tracy Hammond, Sketch Recognition Lab, Texas A&M University
 */
public class LongFeatureList extends FeatureList {

	/**
	 * Computes the list of RubineFeatures for a particular stroke
	 * @param stroke the stroke to compute the feature list for
	 */
	public LongFeatureList(SRL_Stroke stroke){		
		addFeature("Long1", stroke.getStartAngleCosine(2), "Cosine of starting angle from points 0-2");
		addFeature("Long2", stroke.getStartAngleSine(2), "Sine of starting angle from points 0-2");
		addFeature("Long3", stroke.getLengthOfDiagonal(), "Length of the diagonal of the bounding box");
		addFeature("Long4", stroke.getBoundingBoxDiagonalAngle(), "Angle of the diagonal of the bounding box");
		addFeature("Long5", stroke.getEuclideanDistance(), "Distance between the start and the end point");
		addFeature("Long6", stroke.getEndAngleCosine(), "Cosine of angle from start to end point");
		addFeature("Long7", stroke.getEndAngleSine(), "Sine of angle from start to end point");
		addFeature("Long8", stroke.getStrokeLength(), "Length of stroke");
		addFeature("Long9", stroke.getRotationSum(), "Rotation: The total rotation of a stroke (circle is 2PI, wave is about 0, spiral is many PI)");
		addFeature("Long10", stroke.getRotationAbsolute(), "Curviness: the sum of the absolute rotations between each point (wave will have larger curviness than circle)");
		addFeature("Long11", stroke.getRotationSquared(), "Sharpness: the sum of the squared rotations between each point");
		addFeature("Long12", Math.abs(Math.PI/2 - stroke.getBoundingBoxDiagonalAngle()));
		addFeature("Long13", stroke.getCurviness(), "Curviness adding angles less than 19 degrees");
		addFeature("Long14", stroke.getRotationSum() / stroke.getStrokeLength(), "total angle traversed / stroke length");
		addFeature("Long15", stroke.getStrokeLength()/stroke.getEuclideanDistance(), "Density metric 1 [#8 / #5]");
		addFeature("Long16", stroke.getStrokeLength()/stroke.getLengthOfDiagonal(), "Density metric 2 [#8 /  #3]");
		addFeature("Long17", stroke.getEuclideanDistance()/stroke.getLengthOfDiagonal(), "Non-subjective openness [#5 / #3]");
		addFeature("Long18", stroke.getArea(), "Area of bounding box");
		addFeature("Long19", Math.log(stroke.getArea()), "log of area");
		addFeature("Long20", stroke.getRotationSum()/ stroke.getRotationAbsolute(), "Total angle / total absolute angle");
		addFeature("Long21", Math.log(stroke.getStrokeLength()), "Log(total length) ");
		addFeature("Long22", Math.log(Math.abs(Math.PI/2 - stroke.getBoundingBoxDiagonalAngle())), "Log(aspect)");
	
	}
}
