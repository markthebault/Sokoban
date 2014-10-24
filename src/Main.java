import java.util.Scanner;
import SokobanModel.*;


public class Main 
{

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		Scanner sc = new Scanner(System.in);		
		System.out.print("Bonjour\n");
	
		
		TestGame test = new TestGame();
		
		while(!test.quit())
		{
			test.run();
		}
		

		
	}


}
