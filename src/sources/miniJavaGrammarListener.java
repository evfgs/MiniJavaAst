// Generated from miniJavaGrammar.g4 by ANTLR 4.4
package sources;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link miniJavaGrammarParser}.
 */
public interface miniJavaGrammarListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link miniJavaGrammarParser#methodDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterMethodDeclaration(@NotNull miniJavaGrammarParser.MethodDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link miniJavaGrammarParser#methodDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitMethodDeclaration(@NotNull miniJavaGrammarParser.MethodDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link miniJavaGrammarParser#goal}.
	 * @param ctx the parse tree
	 */
	void enterGoal(@NotNull miniJavaGrammarParser.GoalContext ctx);
	/**
	 * Exit a parse tree produced by {@link miniJavaGrammarParser#goal}.
	 * @param ctx the parse tree
	 */
	void exitGoal(@NotNull miniJavaGrammarParser.GoalContext ctx);
	/**
	 * Enter a parse tree produced by {@link miniJavaGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(@NotNull miniJavaGrammarParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link miniJavaGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(@NotNull miniJavaGrammarParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link miniJavaGrammarParser#mainClass}.
	 * @param ctx the parse tree
	 */
	void enterMainClass(@NotNull miniJavaGrammarParser.MainClassContext ctx);
	/**
	 * Exit a parse tree produced by {@link miniJavaGrammarParser#mainClass}.
	 * @param ctx the parse tree
	 */
	void exitMainClass(@NotNull miniJavaGrammarParser.MainClassContext ctx);
	/**
	 * Enter a parse tree produced by {@link miniJavaGrammarParser#formalList}.
	 * @param ctx the parse tree
	 */
	void enterFormalList(@NotNull miniJavaGrammarParser.FormalListContext ctx);
	/**
	 * Exit a parse tree produced by {@link miniJavaGrammarParser#formalList}.
	 * @param ctx the parse tree
	 */
	void exitFormalList(@NotNull miniJavaGrammarParser.FormalListContext ctx);
	/**
	 * Enter a parse tree produced by {@link miniJavaGrammarParser#formalRest}.
	 * @param ctx the parse tree
	 */
	void enterFormalRest(@NotNull miniJavaGrammarParser.FormalRestContext ctx);
	/**
	 * Exit a parse tree produced by {@link miniJavaGrammarParser#formalRest}.
	 * @param ctx the parse tree
	 */
	void exitFormalRest(@NotNull miniJavaGrammarParser.FormalRestContext ctx);
	/**
	 * Enter a parse tree produced by {@link miniJavaGrammarParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(@NotNull miniJavaGrammarParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link miniJavaGrammarParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(@NotNull miniJavaGrammarParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link miniJavaGrammarParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(@NotNull miniJavaGrammarParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link miniJavaGrammarParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(@NotNull miniJavaGrammarParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link miniJavaGrammarParser#varDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterVarDeclaration(@NotNull miniJavaGrammarParser.VarDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link miniJavaGrammarParser#varDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitVarDeclaration(@NotNull miniJavaGrammarParser.VarDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link miniJavaGrammarParser#classDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterClassDeclaration(@NotNull miniJavaGrammarParser.ClassDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link miniJavaGrammarParser#classDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitClassDeclaration(@NotNull miniJavaGrammarParser.ClassDeclarationContext ctx);
}