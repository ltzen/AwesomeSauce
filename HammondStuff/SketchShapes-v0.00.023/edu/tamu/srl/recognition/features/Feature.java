/**
 * 
 */
package edu.tamu.srl.recognition.features;

/**
 * @author hammond
 * @copyright Tracy Hammond, Sketch Recognition Lab, Texas A&M University
 */
public class Feature {

	private String m_classname = "NotSet";
	
	/**
	 * The value of the feature
	 * If not set this is initially  set to Double.NaN
	 */
	private double m_value = Double.NaN;

	/**
	 * A short name describing the feature
	 */
	private String m_name = "NotSet";
	
	/**
	 * A longer description of the feature
	 */
	private String m_description = "NotSet";

	/**
	 * The maximum allowable value for a feature;
	 */
	private double m_maxAllowableValue = Double.POSITIVE_INFINITY;
	
	/**
	 * The minimum allowable value for a feature;
	 */
	private double m_minAllowableValue = Double.NEGATIVE_INFINITY;
	
	/**
	 * Creates a new feature setting the value and a shortname for the feature
	 * @param classname the name of the class type if known
	 * @param shortname a shortname describing the feature
	 * @param value the value for the feature
	 */
	public Feature(String classname, String shortname, double value){
		setClassname(classname);
		m_value = value;
		m_name = shortname;
	}
	
	/**
	 * Creates a new feature setting the value, shortname, and description for the feature
	 * @param classname the name of the class type if known
	 * @param name name of the feature
	 * @param value value of the feature
	 * @param description long description of the feature
	 */
	public Feature(String classname, String name, double value, String description) {
		setClassname(classname);
		m_value = value;
		m_name = name;
		m_description = description;
	}
	
	/**
	 * Creates a new feature setting the value and a shortname for the feature
	 * @param shortname a shortname describing the feature
	 * @param value the value for the feature
	 */
	public Feature(String shortname, double value){
		m_value = value;
		m_name = shortname;
	}
	
	/**
	 * Creates a new feature setting the value, shortname, and description for the feature
	 * @param name name of the feature
	 * @param value value of the feature
	 * @param description long description of the feature
	 */
	public Feature(String name, double value, String description) {
		m_value = value;
		m_name = name;
		m_description = description;
	}

	/**
	 * Gets the value of the feature
	 * If not set, this is initially set to Double.Nan
	 * @return value of the feature
	 */
	public double getValue() {
		return m_value;
	}
	
	/**
	 * sets the value of the feature
	 * if too large or too small, the value is set to Double.NaN
	 * @param value value the feature should be
	 */
	public void setValue(double value) {
		if(value > getMaxAllowableValue()) m_value = Double.NaN;
		if(value < getMinAllowableValue()) m_value = Double.NaN;
		m_value = value;
	}

	/**
	 * Gets the short name of this feature
	 * If this is not set, this is initially set to "Not Set"
	 * @return short name of feature
	 */
	public String getName() {
		return m_name;
	}

	/**
	 * Sets the short name of this feature
	 * @param name short name describing the feature
	 */
	public void setName(String name) {
		m_name = name;
	}

	/**
	 * Gets a long description of a feature
	 * Essentially this serves for documentation of what the feature is computing
	 * @return long description of a feature
	 */
	public String getDescription() {
		return m_description;
	}

	/**
	 * Sets a long description of a feature
	 * Essentially this serves for documentation of what the feature is computing
	 * @param description long description of a feature
	 */
	public void setDescription(String description) {
		m_description = description;
	}

	/**
	 * gets the maximum allowable value for the feature
	 * By default this is Double.POSITIVE_INFINITY
	 * @return the maximum allowable value
	 */
	public double getMaxAllowableValue() {
		return m_maxAllowableValue;
	}

	/**
	 * Sets the maximum allowable value for the feature
	 * @param maxAllowableValue the max allowable value for the feature
	 */
	public void setMaxAllowableValue(double maxAllowableValue) {
		m_maxAllowableValue = maxAllowableValue;
	}

	/**
	 * gets the minimum allowable value for the feature
	 * By default this is Double.NEGTIVE_INFINITY
	 * @return the minimum allowable value
	 */
	public double getMinAllowableValue() {
		return m_minAllowableValue;
	}

	/**
	 * Sets the minimum allowable value for the feature
	 * @param minAllowableValue the minimum allowable value for the feature
	 */
	public void setMinAllowableValue(double minAllowableValue) {
		m_minAllowableValue = minAllowableValue;
	}
	
	/**
	 * Returns a string of information about the feature
	 * Feature name 
	 * Feature value
	 * Feature description
	 * Feature maxValue
	 * Feature minValue
	 * returns the string of information about the feature
	 */
	public String toLongString(){
		return "Feature Class = " + getClassname() + "\n" +
				"Feature Name = " + getName() + "\n" + 
				"Feature Value = " + getValue() + "\n" + 
				"Feature Description = " + getDescription() + "\n" + 
				"Feature min = " + getMinAllowableValue() + "\n" + 
				"Feature max = " + getMaxAllowableValue() + "\n";
	}
	
	/**
	 * Returns feature name and value
	 * Feature name : value
	 * returns the string with name and value of the feature
	 */
	public String toString(){
		return "Feature " + getClassname() + " : " + getName() + " : " + getValue();
	}

	/**
	 * Gets the name of the class.
	 * If not known this returns "NotSet"
	 * @return name of the class
	 */
	public String getClassname() {
		return m_classname;
	}

	/**
	 * Sets the name of the class
	 * @param classname name of the class
	 */
	public void setClassname(String classname) {
		m_classname = classname;
	}
	
	
	/**
	 * tester for the class
	 * @param args
	 */
	public static void main(String[] args){
		Feature f = new Feature("male", "height", 70, "the height in inches of a person");
		System.out.println(f);
		System.out.println();
		System.out.println(f.toLongString());
	}
	
}
