/**
 * 
 */
package edu.tamu.srl.recognition.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.tamu.srl.io.XMLFile;
import edu.tamu.srl.object.SRL_Interpretation;
import edu.tamu.srl.object.SRL_Object;
import edu.tamu.srl.object.SRL_Sketch;
import edu.tamu.srl.object.shape.JapaneseKanji;
import edu.tamu.srl.object.shape.SRL_Shape;
import edu.tamu.srl.object.shape.stroke.SRL_Stroke;
import edu.tamu.srl.recognition.SRL_Recognizer;
import edu.tamu.srl.recognition.classifier.LinearClassifier;
import edu.tamu.srl.recognition.data.SimpleKanjiData;
import edu.tamu.srl.recognition.features.ClassFeatureSet;
import edu.tamu.srl.recognition.features.stroke.RubineFeatureList;
import edu.tamu.srl.recognition.primitives.LineRecognizer;
import edu.tamu.srl.util.math.stat.Stat0D;

/**
 * @author hammond
 * @copyright Tracy Hammond, Sketch Recognition Lab, Texas A&M University
 */
public class SimpleKanjiRecognizer extends SRL_Recognizer{

	public ArrayList<SRL_Shape> recognize(ArrayList<SRL_Shape> shapes){
		
		//ArrayList<SRL_Shape[]> permutations = new ArrayList<SRLShape[]>()); 
		
		ArrayList<SRL_Shape> finalshapes = new ArrayList<SRL_Shape>();
		SRL_Shape finalshape = null;
		ArrayList<ArrayList<Integer>> permutations = Stat0D.getPermutations(shapes.size());
		for(ArrayList<Integer> permutation : permutations){
			ArrayList<SRL_Shape> neworder = new ArrayList<SRL_Shape>();
			for(Integer i : permutation){
				neworder.add(shapes.get(i-1));
			}
			SRL_Shape rubineshape = recognizeRubine(neworder).get(0);
			rubineshape.getBestInterpretation().setComplexity(rubineshape.getBestInterpretation().getComplexity()+1);
			if(finalshape == null){
				finalshape = rubineshape;
				int i = 0;
				for(SRL_Interpretation interp : finalshape.getAllInterpretations()){
					
					interp.setNote(permutation.toString());
				}
			} else {
				for(SRL_Interpretation interpretation : rubineshape.getAllInterpretations()){
					SRL_Interpretation currentbest = finalshape.getInterpretation(interpretation.getInterpretation());
					if(currentbest.getConfidence() < interpretation.getConfidence()){
						currentbest.setConfidence(interpretation.getConfidence());
						currentbest.setNote("rubine: " + permutation.toString());
					} 			
				}
			}
		}
		finalshapes.add(finalshape);
		return finalshapes;

	}
	


	
	public ArrayList<SRL_Shape> recognizeRubine(ArrayList<SRL_Shape> shapes){
		ArrayList<SRL_Shape> recognizedShapes = new ArrayList<SRL_Shape>();
		SRL_Stroke stroke = new SRL_Stroke();
		for(SRL_Shape shape : shapes){
			stroke.addPoints(shape.getPoints());
		}
		RubineFeatureList rubineFeatureList = new RubineFeatureList(stroke);	
		double[] classificationValues = LinearClassifier.classify(SimpleKanjiData.wcRubine, rubineFeatureList.getValueArray());		
		String classification = "";
		int classificationNum = -1;
		double maxValue = Double.NEGATIVE_INFINITY;
		JapaneseKanji shape = new JapaneseKanji(shapes);
		for(int valueNum = 0; valueNum < classificationValues.length; valueNum++){
			if(classificationValues[valueNum] > maxValue){
				maxValue = classificationValues[valueNum];
				classification = SimpleKanjiData.classnames[valueNum];
				classificationNum = valueNum;
			}
			shape.addInterpretation(SimpleKanjiData.classnames[valueNum],
					classificationValues[valueNum], 2);
		}	
		/**System.out.println("Best: " + shape.getBestInterpretation());
		for(SRL_Interpretation interpretation : shape.getInterpretations()){
			System.out.println(interpretation);
		}
		System.out.println("Best: " + shape.getBestInterpretation());
		 **/
		recognizedShapes.add(shape);
		return recognizedShapes;
	}
	
	
	public static void main(String[] args){
		SRL_Sketch sketch1 = XMLFile.loadXMLFile("../SRLData/Datasets/227/woman2813/37913.xml");
		SRL_Sketch sketch2 = XMLFile.loadXMLFile("../SRLData/Datasets/227/dog2835/30877.xml");
		SRL_Sketch sketch3 = XMLFile.loadXMLFile("../SRLData/Datasets/227/dog2835/36246.xml");
		SRL_Sketch sketch4 = XMLFile.loadXMLFile("../SRLData/Datasets/227/dog2835/37698.xml");
		ArrayList<SRL_Sketch> sketches = new ArrayList<SRL_Sketch>();
		sketches.add(sketch1);
		sketches.add(sketch2);
		sketches.add(sketch3);
		sketches.add(sketch4);
		for(SRL_Sketch sketch : sketches){
			LineRecognizer lr = new LineRecognizer();
			ArrayList<SRL_Shape> recognizedshapes = lr.recognizeCorners(sketch.getSubShapes("stroke"));
			for(SRL_Shape s: recognizedshapes){
				System.out.println(s.getBestInterpretation().toString());
			}
			SimpleKanjiRecognizer skr = new SimpleKanjiRecognizer();
			ArrayList<SRL_Shape> shapes = skr.recognizeRubine(sketch.getSubShapes("Stroke"));
			System.out.println(shapes.get(0).getBestInterpretation().getInterpretation() + " " + shapes.get(0).getBestInterpretation().getConfidence() + " " + shapes.get(0).getBestInterpretation().getNote());
			/**int j = 0;
			for(SRL_Interpretation interp : shapes.get(0).getAllInterpretations()){
			
			System.out.println(j++ + interp.toString());
			}**/
			ArrayList<SRL_Shape> shapes2 = skr.recognize(sketch.getSubShapes("Stroke"));
			System.out.println(shapes2.get(0).getBestInterpretation().getInterpretation()+ " " + shapes2.get(0).getBestInterpretation().getConfidence() +" " + shapes2.get(0).getBestInterpretation().getNote());
			int i = 0;
			for(SRL_Interpretation interp : shapes2.get(0).getAllInterpretations()){
				if (interp.getInterpretation().equals(shapes2.get(0).getBestInterpretation().getInterpretation()) || 
					interp.getInterpretation().equals(shapes.get(0).getBestInterpretation().getInterpretation()))
					System.out.println(i++ + interp.toString());
			}
		}
	}
}
