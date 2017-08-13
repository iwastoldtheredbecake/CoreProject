JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = Tokenizer.java \
	  AssignNode.java \
	  CompNode.java \
	  CondNode.java \
	  DeclNode.java \
 	  DeclSeqNode.java \
	  ExpNode.java \
	  Executor.java \
	  FacNode.java \
	  IDNode.java \
 	  IfNode.java \
     	  InNode.java \
	  LoopNode.java \
	  OutNode.java \
	  ProgramNode.java \
	  StmtNode.java \
	  StmtSeqNode.java \
	  TermNode.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class