/**
 * Implementation of exp node of CORE language
 */
public class ExpNode {

    /*
     * Private members
     */

    // The children nodes of ExpNode
    private TermNode term = new TermNode();
    private ExpNode exp = null;
    private int selection = 1;

    /*
     * Public members
     */

    // Public constructor
    ExpNode() {
        this.term = new TermNode();
        this.exp = null;
        this.selection = 1;
    }

    /*
     * Parse the exp node.
     * @ requires current token in Tokenizer t is start of exp node (current token is term node)
     */
    public void parseExp(Tokenizer t) {

        // parse first term node
        this.term.parseTerm(t);

        // check selection
        if (t.currentToken().equals("+")) {
            // term + exp option

            // update selection
            this.selection = 2;

            // chomp "+" token
            t.nextToken();

            // parse exp node
            this.exp = new ExpNode();
            this.exp.parseExp(t);

        } else if (t.currentToken().equals("-")) {
            // term - exp option

            // update selection
            this.selection = 3;

            // chomp "-" token
            t.nextToken();

            // parse exp token
            this.exp = new ExpNode();
            this.exp.parseExp(t);

        }

    }

    // print exp token to console
    public void printExp() {

        // print term node
        this.term.printTerm();

        // check selection
        if (this.selection == 2) {
            // term + exp option

            // print "+"
            System.out.print(" + ");

            // print termNode
            this.term.printTerm();

        } else if (this.selection == 3) {
            // term - exp option

            // print "+"
            System.out.print(" - ");

            // print termNode
            this.term.printTerm();
        }

    }

    /*
     * Evaluate exp node
     *
     * @Returns value of exp node
     *
     */
    public String evalExp() {

        // value to return
        String value = "";

        if (this.selection == 1) {

            // evaluate option 1: <term>
            value = this.term.evalTerm();

        } else if (this.selection == 2) {

            // evaluate option 2: <term> + <exp>
            value = this.term.evalTerm();
            String valueExp = this.exp.evalExp();

            // perform integer addition
            int value1 = Integer.parseInt(value);
            int value2 = Integer.parseInt(valueExp);
            int finalValue = value1 + value2;

            // convert back to string
            value = Integer.toString(finalValue);

        } else if (this.selection == 3) {

            // evaluate option 3: <term> - <exp>
            value = this.term.evalTerm();
            String valueExp = this.exp.evalExp();

            // perform integer subtraction
            int value1 = Integer.parseInt(value);
            int value2 = Integer.parseInt(valueExp);
            int finalValue = value1 - value2;

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
