package edu.tamu.srl.object;

/**
 * Interpretation data class, holds confidence and complexity
 * @author hammond
 * @copyright Tracy Hammond, Sketch Recognition Lab, Texas A&M University
 */
public class SRL_Interpretation implements Comparable<SRL_Interpretation>, Cloneable{

	/**
	 * A string description of the interpretation
	 */
	private String m_interpretation;
	
	/**
	 * A numerical measurement of confidence of an interpretation
	 */
	private double m_confidence;

	/**
	 * A numerical measurement of complexity of an interpretation
	 * (Obviously more complex interpretations are preferred.)
	 */
	private int m_complexity;
	
	/**
	 * A string holding information about this interpretation
	 */
	private String m_note;
	
	/**
	 * @return the note
	 */
	public String getNote() {
		return m_note;
	}

	/**
	 * @param note the note to set
	 */
	public void setNote(String note) {
		m_note = note;
	}

	/**
	 * Constructor that set the three values
	 * @param interpretation string name describing the interpretation
	 * @param confidence double representing confidence
	 * @param complexity double representing complexity
	 */
	public SRL_Interpretation(String interpretation, double confidence,
			int complexity) {
		m_interpretation = interpretation;
		m_confidence = confidence;
		m_complexity = complexity;
	}

	@Override
	/**
	 * Compares two interpretations. 
	 * It prefers interpretations of high complexity
	 * If the complexities are equal, it prefers interpretations of higher confidence
	 */
	public int compareTo(SRL_Interpretation other) {
		if(Double.compare(m_complexity, other.getComplexity()) == 0){
			return -1 * Double.compare(m_confidence, other.getConfidence());
		}
		return -1 * Double.compare(m_complexity, other.getComplexity());
	}

	/**
	 * get the interpretation of an object
	 * @return a string name for the interpretation
	 */
	public String getInterpretation() {
		return m_interpretation;
	}

	/**
	 * set the interpretation of an object
	 * @param interpretation a string name of the interpretation
	 */
	public void setInterpretation(String interpretation) {
		m_interpretation = interpretation;
	}

	/**
	 * Get the complexity of an interpretation
	 * @return a number representing the complexity of an interpretation
	 */
	public int getComplexity() {
		return m_complexity;
	}

	/**
	 * Set the complexity of an interpretation
	 * @param complexity a number representing the complexity of an interpretation
	 */
	public void setComplexity(int complexity) {
		m_complexity = complexity;
	}

	/**
	 * Get the confidence of an interpretation
	 * @return a number representing the confidence of an interpretation
	 */
	public double getConfidence() {
		return m_confidence;
	}

	/**
	 * Set the confidence of an interpretation
	 * @param confidence a number representing the confidence of an interpretation
	 */
	public void setConfidence(double confidence) {
		m_confidence = confidence;
	}
	
	public String toString(){
		return "Interpretation " + m_interpretation + ": confidence = " + m_confidence + ", complexity = " + m_complexity + ", note: " + m_note;
	}
	
	/**
	 * Returns a cloned version of this
	 * return a clone
	 */
	public SRL_Interpretation clone(){
		return new SRL_Interpretation(m_interpretation, m_confidence, m_complexity);
	}	

}
