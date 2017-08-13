/**
 *   Cameron Utsman
 *   utsman.1@osu.edu
 *   CSE 3341 MWF 3-3:55
 *   Prof. Morris
 *   Lab 1 -- Core Tokenizer
 *   2/14/17
 *
 *   Updated: 3/22/17
 */

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;


public final class Tokenizer {

    /*
     * Public Members
     */

    public int MAX = 99999999;

    // Public constructor
    public Tokenizer() {
        this.tokens = new LinkedList<String>();
        this.pastTokens = new LinkedList<String>();
    }

    /*
     * Return String of current token
     */
    public String currentToken() {

        // Check that tokens is not empty
        if (this.tokens.size() > 0) {
            //  return current token
            return this.tokens.peek();
        }
        else {
            // return error
            return ("ERROR");
        }
    }

    /*
     * advance to next token
     *
     * removes next token from this.tokens and stores into this.pastTokens
     *
     * @requires current token is not EOF token
     *
     */
    public void nextToken() {
        this.pastTokens.add(this.tokens.remove());
    }

    /*
     * Output all tokens in Tokenizer as strings
     *
     * @this -- the Tokenizer
     *
     */
    public void printTokens() {

        // print each token
        for (String token : this.tokens) {
            System.out.println(token);
        }
        System.out.println();

    }

    /*
     *  Output numeric values of tokens to the console
     *
     *  @this -- the Tokenizer
     */
    public void printNumbers() {

        // check which number token is
        for (String token : this.tokens) {

            if (TABLE.containsKey(token)) {

                // Token is reserved work or special symbol
                System.out.println(TABLE.get(token));

            }
            else if (token.equals("EOF")) {

                // Token is EOF
                System.out.println("33");

            }
            else if (Character.isDigit(token.charAt(0))) {

                // Token is an integer
                System.out.println("31");

            }

            else if (Character.isAlphabetic(token.charAt(0))) {

                // Token is an identifier
                System.out.println("32");

            }
            else {

                // Token has not been recognized
                System.out.println("Error. A token's type was not recognized. Token: " + token);

            }

        }

    }

    /*
     * Receives file name of Core source file, tokenizes files, and returns programNode
     *
     *
     */
    public ProgramNode tokenize(String fileName) throws IOException {

        // Create new ProgramNode to return
        ProgramNode program = new ProgramNode();

        //Initialize table
        initializeTable();

        // Create Scanner to read file
        try {

            // Open file for reading
            FileReader file = new FileReader(fileName);
            Scanner fileScan = new Scanner(file);

            // Read tokens from file
            this.readTokens(fileScan);

            // close file
            fileScan.close();
            file.close();

            program.parseProgramToken(this);

        } catch (FileNotFoundException e) {

            // Report error and exit
            System.out.println("File not found.");
            e.printStackTrace();
        }

        // return program node
        return program;

    }

    /*
     * Private Members
     */

    // max length of int or identifier
    private int VALIDLENGTH = 8;

    // Reserved words
    private static String[] RESERVEDANDSPECIAL = {"program", "begin", "end", "int", "if", "then", "else", "while", "loop",
            "read", "write", "and", "or", ";", ",", "=", "!", "[", "]", "(", ")", "+", "-", "*", "!=", "==", ">=", "<=", ">", "<"};

    // Token lookup table
    private static Map<String, Integer> TABLE = new HashMap<String, Integer>();

    // Queue of tokens
    private Queue<String> tokens; // tokens not yet processed
    private Queue<String> pastTokens; // tokens having been processed

    /*
     * Read tokens from file into Tokenizer
     *
     * @this -- the Tokenizer to read tokens into
     *
     * @param fileScan -- the input file
     */
    private void readTokens(Scanner fileScan){

        // Current line number in file -- used for error reporting
        int lineNum = 1;

        // Read file line by line
        while (fileScan.hasNext()) {

            // Get current line
            String line = fileScan.nextLine();

            // Go through tokens in line
            for (String token: line.split(" ")) {

                // Resolve token
                if (token.length() > 0) {
                    this.resolveToken(token, lineNum);
                }
            }

            // increment linNum
            lineNum++;

        }

        // Add end of file
        this.tokens.add("EOF");

    }

    /*
     *  Determine if a potential token is valid and store into this.tokens if valid
     *  If token is not valid, print error message and exit
     *
     *  @param tokenIn -- the token or possible tokens to be evaluated
     *  @param linNum -- the line number in input file tokenIn is from
     *
     *  @updates -- this.tokens
     *
     *  @ensures -- any token added to tokens is a valid token
     */
    private void resolveToken(String tokenIn, int linNum) {

        // Deal with any leading whitespace (possible if recursive called)
        String token = tokenIn.trim();

        // if token in not ""
        if (token.length() > 0) {

            if (TABLE.containsKey(token)) {
                // Reserved word or special symbol

                // add to tokens
                this.tokens.add(token);

            }
            else if (token.endsWith(";")) {
                // Semicolon terminating line?

                // get token
                String tokenOne = token.substring(0, token.length() - 1);

                // resolve token
                this.resolveToken(tokenOne, linNum);

                // add ";" token
                this.tokens.add(";");

            }
            else if (Character.isDigit(token.charAt(0))) {
                // Integer

                // Check if valid integer
                boolean validNum = true;
                int i = 1;
                while (i < token.length() && validNum) {

                    // each char must be an int
                    validNum = (Character.isDigit(token.charAt(i)));
                    i++;

                }
                // If it is a valid integer, is it a valid length?
                if (validNum && token.length() <= VALIDLENGTH) {

                    // add to tokens
                    tokens.add(token);

                }
                // If not valid, is there a trailing special symbol(s)?
                else if (!Character.isAlphabetic(token.charAt(i - 1)) && !Character.isDigit(token.charAt(i - 1))) {

                    // add the valid first token
                    tokens.add(token.substring(0, i - 1));

                    // resolve the remaining token(s)
                    this.resolveToken(token.substring(i - 1), linNum);

                }
                // Otherwise, not a valid int
                else {

                    System.out.println("Error: Invalid Integer Token at line " + linNum);
                    System.exit(-1);

                }

            }
            else if (Character.isAlphabetic(token.charAt(0)) && Character.isUpperCase(token.charAt(0))) {
                // Identifier

                // Check if valid identifier
                boolean validID = true;
                int i = 0;
                while (i < token.length() && validID) {

                    // is char at i digit?
                    boolean isDigit = Character.isDigit(token.charAt(i));

                    // valid identifier may have uppercase character or digit at this point
                    validID = (Character.isUpperCase(token.charAt(i)) || isDigit);
                    i++;

                    // case character is digit, all remaining characters must also be a digit to be valid identifier
                    if (isDigit) {

                        // check remaing chars are all digits
                        while (i < token.length() && validID) {
                            validID = Character.isDigit(token.charAt(i));
                            i++;

                        }

                    }

                }

                // If valid identifier, is it valid length??
                if (validID && token.length() <= VALIDLENGTH) {

                    // Valid -- add to tokens
                    tokens.add(token);
                }
                // If not, is there a trailing special symbol(s)?
                else if (!Character.isAlphabetic(token.charAt(i - 1)) && !Character.isDigit(token.charAt(i - 1))) {

                    // add the valid first token
                    tokens.add(token.substring(0, i - 1));

                    // resolve remaining token(s)
                    this.resolveToken(token.substring(i - 1), linNum);

                }
                // Otherwise, not a valid identifier
                else {
                    System.out.println("Error: Invalid identifier at line " + linNum);
                    System.exit(-1);

                }

            }
            else if (!Character.isDigit(token.charAt(0)) && !Character.isAlphabetic(token.charAt(0))) {
                // Token does not begin with a letter or number.
                // Does it begin with a valid special symbol?

                // Get the first two char
                String firstTwo = "";
                if (token.length() >= 2) {
                    firstTwo = token.substring(0, 2);
                }

                // Get first char
                String firstOne = token.substring(0, 1);

                // Are either of these a valid special symbol?
                if (firstTwo.length() == 2 && TABLE.containsKey(firstTwo)) {

                    // Add the valid special symbol
                    tokens.add(firstTwo);

                    // resolve remaining token(s)
                    this.resolveToken(token.substring(2), linNum);

                } else if (TABLE.containsKey(firstOne)) {

                    // Add the valid special symbol
                    tokens.add(firstOne);

                    // resolve the remaining token(s)
                    this.resolveToken(token.substring(1), linNum);

                }
                // Else token is invalid symbol
                else {

                    System.out.println("Error: Invalid Symbol Token at line " + linNum);
                    System.exit(-1);

                }

            }
            else {
                // Else we must be dealing with an invalid reserved word or special symbol with no whitespace

                // Check for special symbol
                int i = 1;
                boolean specialSymbol = (!Character.isDigit(token.charAt(0)) && !Character.isAlphabetic(token.charAt(0)));
                while (i < token.length() && !specialSymbol) {
                    specialSymbol = (!Character.isDigit(token.charAt(i)) && !Character.isAlphabetic(token.charAt(i)));
                    i++;
                }

                // If special symbol with no white space, resolve the leading token and the trailing token(s)
                if (specialSymbol) {

                    // reslove leading token
                    this.resolveToken(token.substring(0, i - 1), linNum);

                    // resolve trailing token
                    this.resolveToken(token.substring(i - 1), linNum);

                }

                // else this token is an invalid reserved word
                else {

                    System.out.println("Error: Invalid Reserved Word '" + token + "' at line " + linNum);
                    System.exit(-1);

                }

            }

        }

    }

    /*
     *  Create the hashmap TABLE to look up tokens
     */
    private static void initializeTable() {

        // add each reserved and special symbol to TABLE
        for (int i = 0; i < RESERVEDANDSPECIAL.length; i++) {
            TABLE.put(RESERVEDANDSPECIAL[i], i+1);
        }

    }

    /*
     *  Main method -- read file name and create Tokenizer for file, then create parsetree and print program
     */
    public static void main(String[] args) throws IOException {

        // Check for valid number of inputs
        if (args.length == 1) {

            // Get file to read
            String fileName = args[0];

            //Initialize table
            initializeTable();

            // Create Scanner to read file
            try {

                // Open file for reading
                FileReader file = new FileReader(fileName);
                Scanner fileScan = new Scanner(file);

                // Create tokenizer
                Tokenizer tokens = new Tokenizer();

                // Read tokens from file
                tokens.readTokens(fileScan);

                // Parse the tokens
                ProgramNode program = new ProgramNode();
                program.parseProgramToken(tokens);

                // Print the program
                program.printProgram();

                // close file
                fileScan.close();
                file.close();

            } catch (FileNotFoundException e) {

                // Report error and exit
                System.out.println("File not found.");
                e.printStackTrace();
            }

        } else {

            // report error and end
            System.out.print("Error: Too many inputs. Please input a single file.\n");
        }
    }
}
