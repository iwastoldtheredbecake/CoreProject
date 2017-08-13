/**
 * Implementation of cond node of CORE language
 */
public class CondNode {

    /*
     * Private members
     */

    // The children nodes of CondNode
    private CompNode comp;
    private CondNode cond;
    private CondNode cond2;
    private int selection;

    /*
     * Public members
     */

    // Public constructor
    CondNode() {
        this.comp = null;
        this.cond = null;
        this.cond2 = null;
        this.selection = 1;
    }

    /*
     * Parse the cond node.
     * @ requires current token in Tokenizer t is start of cond node (current token is comp node, "!", or "[")
     */
    public void parseCond(Tokenizer t) {

        // determine selection
        if (t.currentToken().equals("(")) {
            // comp option

            // parse comp node
            this.comp = new CompNode();
            this.comp.parseComp(t);

        } else if (t.currentToken().equals("!")) {
            // !comp option

            // update selection
            this.selection = 2;

            // chomp "!" token
            t.nextToken();

            // parse cond node
            this.cond = new CondNode();
            this.cond.parseCond(t);

        } else if (t.currentToken().equals("[")) {
            // cond and cond OR cond or cond option

            // chomp "[" token
            t.nextToken();

            // parse first cond node
            this.cond = new CondNode();
            this.cond.parseCond(t);

            // check selection
            if (t.currentToken().equals("and")) {
                // and option

                // update selection
                this.selection = 3;

            } else if (t.currentToken().equals("or")) {
                // or option

                // update selection
                this.selection = 4;

            } else {
                // invalid token

                System.out.println("Parsing error: invalid cond node");
                System.exit(-1);

            }

            // check for proper token
            if (!t.currentToken().equals("and") && !t.currentToken().equals("or")) {
                System.out.println("Parsing error: expecing 'and' or 'or'");
                System.exit(-1);
            }

            // chomp "and" or "or" token
            t.nextToken();

            // parse second cond node
            this.cond2 = new CondNode();
            this.cond2.parseCond(t);

            // check for proper token
            if (!t.currentToken().equals("]")) {
                System.out.println("Parsing error: expected ']'");
                System.exit(-1);
            }
            // chomp "]" token
            t.nextToken();

        }

    }


    public void printCond(){

        // check selection
        if (this.selection == 1) {
            //comp option

            // print comp node
            this.comp.printComp();

        } else if (this.selection == 2) {
            // !comp option

            // print "!"
            System.out.print("!");

            // print cond node
            this.cond.printCond();

        } else if (this.selection == 3) {
            // cond and cond option

            // print "["
            System.out.print("[ ");

            // print first cond
            this.cond.printCond();

            // print "and"
            System.out.print("and ");

            // print second cond node
            this.cond2.printCond();

            // print "]"
            System.out.print("] ");

        } else if (this.selection == 4) {
            // cond and cond option

            // print "["
            System.out.print("[ ");

            // print first cond
            this.cond.printCond();

            // print "and"
            System.out.print("or ");

            // print second cond node
            this.cond2.printCond();

            // print "]"
            System.out.print("] ");

        } else {
            // cond not initialized this should never happen
        }

    }

    public boolean evalCond() {

        boolean b = true;

        if (this.selection == 1) {

            // evaluate option 1: <comp>
            b = this.comp.evalComp();

        } else if (this.selection == 2) {

            // evaluate option 2: !<cond>
            b = !this.cond.evalCond();

        } else if (this.selection == 3) {

            // evaluate option 3: [ <cond> and <cond> ]
            b = this.cond.evalCond() && this.cond2.evalCond();

        } else if (this.selection == 4) {

            // evaluate option 4: [ <cond> or <cond> ]
            b = this.cond.evalCond() || this.cond2.evalCond();

        }

        return b;

    }

}
