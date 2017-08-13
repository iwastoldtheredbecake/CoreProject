/**
 * Implementation of comp op node of CORE language
 */
public class CompOp {
    /*
     * Private members
     */

    // The children nodes of CompOpNode
    private String compOp;

    /*
     * Public members
     */

    // Public constructor
    CompOp() {
        this.compOp = null;
    }

    /*
     * Parse the comp op node.
     * @ requires current token in Tokenizer t is start of comp op node (current token "!=", "==", "<", ">", "<=", or ">=")
     */
    public void parseCompOp(Tokenizer t) {

        // get compOp token
        compOp = t.currentToken();

        // check for proper token
        if (!t.currentToken().equals("!=") && !t.currentToken().equals("==") && !t.currentToken().equals("<") &&
                !t.currentToken().equals(">") && !t.currentToken().equals("<=") && !t.currentToken().equals(">=")) {
            System.out.println("Parsing error: expecting comp op");
            System.exit(-1);
        }

        // chomp compOp token
        t.nextToken();

    }

    // return comp op
    public String getCompOp() {

        // return this.compOp
        return this.compOp;

    }

    public void printCompOp() {

        // print compOp
        System.out.print(" " + compOp + " ");

    }

}
