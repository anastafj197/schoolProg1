package clients;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.Scanner;
import java.io.InputStream;
import java.lang.Character;

/*
 * pig latin
 */

// HybridProxy acts as botha client and a proxy connecting to a text server 
// as well as a new client spitting out the requests from the text server then 
// converting them into piglatin and returning to client

public class HybridProxy {
    
    // new stuff for server hybrid
    
    /** Default port number; used if none is provided. */
    public final static int DEFAULT_PORT_NUMBER = 3950;

    /** Default machine name is the local machine; used if none provided. */
    public final static String DEFAULT_MACHINE_NAME = "localhost";

    /** Command-line switches */
    public final static String ARG_PORT = "--port";
    public final static String ARG_MACHINE = "--machine";
    
    /** Message op-codes */
    public final static String MSG_HELLO = "Hello";
    public final static String MSG_GOODBYE = "Goodbye";
    
    /** Port number of distant machine */
    private int portNumber;
    
    // new stuff for server hybrid
    private Socket server;
    
    //private ServerSocket newServer;
    
    public HybridProxy (String host, int port, int port2) {
        try {
            server = new Socket(host, port);
            System.out.println("first connection");
            this.portNumber = port2;
            //newServer = new ServerSocket(port2);
        } catch (Exception e) {
            System.out.println("whoops");
            System.exit(0);
        }
    }
    
    // run() spins up the program after the connection and gives 
    // the user a visually appealing prompt
    
    public void run() {
        try { 
            ServerSocket forNew = new ServerSocket(portNumber);
            System.out.format("Server now accepting connections on port %d!!!!\n",
            portNumber);
            
            Scanner sin = new Scanner(server.getInputStream());        
            PrintStream sout = new PrintStream(server.getOutputStream());
            
           // Scanner keyboard = new Scanner(System.in);
            
            Socket client;
            
            while ((client = forNew.accept()) != null) {
                System.out.format("Connection from %s\n", client);

                Scanner cin = new Scanner(client.getInputStream());
                PrintStream cout = new PrintStream(client.getOutputStream());

                String clientMessage = "";

                while (cin.hasNextLine()) {

                    String line = cin.nextLine();
                    System.out.println(line);
                         
                       sout.println(line);
                       System.out.println("sent to p");
                       String serverLine = sin.nextLine();
                       System.out.println(serverLine);
                       
                       //toPiglatin() returns a string 
                       cout.println(toPiglatin(serverLine));
                       //sends translated back to the new server                        
                       //cout.println(converted);

                }

                if (!clientMessage.isEmpty()) {
                    System.out.format("Server saw \"%s\" and is exiting.\n",
                    clientMessage);
                }

                cout.close();
                cin.close();
            }
        } catch (IOException ioe) {
            // there was a standard input/output error (lower-level from uhe)
            ioe.printStackTrace();
            System.exit(1);
        }

    }
    
    
    // gets sent serverLine to convert into piglatin 
    public String toPiglatin(String serverLine) {
        String translated = "";
        String accumulator = "";
        int count = 0; 
        int position = 0;
        
        
        //while (!serverLine.isEmpty()) {
        String[] words = serverLine.split(" ");
        for (String s : words) {
            //String[] words = serverLine.split(" ");
            int max = s.length();
            //char first = s.charAt(0);
            
            // if the last character in the word of the spot your on isnt a letter
            // its most likely a punctuation so grab the punction 
            // then do the piglatin on the word and add the punctation back
            //if (!Character.isLetter(s.charAt(s.length()))) {
            //    char end = s.charAt(s.length());
            //    s = s.substring(0, s.length());
            //}
            
            //if (!Character.isLetter(s.charAt(s.length()-1))) {
            //    if (!Character.isDigit(s.charAt(s.length()-1))) {
            //        char punt = s.charAt(s.length()-1);
            //       String stripped = s.substring(0,s.length()-2);
            //        
            //        accumulator = stripped + "-ay " + punt;
            //        System.out.println("punct!");
            //    } 
            //}
                        
            // if the first character in the word is not a letter 
            if (!Character.isLetter(s.charAt(0))) {
                accumulator += s + " ";
                count++;
            }
            
            
            // checks for all consants
            for (int i = 0; i < s.length(); i++) {
                if (!isVowel(s.charAt(i))) {
                    if (i == max) {
                        accumulator += s + " ";
                    }                   
                }
            }
            
            //if the first character in the word is a vowel
            if (isVowel(s.charAt(0))) {
                if (!Character.isLetter(s.charAt(s.length()-1))) {
                    if (!Character.isDigit(s.charAt(s.length()-1))) {
                        char punt = s.charAt(s.length()-1);
                        String stripped = s.substring(0,s.length()-2);
                        accumulator = stripped + "-ay " + punt;
                    } 
                } else {
                    accumulator += s + "-ay ";
                    count++;
                }
            }
            
            //if the first character in the word is a consanant
            if (!isVowel(s.charAt(0))) { 
                // if the first letter is uppcase and consanant
                if (Character.isUpperCase(s.charAt(0)) == true) {
                    System.out.println("Is upperCase");
                    String lower = s.toLowerCase();
                    System.out.println(lower);
                    for (int i = 0; i < lower.length(); i++) {
                        if (isVowel(lower.charAt(i))) {
                            position = i;
                            break;
                        }
                    }
                    String firstpart = lower.substring(position, lower.length());
                    String secondpart = lower.substring(0, position) + "-ay ";
                    lower = firstpart + secondpart; 
                   
                    char u = Character.toUpperCase(lower.charAt(0));
                    System.out.println(lower);
                    accumulator += u + lower.substring(1); 
                    count++; 
                } else {
                    // if the first letter is consanant and lowercase
                    for (int i = 0; i < s.length(); i++) {
                        if (isVowel(s.charAt(i))) {
                            position = i;
                            break;
                        }
                    }
                    String firstpart = s.substring(position, s.length());
                    String secondpart = "-" + s.substring(0, position) + "ay ";
                    accumulator += firstpart + secondpart; 
                    count++; 
                    
                }
            }
            
            //return translated += accumulator;
        }
        return accumulator; 
    }

    // compares the vowels with the passed character checking for vowels
    // returns true if its matches  
    public boolean isVowel(char v) {
        if (v == 'a') 
            return true;
        else if(v == 'e')
            return true;
        else if(v == 'i')
            return true;
        else if(v == 'o')
            return true;
        else if(v == 'u')
            return true;
        else
            return false;        
    }

        
    // Establishes the local host 
    
    public static void main(String[] args) {
        int port = DEFAULT_PORT_NUMBER;

        /* Parsing parameters. argNdx will move forward across the
        * indices; remember for arguments that have their own parameters, you
        * must advance past the value for the argument too.
        */
        int argNdx = 0;

        while (argNdx < args.length) {
            String curr = args[argNdx];

            if (curr.equals(ARG_PORT)) {
                ++argNdx;

                String numberStr = args[argNdx];
                port = Integer.parseInt(numberStr);
            } else {

                // if there is an unknown parameter, give usage and quit
                System.err.println("Unknown parameter \"" + curr + "\"");
                //usage(); instructions
                System.exit(1);
            }

            ++argNdx;
        }
 
        HybridProxy ec = new HybridProxy("localhost", 3939, 3950);
        ec.run();
    }
}
