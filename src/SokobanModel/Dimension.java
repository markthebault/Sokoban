package SokobanModel;

public class Dimension
{
	private int x;
	private int y;
	
	
	/**
	 * Build a dimension (0,0)
	 */
	public Dimension()
	{
		this.x = 0;
		this.y = 0;
	}
	
	/**
	 * Build a dimension (x,y)
	 * @param x
	 * @param y
	 */
	public Dimension(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	/**
	 * get the Y
	 * @return
	 */
	public int getY()
	{
		return y;
	}
	
	
	/**
	 * Set the y
	 * @param y
	 */
	public void setY(int y)
	{
		this.y = y;
	}
	
	/**
	 * get the X
	 * @return
	 */
	public int getX()
	{
		return x;
	}
	
	
	/**
	 * Set the X
	 * @param x
	 */
	public void setX(int x)
	{
		this.x = x;
	}
	
	/**
	 * Compare the positions
	 * @param p
	 * @return
	 */
	public boolean equals(Position p)
	{
		if(p.getX() == this.x && p.getY() == this.y)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Write the position
	 */
	public String toString()
	{
		return new String("Position : ("+this.x+" , "+this.y+" )");
	}
}
