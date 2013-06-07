/**
 * 
 */
package edu.tamu.srl.recognition.classifier;

import java.util.ArrayList;

import edu.tamu.srl.io.XMLFile;
import edu.tamu.srl.object.SRL_Sketch;
import edu.tamu.srl.recognition.ClassTrainingData;
import edu.tamu.srl.recognition.features.ClassFeatureSet;
import edu.tamu.srl.util.math.stat.Stat1D;
import edu.tamu.srl.util.math.stat.Stat2D;

/**
 * @author hammond
 * @copyright Tracy Hammond, Sketch Recognition Lab, Texas A&M University
 */
public class LinearClassifier {
	
	
	public static void main(String[] args){
		ClassFeatureSet men = new ClassFeatureSet("men", 3);
		men.addFeatureList(new double[]{70,53,2});
		men.addFeatureList(new double[]{72,42,1});
		men.addFeatureList(new double[]{71,36,8});
		men.addFeatureList(new double[]{65,46,3});
		men.addFeatureList(new double[]{68,54,2});
		men.addFeatureList(new double[]{74,12,3});
		men.addFeatureList(new double[]{62,74,2});
		men.addFeatureList(new double[]{75,24,12});
		men.addFeatureList(new double[]{70,65,3});
		men.addFeatureList(new double[]{69,71,2});
		ClassFeatureSet women = new ClassFeatureSet("women", 3);
		women.addFeatureList(new double[]{60,32,12});
		women.addFeatureList(new double[]{61,72,2});
		women.addFeatureList(new double[]{64,14,15});
		women.addFeatureList(new double[]{67,53,8});
		women.addFeatureList(new double[]{68,48,5});
		women.addFeatureList(new double[]{62,26,24});
		women.addFeatureList(new double[]{61,36,18});
		women.addFeatureList(new double[]{64,54,5});
		women.addFeatureList(new double[]{63,68,12});
		women.addFeatureList(new double[]{63,35,14});
		ArrayList<ClassFeatureSet> personData = new ArrayList<ClassFeatureSet>();
		personData.add(men);
		personData.add(women);
		ArrayList<double[]> wc = trainFeatures(personData);
		
		
		double[][] peopleConfusionMatrix = printResults(personData, wc);
		System.out.println(Stat2D.toString(peopleConfusionMatrix));
		
		//recognizeData("../SRLData/Datasets/HammondLowercaseLetters/");

		recognizeData("../SRLData/Datasets/259/");
		//recognizeData("../SRLData/Datasets/227/");	
		//recognizeData("../SRLData/Datasets/chemistry/");	
		
	}
	
	public static void recognizeData(String folderName){
		ArrayList<ClassTrainingData> data = XMLFile.getAllTrainingData(folderName);
		ArrayList<ClassFeatureSet> rubineFeatures = trainSketches(data, "rubine");
		ArrayList<double[]> wc = trainFeatures(rubineFeatures);
		double[][] confusionMatrix = printResults(rubineFeatures, wc);
		for(ClassFeatureSet cfs : rubineFeatures){
			System.out.print("\"" + cfs.getClassName() + "\", ");
		}
		System.out.println();
		System.out.println(Stat2D.toString(confusionMatrix));
		System.out.println(getWcRubineString(wc));		
		
		int numExamples = 0;
		for(ClassTrainingData d : data){
			numExamples += d.getSketches().size();
		}
		double[][] TPTNFPFN = new double[confusionMatrix.length][4];
		for(int i = 0; i < confusionMatrix.length; i++){
			TPTNFPFN[i][0] = confusionMatrix[i][i];
			TPTNFPFN[i][3] = data.get(i).getSketches().size() - confusionMatrix[i][i];
			for(int j = 0; j < confusionMatrix.length; j++){
				if(j!=i){
					TPTNFPFN[i][2] += confusionMatrix[i][j];
				}
			}
			TPTNFPFN[i][1] = numExamples - TPTNFPFN[i][0] - TPTNFPFN[i][2] - TPTNFPFN[i][3];
		}
		System.out.println(Stat2D.toString(TPTNFPFN));
		
	}
	
	/**
	 * Returns a string listing the weights for all of the classes that is in the format of 
	 * that can be used to declare a new array in Java.
	 * @param wc the weights for the classes
	 * @return a string in the form {{wc0, wc1, wc2},{wc0, wc1, wc2}, ...}
	 */
	public static String getWcRubineString(ArrayList<double[]> wc){
		String s = "\n{";
		for(int i = 0; i < wc.size(); i++){
			s += "{";
			for(int j = 0; j < wc.get(0).length; j++){
				s += wc.get(i)[j];
				if(j < wc.get(0).length - 1){
					s+=",";
				}
			}
			s += "}";
			if(i < wc.size() - 1){
				s+=",\n";
			}
		}
		s+="}\n";
		return s;
	}
	
	public static double[] classify(SRL_Sketch sketch, String recognizerName){
		return null;	
	}

	public static double[][] printResults(ArrayList<ClassFeatureSet> allFeatures, ArrayList<double[]> wc){
		double[][] confusionMatrix = new double[allFeatures.size()][allFeatures.size()];
		for(int j = 0; j < allFeatures.size(); j++){
			for(int i = 0; i < allFeatures.get(j).getNumExamples(); i++){
				double[] classificationValues = classify(wc, allFeatures.get(j).getExample(i));				
				String classification = "";
				int classificationNum = -1;
				double maxValue = Double.NEGATIVE_INFINITY;
				for(int valueNum = 0; valueNum < classificationValues.length; valueNum++){
					if(classificationValues[valueNum] > maxValue){
						maxValue = classificationValues[valueNum];
						classification = allFeatures.get(valueNum).getClassName();
						classificationNum = valueNum;
					}				
				}
				//System.out.println(classificationNum + " " + classificationValues[j] + " maxValue = " + maxValue);
				//System.out.print("is: " + allFeatures.get(j).getClassName() + " labeled: "+ classification + " ");
				//System.out.print(Stat1D.toString(classify(wc, allFeatures.get(j).getExample(i))));
				confusionMatrix[classificationNum][j] += 1;
				if(classificationNum != j){System.out.println("wrong: " + allFeatures.get(j).getClassName() + " classified as "  + allFeatures.get(classificationNum).getClassName() + " example number " + i);}
			}
		}
		return confusionMatrix;
	}
	
	/**
	 * Get a list of Features for the given classifier.
	 * @param data
	 * @param classifier
	 * @return
	 */
	public static ArrayList<ClassFeatureSet> trainSketches(ArrayList<ClassTrainingData> data, String classifier){
		ArrayList<ClassFeatureSet> featureSets = new ArrayList<ClassFeatureSet>();
		for(ClassTrainingData classdata : data){
			ClassFeatureSet classfeatures = new ClassFeatureSet(classdata, classifier);
			featureSets.add(classfeatures);
		}
		return featureSets;
	}
	
	/**
	 * Returns the training values needed for classification
	 * @param data the features for all of the classes
	 * @return the training values needed for classification
	 */
	public static ArrayList<double[]> trainFeatures(ArrayList<ClassFeatureSet> data){
		ArrayList<double[][]> covarianceMatrices = new ArrayList<double[][]>();
		ArrayList<double[]> featureMeans = new ArrayList<double[]>();
		for(ClassFeatureSet classdata : data){			
			double[][] covarianceMatrix = Stat2D.computeCovarianceMatrix(classdata.getAllFeatureValues());
			
			double[] meanFeatures = Stat2D.getFeaturesMean(classdata.getAllFeatureValues());
			covarianceMatrices.add(covarianceMatrix);
			featureMeans.add(meanFeatures);
			//System.out.println(Stat1D.toString(meanFeatures));
		}
		double[][] commonCovarianceMatrix = Stat2D.computeAverageMatrix(covarianceMatrices);
		//System.out.println(Stat2D.toString(commonCovarianceMatrix));		
		double[][] inverseCommon = Stat2D.inverse(commonCovarianceMatrix);
		//System.out.println(Stat2D.toString(inverseCommon));
		ArrayList<double[]> wc = new ArrayList<double[]>();
		for(int i = 0; i < featureMeans.size(); i++){
			double[] wcj = new double[inverseCommon.length+1];
			for(int j = 0; j < inverseCommon.length ; j++){
				wcj[j+1] = wcj(inverseCommon, featureMeans.get(i), j);
			}	
			wcj[0] = wc0(wcj, featureMeans.get(i));
			wc.add(wcj);
		}
		return wc;
	}
	
	/**
	 * Computes the weights for each class and feature
	 * @param inverseCommonCovarianceMatrix
	 * @param averageClassFeatureValue
	 * @param j
	 * @return
	 */
	private static double wcj(double[][] inverseCommonCovarianceMatrix, double[] averageClassFeatureValue, int j){
		double sum = 0;		
		for(int i = 0; i < averageClassFeatureValue.length; i++){
			sum += inverseCommonCovarianceMatrix[i][j] * averageClassFeatureValue[i];
		}
		return sum;
	}
	
	/**
	 * Initial weight for linear classifier (as defined by Rubine)
	 * @param wci weights for each feature
	 * @param averageClassFeatureValue average value for each feature
	 * @return initial weight
	 */
	private static double wc0(double[] wci, double[] averageClassFeatureValue){
		double sum = 0;
		for(int i = 0; i < averageClassFeatureValue.length; i++){
			sum += wci[i+1] * averageClassFeatureValue[i];
		}
		return -1*sum/2;
	}
	
	/**
	 * 
	 * @param wc classification values for that class
	 * @param f feature values
	 * @return
	 */
	public static double[] classify(ArrayList<double[]> wc, double[] f){
		double[] values = new double[wc.size()];
		int count = 0;
		for(double[] wcj : wc){
			double sum = wcj[0];
			for(int i = 1; i < wcj.length; i++){
				sum+= wcj[i]*f[i-1];
			}
			values[count++] =sum;
		}
		return values;
	}
	
	/**
	 * 
	 * @param wc classification values for that class
	 * @param f feature values
	 * @return
	 */
	public static double[] classify(double[][] wc, double[] f){
		double[] values = new double[wc.length];
		int count = 0;
		for(double[] wcj : wc){
			double sum = wcj[0];
			for(int i = 1; i < wcj.length; i++){
				sum+= wcj[i]*f[i-1];
			}
			values[count++] =sum;
		}
		return values;
	}
}
