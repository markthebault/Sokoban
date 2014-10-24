package SokobanModel;

import java.util.ArrayList;

/**
 * @ Project : SokobanAndroidApp
 * @ File Name : Game.java
 * @ Date : 15/02/2012
 * @ Author : Mark THEBAULT
**/




public class Game
{
	private GridMap map;
	private int currentLevel;
	private FileHandler fileManager;
	
	public Game(FileHandler fm)
	{
		//start level
		this.currentLevel = 0;
		
		//get the File handler
		this.fileManager = fm;
		
	}
	
	/**
	 * Start the game
	 */
	public void newGame()
	{
		//get the maps
		this.map = this.fileManager.getMap(this.getCurrentLevel());
	}
	
	/**
	 * Move the character
	 * @param d
	 */
	public void movePlayer(Direction d)
	{
		try
		{
			this.getCurrentGridMap().moveCharacter(d);
		}
		catch(Exception e)
		{
			System.out.print(""+e+"\n");
		}
	}
	
	/**
	 * return current gridMap
	 * @return
	 */
	public GridMap getCurrentGridMap()
	{
		return this.map;
	}
	
	
	/**
	 * Load last played map
	 */
	public void resumeGame()
	{
		this.currentLevel = this.fileManager.getLastPlayedLevel();
		
		this.map = this.fileManager.getLastPlayedMap();
	}
	
	
	/**
	 * return a state that say if level is finish
	 * @return
	 */
	public Boolean isFinishLevel()
	{
		return this.getCurrentGridMap().getAllObjectivesCompleteds();
	}
	
	/**
	 * get number of current level
	 * @return
	 */
	public int getCurrentLevel()
	{
		return this.currentLevel;
	}
	
	
	/**
	 * Says if all levels are finished
	 * @return
	 */
	public Boolean isFinishedAllLevels()
	{
		//TODO
		//return ( this.currentLevel == this.maps.size() );
		return false;
	}
	
	
	/**
	 * increase the level
	 */
	public void nextLevel()
	{
		//change level
		this.currentLevel++;

		//load next map
		this.map = this.fileManager.getMap(this.currentLevel);
	}
	
	/**
	 * write the map
	 */
	public String toString()
	{
		return this.getCurrentGridMap().toString();
	}
	
	
	/**
	 * Load a new levels
	 * @param srcFile
	 */
	public void changeLevelsFile(String srcFile)
	{
		//change file
		this.fileManager.loadXmlFile(srcFile);
		
		//start from first level
		this.currentLevel = 0;
		
		//get the maps
		this.map = this.fileManager.getMap(this.getCurrentLevel());
	}
	
	/**
	 * Save actual game in a file
	 */
	public void saveActualGame()
	{
		this.fileManager.saveCurrentGame(this.map, this.currentLevel);
	}
}
