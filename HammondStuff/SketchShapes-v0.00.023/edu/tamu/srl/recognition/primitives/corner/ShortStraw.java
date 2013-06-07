/**
 * 
 */
package edu.tamu.srl.recognition.primitives.corner;

import java.util.ArrayList;

import edu.tamu.srl.object.shape.SRL_Shape;
import edu.tamu.srl.object.shape.primitive.SRL_Line;
import edu.tamu.srl.object.shape.primitive.SRL_Point;
import edu.tamu.srl.object.shape.stroke.SRL_Stroke;
import edu.tamu.srl.recognition.SRL_Recognizer;
import edu.tamu.srl.recognition.SRL_StrokeRecognizer;
import edu.tamu.srl.util.math.stat.Stat1D;

/**
 * @author hammond
 * @copyright Tracy Hammond, Sketch Recognition Lab, Texas A&M University
 */
public class ShortStraw extends SRL_StrokeRecognizer{

	/* (non-Javadoc)
	 * @see edu.tamu.srl.recognition.domain.SRL_Recognizer#recognize(java.util.ArrayList)
	 */
	@Override
	public ArrayList<SRL_Shape> recognize(SRL_Stroke stroke) {
		ArrayList<SRL_Shape> newshapes = new ArrayList<SRL_Shape>();
		if(stroke.isLine()){
			newshapes.add(stroke.getEndpointLine());
			return newshapes;
		}
		SRL_Stroke newstroke = SRL_Stroke.resample(stroke);
		ArrayList<SRL_Point> corners = getCorners(newstroke);
		//System.out.println("corners size = " + corners.size());
		for(int i = 0; i < corners.size() - 1; i++){
			SRL_Line line = new SRL_Line(corners.get(i), corners.get(i+1));
			line.getInterpretation("line").setComplexity(2);
			line.getInterpretation("line").setConfidence(.951); 
			line.getInterpretation("line").setNote("shortstraw");
			//toDO add the substroke and set real confidence
			newshapes.add(line);
		//	System.out.println("added: " + line.toString());
		}
		//System.out.println("total number of lines " + newshapes.size());
		return newshapes;
	}

	private static ArrayList<SRL_Point> getCorners(SRL_Stroke stroke){
		ArrayList<Integer> cornerIndices = new ArrayList<Integer>();
		int window = 3;
		cornerIndices.add(0);
		//get strawlengths
		double[] strawLengths = new double[stroke.getPoints().size()];
		for(int strawcount = window; strawcount < stroke.getNumPoints() - window; strawcount++){
			strawLengths[strawcount] = stroke.getPoints().get(strawcount - window).distance(stroke.getPoint(strawcount + window)); 
		//	System.out.println("straw length = " + strawLengths[strawcount]);
		}
		
		//select corners
		double strawthreshold = Stat1D.getMean(strawLengths) * .95;
		//System.out.println("strawthreshold = " + strawthreshold);
		for(int strawcount = window; strawcount < stroke.getNumPoints() - window; strawcount++){
			//System.out.println("processing straw " + strawcount + " " + strawLengths[strawcount]);
			if(strawLengths[strawcount] < strawthreshold){
				double localMin = strawLengths[strawcount];
				//System.out.println("local min " + localMin);
				int localMinIndex = strawcount;
				strawcount++;
				//System.out.println("strawcount = " + strawcount + " and strawlengthssize = " + strawLengths.length);
				while(strawcount < strawLengths.length && strawLengths[strawcount] < strawthreshold){
					//System.out.println("processing straw " + strawcount + " " + strawLengths[strawcount]);
					if(strawLengths[strawcount] < localMin){
						localMin = strawLengths[strawcount];
						localMinIndex = strawcount;
					}
					strawcount++;
					//System.out.println("strawcount = " + strawcount + " and strawlengthssize = " + strawLengths.length);
					
				}
				cornerIndices.add(localMinIndex);
			}
		}		
		cornerIndices.add(stroke.getNumPoints()-1);
		// add corners
/**		boolean done = true;
		while (done){
			done = false;
			for(int corner = 1 ; corner < cornerIndices.size(); corner++){
				SRL_Point c1 = stroke.getPoint(cornerIndices.get(corner-1));
				SRL_Point c2 = stroke.getPoint(cornerIndices.get(corner));
				if(c1.distance(c2) < .95){
					int quarter = (cornerIndices.get(corner-1) - cornerIndices.get(corner))/4;
					double minValue = Double.POSITIVE_INFINITY;
					int minIndex = -1;
					for(int i = cornerIndices.get(corner) - quarter; i <= cornerIndices.get(corner) + quarter; i++){
						if(strawLengths[i] < strawthreshold){
							System.out.println("Actually got here I can't believe it...seems impossible... in shortstraw");
							minValue = strawLengths[i];
							minIndex = i;
						}
					}
					if(minIndex > -1){
						cornerIndices.add(minIndex, corner);
					}
					done = true;
				}
			}
		}**/
		
		//remove points
		for(int corner = 1 ; corner < cornerIndices.size() - 1; corner++){
			SRL_Point c1 = stroke.getPoint(cornerIndices.get(corner-1));
			SRL_Point c2 = stroke.getPoint(cornerIndices.get(corner));
			SRL_Point c3 = stroke.getPoint(cornerIndices.get(corner+1));
			SRL_Stroke test = new SRL_Stroke();
			test.addPoint(c1);
			test.addPoint(c2);
			test.addPoint(c3);
			if(stroke.getEuclideanDistance()/stroke.getStrokeLength() >.95){
				cornerIndices.remove(corner);
			}
		}
		
		//make arraylist of points
		ArrayList<SRL_Point> corners = new ArrayList<SRL_Point>();
		for(int corner = 0 ; corner < cornerIndices.size(); corner++){
			corners.add(stroke.getPoint(corner));
		}

		return corners;		
	}

	
}
