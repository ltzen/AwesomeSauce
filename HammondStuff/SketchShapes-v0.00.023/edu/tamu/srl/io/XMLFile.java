/**
 * 
 */
package edu.tamu.srl.io;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.tamu.srl.object.SRL_Sketch;
import edu.tamu.srl.object.shape.primitive.SRL_Point;
import edu.tamu.srl.object.shape.stroke.SRL_Stroke;
import edu.tamu.srl.recognition.ClassTrainingData;


/**
 * @author hammond
 * @copyright Tracy Hammond, Sketch Recognition Lab, Texas A&M University
 */
public class XMLFile {

	
	/**
	 * Returns a list of ClassTrainingData where each ClassTrainingData contains a classname
	 * and a list of sketches for that class 
	 * Note that the classnames will be set to the name of the directory that contains those sketches
	 * @param dataFolderName the foldername that contains all of the data
	 * @return a list of ClassTrainingData
	 */
	public static ArrayList<ClassTrainingData> getAllTrainingData(String dataFolderName){
		ArrayList<ClassTrainingData> data = new ArrayList<ClassTrainingData>();
		File folder = getFolder(dataFolderName);
		File[] classes = folder.listFiles();
		for(File classdata : classes){
			String dirName = classdata.getPath();
			ClassTrainingData ctd = getTrainingDataForClass(dirName);
			if(ctd != null) data.add(ctd);
		}
		return data;
	}
	
	/**
	 * Returns a ClassTrainingData that sets the name of the class to the name of the directory	
	 * @param classFolderName name of the folder that contains the sketches
	 * @return a ClassTrainingData with the sketches of one directory
	 */
	public static ClassTrainingData getTrainingDataForClass(String classFolderName){
		ArrayList<SRL_Sketch> sketches = getSketches(classFolderName);
		if(sketches == null){return null;}
		String classname = (new File(classFolderName)).getName();
		return new ClassTrainingData(classname, sketches);
	}
	
	/**
	 * Get a folder
	 * @param foldername the path of the folder
	 * @return the folder
	 */
	public static File getFolder(String foldername){
		return new File(foldername);
	}
	
	/**
	 * Gets an arraylist of sketches in a directory
	 * @param foldername the path of the directory
	 * @return a list of sketches
	 */
	public static ArrayList<SRL_Sketch> getSketches(String foldername){
		ArrayList<SRL_Sketch> sketches = new ArrayList<SRL_Sketch>();
		File[] folder = getXMLFiles(foldername);
		if(folder == null){return null;}
		for(File xmlfile : folder){
			SRL_Sketch sketch = loadXMLFile(xmlfile.getPath());		
			if(sketch != null){
				sketches.add(sketch);
			}
		}
		return sketches;
	}
	
	/**
	 * Gets the files in a directory
	 * @param foldername the path of the directory
	 * @return a list of files
	 */
	public static File[] getFiles(String foldername){
		return getFolder(foldername).listFiles();
	}
	
	/**
	 * Gets the xml files in a directory
	 * @param foldername the path of the directory
	 * @return a list of xml files
	 */
	public static File[] getXMLFiles(String foldername){
		FilenameFilter filter = new FilenameFilter(){
			public boolean accept(File directory, String fileName) {
				return fileName.endsWith(".xml");
	        }
		};
		return getFolder(foldername).listFiles(filter);
	}
	
	/**
	 * This currently assumes the file consists only of strokes and points
	 * @param filename name of file to load
	 * @return SRL_Sketch object with strokes and points
	 */
	public static SRL_Sketch loadXMLFile(String filename){
		try{	
			File fXmlFile = new File(filename);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
		 
			//optional, but recommended   
			//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();
		 
			SRL_Sketch sketch = new SRL_Sketch();
			NodeList pointList =  doc.getElementsByTagName("point");
			ArrayList<SRL_Point> points = new ArrayList<SRL_Point>();
			for (int i = 0; i < pointList.getLength(); i++) {
				Node nNode = pointList.item(i);
				//System.out.println("\nCurrent Element :" + nNode.getNodeName());
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;	
					int xVal = Integer.parseInt(eElement.getAttribute("x"));
					int yVal = Integer.parseInt(eElement.getAttribute("y"));
					long timeVal = Long.parseLong(eElement.getAttribute("time"));
					UUID id = UUID.fromString(eElement.getAttribute("id"));
					SRL_Point p = new SRL_Point(xVal,yVal,timeVal);
					p.setId(id);
					points.add(p);					
				}
			}
			
			NodeList strokeList = doc.getElementsByTagName("stroke");
			for(int strokecount = 0; strokecount < strokeList.getLength(); strokecount++){
				SRL_Stroke stroke = new  SRL_Stroke();	
				Node strokeNode = strokeList.item(strokecount);
				if(strokeNode.getNodeType() == Node.ELEMENT_NODE){
					if(((Element)strokeNode).hasChildNodes()){
						NodeList argsList = ((Element)strokeNode).getElementsByTagName("arg");
						for(int argcount = 0; argcount < argsList.getLength(); argcount++){
							Node arg = argsList.item(argcount);
							if(arg.getNodeType() == Node.ELEMENT_NODE){
								Element earg = (Element)arg;
								if(earg.getAttribute("type").equals("point")){
									UUID id = UUID.fromString(earg.getTextContent());
									for(SRL_Point p : points){
										if(p.getId().equals(id)){
											stroke.addPoint(p);
										}
									}
								}
							}

						}
					}
				}
				if(stroke.getNumPoints() > 0){
					sketch.addSubObject(stroke);
				}
			}
			if(sketch.getSubObjects().size() == 0){return null;}
			return sketch;
		} catch (Exception e) {
			e.printStackTrace();
		}
	return null;
	}
}
