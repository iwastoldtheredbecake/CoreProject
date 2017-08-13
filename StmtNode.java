/**
 * Implementation of StmtNode of CORE language
 */
public class StmtNode {

    /*
     * Private members
     */

    // The children nodes of StmtNode
    private  AssignNode ass;
    private IfNode iff;
    private LoopNode loop;
    private InNode in;
    private OutNode out;

    // The selection specifier
    private int selection;

    // the indent of Stmt node
    private int indent;

    /*
     * Public members
     */

    // Public constructor
    StmtNode(int ind) {
        this.indent = ind;
        this.ass = null;
        this.iff = null;
        this.loop = null;
        this.in = null;
        this.out = null;
        this.selection = 0;
    }


    /*
     * Parse the stmt node.
     * @ requires current token in Tokenizer t is start of stmt node
     */
    public void parseStmt(Tokenizer t) {

        // is stmt option assign?
        if (Character.isAlphabetic(t.currentToken().charAt(0)) && Character.isUpperCase(t.currentToken().charAt(0))) {

            // update selection
            this.selection = 1;

            // parse assign node
            this.ass = new AssignNode();
            this.ass.parseAssign(t);

        }

        // or is stmt option if?
        else if (t.currentToken().equals("if")) {

            // update selection
            this.selection = 2;

            // parse if node
            this.iff = new IfNode(this.indent);
            this.iff.parseIf(t);

        }

        // or is stmt option loop?
        else if (t.currentToken().equals("while")) {

            // update selection
            this.selection = 3;

            // parse loop node
            this.loop = new LoopNode(this.indent);
            this.loop.parseLoop(t);

        }

        // or is stmt option in?
        else if (t.currentToken().equals("read")) {

            // update selection
            this.selection = 4;

            // parse in node
            this.in = new InNode();
            this.in.parseIn(t);

        }

        // or is stmt option out?
        else if (t.currentToken().equals("write")) {

            // update selection
            this.selection = 5;

            // parse out node
            this.out = new OutNode();
            this.out.parseOut(t);
        }

        // else error
        else {
            System.out.println("Parsing Error in StmtMode");
            //System.exit(-1);
        }

    }

    // print stmt node to console
    public void printStmt() {

        // determine which option to print
        if (this.selection == 1) {

            // printAssign
            this.ass.printAssign();

        } else if (this.selection == 2) {

            //print if
            this.iff.printIf();

        } else if (this.selection == 3) {

            // print loop
            this.loop.printLoop();

        } else if (this.selection == 4) {

            // print in
            this.in.printIn();

        } else if (this.selection == 5) {

            // print out
            this.out.printOut();

        } else {

            // error
            // this should not ever happen, parsing errors would preculde reaching printStmt
        }

    }

    // execute statement node
    public void execStmt() {

        // execute assign OR if OR loop OR in OR out
        if (this.selection == 1) {

            // execute assign
            this.ass.execAss();

        } else if (this.selection == 2) {

            // execute if
            this.iff.execIf();

        } else if (this.selection == 3) {

            // execute loop
            this.loop.execLoop();

        } else if (this.selection == 4) {

            // execute in
            this.in.execIn();

        } else if (this.selection == 5) {

            // execute out
            this.out.execOut();

        } else {

            // error
            // this should never happen
        }

    }

}
