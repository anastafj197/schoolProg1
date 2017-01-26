// Frank Anastasia 
// p001 sans-comment-filter 
// Due Date 2/3/17
// Dr. Ladd 

import java.io.*;
import java.util.*;

public class sans {
	
	public static void main (String [] args) throws FileNotFoundException {

		System.out.println("Please enter a file name to strip");

	    Scanner keyboard = new Scanner(System.in);

		String fileName = keyboard.nextLine();

		try {

			FileReader fRead = new FileReader(fileName);

			Scanner fileScan = new Scanner(fRead);

			String fileString = "";

			while(fileScan.hasNextLine()) {
				if (fileScan.nextLine().contains("\\")) {
					fileScan.nextLine();
				}
				
				fileString += fileScan.nextLine() + "\n";


			}

			System.out.println(fileString);

		} catch (FileNotFoundException ex) {
			System.out.println("File not even found man");
		}

	}
}