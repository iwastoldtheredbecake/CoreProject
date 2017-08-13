/**
 * Implementation of comp node of CORE language
 */
public class CompNode {

    /*
     * Private members
     */

    // The children nodes of CondNode
    private FacNode fac;
    private CompOp co = new CompOp();
    private FacNode fac2 = new FacNode();

    /*
     * Public members
     */

    // Public constructor
    CompNode() {
        this.fac = new FacNode();
        this.co = new CompOp();
        this.fac2 = new FacNode();
    }

    /*
     * Parse the comp node.
     * @ requires current token in Tokenizer t is start of comp node (current token is "(")
     */
    public void parseComp(Tokenizer t) {

        // check for proper token
        if (!t.currentToken().equals("(")) {
            System.out.println("Parsing error: expected '('");
            System.exit(-1);
        }

        // Chomp "(" token
        t.nextToken();

        // Parse first Fac node
        this.fac.parseFac(t);

        // Parse CompOp node
        this.co.parseCompOp(t);

        // Parse second Fac node
        this.fac2.parseFac(t);

        // check for proper token
        if (!t.currentToken().equals(")")) {
            System.out.println("Parsing error: expected '='");
            System.exit(-1);
        }

        // Chomp ")" token
        t.nextToken();

    }

    // Print comp node to console
    public void printComp() {

        // print "("
        System.out.print("( ");

        // print first Fac node
        this.fac.printFac();

        // print CompOp node
        this.co.printCompOp();

        // print second Fac node
        this.fac2.printFac();

        // print ")"
        System.out.print(" ) ");

    }

    public boolean evalComp() {

        // value to return
        boolean b = true;

        // value of first and second fac
        int v1 = Integer.parseInt(this.fac.evalFac());
        int v2 = Integer.parseInt(this.fac2.evalFac());

        // get compOp
        String compOp = this.co.getCompOp();

        if (compOp.equals("!=")) {

            // evaluate option 1: !=
            b = (v1 != v2);

        } else if (compOp.equals("==")) {

            // evaluate option 2: ==
            b = (v1 == v2);

        } else if (compOp.equals("<")) {

            // evaluate option 3: <
            b = (v1 < v2);

        } else if (compOp.equals(">")) {

            // evaluate option 4: >
            b = (v1 > v2);

        } else if (compOp.equals("<=")) {

            // evaluate option 5: <=
            b = (v1 <= v2);

        } else if (compOp.equals(">=")) {

            // evaluate option 6: >=
            b = (v1 >= v2);

        } else {

            // error
            // this should never happen

        }

        // return value
        return b;

    }

}
