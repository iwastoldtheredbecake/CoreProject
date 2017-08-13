/**
 * Implementation of out node of CORE language
 */
public class OutNode {

    /*
     * Private members
     */

    // The children nodes of inNode
    private IDNode id;

    /*
     * Public members
     */

    // Public constructor
    OutNode() {
        this.id = new IDNode();
    }

    /*
     * Parse the in node.
     * @ requires current token in Tokenizer t is start of out node (current token is "write")
     */
    public void parseOut (Tokenizer t) {

        // check for proper token
        if (!t.currentToken().equals("write")) {
            System.out.println("Parsing error: expected 'write'");
            System.exit(-1);
        }

        // Chomp "write" token
        t.nextToken();

        // parse IDNode
        this.id.parseID(t, false);

        // has this id been declared?
        if (!this.id.isDecl(this.id.getValue())) {

            // error
            System.out.println("Error. Use of undeclared ID");
            System.exit(-1);

        }

        // check for proper token
        if (!t.currentToken().equals(";")) {
            System.out.println("Parsing error: expected ';'");
            System.exit(-1);
        }

        // Chomp ";" token
        t.nextToken();

    }

    // print out node to console
    public void printOut() {

        // print "write"
        System.out.print("write ");

        // print IDNode
        this.id.printID();

        // print ";" plus newline
        System.out.print(";\n");

    }

    // execute out node
    public void execOut() {

        // print IDValue(s)
        this.id.printIDValue();

    }

}
