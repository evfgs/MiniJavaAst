package teste;

import java.util.List;

import org.antlr.v4.runtime.tree.TerminalNode;

import ast.*;
import sources.miniJavaGrammarParser.*;


public class AstBuilder {

	MainClass mainClass;
	ClassDeclList classDeclList;

	public MainClass getMainClass() {
		return mainClass;
	}
	public void setMainClass(MainClass mainClass) {
		this.mainClass = mainClass;
	}
	public ClassDeclList getClassDeclList() {
		return classDeclList;
	}
	public void setClassDeclList(ClassDeclList classDeclList) {
		this.classDeclList = classDeclList;
	}
	public AstBuilder(){
		mainClass = new MainClass(null, null, null);
	}

	/*
	 public BuildAST(){

	}
	public MainClass buildMainClass(){

		return new MainClass(null, null, null);
	}
	 */

	public Program visitGoal(GoalContext goal) {
		mainClass = visitMainClass(goal.mainClass());
		classDeclList = visitClassDeclList(goal.classDeclaration());
		return new Program(mainClass, classDeclList);
	}

	private MainClass visitMainClass(MainClassContext mainClassContext) {
		Identifier identifier1  = this.visitIdentifier(mainClassContext.Identifier(0));
		Identifier identifier2 = this.visitIdentifier(mainClassContext.Identifier(1));
		Statement statement =  this.visitStatement(mainClassContext.statement());

		return new MainClass(identifier1, identifier2, statement);
	}

	private ClassDeclList visitClassDeclList(List<ClassDeclarationContext> classDeclarationContext) {
		ClassDeclList classDecList = new ClassDeclList();
		for (ClassDeclarationContext c : classDeclarationContext){
			if(c.Identifier().size() > 1){
				classDecList.addElement(this.visitClassDeclExtends(c));
			} else {
				classDecList.addElement(this.visitClassDecl(c));
			}
		}

		return classDecList;
	}
	private Identifier visitIdentifier(TerminalNode identifier) {
		return new Identifier(identifier.toString());
	}

	private Statement visitStatement(StatementContext statement) {
		if(statement.isEmpty() || statement == null){
			return null;
		}

		if(statement.getChild(0).getText().equals("System.out.println")){
			return new Print(this.visitExpression(statement.expression(0)));
		}else if(statement.getChild(0).getText().equals("if")){
			return new If(this.visitExpression(statement.expression(0)),
					this.visitStatement(statement.statement(0)), this.visitStatement(statement.statement(1)));
		}else if(statement.getChild(0).getText().equals("while")){
			return new While(this.visitExpression(statement.expression(0)), 
					this.visitStatement(statement.statement(0)));
		}else if(statement.getChild(1).getText().equals("=")){
			return this.visitAssign(statement.Identifier(),
					statement.expression(0));
		}else if(statement.getChild(0).getText().equals("{")){
			return this.visitBlock(statement.statement());
		}else{
			return this.visitArrayAssign(statement.Identifier(),statement.expression(0),
					statement.expression(1));
		}
	}

	private Statement visitArrayAssign(TerminalNode identifier, ExpressionContext expressionContext1,
			ExpressionContext expressionContext2) {
		return new ArrayAssign(this.visitIdentifier(identifier), this.visitExpression(expressionContext1),
				this.visitExpression(expressionContext2));
	}

	private Statement visitAssign(TerminalNode terminalNode, ExpressionContext expressionContext) {
		return new Assign(this.visitIdentifier(terminalNode), this.visitExpression(expressionContext));
	}
	// talvez private Block visitBlock \/
	private Statement visitBlock(List<StatementContext> statement) {
		return new Block(this.visitStatementList(statement));
	}

	private StatementList visitStatementList(List<StatementContext> statementList) {
		StatementList statement = new StatementList();
		for(StatementContext statementContext : statementList){
			statement.addElement(this.visitStatement(statementContext));
		}
		return statement;
	}



	private Exp visitExpression(ExpressionContext expression) {
		if((expression == null) || (expression.isEmpty())){
			return null;
		}else if(!expression.expression().isEmpty()){
			if(expression.getChild(1).getText().equals(".")){ 
				if(expression.getChild(2).getText().equals("length")){
					return new ArrayLength(this.visitExpression(expression.expression(0)));
				}
				return new Call(this.visitExpression(expression.expression(0)),
						this.visitIdentifier(expression.Identifier()),
						this.visitExpressionList(expression.expression().subList(1, 
								expression.expression().size())));
			}
			if(expression.getChild(1).getText() == "&&"){
				return new And(this.visitExpression(expression.expression(0)), this.visitExpression(expression.expression(1)));
			} else if(expression.getChild(1).getText() == "*"){
				return new Times(this.visitExpression(expression.expression(0)), this.visitExpression(expression.expression(1)));
			} else if(expression.getChild(1).getText() == "<"){
				return new LessThan(this.visitExpression(expression.expression(0)), this.visitExpression(expression.expression(1)));
			} else if(expression.getChild(1).getText() == "-"){
				return new Minus(this.visitExpression(expression.expression(0)), this.visitExpression(expression.expression(1)));
			} else if(expression.getChild(1).getText() == "["){
				return new ArrayLookup(this.visitExpression(expression.expression(0)), this.visitExpression(expression.expression(1)));
			}
			if(expression.getChild(0).getText().equals("!")){
				return new Not(this.visitExpression(expression.expression(0)));
			}
			this.visitExpressionList(expression.expression());
		}else if(expression.getChildCount() >= 1){
			if(expression.getChild(0).getText().equals("this")) return new This();
			if(expression.getChild(0).getText().equals("true")) return new True();
			if(expression.getChild(0).getText().equals("false")) return new False();
			if(expression.getChild(0).getText().equals("new")){
				if(expression.Identifier() != null) return new NewObject(this.visitIdentifier(expression.Identifier()));
				return new NewArray (this.visitExpression(expression.expression(0)));
			}

			try{
				int x = Integer.parseInt(expression.getChild(0).getText());
				return new IntegerLiteral(x);
			}catch(Exception e){
				return new IdentifierExp(expression.getChild(0).getText());
			}
		}

		if(expression.expression() != null){
			return this.visitExpression(expression.expression(0));
		}
		return null;
	}

	private ExpList visitExpressionList(List<ExpressionContext> expressionList) {
		ExpList expression = new ExpList();
		for (int i = 0; i < expressionList.size(); i++){
			expression.addElement(this.visitExpression(expressionList.get(i)));
		} 
		return expression;
	}

	private MethodDeclList visitMethodDeclList(List<MethodDeclarationContext> methodDeclarationContext) {
		MethodDeclList method = new MethodDeclList();
		for (int i = 0; i < methodDeclarationContext.size(); i++){
			method.addElement(this.visitMethodDecl(methodDeclarationContext.get(i)));
		}
		return method;
	}

	private MethodDecl visitMethodDecl(MethodDeclarationContext m) {
		Identifier identifier = this.visitIdentifier(m.Identifier());
		Type type = this.visitType(m.type());
		FormalList formalList = this.visitFormalList(m.formalList());
		VarDeclList varDeclList = this.visitVarDeclList(m.varDeclaration());
		StatementList statementList = this.visitStatementList(m.statement());
		Exp expression = this.visitExpression(m.expression());
		return new MethodDecl(type,identifier,formalList,varDeclList,statementList,expression);
	}

	private FormalList visitFormalList(FormalListContext formalListContext) {
		FormalList formalList = new FormalList();
		Identifier identifier = this.visitIdentifier(formalListContext.Identifier());
		Type type = this.visitType(formalListContext.type());
		formalList.addElement(new Formal(type,identifier));
		for (FormalRestContext ctx : formalListContext.formalRest()){
			formalList.addElement(this.visitFormalAux(ctx));
		}
		return formalList;
	}

	private Formal visitFormalAux(FormalRestContext contexto) {
		Type type = this.visitType(contexto.type());
		Identifier identifier = this.visitIdentifier(contexto.Identifier());
		return new Formal(type, identifier);
	}

	private Type visitType(TypeContext typeContext) {
		if (typeContext.getText().equals("int")){
			return new IntegerType();
		}else if(typeContext.getText().equals("boolean")){
			return new BooleanType();
		}else if(typeContext.getText().equals("int[]") || (typeContext.getText().equals("int []"))){
			return new IntArrayType();
		}
		return null;
	}

	private VarDeclList visitVarDeclList(List<VarDeclarationContext> varDeclarationContext) {
		VarDeclList varDeclList = new VarDeclList();
		for(VarDeclarationContext varContext : varDeclarationContext){
			varDeclList.addElement(this.visitVarDecl(varContext));
		}
		return varDeclList;
	}

	private VarDecl visitVarDecl(VarDeclarationContext varDeclarationContext) {
		return new VarDecl(this.visitType(varDeclarationContext.type()),this.visitIdentifier(varDeclarationContext.Identifier()));
	}

	private ClassDecl visitClassDeclExtends(ClassDeclarationContext c) {
		Identifier identifier1 = this.visitIdentifier(c.Identifier(0));
		Identifier identifier2 = this.visitIdentifier(c.Identifier(1));
		VarDeclList varList = this.visitVarDeclList(c.varDeclaration());
		MethodDeclList method = this.visitMethodDeclList(c.methodDeclaration());
		return new ClassDeclExtends(identifier1, identifier2, varList, method);
	}

	private ClassDecl visitClassDecl(ClassDeclarationContext c) {

		Identifier identifier1 = this.visitIdentifier(c.Identifier(0));
		VarDeclList varList = this.visitVarDeclList(c.varDeclaration());
		MethodDeclList method = this.visitMethodDeclList(c.methodDeclaration());
		return new ClassDeclSimple(identifier1, varList, method);
	}

}
