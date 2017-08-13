/**
 * Implementation of loop node of CORE language
 */
public class LoopNode {

    /*
     * Private members
     */

    // The children nodes of LoopNode
    private CondNode cond;
    private StmtSeqNode ss;

    // the indent of Loop node
    private int indent;

    /*
     * Public members
     */

    // Public constructor
    LoopNode(int ind) {
        this.indent = ind;
        this.cond = new CondNode();
        this.ss = new StmtSeqNode(this.indent + 2);
    }

    /*
     * Parse the loop node.
     * @ requires current token in Tokenizer t is start of loop node (current token is "while")
     */
    public void parseLoop(Tokenizer t) {

        // check for proper token
        if (!t.currentToken().equals("while")) {
            System.out.println("Parsing error: expected 'while'");
            System.exit(-1);
        }

        // chomp "while" token
        t.nextToken();

        // parse cond node
        this.cond.parseCond(t);

        // check for proper token
        if (!t.currentToken().equals("loop")) {
            System.out.println("Parsing error: expected 'loop'");
            System.exit(-1);
        }

        // chomp "loop" token
        t.nextToken();

        // parse stmt seq node
        this.ss.parseStmtSeq(t);

        // check for proper token
        if (!t.currentToken().equals("end")) {
            System.out.println("Parsing error: expected 'end'");
            System.exit(-1);
        }

        // chomp "end" token
        t.nextToken();

        // check for proper token
        if (!t.currentToken().equals(";")) {
            System.out.println("Parsing error: expected ';'");
            System.exit(-1);
        }

        // chomp ";" token
        t.nextToken();

    }

    // print loop node to console
    public void printLoop() {

        // print "while"
        System.out.print("while ");

        // print cond node
        this.cond.printCond();

        // print "loop" plus newline
        System.out.print("loop\n");

        // print stmt seq node
        this.ss.printStmtSeq();

        // print indent
        for (int i = 0; i < this.indent; i++) {
            System.out.print(" ");
        }

        // print "end;"
        System.out.println("end;");

    }

    // execute loop node
    public void execLoop() {

        // evaluate cond
        boolean c = this.cond.evalCond();

        // execute stmt seq while c
        while (c) {

            // execute stmt seq
            this.ss.execStmtSeq();

            // reevaluate c
            c = this.cond.evalCond();

        }

    }

}
