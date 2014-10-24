import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Scanner;

import SokobanModel.*;


public class TestGame 
{
	private boolean quit;
	private Game game;
	private FileHandler fh;
	
	public TestGame()
	{
		this.quit = false;
		
		fh = new FileHandler("Data/gameConf.xml");//, "Data/Original.xml"
				
		game = new Game(fh);
		game.resumeGame();
		
		System.out.print(this.game+"\n");
	}
	
	public void run()
	{
		char lettre;
		Scanner sc = new Scanner(System.in);
		String line = sc.nextLine();
		
		if(line.length() > 0)
		{
			lettre = line.charAt(0);
			
			
			try
			{
				switch(lettre)
				{
				case 'p' :
					this.quit = true;
					
					this.game.saveActualGame();
					
				break;
				
				case 'z' :
					this.game.movePlayer(Direction.UP);
					break;
					
				case 'q':
					this.game.movePlayer(Direction.LEFT);
					break;
					
				case 's':
					this.game.movePlayer(Direction.DOWN);
					break;
					
				case 'd':
					this.game.movePlayer(Direction.RIGHT);
					break;
					
				default:break;
				}
				
				System.out.print(this.game+"\n");
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
		}
		
		if(this.game.isFinishLevel() && !this.game.isFinishedAllLevels())
		{
			this.game.nextLevel();
			
			System.out.print("NextLevel : \n"+this.game+"\n");
		}
		
		
		
		
		
		
	}
	
	public boolean quit()
	{
		return this.quit;
	}

	

}
