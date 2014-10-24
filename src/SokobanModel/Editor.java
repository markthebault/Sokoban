package SokobanModel;
/**
 * @ Project : SokobanAndroidApp
 * @ File Name : Editor.java
 * @ Date : 15/02/2012
 * @ Author : Mark THEBAULT
**/




public class Editor
{
	private GridMap map;
	private FileHandler fileManager;
	
	
	public Editor(FileHandler fm)
	{
		this.fileManager = fm;
	}
	
	
	/**
	 * Set a cell
	 * @param cell
	 */
	public void setCell(Cell cell)
	{
		try
		{
			this.map.addCell(cell);
		}
		catch (Exception e) 
		{
			System.out.println(e);
		}
	}
	
	
	/**
	 * Get the map
	 * @return
	 */
	public GridMap getGridMap()
	{
		return this.map;
	}
	
	
	/**
	 * Create empty map
	 * @param name
	 * @param dim
	 */
	public void createEmptyMap(String name, Dimension sizeOfMap)
	{
		this.map = new GridMap(name, sizeOfMap);
	}
	
	
	/**
	 * Set gridmap
	 * @param map
	 */
	public void setGripMap(GridMap map)
	{
		this.map = map;
	}
	
	
	/**
	 * Save the map
	 */
	public void saveMap()
	{
		this.fileManager.saveNewMap(this.map);
	}
}
