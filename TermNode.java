/**
 * Implementation of term node of CORE language
 */
public class TermNode {

    /*
     * Private members
     */

    // The children nodes of TermNode
    private FacNode fac;
    private TermNode term;
    private int selection;

    /*
     * Public members
     */

    // Public constructor
    TermNode() {
        this.fac = new FacNode();
        this.term = null;
        this.selection = 1;
    }

    /*
     * Parse the term node.
     * @ requires current token in Tokenizer t is start of term node (current token is fac node)
     */
    public void parseTerm(Tokenizer t) {

        // parse the fac node
        this.fac.parseFac(t);

        // check selection
        if(t.currentToken().equals("*")) {

            // Chomp "*" token
            t.nextToken();

            // update selection
            this.selection = 2;

            // parse term node
            this.term = new TermNode();
            this.term.parseTerm(t);

        }

    }

    // print term node to console
    public void printTerm(){

        // print fac node
        this.fac.printFac();

        // check selection
        if (this.selection == 2) {

            // print "*"
            System.out.print(" * ");

            // print term node
            this.term.printTerm();

        }

    }

    // return value of term node as string
    public String evalTerm() {

        // the value to return
        String value = "";

        if (this.selection == 1) {

            // evaluate option 1: <fac>
            value = this.fac.evalFac();

        } else if (this.selection == 2) {

            // evaluate option 2: <fac> * <term>
            value = this.fac.evalFac();
            String valueTerm = this.term.evalTerm();

            // perform integer multiplication
            int value1 = Integer.parseInt(value);
            int value2 = Integer.parseInt(valueTerm);
            int finalValue = value1 * value2;

            // convert back to string
            value = Integer.toString(finalValue);

        }

        // check for value greater than 99999999
        if (java.lang.Math.abs(Integer.parseInt(value)) > 99999999) {

            // error
            System.out.println("Error: Value exceeds 8 digits.");
            System.exit(-1);

        }

        return value;

    }

}
