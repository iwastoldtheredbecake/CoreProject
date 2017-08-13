import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Implementation of IDNode of CORE language
 */
public class IDNode {

    /*
     * Private members
     */

    private String idValue;
    private IDNode nextId;
    private int selection;
    private String assignedVal;
    private boolean initialized;
    private boolean declared;
    private static Map<String, IDNode> symTab = new HashMap<String, IDNode>();

    /*
     * Public members
     */

    // Public constructor
    IDNode() {
        this.idValue = null;
        this.nextId = null;
        this.selection = 1;
        this.initialized = false;
        this.declared = false;

    }


    /*
     * Parse the id node.
     * @ requires current token in Tokenizer t is start of id node (current token = uppercase char)
     *
     * isDecl = true if being called from decl node
     */
    public void parseID(Tokenizer t, boolean isDecl) {

        // get the first id
        this.idValue = t.currentToken();

        // has this id already been declared?
        if (symTab.containsKey(this.idValue)) {

            // set declared to true
            this.declared = true; // local use only

        }

        // this id has not been declared
        else {

            // add id to symTab
            this.symTab.put(this.idValue, this);

        }

        // are there more id's?
        // chomp first id token
        t.nextToken();

        if (t.currentToken().equals(",")) {
            // chomp "," token
            t.nextToken();

            // update selection
            this.selection = 2;

            // parse next id node
            this.nextId = new IDNode();
            this.nextId.parseID(t, isDecl);

            // check if id has already been declared
            if (isDecl && !this.nextId.declared) {

                // set id as declared
                this.nextId.declared = true;

            } else if (isDecl){

                // error
                System.out.println("Error: ID already declared.");
                System.exit(-1);

            }

        }

    }

    //Sets declared to true
    public void setDeclared() {

        // get id corresponding to this.idValue
        IDNode update = this.symTab.remove(this.idValue);

        // set declared to true
        update.declared = true;

        // put update back
        this.symTab.put(this.idValue, update);

    }

    /*
     *
     * Sets idValue to value
     * @this -- the id node to set value
     * @param value -- the value to set
     *
     * Post condition -- IDNode in symtab will be initialized
     */
    public void setValue(String value) {

        // get id corresponding to this.idValue
        IDNode update = this.symTab.remove(this.idValue);

        // assign value to update
        update.assignedVal = value;

        // set initialized
        update.initialized = true;

        // put update back
        this.symTab.put(this.idValue, update);

    }

    // return if IDNode is declared
    public boolean isDecl(String value) {

        // get id corresponding to this.idValue
        IDNode query = this.symTab.get(this.idValue);

        // return value
        return query.declared;

    }

    // return if IDNode is initialized
    public boolean isInit() {

        // Get IDnode
        IDNode node = this.symTab.get(this.idValue);

        // return initialized
        return node.initialized;

    }

    // return value of id
    public String getValue() {

        // get IDnode
        IDNode node = this.symTab.get(this.idValue);

        // return idValue
        return node.assignedVal;

    }

    // print id node to console
    public void printID(){

        // print first id
        System.out.print(this.idValue);

        // if more id's print the,
        if (this.selection == 2) {
            System.out.print(", ");
            this.nextId.printID();
        }

    }

    // print id value(s) to the console or exits if ID uninitialized
    public void printIDValue() {

        // has id been initialized?
        if (this.isInit()) {

            // Print
            System.out.println(this.idValue + " = " + this.getValue());

            // More ID's?
            if (this.selection == 2) {
                this.nextId.printIDValue();
            }

        } else {

            // error
            System.out.println("Error: Write to uninitialized ID");
            System.exit(-1);

        }

    }

    // read id value(s) from console
    public void readIDValue() {

        // Output prompt:
        System.out.print(this.idValue + " =? ");

        // get scanner
        Scanner in = new Scanner(System.in);

        // read next int
        String value = Integer.toString(in.nextInt());

        // set value
        this.setValue(value);

        // close scanner -- creates a bug by closing System.in permanently
        //in.close();

        // are there more IDs to read?
        if (this.selection == 2) {
            this.nextId.readIDValue();
        }

    }

}
