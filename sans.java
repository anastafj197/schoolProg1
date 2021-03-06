// Frank Anastasia 
// p001 sans-comment-filter 
// Due Date 1/27/17
// Dr. Ladd 

import java.io.*;
import java.util.*;

// sans comment filter extends the FilterReader 
// Used to remove java style comments to the file 
// that you specify in the comand line 

public class sans extends FilterReader {

	// constructor calls super 
	public sans (Reader in) {
		super(in);
	}

	// Boolean flag that tracks if we  
	// are inside of a comment or not 
	boolean commentFlag = false; 

	// overwritten read method calls in.read to fill the buffer 
	// Parameters: takes in a char [] buffer, int length and int from
	//			   the last character of the comment 
	// Returns: the number of characters to read 
	public int read(char[] buf, int from, int len) throws IOException {
		int numChars = 0;

		while (numChars == 0) {
			numChars = in.read(buf, from, len);
			if (numChars == -1) 
				return -1;
			
			int last = from; // set the last character to read 

			for (int i = from; i < from + numChars; i++) {
				if (!commentFlag) {  // if we arent in a comment 
					if (buf[i] == '/' && buf[i+1] == '/') { // check 
						i = i+2;
						while (buf[i] != '\n') {
							buf[i] = 0;
							i++;
						}
						//buf[i] = 0;
						
						commentFlag = true; // flag 
					}
					commentFlag = false;  // reset the flag 
					if (buf[i] == '/' && buf[i+1] == '*') {
						while (buf[i] != '/') {
							buf[i] = '\n';
							i++;
						}
						//buf[i] = '\n';
						commentFlag = true;
					} else {
						//buf[last] = '\n';
						buf[last++] = buf[i];

					}
				} else if (buf[i] == '*' && buf[i+1] == '/') {
					buf[i] = buf[i++];
					commentFlag = false;
				} 
			}
			numChars = last - from; // calculate remaining characters 
		}
		return numChars;
	}

	public int read() throws IOException {
		char[] buf = new char[1];
		int result = read(buf, 0, 1);
		if (result == -1)
			return -1;
		else 
			return (int) buf[0];
	}
		


	public static void main (String [] args) throws FileNotFoundException {

		//System.out.println("* Enter a txt file name to strip *");

	    //Scanner keyboard = new Scanner(System.in);

		//String fileName = keyboard.nextLine();

		try {

			if (args.length != 1)
				throw new IllegalArgumentException("Not even the right number of args man!");

			BufferedReader in  = new BufferedReader(new sans(new FileReader(args[0])));

			String line; // read line by line 

			while ((line = in.readLine()) != null)
				System.out.println(line);

			in.close();

		} catch (Exception e) {
			System.err.println(e);
			System.err.println("Usage: java sans$Test");
		}
	}
}

	/*		//FileReader fRead = new FileReader(fileName);

			//Scanner fileScan = new Scanner(fRead);

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
*/
