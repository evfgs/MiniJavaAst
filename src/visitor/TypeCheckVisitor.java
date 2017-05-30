package visitor;

import symboltable.SymbolTable;
import symboltable.Class;
import symboltable.Method;
import ast.And;
import ast.ArrayAssign;
import ast.ArrayLength;
import ast.ArrayLookup;
import ast.Assign;
import ast.Block;
import ast.BooleanType;
import ast.Call;
import ast.ClassDeclExtends;
import ast.ClassDeclSimple;
import ast.Exp;
import ast.False;
import ast.Formal;
import ast.Identifier;
import ast.IdentifierExp;
import ast.IdentifierType;
import ast.If;
import ast.IntArrayType;
import ast.IntegerLiteral;
import ast.IntegerType;
import ast.LessThan;
import ast.MainClass;
import ast.MethodDecl;
import ast.Minus;
import ast.NewArray;
import ast.NewObject;
import ast.Not;
import ast.Plus;
import ast.Print;
import ast.Program;
import ast.This;
import ast.Times;
import ast.True;
import ast.Type;
import ast.VarDecl;
import ast.While;

public class TypeCheckVisitor implements TypeVisitor {

	private SymbolTable symbolTable;
	private Method mMethod;
	private Class mClass;

	public TypeCheckVisitor(SymbolTable st) {
		symbolTable = st;
	}

	// MainClass m;
	// ClassDeclList cl;
	public Type visit(Program n) {
		n.m.accept(this);
		for (int i = 0; i < n.cl.size(); i++) {
			n.cl.elementAt(i).accept(this);
		}
		return null;
	}

	// Identifier i1,i2;
	// Statement s;
	public Type visit(MainClass n) {
		mClass = symbolTable.getClass(n.i1.s);
		mMethod = symbolTable.getMethod("main", mClass.getId());
		n.s.accept(this);
		mClass = null;
		return null;
	}

	// Identifier i;
	// VarDeclList vl;
	// MethodDeclList ml;
	public Type visit(ClassDeclSimple n) {
		mClass = symbolTable.getClass(n.i.s);
		for (int i = 0; i < n.vl.size(); i++) {
			n.vl.elementAt(i).accept(this);
		}
		for (int i = 0; i < n.ml.size(); i++) {
			n.ml.elementAt(i).accept(this);
		}
		mClass = null;
		return null;
	}

	// Identifier i;
	// Identifier j;
	// VarDeclList vl;
	// MethodDeclList ml;
	public Type visit(ClassDeclExtends n) {
		mClass = symbolTable.getClass(n.i.s);
		n.i.accept(this);
		n.j.accept(this);
		for (int i = 0; i < n.vl.size(); i++) {
			n.vl.elementAt(i).accept(this);
		}
		for (int i = 0; i < n.ml.size(); i++) {
			n.ml.elementAt(i).accept(this);
		}
		mClass = null;
		return null;
	}

	// Type t;
	// Identifier i;
	public Type visit(VarDecl n) {
		n.t.accept(this);
		n.i.accept(this);
		return n.t;
	}

	// Type t;
	// Identifier i;
	// FormalList fl;
	// VarDeclList vl;
	// StatementList sl;
	// Exp e;
	public Type visit(MethodDecl n) {
		mMethod = this.symbolTable.getMethod(n.i.s, mClass.getId());
		n.t.accept(this);
		Type tipo = symbolTable.getMethodType(n.i.s, mClass.getId());
		for (int i = 0; i < n.fl.size(); i++) {
			n.fl.elementAt(i).accept(this);
		}
		for (int i = 0; i < n.vl.size(); i++) {
			n.vl.elementAt(i).accept(this);
		}
		for (int i = 0; i < n.sl.size(); i++) {
			n.sl.elementAt(i).accept(this);
		}
		n.e.accept(this);
		return tipo;
	}

	// Type t;
	// Identifier i;
	public Type visit(Formal n) {
		n.t.accept(this);
		n.i.accept(this);
		return null;
	}

	public Type visit(IntArrayType n) {
		return new IntegerType();
	}

	public Type visit(BooleanType n) {
		return new BooleanType();
	}

	public Type visit(IntegerType n) {
		return new IntegerType();
	}

	// String s;
	public Type visit(IdentifierType n) {
		return null;
	}

	// StatementList sl;
	public Type visit(Block n) {
		for (int i = 0; i < n.sl.size(); i++) {
			n.sl.elementAt(i).accept(this);
		}
		return null;
	}

	// Exp e;
	// Statement s1,s2;
	public Type visit(If n) {
		Type tipo = n.e.accept(this);
		boolean saida = this.symbolTable.compareTypes(tipo, new BooleanType());
		if(!saida){
			System.out.println("O If não contém uma expressão booleana!");
			System.exit(0);
		}
		n.s1.accept(this);
		n.s2.accept(this);
		return null;
	}

	// Exp e;
	// Statement s;
	public Type visit(While n) {
		Type tipo = n.e.accept(this);
		boolean saida = this.symbolTable.compareTypes(tipo, new BooleanType());
		if(!saida){
			System.out.println("O If não contém uma expressão booleana!");
			System.exit(0);
		}
		n.s.accept(this);
		return null;
	}

	// Exp e;
	public Type visit(Print n) {
		n.e.accept(this);
		return null;
	}

	// Identifier i;
	// Exp e;
	public Type visit(Assign n) {
		Type tipo1 = n.i.accept(this);
		if(symbolTable.compareTypes(tipo1, new IntArrayType())){
			tipo1 = new IntegerType();
		}
		Type tipo2 = n.e.accept(this);
		boolean saida = this.symbolTable.compareTypes(tipo1, tipo2);
		
		if(!saida){
			System.out.println("Erro de Tipo");
			System.exit(0);
		}
		return null;
	}

	// Identifier i;
	// Exp e1,e2;
	public Type visit(ArrayAssign n) {
		n.i.accept(this);
		n.e1.accept(this);
		n.e2.accept(this);
		return null;
	}
	// Talvez seja possivel refatorar os metodos abaixo, visto q ficaram praticamente identicos :/
	// Exp e1,e2;
	public Type visit(And n) {
		Type tipo1 = n.e1.accept(this);
		Type tipo2 = n.e2.accept(this);
		boolean saida = this.symbolTable.compareTypes(tipo1, new BooleanType());
		saida = true && this.symbolTable.compareTypes(tipo2, new BooleanType());
		if(!saida){
			System.out.println("Erro de Tipo");
			System.exit(0);
		}
		return new BooleanType();
	}

	// Exp e1,e2;
	public Type visit(LessThan n) {
		Type tipo1 = n.e1.accept(this);
		Type tipo2 = n.e2.accept(this);
		boolean saida = this.symbolTable.compareTypes(tipo1, new IntegerType());
		saida = true && this.symbolTable.compareTypes(tipo2, new IntegerType());
		if(!saida){
			System.out.println("Erro de Tipo");
			System.exit(0);
		}
		return new BooleanType();
	}

	// Exp e1,e2;
	public Type visit(Plus n) {
		Type tipo1 = n.e1.accept(this);
		Type tipo2 = n.e2.accept(this);
		boolean saida = this.symbolTable.compareTypes(tipo1, new IntegerType());
		saida = true && this.symbolTable.compareTypes(tipo2, new IntegerType());
		if(!saida){
			System.out.println("Erro de Tipo");
			System.exit(0);
		}
		return new IntegerType();
	}

	// Exp e1,e2;
	public Type visit(Minus n) {
		Type tipo1 = n.e1.accept(this);
		Type tipo2 = n.e2.accept(this);
		boolean saida = this.symbolTable.compareTypes(tipo1, new IntegerType());
		saida = true && this.symbolTable.compareTypes(tipo2, new IntegerType());
		if(!saida){
			System.out.println("Erro de Tipo");
			System.exit(0);
		}
		return new IntegerType();
	}

	// Exp e1,e2;
	public Type visit(Times n) {
		Type tipo1 = n.e1.accept(this);
		Type tipo2 = n.e2.accept(this);
		boolean saida = this.symbolTable.compareTypes(tipo1, new IntegerType());
		saida = true && this.symbolTable.compareTypes(tipo2, new IntegerType());
		if(!saida){
			System.out.println("Erro de Tipo");
			System.exit(0);
		}
		return new IntegerType();
	}

	// Exp e1,e2;
	public Type visit(ArrayLookup n) {
		n.e1.accept(this);
		Type tipo = n.e2.accept(this);
		boolean saida = this.symbolTable.compareTypes(tipo, new IntegerType());
		if(!saida){
			System.out.println("Erro de tipo no array");
			System.exit(0);
		}
		return new IntegerType();
	}

	// Exp e;
	public Type visit(ArrayLength n) {
		n.e.accept(this);
		return new IntegerType();
	}

	// Exp e;
	// Identifier i;
	// ExpList el;
	public Type visit(Call n) {
		Class ctemp = mClass;
		Method mtemp = mMethod;
		n.e.accept(this);
		Type methodType = symbolTable.getMethodType(n.i.s, mClass.getId());
		Type expressionType = null;
		for (int i = 0; i < n.el.size(); i++) {
			if(n.el.elementAt(i) != null){ 
				expressionType = n.el.elementAt(i).accept(this);
			}
		}
		mClass = ctemp;
		mMethod = mtemp;
		if(methodType != null){
			return methodType;
		}
		return expressionType;
	}

	// int i;
	public Type visit(IntegerLiteral n) {
		return new IntegerType();
	}

	public Type visit(True n) {
		return new BooleanType();
	}

	public Type visit(False n) {
		return new BooleanType();
	}

	// String s;
	public Type visit(IdentifierExp n) {
		return symbolTable.getVarType(mMethod, mClass, n.s);
	}

	public Type visit(This n) {
		return null;
	}

	// Exp e;
	public Type visit(NewArray n) {
		n.e.accept(this);
		return null;
	}

	// Identifier i;
	public Type visit(NewObject n) {
		mClass = this.symbolTable.getClass(n.i.s);
		mMethod = null;
		return null;
	}

	// Exp e;
	public Type visit(Not n) {
		Type tipo = n.e.accept(this);
		if(!this.symbolTable.compareTypes(tipo, new BooleanType())){
			System.out.println("Dentro da negativa não há uma tipo booleano");
		}
		return new BooleanType();
	}

	// String s;
	public Type visit(Identifier n) {
		return symbolTable.getVarType(mMethod, mClass, n.s);
	}
}
