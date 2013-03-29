/* Kenneth Smith - ASU ID: xxxxxxxxx
 * CSE 340 - T/Th 12pm-1:15pm - Project 1 - Lexical Scanner
 * 
 * This program scans an input file containing code in a language defined by
 * the following alphabet: 
 *              A-Z, a-z, 0-9, +, -, *, /, =. <, and >
 * 
 * The language contains 4 token types, as defined by:
 *      Reserved words: begin, end, if, while, do, then, print, int, real
 *      Literals: [0-9]+
 *      Symbols: +, -, *, /, =, >, <, <=, >=, <<, >>
 *      Identifiers: [A-Za-z][A-Za-z0-9]*
 * 
 * White space, " ", is used as the delimiter in determining token size.
 * 
 * Input: User input string name of text file, e.g. "Test.txt"
 * Output: Console output of token and derived token type (or error) 
 * 
 * How to run using general.asu.edu:
 *      From command line in general, type "javac Kenneth_Smith_Proj1.java",
 *      then after file if compiled, type "java Kenneth_Smith_Proj1"
 *      When the program prompts user, type in the name of the text file.
 *      DO NOT FORGET THE PROPER FILE EXTENSION.
 * 
 *      Note: input file must be on general or this program will throw an error.
 */
package lexicalscannerapp;
import java.io.*;
import java.util.*;
import java.util.Scanner;
import java.io.FileReader;
import java.io.IOException;

public class LexicalScannerApp
{
	 public static void main(String[] args) throws IOException
	 {
                String fileName = "";
		Scanner keyboard = new Scanner(System.in);
		System.out.print("Name of Input File: ");
		fileName = keyboard.next();
                grabToken(copyCharacters(fileName));
	 }

	 public static String copyCharacters(String fileName) throws IOException
	 {
		 /* read from a text file, line by line, and insert
		  * data into a string to be passed for further processing
		  *
                  * Variables:
                  *             data: string to pass contents of data file
                  *             in: BufferReader instance to read in data
                  *             str: converts BufferReader input to string
                  * 
		  * Input: String containing file name and extension
		  * Output: A string containing the contents of the text file
		  */

		 String data = "";
		 try
		 {
			 BufferedReader in = new BufferedReader(new FileReader(fileName));
			 String str;
			 while ((str = in.readLine()) != null)
			 {
				 data = data + " " + str;
			 }

			 in.close();
		}
		catch (IOException e) {}
		data = data + " ";
		return data;

	 }

	 public static String grabToken(String tokenData)
	 {
             /* places string data in to an array of characters, then uses 
              * elements equal to ' ' (white space) to determine length and to 
              * separate each token.
              * 
              * Variables: 
              *             tokens: string data converted to char[]
              *             token: string to store individual tokens
              *             k: counter for moving through char[]
              *             j: counter to determine length of token
              * 
              * Input: String containing the contents of a data file
              * Action: Convert string to char[] and back into string upon white space discovery.
              * Output: String containing 1 token
              */
             
		 char[] tokens = tokenData.toCharArray();
                 String token = "";
		 int k,j;
		 j = 0;
                 k = 0;

		 do
		 {
                    if(tokens[k] == ' ' && j < 1) //Skipping white space
                    {
			k++;
                        token = "";
                    }

                    else if(tokens[k] == ' ' && j >= 1) //end of token
                    {
                        token = token + " " + identifyType(token) + "\n";
                        System.out.print(token);
                        j = 0;
                        k++;
                        token = "";
                    }

                    else if(tokens[k] != ' ') //reading current token
                    {
                        j++;
                        token = token + tokens[k];
                        k++;
		   }
		 } while(k < tokenData.length());

		 return token;
	 }

	 public static String identifyType(String token)
	 {
             /* determines the token type of a given token
              * 
              * Variables:
              *             j: counter for moving through a token, 1 character at a time
              *             k: used to determine error type
              *             numberVal: used to determine the value of a numerical literal            
              * 
              * Input: String containing 1 token
              * Output: String representing the token type of a given token
              */            
                int j, k, numberVal;
                k = 0;
                j = 0;

                if(token.equals("+") || token.equals("-") || token.equals("*")
                   || token.equals("/") || token.equals("=") || token.equals("<")
                   || token.equals(">") || token.equals("<<") || token.equals(">>") 
                   || token.equals(">=") || token.equals("<="))
                         return "symbol";

                else if(token.equals("begin") || token.equals("end") || token.equals("if")
                        || token.equals("while") || token.equals("do") || token.equals("then")
                        || token.equals("print") || token.equals("int") || token.equals("real"))
                            return "reserved word";

                else if(Character.isDigit(token.charAt(j)))
                {
                       numberVal = 0;
                       if(token.length() <= 1)
                            return "literal";
                       else if(token.length() > 1)
                           while(j < token.length())
                           {
                                if(!Character.isDigit(token.charAt(j)) && 
                                  ((int)token.charAt(j) >= 65 && (int)token.charAt(j) <= 90)
                                   || ((int)token.charAt(j) >= 97 && (int)token.charAt(j) <= 122))
                                    return "ERROR CODE 2";
                                else if(!Character.isDigit(token.charAt(j)) &&
                                        (int)token.charAt(j) < 48 || ((int)token.charAt(j) > 57 && (int)token.charAt(j) < 65)
                                        || ((int)token.charAt(j) > 90 && (int)token.charAt(j) < 97) || (int)token.charAt(j) > 122)
                                    return "ERROR CODE 1";
                                
                                numberVal = 10 * numberVal + token.charAt(j) - '0';
                                j++;
                           }
                        return "literal";
                }

                else if(((int)token.charAt(j) >= 65 && (int)token.charAt(j) <= 90)
                        || ((int)token.charAt(j) >= 97 && (int)token.charAt(j) <= 122))
                {
                    if(token.length() <= 1)
                        return "identifier";

                    else if(token.length() > 1)
                        while(j < token.length())
                        {
                            if((int)token.charAt(j) < 48 || ((int)token.charAt(j) > 57 && (int)token.charAt(j) < 65)
                            || ((int)token.charAt(j) > 90 && (int)token.charAt(j) < 97) || (int)token.charAt(j) > 122)
                                    return "ERROR CODE 1";
                            j++;
                        }
                     return "identifier";
                }
                
                else if((int)token.charAt(j) < 48 || ((int)token.charAt(j) > 57 && (int)token.charAt(j) < 65)
                       || ((int)token.charAt(j) > 90 && (int)token.charAt(j) < 97) || (int)token.charAt(j) > 122)
                     return "ERROR CODE 1";
                
                return "";
         }
}
