/**
 * Implementation of assign node of CORE language
 */
public class AssignNode {

    /*
     * Private members
     */

    // The children nodes of AssignNode
    private IDNode id;
    private ExpNode exp = new ExpNode();

    /*
     * Public members
     */

    // Public constructor
    AssignNode() {
        this.id = new IDNode();
        this.exp = new ExpNode();
    }

    /*
     * Parse the assign node.
     * @ requires current token in Tokenizer t is start of assign node (current token is id token)
     */
    public void parseAssign(Tokenizer t) {

        // parse id node
        this.id.parseID(t, false);

        // has this id been declared?
        if (!this.id.isDecl(this.id.getValue())) {

            // error
            System.out.println("Error. Assign to undeclared variable");
            System.exit(-1);

        }

        // check for proper token
        if (!t.currentToken().equals("=")) {
            System.out.println("Parsing error: expected '='");
            System.exit(-1);
        }

        // Chomp "=" token
        t.nextToken();

        // parse exp node
        this.exp.parseExp(t);

        // check for proper token
        if (!t.currentToken().equals(";")) {
            System.out.println("Parsing error: expected ';'");
            System.exit(-1);
        }

        // chomp ";" token
        t.nextToken();

    }

    // print assign node to console
    public void printAssign() {

        // print id
        this.id.printID();

        // print " ="
        System.out.print(" = ");

        // print exp
        this.exp.printExp();

        // print ";" and newline
        System.out.print(";\n");
    }

    // execute assign node
    public void execAss() {

        // assign value of expression to id
        this.id.setValue(this.exp.evalExp());

    }

}
