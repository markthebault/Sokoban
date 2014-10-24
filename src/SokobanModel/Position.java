package SokobanModel;
/**
 * @ Project : SokobanAndroidApp
 * @ File Name : Position.java
 * @ Date : 15/02/2012
 * @ Author : Mark THEBAULT
**/




public class Position extends Dimension
{
	/**
	 * Build a position (0,0)
	 */
	public Position()
	{
		super();
	}
	
	/**
	 * Build a position (x,y)
	 * @param x
	 * @param y
	 */
	public Position(int x, int y)
	{
		super(x,y);
	}
	
	/**
	 * make a sum of object position and parameter position
	 * @param p
	 * @return
	 */
	public Position getAdd(Position p)
	{
		return new Position(this.getX() + p.getX(), this.getY() + p.getY()); 
	}
	
	/**
	 * Translate the object position using p position
	 * @param p
	 */
	public void translate(Position p)
	{
		this.setX(this.getX() + p.getX());
		this.setY(this.getY() + p.getY());
	}

}
