package SokobanModel;
/**
 * @ Project : SokobanAndroidApp
 * @ File Name : Cell.java
 * @ Date : 15/02/2012
 * @ Author : Mark THEBAULT
**/

public abstract class Cell
{
	private String name;
	private Position pos;
	

	/**
	 * Constructor
	 * @param pos
	 */
	public Cell(Position pos)
	{
		//Get the position
		this.pos = pos;
	}
	
	/**
	 * Return the postion of cell
	 * @return
	 */
	public Position getPosition()
	{
		return this.pos;
	}
	
	/***
	 * Set the position of cell
	 * @param pos
	 */
	public void setPosition(Position pos)
	{
		//Set the position
		this.pos = pos;
	}
}
