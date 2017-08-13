/**
 *   Cameron Utsman
 *   utsman.1@osu.edu
 *   CSE 3341 MWF 3-3:55
 *   Prof. Morris
 *   Lab 3 -- Core Executor
 *   3/22/17
 *
 *   Updated: 3/22/17
 */

import java.io.IOException;

public class Executor {

    /*
     *  Main method -- Get Core file name, create program node, execute program node
     */
    public static void main(String[] args) throws IOException {


        // Check for valid number of inputs
        if (args.length == 1) {

            // Get name of core file to execute
            String fileName = args[0];

            // Tokenize core file and get ProgramNode
            Tokenizer tokens = new Tokenizer();
            ProgramNode program = tokens.tokenize(fileName);

            // execute programNode
            program.execProg();

        } else {

            // report error and end
            System.out.print("Error: Too many inputs. Please input a single file.\n");
        }

    }

}
