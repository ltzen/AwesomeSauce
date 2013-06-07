package edu.tamu.srl.object;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.UUID;

import edu.tamu.srl.object.shape.SRL_Shape;
import edu.tamu.srl.object.shape.primitive.SRL_Point;
import edu.tamu.srl.object.shape.stroke.SRL_Stroke;

/**
 * Object data class
 * @author hammond
 * @copyright Tracy Hammond, Sketch Recognition Lab, Texas A&M University
 */
public abstract class SRL_Object implements Comparable<SRL_Object>, Cloneable{

	/**
	 * Each object has a unique ID associated with it.
	 */
	private	UUID m_id = UUID.randomUUID();
	/**
	 * The name of the object, such as "triangle1"
	 */
	private String m_name = "";
	/**
	 * The creation time of the object.
	 */
	private long m_time = (new Date()).getTime();
	/**
	 * An object can be created by a user 
	 * (like drawing a shape, or speaking a phrase)
	 * or it can be created by a system
	 * (like a recognition of a higher level shape)
	 */
	private boolean m_isUserCreated = false;
	/**
	 * A list of possible interpretations for an object
	 */
	private ArrayList<SRL_Interpretation> m_interpretations = new ArrayList<SRL_Interpretation>();

	/**
	 * @return the interpretations
	 */
	public ArrayList<SRL_Interpretation> getInterpretations() {
		return m_interpretations;
	}

	/**
	 * @param interpretations the interpretations to set
	 */
	public void setInterpretations(ArrayList<SRL_Interpretation> interpretations) {
		m_interpretations = interpretations;
	}

	/**
	 * Was this object made up from a collection of subObjects? 
	 * If so they are in this list.
	 * This list usually gets filled in through recognition.
	 * This list can be examined hierarchically.
	 * e.g., an arrow might have three lines inside, and each line might have a stroke.
	 */
	private ArrayList<SRL_Object> m_subObjects = new ArrayList<SRL_Object>();
	
	
	/**
	 * Adds a subobject to this object. 
	 * This usually happens during recognition, when a new object
	 * is made up from one or more objects
	 * @param subObject
	 */
	public void addSubObject(SRL_Object subObject){
		m_subObjects.add(subObject);
	}
	
	/**
	 * Gets the list of subobjects
	 * @return list of objects that make up this object
	 */
	public ArrayList<SRL_Object> getSubObjects(){
		return m_subObjects;
	}
	
	/**
	 * Gets the list of subobjects
	 * @param type the interpretation of the object
	 * @return list of objects that make up this object
	 */
	public ArrayList<SRL_Shape> getSubShapes(String type){
		ArrayList<SRL_Shape> shapes = new ArrayList<SRL_Shape>();
		for(SRL_Object o : getSubObjects()){
			if(o.hasInterpretation(type)){
				shapes.add((SRL_Shape)o);
			}
		}
		return shapes;
	}
	
	
	/**
	 * adds a list of subobjects to this object
	 * @param subobjects list of subobjects to add
	 */
	public void addSubObjects(ArrayList<SRL_Object> subobjects) {
		m_subObjects.addAll(subobjects);
	}
	
	/**
	 * Gets a list of all of the objects that make up this object.
	 * This is a recursive search through all of the subobjects.
	 * This objects is also included on the list.
	 * @return
	 */
	public ArrayList<SRL_Object> getRecursiveSubObjectList(){
		ArrayList<SRL_Object> completeList = new ArrayList<SRL_Object>();
		completeList.add(this);
		for(SRL_Object o : m_subObjects){
			completeList.addAll(o.getRecursiveSubObjectList());
		}
		return completeList;
	}
	
	public abstract SRL_Object clone();
	
	/**
	 * Clones all of the information to the object sent in
	 * @param cloned the new clone object
	 * @return the same cloned object (superfluous return)
	 */
	protected SRL_Object clone(SRL_Object cloned){
		cloned.m_id = m_id;
		cloned.m_isUserCreated = m_isUserCreated;
		cloned.m_name = m_name;
		cloned.m_time = m_time;
		for(SRL_Interpretation i : m_interpretations){
			cloned.m_interpretations.add(i.clone());
		}
		for(SRL_Object o : m_subObjects){
			cloned.m_subObjects.add(o.clone());
		}
		return cloned;
	}
	
	
	/**
	 * add an interpretation for an object
	 * @param interpretation a string name representing the interpretation
	 * @param confidence a double representing the confidence
	 * @param complexity a double representing the complexity
	 */
	public void addInterpretation(String interpretation, double confidence, int complexity){
		m_interpretations.add(new SRL_Interpretation(interpretation, confidence, complexity));
	}
	
	/**
	 * @return unique UUID for an object
	 */
	public UUID getId() {
		return m_id;
	}

	/**
	 * In general you should not be setting a UUID unless you are
	 * loading in pre-existing objects with pre-existing UUIDs.
	 * @param id the unique id for the object
	 */
	public void setId(UUID id) {
		m_id = id;
	}

	/**
	 * An object can have a name, such as "triangle1". 
	 * @return the string name of the object
	 */
	public String getName() {
		return m_name;
	}

	/**
	 * An object can have a name, such as "triangle1". 
	 * @param name object name
	 */
	public void setName(String name) {
		m_name = name;
	}

	/**
	 * Gets the time associated with the object. 
	 * The default time is the time it was created
	 * @return the time the object was created.
	 */
	public long getTime() {
		return m_time;
	}

	/**
	 * Sets the time the object was created. This probably should 
	 * only be used when loading in pre-existing objects.
	 * @param time the time the object was created.
	 */
	public void setTime(long time) {
		m_time = time;
	}
	
	/**
	 * An object can be created by a user 
	 * (like drawing a shape, or speaking a phrase)
	 * or it can be created by a system
	 * (like a recognition of a higher level shape)
	 * default is false if not explicitly set
	 * @return true if a user created the shape
	 */

	public boolean isUserCreated() {
		return m_isUserCreated;
	}
	
	/**
	 * An object can be created by a user 
	 * (like drawing a shape, or speaking a phrase)
	 * or it can be created by a system
	 * (like a recognition of a higher level shape)
	 * @param isUserCreated true if the user created the shape, else false
	 */

	public void setUserCreated(boolean isUserCreated) {
		m_isUserCreated = isUserCreated;
	}
	
	/**
	 * Compares two objects based on their time stamps.
	 * Might be better to compare based on another method, 
	 * but haven't decided yet what that is.
	 * @param o the object to compare it to
	 * return 0 if they are equal
	 */
	public int compareTo(SRL_Object o){
		return ((Long)getTime()).compareTo((Long)o.getTime());
	}
	
	/**
	 * Checks if it contains an interpretation of a certain type
	 * @param interpretation the string name of the interpretation
	 * @return a boolean if it has such an interpretation or not
	 */
	public boolean hasInterpretation(String interpretation){
		for(SRL_Interpretation i : m_interpretations){
			if(i.getInterpretation().toLowerCase().equals(interpretation.toLowerCase())){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns the interpretation with the highest complexity
	 * and among those the one with the highest confidence
	 * @return best interpretation
	 */
	public SRL_Interpretation getBestInterpretation(){
		Collections.sort(m_interpretations);
		return m_interpretations.get(0);
/**		if(m_interpretations.size() == 0) return null;
		SRL_Interpretation bestInterpretation = m_interpretations.get(0);
		for(SRL_Interpretation i : m_interpretations){
			if(i.compareTo(bestInterpretation) > 0){
				bestInterpretation = i;
			}
		}
		return bestInterpretation;
		**/
	}
	
	/**
	 * Checks if it has a particular interpretation, if so, 
	 * it returns it, else it returns null.
	 * @param interpretation the string name of the interpretation
	 * @return the interpretation requested (complete with confidence and complexity)
	 */
	public SRL_Interpretation getInterpretation(String interpretation){
		for(SRL_Interpretation i : m_interpretations){
			if(i.getInterpretation().equals(interpretation)){
				return i;
			}
		}
		return null;
	}
	
	/**
	 * Returns the confidence of a particular interpretation.
	 * If the interpretation does not exist, it return -1.
	 * @param interpretation the string name of the interpretation
	 * @return the confidence of that interpretation
	 */
	public double getInterpretationConfidence(String interpretation){
		for(SRL_Interpretation i : m_interpretations){
			if(i.getInterpretation().equals(interpretation)){
				return i.getConfidence();
			}
		}
		return -1;
	}
	
	/**
	 * Returns the complexity of a particular interpretation.
	 * If the interpretation does not exist, it return -1.
	 * @param interpretation the string name of the interpretation
	 * @return the complexity of that interpretation
	 */
	public double getInterpretationComplexity(String interpretation){
		for(SRL_Interpretation i : m_interpretations){
			if(i.getInterpretation().equals(interpretation)){
				return i.getComplexity();
			}
		}
		return -1;
	}
	
	/**
	 * Gets a list of all of the interpretations available for the object
	 * @return the list of interpretations
	 */
	public ArrayList<SRL_Interpretation> getAllInterpretations(){
		Collections.sort(m_interpretations);
		return m_interpretations;
	}
	
	/**
	 * Gets a list of all of the strokes that make up this object.
	 * It searches recursively to get all of the strokes of this object.
	 * If it does not have any strokes, the list will be empty.
	 * @return
	 */
	public ArrayList<SRL_Stroke> getStrokes(){
		ArrayList<SRL_Stroke> completeList = new ArrayList<SRL_Stroke>();
		for(SRL_Object o : getRecursiveSubObjectList()){
			try {
				if(o.getClass() == Class.forName("edu.tamu.srl.object.shape.stroke.SRL_Stroke")){
					completeList.add((SRL_Stroke)o);
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return completeList;
	}
	
	/**
	 * Gets a list of all of the points that make up this object.
	 * It searches recursively to get all of the strokes of this object.
	 * If it does not have any strokes, and then returns the points in those strokes.
	 * @return
	 */
	public ArrayList<SRL_Point> getPoints(){
		ArrayList<SRL_Stroke> strokes = getStrokes();
		ArrayList<SRL_Point> allPoints = new ArrayList<SRL_Point>();
		for(SRL_Stroke stroke : strokes){
			allPoints.addAll(stroke.getPoints());
		}
		return allPoints;
	}
	
}
