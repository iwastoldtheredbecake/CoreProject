/**
 * Implementation of DeclNode of CORE language
 */
public class DeclNode {

    /*
     * Private members
     */

    // The children nodes of DeclNode
    private IDNode id = new IDNode();

    /*
     * Parse the decl  node.
     * @ requires current token in Tokenizer t is start of decl node (current token = 'int')
     */
    public void parseDecl(Tokenizer t) {

        // chomp "int" node
        t.nextToken();

        // parse IDnode
        this.id.parseID(t, true);

        // check if id has already been declared
        if (!this.id.isDecl(this.id.getValue())) {

            // set id as declared
            // this step really isn't necessary
            this.id.setDeclared();

        } else {

            // error
            System.out.println("Error: ID already declared.");
            System.exit(-1);

        }

        // check for proper token
        if (!t.currentToken().equals(";")) {
            System.out.println("Parsing error: expected ';'");
            System.exit(-1);
        }

        // chomp ";" node
        if (t.currentToken().equals(";")) {
            t.nextToken();
        }

    }

    // Prints Decl node to console
    public void printDecl() {

        // print "int"
        System.out.print("int ");

        // print id node
        this.id.printID();

        // print ";" and newline
        System.out.print(";\n");

    }

}
