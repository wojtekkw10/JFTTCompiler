// Generated from JFTT.g4 by ANTLR 4.7.2

    package parser;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link JFTTParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface JFTTVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link JFTTParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(JFTTParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link JFTTParser#declarations}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclarations(JFTTParser.DeclarationsContext ctx);
	/**
	 * Visit a parse tree produced by {@link JFTTParser#commands}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommands(JFTTParser.CommandsContext ctx);
	/**
	 * Visit a parse tree produced by {@link JFTTParser#command}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommand(JFTTParser.CommandContext ctx);
	/**
	 * Visit a parse tree produced by {@link JFTTParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(JFTTParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link JFTTParser#condition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCondition(JFTTParser.ConditionContext ctx);
	/**
	 * Visit a parse tree produced by {@link JFTTParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValue(JFTTParser.ValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link JFTTParser#identifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentifier(JFTTParser.IdentifierContext ctx);
}