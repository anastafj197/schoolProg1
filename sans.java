// Frank Anastasia 
// p001 sans-comment-filter 
// Due Date 2/3/17
// Dr. Ladd 

import java.io.*;
import java.util.*;

public class sans {
	
	public static void main (String [] args) throws FileNotFoundException {

		System.out.println("* Enter a txt file name to strip *");

	    Scanner keyboard = new Scanner(System.in);

		String fileName = keyboard.nextLine();

		try {

			FileReader fRead = new FileReader(fileName);

			Scanner fileScan = new Scanner(fRead);

			String fileString = "";

			while(fileScan.hasNextLine()) {
				String current = fileScan.nextLine();
				//System.out.println(current + "curr");
				if (current.contains("//")) {
					//System.out.println("Work or nah!");
					fileScan.nextLine();
					fileString += "\n";
					//System.out.println("in the if");
				} else {

				//System.out.println(current + " curr");

			

					fileString += current + "\n";
				}
					
			}

			System.out.println(fileString);

		} catch (FileNotFoundException ex) {
			System.out.println("File not even found man");
		}

	}
}