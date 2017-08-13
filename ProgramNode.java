/**
 * Implementation of ProgramNode of CORE language
 */
public class ProgramNode {

    /*
     * Private members
     */

    // The children nodes of ProgramNode
    private DeclSeqNode ds;
    private StmtSeqNode ss;

    // the indent of Program node
    private int indent;

    /*
     * Public members
     */

    // Public constructor
    ProgramNode() {
        this.indent = 0;
        this.ds = new DeclSeqNode(this.indent + 2);
        this.ss = new StmtSeqNode(this.indent + 4);
    }

    /*
     * Parse the program node.
     * @ requires current token in Tokenizer t is program node
     */
    void parseProgramToken(Tokenizer t) {

        // check for proper token
        if (!t.currentToken().equals("program")) {
            System.out.println("Parsing error: expected 'program'");
            System.exit(-1);
        }

        // Chomp "program" token
        t.nextToken();

        // parse DeclSeqNode
        this.ds.parseDeclSeq(t);

        // check for proper token
        if (!t.currentToken().equals("begin")) {
            System.out.println("Parsing error: expected 'begin'");
            System.exit(-1);
        }

        // Chomp "begin" token
        t.nextToken();

        // parse StmtSeqNode
        this.ss.parseStmtSeq(t);

        // check for proper token
        if (!t.currentToken().equals("end")) {
            System.out.println("Parsing error: expected 'end'");
            System.exit(-1);
        }

        // Chomp "end" token
        t.nextToken();

        // Check for end of file
        if(!t.currentToken().equals("EOF")) {
            System.out.println("Parsing error: expected EOF");
            System.exit(-1);
        }

    }

    // Prints program node to console
    void printProgram() {

        // print "program"
        System.out.println("program");

        // Print DeclSeqNode
        this.ds.printDeclSeq();

        // print "begin"
        System.out.println("  begin");

        // Print StmtSeqNode
        this.ss.printStmtSeq();

        // print "end"
        System.out.println("  end");

    }

    // Executes program node
    void execProg() {

        // execute statement seq
        this.ss.execStmtSeq();

    }

}
