Cameron Utsman
utsman.1@osu.edu
CSE 3341 MWF 3-3:55
Prof. Morris
Lab 3 -- Core Executor README.txt
3/22/17

Files:

README.txt: File specifying contact info, compilation and execution instructions.

DOCUMENTATION.txt: File containing design info, usage info, testing info.

Tokenizer.java:	Tokenizer file and main routine

Executor.java: Driver to tokenize, parse, and execute a core file

AssignNode.java: Assign Node Class file 

CompNode.java: Comp Node Class file

CompOp.java: Comp Op Class file

CondNode.java: Cond Node Class file

DeclNode.java: Decl Node Class file

DeclSeqNode.java: Decl Seq Node Class file

ExpNode.java: Exp Node Class file

FacNode.java: Fac Node Class file

IDNode.java: ID Node Class file

IfNode.java: If Node Class file

InNode.java: In Node Class file

LoopNode.java: Loop Node Class file

OutNode.java: Out Node Class file

ProgramNode.java: Program Node Class File

StmtNode.java: Stmt Node Class file

StmtSeqNode.java: Stmt Seq Node Class file

TermNode.java: Term Node Class file


To compile:

From the directory containing source files and Makefile, execute ‘make’ with no arguments. 

	
To execute:

Ensure that the .core or sample .txt file you wish to parse is located in the directory containing all class files

To execute Executor (lab 3)

From this directory containing, execute with the command:

	java Executor <filename.extension>

where <filename.extension> is the file you wish to tokenize and parse. 

To execute Parse and Tokenizer (Labs 1 and 2)

From this directory containing, execute with the command:

	java Executor <filename.extension>

where <filename.extension> is the file you wish to tokenize and parse.


Output will be displayed to the console.
		