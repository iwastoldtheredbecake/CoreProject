/**
 * Implementation of DeclSeqNode of CORE language
 */
public class DeclSeqNode {

    /*
     * Private members
     */

    // The children nodes of DeclSeqNode
    private DeclNode d;
    private DeclSeqNode ds;

    // The selection specifier
    private int selection;

    // the indent of DeclSeq node
    private int indent;

    /*
     * Public members
     */

    // Public constructor
    DeclSeqNode(int ind) {
        this.indent = ind;
        this.d = new DeclNode();
        this.ds = null;
        this.selection = 1;
    }

    /*
     * Parse the decl seq node.
     * @ requires current token in Tokenizer t is start of decl seq node
     */
    public void parseDeclSeq(Tokenizer t) {

        // Parse DeclNode
        this.d.parseDecl(t);

        // Is there another DeclNode?
        if (t.currentToken().equals("int")) {

            // Update selection
            this.selection = 2;

            // Parse next DeclSeqNode
            this.ds = new DeclSeqNode(this.indent);
            this.ds.parseDeclSeq(t);

        }

    }

    // Prints DeclSeq node to console
    public void printDeclSeq() {

        // print indent
        for (int i = 0; i < this.indent; i++) {
            System.out.print(" ");
        }

        // Print DeclNode
        this.d.printDecl();

        // Print DeclSeqNode if it exists
        if (this.selection == 2) {
            this.ds.printDeclSeq();
        }

    }

}
