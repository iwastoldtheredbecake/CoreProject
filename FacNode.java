/**
 * Implementation of fac node of CORE language
 */
public class FacNode {

    /*
     * Private members
     */

    // The children nodes of FacNode
    private int intValue;
    private IDNode id;
    private ExpNode exp;
    private int selection;

    /*
     * Public members
     */

    // Public constructor
    FacNode() {
        this.id = null;
        this.exp = null;
        this.selection = 1;
    }

    /*
     * Parse the fac node.
     * @ requires current token in Tokenizer t is start of fac node (current token is int, id node, or "(")
     */
    public void parseFac(Tokenizer t) {

        // check selection
        if (Character.isDigit(t.currentToken().charAt(0)) || t.currentToken().charAt(0) == '-') {
            // int option

            // handle negative case

            boolean negative = false;
            if (t.currentToken().charAt(0) == '-') {

                // set negative flag
                negative = true;

                // chomp the negative token
                t.nextToken();

            }

            // assign int value
            this.intValue = Integer.parseInt(t.currentToken());

            // if negative, multiply by -1
            if (negative) {

                this.intValue *= -1;

            }

            // chomp int token
            t.nextToken();

        } else if (Character.isAlphabetic(t.currentToken().charAt(0))) {
            // id node option

            // update selection
            this.selection = 2;

            // parse id node
            this.id = new IDNode();
            this.id.parseID(t, false);

            // has this id been declared?
            if (!this.id.isDecl(this.id.getValue())) {

                // error
                System.out.println("Error. Use of undeclared ID");
                System.exit(-1);

            }

        } else if (t.currentToken().equals("(")) {
            // exp node option

            // chomp "(" token
            t.nextToken();

            // parse exp node
            this.exp = new ExpNode();
            this.exp.parseExp(t);

            // check for proper token
            if (!t.currentToken().equals(")")) {
                System.out.println("Parsing error: expected ')'");
                System.exit(-1);
            }

            // chomp ")" token
            t.nextToken();

        } else {

            // error invalid fac
            System.out.println("Parsing error: invalid fac");
            System.exit(-1);

        }

    }

    // print fac node to console
    public void printFac() {

        // check selection
        if (this.selection == 1) {
            // int option

            // print int value
            System.out.print(this.intValue);

        } else if (this.selection == 2) {
            // id node option

            // print id node
            this.id.printID();

        } else if (this.selection == 3) {
            // exp node option

            // print "("
            System.out.print("( ");

            // print exp node
            this.exp.printExp();

            // print ")"
            System.out.print(") ");

        }

    }

    public String evalFac() {

        // value to return
        String value = "";

        if (this.selection == 1) {

            // evaluate option 1: <int>
            value = Integer.toString(this.intValue);

        } else if (this.selection == 2) {

            // evaluate option 2: <id>
            // check if id is initialized
            if (this.id.isInit()) {

                // get value
                value = this.id.getValue();

            } else {

                // error
                System.out.println("Error: Assign to uninitialized ID");
                System.exit(-1);

            }

        } else if (this.selection == 3) {

            // evaluate option 3: (<exp>)
            value = this.exp.evalExp();

        }

        // check for value greater than 99999999
        if (java.lang.Math.abs(Integer.parseInt(value)) > 99999999) {

            // error
            System.out.println("Error: Value exceeds 8 digits.");
            System.exit(-1);

        }

        return value;

    }

}
