package edu.tamu.srl.recognition.features;

import java.util.ArrayList;

/**
 * @author hammond
 * @copyright Tracy Hammond, Sketch Recognition Lab, Texas A&M University
 */
public class FeatureList {

	/**
	 * List of features for recognition
	 */
	ArrayList<Feature> m_featureList = new ArrayList<Feature>();
	
	/**
	 * Name of the class for this list of features
	 * This is only set if it is training data.
	 */
	String m_classname = "NotSet";
	
	/**
	 * @return the classname
	 */
	public String getClassname() {
		return m_classname;
	}

	/**
	 * @param classname the classname to set
	 */
	public void setClassname(String classname) {
		m_classname = classname;
		for(Feature f : m_featureList){
			f.setClassname(classname);
		}
	}

	/**
	 * Create a new empty feature list with a particular class
	 * @param classname name of the class (for training data)
	 */
	public FeatureList(String classname) {
		m_classname = classname;
	}

	/**
	 * Create a new empty feature list with the class not set
	 */
	public FeatureList() {
	}
	
	/**
	 * Returns the number of features (size of the array).
	 * @return
	 */
	public int getNumFeatures(){
		return m_featureList.size();
	}
	
	/**
	 * Adds a feature to the list
	 * @param f feature to add
	 */
	public void addFeature(Feature f){
		m_featureList.add(f);
	}
	
	/**
	 * Adds a feature to the list created from the value and name
	 * @param classname name of the class
	 * @param name short name of new feature
	 * @param value value of new feature
	 */
	public void addFeature(String classname, String name, double value){
		m_featureList.add(new Feature(classname, name, value));
	}
	
	/**
	 * Adds a feature to the list created from the value, name, and description
	 * @param classname name of the class
	 * @param name name of the new feature
	 * @param value value of the new feature
	 * @param description description of the new feature
	 */
	public void addFeature(String classname, String name, double value, String description){
		m_featureList.add(new Feature(classname, name, value, description));
	}
	
	/**
	 * Adds a feature to the list created from the value and name
	 * @param name short name of new feature
	 * @param value value of new feature
	 */
	public void addFeature(String name, double value){
		m_featureList.add(new Feature(m_classname, name, value));
	}
	
	/**
	 * Adds a feature to the list created from the value, name, and description
	 * @param name name of the new feature
	 * @param value value of the new feature
	 * @param description description of the new feature
	 */
	public void addFeature(String name, double value, String description){
		m_featureList.add(new Feature(m_classname, name, value, description));
	}
	
	/**
	 * Finds the feature for a feature with a particular shortname
	 * If there are more than one features with this name, 
	 * it returns the first one
	 * @param name name of feature to search for
	 * returns feature 
	 */
	public Feature getFeature(String name){
		for(Feature f : m_featureList){
			if(f.getName().equals(name)){
				return f;
			}
		}
		return null;
	}
	
	/**
	 * Finds the feature value for a feature with a particular shortname
	 * If there are more than one features with this name, 
	 * it returns the first one
	 * @param name name of feature to search for
	 * returns feature value
	 */
	public double getFeatureValue(String name){
		for(Feature f : m_featureList){
			if(f.getName().equals(name)){
				return f.getValue();
			}
		}
		return Double.NaN;
	}
	
	/**
	 * Gets the entire list of features
	 * @return list of features
	 */
	public ArrayList<Feature> getFeatures(){
		return m_featureList;
	}
	
	/**
	 * Creates a 1D array of doubles holding the values only in this list of features
	 * @return an array of feature values
	 */
	public double[] getValueArray(){
		double[] featureArray = new double[m_featureList.size()];
		int count = 0;
		for(Feature f : m_featureList){
			featureArray[count++] = f.getValue();
		}
		return featureArray;
	}
	
	/**
	 * Lists the features in the array as
	 * Feature name0 : value0
	 * Feature name1 : value1
	 * etc.
	 */
	public String toString(){
		String s = "";
		for(Feature f : getFeatures()){
			s += f.toString() + "\n";
		}
		return s;
	}
	
	/**
	 * Lists the features in the array as
	 * -------------------------------
	 * Long description of feature1.
	 * ------
	 * Long description of feature2.
	 * ------
	 * etc.
	 * -------------------------------
	 * return string of this description
	 */
	public String toLongString(){
		String s = "\n-------------------------------";
		for(Feature f : getFeatures()){
			s += "\n" + f.toLongString() + "------";
		}
		s+= "-------------------------\n\n";
		return s;
	}

	/**
	 * Returns the ith feature on the list
	 * @param i feature count
	 * @return the ith feature on the list
	 */
	public Feature getIthFeature(int i) {
		if (i >= getNumFeatures()) return null;
		return getFeatures().get(i);
	}
	
	/**
	 * Returns the value of the ith feature on the list
	 * @param i feature count
	 * @return the value of the ith feature on the list
	 */
	public double getIthFeatureValue(int i) {
		if (i >= getNumFeatures()) return Double.NaN;
		return getFeatures().get(i).getValue();
	}
	
	/**
	 * tester for the class
	 * @param args
	 */
	public static void main(String[] args){
		FeatureList allfeatures = new FeatureList("male");
		allfeatures.addFeature("height", 70, "the height in inches of a person");
		allfeatures.addFeature("age", 53, "the age of a person");
		allfeatures.addFeature("length", 2, "the length of a person's hair");
		System.out.println(allfeatures);
		System.out.println();
		System.out.println(allfeatures.toLongString());
	}
	
	
}
