/**
 * Implementation of in node of CORE language
 */
public class InNode {

    /*
     * Private members
     */

    // The children nodes of inNode
    private IDNode id;

    /*
     * Public members
     */

    // Public constructor
    InNode() {
       this.id = new IDNode();
    }

    /*
     * Parse the in node.
     * @ requires current token in Tokenizer t is start of in node (current token is "read")
     */
    public void parseIn (Tokenizer t) {

        // check for proper token
        if (!t.currentToken().equals("read")) {
            System.out.println("Parsing error: expected 'read'");
            System.exit(-1);
        }

        // Chomp "read" token
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

    // print in node to console
    public void printIn() {

        // print "read"
        System.out.print("read ");

        // print IDNode
        this.id.printID();

        // print ";" plus newline
        System.out.print(";\n");

    }

    // execute In node
    public void execIn() {

        // read IDValue(s)
        this.id.readIDValue();

    }

}
