/**
 * Implementation of if node of CORE language
 */
public class IfNode {

    /*
     * Private members
     */

    // The children nodes of ifNode
    private CondNode cond;
    private StmtSeqNode ss;
    private StmtSeqNode ss2;

    // The selection specifier
    private int selection;

    // the indent of if node
    private int indent;

    /*
     * Public members
     */

    // Public constructor
    IfNode(int ind) {
        this.indent = ind;
        this.cond = new CondNode();
        this.ss = new StmtSeqNode(this.indent + 2);
        this.ss2 = null;
        this.selection = 1;
    }

    /*
     * Parse the if node.
     * @ requires current token in Tokenizer t is start of if node (current token is "if")
     */
    public void parseIf(Tokenizer t) {

        // check for proper token
        if (!t.currentToken().equals("if")) {
            System.out.println("Parsing error: expected 'if'");
            System.exit(-1);
        }

        // chomp "if" token
        t.nextToken();

        // parse cond node
        this.cond.parseCond(t);

        // check for proper token
        if (!t.currentToken().equals("then")) {
            System.out.println("Parsing error: expected 'then'");
            System.exit(-1);
        }

        // chomp "then" token
        t.nextToken();

        // parse stmt-seq node
        this.ss.parseStmtSeq(t);

        // check selection
        if (t.currentToken().equals("else")) {

            // update selection
            this.selection = 2;

            // chomp "else" token
            t.nextToken();

            // parse second stmt seq
            this.ss2 = new StmtSeqNode(this.indent + 2);
            this.ss2.parseStmtSeq(t);

        }

        // check for proper token
        if (!t.currentToken().equals("end")) {
            System.out.println("Parsing error: expected 'end'");
            System.exit(-1);
        }

        // chomp "end"
        t.nextToken();

        // check for proper token
        if (!t.currentToken().equals(";")) {
            System.out.println("Parsing error: expected ';'");
            System.exit(-1);
        }

        // chomp ";" token
        t.nextToken();

    }

    // print if node to console
    public void printIf() {

        // print "if"
        System.out.print("if ");

        // print cond node
        this.cond.printCond();

        // print "then"
        System.out.print("then\n");

        // print stmt seq
        this.ss.printStmtSeq();

        // check selection
        if (this.selection == 2) {

            // print indent
            for (int i = 0; i < this.indent; i++) {
                System.out.print(" ");
            }

            // print "else"
            System.out.print("else\n");

            // print second stmt seq
            this.ss2.printStmtSeq();
        }

        // print indent
        for (int i = 0; i < this.indent; i++) {
            System.out.print(" ");
        }

        // print "end;" and newline
        System.out.print("end;\n");

    }

    // execute if node
    public void execIf() {

        // evaluate condition
        boolean c = this.cond.evalCond();

        // if c, then execute stmt seq
        if (c) {

            // execute stmtseq
            this.ss.execStmtSeq();

        } else if (this.selection == 2) {

            // execute second stmtseq
            this.ss2.execStmtSeq();

        }

    }
}
