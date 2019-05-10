import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class PacManBoard2 {

	/**
	 * Reads from file of grid board. Each number of the board represent a 20x20 sqaure. 
	 * pre: called from game.java constructor
	 * post: returns char two d array of board
	 */
	public static char [] [] board(){
		try
		{
			//Declare varaiable
			String line;
			char [] [] gridData; 
			int rowCount=0,colCount=0;
			
			//Input File
			FileReader in1;
			BufferedReader readFile1;
			

			//Open Input file
			in1 = new FileReader("gridPac.txt");
			readFile1 = new BufferedReader (in1);

			//Checks number of rows/cols
			while ((line = readFile1.readLine()) != null)
			{
				//Count number of col for first line 
				if (rowCount ==0)
				{
					colCount = line.length(); 
				}
				rowCount++; 
			}

			//Close the input file 
			readFile1.close();
			in1.close(); 

			//Allocate space for grid Data
			gridData = new char[rowCount][colCount];

			//Input File for collecting grid data
			FileReader in2;
			BufferedReader readSlide;

			//Open Input file
			in2 = new FileReader("gridPac.txt");
			readSlide = new BufferedReader (in2);

			//Get grid data
			for (int row = 0; row <rowCount ; row++) 
			{
				for (int col = 0; col < colCount; col++) 
				{
					gridData[row][col] = (char)readSlide.read();
					print ( gridData[row][col]);
				}
				readSlide.readLine(); //read past end-of-line
			}
			
			//Close the input file 
			readSlide.close();
			in2.close(); 
			
			return gridData; 
		}
		catch (FileNotFoundException e) //Catch the error 
		{
			System.out.println("File cannot be found");
			System.err.println("FileNotFoundException" + e.getMessage());
		}
		catch (IOException e)
		{
			System.out.println("File cannot be read");
			System.err.println("IOException" + e.getMessage());
		}
		return null; 
	}

	private static void print(char c) {
		// TODO Auto-generated method stub
		
	}
}


