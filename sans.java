// Frank Anastasia 
// p001 sans-comment-filter 
// Due Date 1/27/17
// Dr. Ladd 

import java.io.*;
import java.util.*;

public class sans extends FilterReader {

	public sans (Reader in) {
		super(in);
	}

	boolean commentFlag = false; 
	
	public int read(char[] buf, int from, int len) throws IOException {
		int numChars = 0;

		while (numChars == 0) {
			numChars = in.read(buf, from, len);
			if (numChars == -1) {
				return -1;
			}
			int last = from;
			for (int i = from; i < from + numChars; i++) {

			}
		}

		return 2;

	}

	public static void main (String [] args) throws FileNotFoundException {

		System.out.println("* Enter a txt file name to strip *");

	    Scanner keyboard = new Scanner(System.in);

		String fileName = keyboard.nextLine();

		try {

			FileReader fRead = new FileReader(fileName);

			Scanner fileScan = new Scanner(fRead);

			String fileString = "";

			while (fileScan.hasNextLine()) {
				String current = fileScan.nextLine();
				//System.out.println(current + "curr");
				while (current.contains("//")) {
					//System.out.println("Works or nah!");
					if (!current.startsWith("//")) {
						char [] splitty = new char [current.length()];
						splitty = current.toCharArray();
						for (int i = 0; i < splitty.length; i++) {
							
							if (splitty[i] == '/' && splitty[i+1] == '/') {
								//System.out.println("Mid line comment");

								for (int k = i; k < splitty.length; k++) {
									splitty[k] = 0;
									//System.out.println("");

								}
								
							} else {
								System.out.print(splitty[i]);

							}
						}
						System.out.println();
					}
					if (fileScan.hasNextLine()) {
						current = fileScan.nextLine();
						//fileString += "\n";
					}
				} 
				if (current.startsWith("/*")) {
					//System.out.println("begining multi line comments");
					current = fileScan.nextLine();

					while (current.startsWith("*")) {
						current = fileScan.nextLine();
						break;
					}
				} else {

					//System.out.println(current + " curr");
					fileString += current + "\n";
				}
				//fileString += current + "\n";		
			}

			System.out.println(fileString);

		} catch (FileNotFoundException ex) {
			System.out.println("File not even found man");
		}

	}
}