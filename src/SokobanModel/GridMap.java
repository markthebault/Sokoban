/**
 * @ Project : SokobanAndroidApp
 * @ File Name : GridMap.java
 * @ Date : 15/02/2012
 * @ Author : Mark THEBAULT
**/

package SokobanModel;

import java.util.ArrayList;
import java.util.Collection;


public class GridMap
{
	private Collection<Cell> map;
	private String name;
	private Dimension sizeofMap;
	private Character character;
	
	/**
	 * Default constructor, it save the name
	 * @param name
	 */
	public GridMap(String name, Dimension sizeOfMap)
	{
		this(name, sizeOfMap, new ArrayList<Cell>());
	}
	
	/**
	 * Advanced constructor, it create the map
	 * @param name
	 * @param cells
	 */
	public GridMap(String name, Dimension sizeOfMap, Collection<Cell> cells)
	{
		//Save the name
		this.name = name;

		//get the size
		this.sizeofMap = sizeOfMap;
		
		//create map
		this.setMap(cells);
		
		//set character null
		this.character = null;
	}
	
	/**
	 * return if all objectives are completed
	 * @return
	 */
	public Boolean getAllObjectivesCompleteds()
	{
		boolean allObjComplete = true;
		boolean boxePresent;
		
		
		//list all cells
		for(Cell c : this.map)
		{
			//for each objective look if for the same position thez have a boxe
			if(c instanceof Objective)
			{
				//get cell on objective
				Collection<Cell> cells = this.getCells(c.getPosition());
				
				boxePresent = false;
				
				//look differents cells on objective
				for(Cell onObj : cells)
				{
					//if a cell correspond a one boxe it's good
					if(onObj instanceof Boxe)
					{
						boxePresent = true;
					}
				}
				
				//if they not have boxe on objective it's bad
				if(!boxePresent)
				{
					allObjComplete = false;
				}
			}
		}
		
		
		return allObjComplete;
	}
	
	/**
	 * return the map
	 * @return
	 */
	public Collection<Cell> getAllCells()
	{
		return this.map;
	}
	
	
	/**
	 * add a new cell in the map
	 * You can only insert one box for one position
	 * except for the boxe, it can be on one objective
	 * @param type
	 * @param pos
	 * @throws Exception 
	 */
	public void addCell(Cell cell) throws Exception
	{
		Exception excep = new SokobanModelException("Error an other cell is in this position");
		
		//find cells in this position
		Collection<Cell> cellsOnePosition = this.getCells(cell.getPosition());
		int countBoxe, countPlayer;
		
		//get character
		if(cell instanceof Character)
		{
			//get character if not existe
			if(this.character == null)
			{
				this.character = (Character)cell;
			}
			else //character are allready present
			{
				throw new SokobanModelException("Error character is allready exist");
			}
		}
		
		//insert the cell
		if(!(cell instanceof Wall))
		{
			//boxe can be on one objective
			if(!cellsOnePosition.isEmpty())
			{
				countBoxe = 0;
				countPlayer = 0;
				
				
				//find the cells at this position
				for(Cell c : cellsOnePosition)
				{
					if(c instanceof Boxe) countBoxe++;
					if(c instanceof Character) countPlayer++;
				}
				
				//forbid player and boxe in same place
				if(countBoxe > 0 && cell instanceof Character)
				{
					throw excep;
				}
				
				if(countPlayer > 0 && cell instanceof Boxe)
				{
					throw excep;
				}
				
							
			}
			

		}
		else
		{
			//can insert the cell only on empty position
			if(!cellsOnePosition.isEmpty())
			{
				throw excep;
			}
		}
		
		//insert the boxe
		this.map.add(cell);
	}
	
	
	/**
	 * get the cell that they are in the position pos
	 * @param pos
	 * @return
	 */
	public Collection<Cell> getCells(Position pos)
	{
		//container of cells
		Collection<Cell> matches = new ArrayList<Cell>();
		
		//browse all cells
		for(Cell cell : this.map)
		{
			//compare position of all cell
			if(cell.getPosition().equals(pos))
			{
				matches.add(cell);
			}
		}
		
		
		return matches;
	}
	
	
	/**
	 * change the position of the boxe
	 * @param pos
	 * @param dir
	 */
	public void moveBoxe(Boxe boxe, Direction dir) throws Exception
	{
		//Get the next boxe
		Collection<Cell> nextCells = this.getCells( boxe.getPosition().getAdd(this.dirToPos(dir)) );
		
	
		//control the next boxes
		if(!nextCells.isEmpty())
		{
			//look the around cells
			for(Cell c : nextCells)
			{
				if(!(c instanceof Objective) )
				{
					throw new SokobanModelException("Can't move the boxe");
				}
			}
		}
		
		//move the boxe
		boxe.setPosition( boxe.getPosition().getAdd(this.dirToPos(dir)) );
	}

	
	/**
	 * move the character
	 * @param pos
	 * @param dir
	 */
	public void moveCharacter(Direction dir) throws Exception
	{
		
		if(this.character == null)
		{
			throw new SokobanModelException("Character don't exist !");
		}
		else
		{
			//get next element (nothing or cell)
			Position nextPos = this.character.getPosition().getAdd(this.dirToPos(dir));
			Collection<Cell> nextCells = this.getCells(nextPos );
	
			
			//look next cells
			for(Cell c : nextCells)
			{
				//if next element isn't a wall it could move
				if(!(c instanceof Wall) )
				{
					//if next element is a boxe, player can move if boxe can move
					if(c instanceof Boxe)
					{
						try
						{
							//try to move the next boxe
							this.moveBoxe((Boxe) c, dir);
						}
						catch(Exception e)
						{
							throw new SokobanModelException("Player can't move because next boxe cant move");
						}
					}

				}
				else
				{
					throw new SokobanModelException("Player cant move because there are a wall");
				}
			}
			
			//move the character
			this.character.setPosition( this.character.getPosition().getAdd( this.dirToPos(dir)) );
		}


	}
	
	/**
	 * set the map
	 * @param cells
	 */
	public void setMap(Collection<Cell> cells)
	{
		this.map = cells;
	}
	
	
	/**
	 * get the name of the map
	 * @return
	 */
	public String getName()
	{
		return this.name;
	}
	
	
	/**
	 * get type of the cell
	 * @param cell
	 * @return
	 */
	public TypeCell getTypeCell(Cell cell)
	{
		TypeCell type = null;
		
		
		//look the type
		if(cell instanceof Boxe)
		{
			type = TypeCell.BOXE;
		}
		else if (cell instanceof Wall)
		{
			type = TypeCell.WALL;
		}
		else if (cell instanceof Objective)
		{
			type = TypeCell.OBJECTIVE;
		}
		else if (cell instanceof Character)
		{
			type = TypeCell.CHARACTER;
		}
		else
		{
			//nothing
		}
		
		
		return type;
	}
	
	
	/**
	 * Convert a direction to a relative position
	 * @param d
	 * @return
	 */
	public Position dirToPos(Direction d)
	{
		Position deplacement = null;
		
		/*
		 * Create direction
		 * -----> ydir
		 * î xdir
		 */
		switch (d)
		{
		case UP:
			deplacement = new Position(-1,0);
			break;
		
		case DOWN:
			deplacement = new Position(1,0);
			break;
			
		case LEFT:
			deplacement = new Position(0,-1);
			break;
			
		case RIGHT:
			deplacement = new Position(0,1);
			break;
			
		default: deplacement = new Position(0,0);
			break;
		}
		
		return deplacement;
	}
	
	/**
	 * check the position
	 * @param p
	 * @return
	 */
	public boolean isValidPosition(Position p)
	{
		boolean validity = true;
		
		//TODO make validity of the position
		
		//if(p.getX() >= 0 || p.getX() )
		
		return validity;
	}
	
	
	/**
	 * get size of map
	 * @return
	 */
	public Dimension getMapSize()
	{
		return this.sizeofMap;
	}
	
	
	/**
	 * return the character of the game
	 * @return
	 */
	public Character getCharacter()
	{
		return this.character;
	}
	
	
	/**
	 * translate the map
	 */
	public String toString()
	{
		String mapS = new String("");
		ArrayList<Cell> cellsOnePosition;
		int countBoxe;
		int countPlayer;
		
		for(int i=0; i<this.sizeofMap.getX(); i++)
		{
			for(int j=0; j<this.sizeofMap.getY(); j++)
			{
				cellsOnePosition = (ArrayList<Cell>)this.getCells(new Position(i,j));
				
				if(cellsOnePosition.size() > 1)
				{
					countBoxe = 0;
					countPlayer = 0;
					
					for(Cell c : cellsOnePosition)
					{
						if(c instanceof Boxe) countBoxe++;
						if(c instanceof Character) countPlayer++;
					}
					
					if(countPlayer > 0)
						mapS += "+";
					
					
					if(countBoxe > 0)
						mapS += "*";
				}
				else if (cellsOnePosition.size() == 1)
				{
					for(Cell c : cellsOnePosition)
					{
						if(c instanceof Boxe) mapS += "$";
						if(c instanceof Objective) mapS += ".";
						if(c instanceof Character) mapS += "@";	
						if(c instanceof Wall) mapS += "#";
					}
				}
				else
				{
					mapS += " ";
				}
			}
			mapS += "\n";
		}
		
		/*TypeCell type = null;
		int nbCellByPosition;
		Collection<Cell> cellsCopy = this.map;
		Collection<Cell> cellsOnOnePosition;
		
		//print each cell
		for(Cell c : cellsCopy)
		{
			type = this.getTypeCell(c);
			cellsOnOnePosition = this.getCells(c.getPosition());
			nbCellByPosition = cellsOnOnePosition.size();
			
			switch(type)
			{
			case WALL:
				mapS += "#";
				break;
			
			case BOXE:
				if(nbCellByPosition > 1)
					mapS += "$";
				else
					mapS += "*";
				break;
			
			case OBJECTIVE:
				if(nbCellByPosition > 2)
				{
					if(c.getPosition().equals(this.character.getPosition()))
					{
						mapS += "+";
					}
					else
					{
						mapS += "*";
					}
				}
				else
				{
					mapS += ".";
				}
				break;
				
			case CHARACTER:
				if(nbCellByPosition > 2)
				{
					mapS += "+";
				}
				else
				{
					mapS += "@";
				}
				break;
			
			default: mapS += " ";
				break;
			}
			
			cellsCopy.removeAll(cellsOnOnePosition);
		}*/
		
		return mapS;
	}
}
