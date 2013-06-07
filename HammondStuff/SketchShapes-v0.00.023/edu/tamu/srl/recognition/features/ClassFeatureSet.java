/**
 * 
 */
package edu.tamu.srl.recognition.features;

import java.util.ArrayList;

import edu.tamu.srl.object.SRL_Object;
import edu.tamu.srl.object.SRL_Sketch;
import edu.tamu.srl.object.shape.stroke.SRL_Stroke;
import edu.tamu.srl.recognition.ClassTrainingData;
import edu.tamu.srl.recognition.features.stroke.RubineFeatureList;
import edu.tamu.srl.util.math.stat.Stat1D;
import edu.tamu.srl.util.math.stat.Stat2D;

/**
 * For a particular class (or gesture type, or object, etc, holds the training features)
 * @author hammond
 * @copyright Tracy Hammond, Sketch Recognition Lab, Texas A&M University
 */
public class ClassFeatureSet {

	String m_className = "NotSet";
	String[] m_featureNames;
	String[] m_featureDescriptions;
	ArrayList<double[]> m_classFeatureValues;
	int m_numFeatures;
	
	/**
	 * @return the className
	 */
	public String getClassName() {
		return m_className;
	}
	
	/**
	 * Creates a new classfeatureset that holds the feature values for a particular class
	 * @param className
	 */
	public ClassFeatureSet(String className, int numFeatures){
		init(className, numFeatures);
	}

	/**
	 * initialize the arrays and other variables
	 * @param className
	 * @param numFeatures
	 */
	private void init(String className, int numFeatures){
		m_className = className;
		m_featureNames = new String[numFeatures];
		m_featureDescriptions = new String[numFeatures];
		m_classFeatureValues = new ArrayList<double[]>();
		m_numFeatures = numFeatures;
	}

	
	/**
	 * Create a ClassFeatureSet from the training data of sketches for a particular class
	 * @param classdata data from class
	 */
	public ClassFeatureSet(ClassTrainingData classdata, String classifier){
		ArrayList<SRL_Sketch> sketches = classdata.getSketches();
		m_className = classdata.getClassname();			
		for(SRL_Sketch sketch : sketches){
			if(classifier.equals("rubine")){
				SRL_Stroke stroke = new SRL_Stroke();
				stroke.addPoints(sketch.getPoints());
				RubineFeatureList rubinefeatures = new RubineFeatureList(stroke);	
				rubinefeatures.setClassname(m_className);
				if(m_featureNames == null){
					init(m_className, rubinefeatures.getNumFeatures());
				}
				addFeatureList(rubinefeatures);
				//System.out.println(rubinefeatures);
			}
		}			
	}
	
	
	/**
	 * Adds the features from a training example for this class to this set
	 * @param featurelist
	 */
	public void addFeatureList(FeatureList featureList){
		double[] featureValues = new double[featureList.getNumFeatures()];
		int i = 0;
		for(Feature f : featureList.getFeatures()){
			if(m_classFeatureValues.size() == 0){
				m_featureNames[i] = f.getName();
				m_featureDescriptions[i] = f.getDescription();
			}
			featureValues[i++] = f.getValue();
		}
		m_classFeatureValues.add(featureValues);
	}
	
	public void addFeatureList(double[] features){
		m_classFeatureValues.add(features);
	}
	
	
	/**
	 * Returns the number of training examples we have (how many feature lists we have)
	 * @return number of training examples for the class
	 */
	public int getNumExamples(){
		return m_classFeatureValues.size();
	}
	
	/**
	 * Returns a list of feature values with a particular name
	 * @param featureName
	 * @return
	 */
	public double[] getFeatureValues(String featureName){
		double[] featureValues = new double[getNumExamples()];
		for(int i = 0; i < m_featureNames.length; i++){
			if(m_featureNames[i].equals(featureName)){
				int count = 0;
				for(double[] features : m_classFeatureValues){
					featureValues[count++] = features[i];
				}
				return featureValues;
			}
		}
		return null;
	}
	
	/**
	 * Returns the ith list of feature values
	 * @param i the feature number
	 * @return the value list of the ith feature.
	 */
	public double[] getFeatureValues(int i){
		if(i > m_numFeatures){return null;}
		double[] featureValues = new double[getNumExamples()];
		int count = 0;
		for(double[] features : m_classFeatureValues){
			featureValues[count++] = features[i];
		}
		return featureValues;
	}
	
	/**
	 * Set the featurenames with a list of strings
	 * @param names
	 */
	public void setFeatureNames(String[] names){
		m_featureNames = names;
	}
	
	/**
	 * returns a two dimensional array of the features 
	 * @return
	 */
	public double[][] getAllFeatureValues(){
		double[][] featureValues = new double[getNumExamples()][m_numFeatures];
		int count = 0;
		for(double[] features : m_classFeatureValues){
			featureValues[count++] = features;
		}
		return featureValues;
	}
	
	/**
	 * Returns a short listing of the data
	 * returns a short listing of the data
	 */
	public String toString(){
		String s = "Class = " + m_className + "\n";
		for(String featureName : m_featureNames){
			s += featureName + "    ";
		}
		s += "\n";
		for(double[] features : getAllFeatureValues()){
			for(int i = 0; i < features.length; i++){
				double feature = features[i];
				s+= feature + " ";
				int diff = m_featureNames[i].length() +3 - Double.toString(feature).length();
				for(int d = 0; d < diff; d++){
					s += " ";
				}
			}
			s += "\n";
		}
		return s;
	}
	
	/**
	 * Returns a long description of the data, with a description of each of the features 
	 * returns a long description of the data
	 */
	public String toLongString(){
		String s = "Class = " + m_className + "\n";
		int count = 0;
		for(String featureName : m_featureNames){
			s += featureName + " : " + m_featureDescriptions[count++] + "\n";
		}
		for(String featureName : m_featureNames){
			s += featureName + "    ";
		}
		s += "\n";
		for(double[] features : getAllFeatureValues()){
			for(int i = 0; i < features.length; i++){
				double feature = features[i];
				s+= feature + " ";
				int diff = m_featureNames[i].length() +3 - Double.toString(feature).length();
				for(int d = 0; d < diff; d++){
					s += " ";
				}
			}
			s += "\n";
		}
		return s;
	}
	
	/**
	 * Sets makes the array of feature values
	 * @param values a list of feature values
	 */
	public void setFeatureValues(double[][] values){
		m_classFeatureValues = new ArrayList<double[]>();
		for(double[] example : values){
			m_classFeatureValues.add(example);
		}
	}
	
	/**
	 * Sets the list of the feature descriptions
	 * @param descriptions list of feature descriptions
	 */
	public void setFeatureDescriptions(String[] descriptions){
		m_featureDescriptions = descriptions;
	}
	
	/**
	 * Gets the list of feature names
	 * @return list of feature names
	 */
	public String[] getFeatureNames(){
		return m_featureNames;
	}

	/**
	 * Gets the list of feature descriptions
	 * @return list of feature descriptions
	 */
	public String[] getFeatureDescriptions(){
		return m_featureDescriptions;
	}
	
	/**
	 * tester for the class
	 * @param args
	 */
	public static void main(String args[]){
		ClassFeatureSet malefeatures = new ClassFeatureSet("male", 3);
		String[] names = new String[]{"height", "age", "length"};
		malefeatures.setFeatureNames(names);
		String[] descriptions = new String[]{"the height in inches of a person",
				"the age of a person", "the length of a person's hair"};
		malefeatures.setFeatureDescriptions(descriptions);
		double[][] values = new double[][]{{70,53,2},{72,42,1}, {71,36,8}, {65,46,3},
				 {68,54,2}, {74,12,3}, {62,74,2}, {75,24,12}, {70,65,3}, {69,71,2}};
		malefeatures.setFeatureValues(values);
		System.out.print(malefeatures.toLongString());
		
		for(String featureName : malefeatures.m_featureNames){
			double[] featurevalues = malefeatures.getFeatureValues(featureName); 
			System.out.println("Class male: Mean " + featureName +  " = " + 
					Stat1D.getMean(featurevalues) + "; std = "
					+ Stat1D.getSampleStdDev(featurevalues) + "\n");
		}
		
		ClassFeatureSet femalefeatures = new ClassFeatureSet("female", 3);
		femalefeatures.setFeatureNames(names);
		femalefeatures.setFeatureDescriptions(descriptions);
		values = new double[][]{{60, 32, 12}, {61, 72, 2}, {64, 14, 15}, {67, 53, 8},
				{68, 48, 5}, {62, 26, 24}, {61, 36, 18}, {64, 54, 5}, {63, 68, 12}, {63, 35, 14}};
		femalefeatures.setFeatureValues(values);
		System.out.print(femalefeatures);
		//System.out.println(femalefeatures.toLongString());
		for(String featureName : femalefeatures.m_featureNames){
			double[] featurevalues = femalefeatures.getFeatureValues(featureName); 
			System.out.println("Class female: Mean " + featureName +  " = " + 
					Stat1D.getMean(featurevalues) + "; std = "
					+ Stat1D.getSampleStdDev(featurevalues));
		}
		System.out.print(Stat2D.toString(Stat2D.computeCovarianceMatrix(values)));
	}

	/**
	 * @param i
	 * @return
	 */
	public double[] getExample(int i) {
		// TODO Auto-generated method stub
		return m_classFeatureValues.get(i);
	}


	
}
