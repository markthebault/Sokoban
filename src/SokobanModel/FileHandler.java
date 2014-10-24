package SokobanModel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.text.html.parser.DocumentParser;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;



/**
 * @ Project : SokobanAndroidApp
 * @ File Name : FileHandler.java
 * @ Date : 15/02/2012
 * @ Author : Mark THEBAULT
**/




public class FileHandler
{
	/**
	 * File parameters
	 */
	private String nameCurrentFile;
	private DocumentBuilderFactory docBF;
	private DocumentBuilder docB;
	private Document dom;
	private Element rootElt;
	private String srcConfigFile;
		
	
	/**
	 * Maps parameters
	 */
	private ArrayList<GridMap> mapsOnFile;
	private int mapsCount;
	private int lastPlayeLevel;
	
	
	/**
	 * Basic constructor
	 */
	public FileHandler(String srcConfigFile)
	{
		this.srcConfigFile = srcConfigFile;
	}
	
	/**
	 * Load a new file
	 * @param srcFile
	 */
	public FileHandler(String srcConfigFile, String srcFile)
	{
		//call basic contructor
		this(srcConfigFile);
		
		//load the file and his data
		this.loadXmlFile(srcFile);		

	}
	
	
	/**
	 * Load xml file
	 * @param nameFile
	 */
	public void loadXmlFile(String nameFile)
	{
		//init maps
		this.mapsOnFile = new ArrayList<GridMap>();
		
		//get name of current file
		this.nameCurrentFile = nameFile;
		

		//parse xml file
		this.parseXmlFile(nameFile);
		
		//parse document
		this.parseDocument();
	}
	
	
	/**
	 * Load all maps
	 */
	private void parseDocument()
	{
		//get root element
		this.rootElt = this.dom.getDocumentElement();
		
		//Get all levels
		NodeList nl = this.dom.getElementsByTagName("Level");
		
		//get mapsCount
		this.mapsCount = nl.getLength();
		
		/*if(nl != null && nl.getLength() > 0 )
		{
			for(int i=0; i<nl.getLength(); i++)
			{
				//get current level
				Element elt = (Element)nl.item(i);
				
				//get map by xml
				GridMap xmlMap = this.getMapByXML(elt);
				
				//add the map
				this.mapsOnFile.add(xmlMap);
			}
		}*/
		
	}
	
	
	
	/**
	 * Build a map
	 * @param elt
	 * @return
	 */
	private GridMap getMapByXML(Element elt)
	{
		GridMap myMap;
		int sizeX, sizeY;
		String name, lineContent;
		NodeList line;
		
		
		//Load element of node
		sizeX = Integer.valueOf(elt.getAttribute("Width"));
		sizeY = Integer.valueOf(elt.getAttribute("Height"));
		name = elt.getAttribute("Id");
		
		//create grid map
		myMap = new GridMap(name, new Dimension(sizeY, sizeX));
		
		
		//System.out.println("La map : "+name+" fais la taille ("+sizeX+","+sizeY+")");
		
		
		
		//For each line in the file
		line = elt.getElementsByTagName("L");	
		if(line != null && line.getLength() > 0 )
		{
			for(int i=0; i<line.getLength(); i++)
			{
				//get element of line
				Element eltLine = (Element)line.item(i);
				
				//get value of element
				lineContent = eltLine.getTextContent();
				
			
				//Add cell on map
				this.addCellsOnMap(myMap, lineContent, i);
			}
		}
		
		
		//System.out.println(myMap);

		
		return myMap;
	}

	/**
	 * Create cell according to the character of document
	 * @param myMap
	 * @param lineContent
	 * @param idLine
	 */
	private void addCellsOnMap(GridMap myMap, String lineContent, int idLine)
	{
		//for each character add a cell
		for(int i = 0; i<lineContent.length(); i++)
		{
			try
			{
				//select the cell
				switch(lineContent.charAt(i))
				{
	
				case '.':
					myMap.addCell(new Objective(new Position(idLine,i)));
					break;
					
				case '$':
					myMap.addCell(new Boxe(new Position(idLine,i)));
					break;
					
				case '#':
					myMap.addCell(new Wall(new Position(idLine,i)));
					break;
					
				case '@':
					myMap.addCell(new SokobanModel.Character(new Position(idLine,i)));
					break;
				
				case '+':
					myMap.addCell(new SokobanModel.Character(new Position(idLine,i)));
					myMap.addCell(new Objective(new Position(idLine,i)));
					break;
				
				case '*':
					myMap.addCell(new Boxe(new Position(idLine,i)));
					myMap.addCell(new Objective(new Position(idLine,i)));
					break;				
					
				default: break;
				}
			}
			catch (Exception e) 
			{
				System.out.println(e);
			}
		}
		
	}

	
	/**
	 * Open a new document XML
	 * @param nameFile
	 */
	private void parseXmlFile(String nameFile)
	{
		//get the factory
		docBF = DocumentBuilderFactory.newInstance();

		try 
		{

			//Using factory get an instance of document builder
			docB = docBF.newDocumentBuilder();

			//parse using builder to get DOM representation of the XML file
			dom = docB.parse(nameFile);

		}
		catch(ParserConfigurationException pce) 
		{
			pce.printStackTrace();
		}
		catch(SAXException se) 
		{
			se.printStackTrace();
		}
		catch(IOException ioe) 
		{
			ioe.printStackTrace();
		}
	}
	
	
	
	/**
	 * Get a count of maps
	 * @return
	 */
	public int getMapsCount()
	{
		return this.mapsCount;
	}
	
	
	
	/**
	 * get map by index
	 * @param i
	 * @return
	 */
	public GridMap getMap(int i)
	{
		//get list of maps
		NodeList nl = this.dom.getElementsByTagName("Level");
		
		if(nl.getLength() > 0)
		{
			//return the map
			return this.getMapByXML((Element)nl.item(i));
		}
		else
		{
			return null;
		}
	}
	
	
	/**
	 * Get map by name
	 * @param name
	 * @return
	 */
	public GridMap getMap(String name)
	{
	
		return null;
	}
	
	
	/**
	 * get All maps
	 * @return
	 */
	public ArrayList<GridMap> getMaps()
	{
		return this.mapsOnFile;
	}	
	
	
	/**
	 * Save a new map
	 * @param map
	 */
	public void saveNewMap(GridMap map)
	{
		//TODO id is the name of map
	}
	
	
	/**
	 * Save current played map
	 * @param map
	 */
	public void saveCurrentGame(GridMap map, int currentLevel)
	{
		Element childElt;
		//Create new document
		Document doc = this.docB.newDocument();
		
		//Create root element
		Element rootElt = doc.createElement("LastPlayedMap");
		
		//Add id attribute
		rootElt.setAttribute("idLastMap", new String(""+currentLevel));
		
		//Add source file attribute
		rootElt.setAttribute("sourceMapFile", this.nameCurrentFile);
		
		//set parameters of map name, height and width
		rootElt.setAttribute("Width", new String(""+map.getMapSize().getY()));
		rootElt.setAttribute("Height", new String(""+map.getMapSize().getX()));
		rootElt.setAttribute("Id", map.getName());
		
		//add root element at document xml
		doc.appendChild(rootElt);
		
		
		//Create Lines
		for(String s : this.getMapStrings(map))
		{
			//create line of map string
			childElt = doc.createElement("L");
			childElt.appendChild(doc.createTextNode(s));
			rootElt.appendChild(childElt);
		}

		
		//save the document
		try
		{		
			//tools to write
			TransformerFactory trF = TransformerFactory.newInstance();
			Transformer tr = trF.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(this.srcConfigFile));
			
			//write the file
			tr.transform(source, result);
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		
	}
	
	
	/**
	 * Get strings of line of map
	 * @param map
	 * @return
	 */
	private Collection<String> getMapStrings(GridMap map)
	{
		//The strings
		Collection<String> mapStrs = new ArrayList<String>();

		//Need to convert map on char
		String mapLine;
		Collection<Cell> cellsByPosition;
		Position posCell = new Position();
		int nbObj, nbBoxe, nbPlayer, nbWall;
		
		
		
		//Content of the map
		mapLine = new String();
		
		//for each line
		for(int i = 0; i<map.getMapSize().getX(); i++)
		{
			mapLine = "";
			
			//for each column
			for(int j=0; j<map.getMapSize().getY(); j++)
			{
				//Construnt a position
				posCell.setX(i);
				posCell.setY(j);
				
				//Get cells
				cellsByPosition = map.getCells(posCell);
				
				//Convert cells on character
				if(cellsByPosition.size() > 0)
				{
					//initialize counter
					nbObj = 0; 
					nbBoxe = 0; 
					nbPlayer = 0;
					nbWall = 0;
					
					//look cells on the position
					for(Cell c : cellsByPosition)
					{
						//Count by type
						if(c instanceof Objective) nbObj++;
						if(c instanceof Wall) nbWall++;
						if(c instanceof Boxe) nbBoxe++;
						if(c instanceof Character) nbPlayer++;
										
					}
					
					//make character representation by type
					
					if( nbObj > 0 && nbPlayer > 0) //character on obj
					{
						mapLine += '+';
					}
					else if(nbPlayer > 0) //character only
					{
						mapLine += '@';
					}

					
					if( nbObj > 0 && nbBoxe > 0 ) //boxe on obj
					{
						mapLine += '*';
					}
					else if(nbBoxe > 0) //boxe only
					{
						mapLine += '$';
					}
					
					
					if(nbObj > 0 && nbPlayer == 0 && nbBoxe == 0) //obj only
					{
						mapLine += '.';
					}
					
					if(nbWall > 0) //wall
					{
						mapLine += '#';
					}

					
				}
				else
				{
					mapLine += ' ';
				}
				

			}
			
			//System.out.println("Line : "+mapLine);
			mapStrs.add(mapLine);
			
		}
		
		
		
		return mapStrs;
	}
	
	/**
	 * get last played map 
	 * @return
	 */
	public GridMap getLastPlayedMap()
	{
		GridMap lastMap = null;
		try
		{
			//Open document
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(this.srcConfigFile);
			
			//get first element
			Element rootElt = (Element)doc.getDocumentElement();
			
			//get value of id game and file of levels
			this.lastPlayeLevel = Integer.valueOf(rootElt.getAttribute("idLastMap"));
			this.nameCurrentFile = rootElt.getAttribute("sourceMapFile");
			
			//get content of map
			lastMap = getMapByXML(rootElt);
			
			
			//Load file of levels
			this.loadXmlFile(this.nameCurrentFile);
			
			
		}
		catch(ParserConfigurationException pce) 
		{
			pce.printStackTrace();
		}
		catch(SAXException se) 
		{
			se.printStackTrace();
		}
		catch(IOException ioe) 
		{
			ioe.printStackTrace();
		}
		
		
		
		return lastMap;
	}
	
	
	/**
	 * return the last level played
	 * @return
	 */
	public int getLastPlayedLevel()
	{
		return this.lastPlayeLevel;
	}
	

	

}
