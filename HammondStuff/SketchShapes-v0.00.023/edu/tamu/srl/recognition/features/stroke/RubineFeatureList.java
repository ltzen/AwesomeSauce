/**
 * 
 */
package edu.tamu.srl.recognition.features.stroke;

import edu.tamu.srl.object.shape.stroke.SRL_Stroke;
import edu.tamu.srl.recognition.features.FeatureList;

/**
 * List of features from the Rubine paper
 * @author hammond
 * @copyright Tracy Hammond, Sketch Recognition Lab, Texas A&M University
 */
public class RubineFeatureList extends FeatureList {
	
	/**
	 * Computes the list of RubineFeatures for a particular stroke
	 * @param stroke the stroke to compute the feature list for
	 */
	public RubineFeatureList(SRL_Stroke stroke){		
		addFeature("Rubine1", stroke.getStartAngleCosine(2), "Cosine of starting angle from points 0-2");
		addFeature("Rubine2", stroke.getStartAngleSine(2), "Sine of starting angle from points 0-2");
		addFeature("Rubine3", stroke.getLengthOfDiagonal(), "Length of the diagonal of the bounding box");
		addFeature("Rubine4", stroke.getBoundingBoxDiagonalAngle(), "Angle of the diagonal of the bounding box");
		addFeature("Rubine5", stroke.getEuclideanDistance(), "Distance between the start and the end point");
		addFeature("Rubine6", stroke.getEndAngleCosine(), "Cosine of angle from start to end point");
		addFeature("Rubine7", stroke.getEndAngleSine(), "Sine of angle from start to end point");
		addFeature("Rubine8", stroke.getStrokeLength(), "Length of stroke");
		addFeature("Rubine9", stroke.getRotationSum(), "Rotation: The total rotation of a stroke (circle is 2PI, wave is about 0, spiral is many PI)");
		addFeature("Rubine10", stroke.getRotationAbsolute(), "Curviness: the sum of the absolute rotations between each point (wave will have larger curviness than circle)");
		addFeature("Rubine11", stroke.getRotationSquared(), "Sharpness: the sum of the squared rotations between each point");
		addFeature("Rubine12", stroke.getMaximumSpeed(), "Maximum speed of the stroke");
		addFeature("Rubine13", stroke.getTotalTime(), "Total time of the stroke");
	}
}
