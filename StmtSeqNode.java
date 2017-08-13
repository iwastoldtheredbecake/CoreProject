/**
 * Implementation of StmtSeqNode of CORE language
 */
public class StmtSeqNode {

    /*
     * Private members
     */

    // The children nodes of StmtSeqNode
    private StmtNode s;
    private StmtSeqNode ss;

    // The selection specifier
    private int selection;

    // the indent of StmtSeq node
    private int indent;

    /*
     * Public members
     */

    // Public constructor
    StmtSeqNode(int ind) {
        this.indent = ind;
        this.s = new StmtNode(this.indent);
        this.ss = null;
        this.selection = 1;
    }

    /*
     * Parse the stmt seq node.
     * @ requires current token in Tokenizer t is start of stmt seq node
     */
    public void parseStmtSeq(Tokenizer t) {

        // Parse first stmt node
        this.s.parseStmt(t);

        // Is there another Stmtseq Node?
        if (t.currentToken().equals("while") || t.currentToken().equals("if") || t.currentToken().equals("read")
                || t.currentToken().equals("write") || Character.isUpperCase(t.currentToken().charAt(0))) {

            // Update selection
            this.selection = 2;

            // Parse StmtSeqNode
            this.ss = new StmtSeqNode(this.indent);
            this.ss.parseStmtSeq(t);
        }

    }

    // print stmt seq node to console
    public void printStmtSeq(){

        for (int i = 0; i < this.indent; i++) {
            System.out.print(" ");
        }

        // Print first stmt Node
        this.s.printStmt();

        // Print StmtSeqNode if it exists
        if (this.selection == 2) {
            this.ss.printStmtSeq();

        }

    }

    // execute stmt seq node
    public void execStmtSeq(){

        // execute statement
        this.s.execStmt();

        // if second stmtseq, execute stmtseq
        if (this.selection == 2) {
            this.ss.execStmtSeq();
        }

    }

}
