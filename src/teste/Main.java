package teste;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

import sources.miniJavaGrammarLexer;
import sources.miniJavaGrammarParser;
import visitor.BuildSymbolTableVisitor;
import visitor.PrettyPrintVisitor;
import visitor.TypeCheckVisitor;

import ast.Program;

public class Main {

	public static void main(String[] args) throws IOException {
		InputStream stream = new FileInputStream("teste.txt");
		ANTLRInputStream input = new ANTLRInputStream(stream);
		miniJavaGrammarLexer lexer = new miniJavaGrammarLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		
		miniJavaGrammarParser parser = new miniJavaGrammarParser(tokens);
		AstBuilder builder = new AstBuilder();
		Program prog = builder.visitGoal(parser.goal());
		PrettyPrintVisitor ptv = new PrettyPrintVisitor();
		prog.accept(ptv);
		
		BuildSymbolTableVisitor sbv = new BuildSymbolTableVisitor();
		prog.accept(sbv);
		TypeCheckVisitor test = new TypeCheckVisitor(sbv.getSymbolTable());
		prog.accept(test);
	}
}
