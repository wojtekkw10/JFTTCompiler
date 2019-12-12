// Generated from JFTT.g4 by ANTLR 4.7.2

    package parser;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link JFTTParser}.
 */
public interface JFTTListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link JFTTParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(JFTTParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link JFTTParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(JFTTParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link JFTTParser#declarations}.
	 * @param ctx the parse tree
	 */
	void enterDeclarations(JFTTParser.DeclarationsContext ctx);
	/**
	 * Exit a parse tree produced by {@link JFTTParser#declarations}.
	 * @param ctx the parse tree
	 */
	void exitDeclarations(JFTTParser.DeclarationsContext ctx);
	/**
	 * Enter a parse tree produced by {@link JFTTParser#commands}.
	 * @param ctx the parse tree
	 */
	void enterCommands(JFTTParser.CommandsContext ctx);
	/**
	 * Exit a parse tree produced by {@link JFTTParser#commands}.
	 * @param ctx the parse tree
	 */
	void exitCommands(JFTTParser.CommandsContext ctx);
	/**
	 * Enter a parse tree produced by {@link JFTTParser#command}.
	 * @param ctx the parse tree
	 */
	void enterCommand(JFTTParser.CommandContext ctx);
	/**
	 * Exit a parse tree produced by {@link JFTTParser#command}.
	 * @param ctx the parse tree
	 */
	void exitCommand(JFTTParser.CommandContext ctx);
	/**
	 * Enter a parse tree produced by {@link JFTTParser#upfor}.
	 * @param ctx the parse tree
	 */
	void enterUpfor(JFTTParser.UpforContext ctx);
	/**
	 * Exit a parse tree produced by {@link JFTTParser#upfor}.
	 * @param ctx the parse tree
	 */
	void exitUpfor(JFTTParser.UpforContext ctx);
	/**
	 * Enter a parse tree produced by {@link JFTTParser#downfor}.
	 * @param ctx the parse tree
	 */
	void enterDownfor(JFTTParser.DownforContext ctx);
	/**
	 * Exit a parse tree produced by {@link JFTTParser#downfor}.
	 * @param ctx the parse tree
	 */
	void exitDownfor(JFTTParser.DownforContext ctx);
	/**
	 * Enter a parse tree produced by {@link JFTTParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(JFTTParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link JFTTParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(JFTTParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link JFTTParser#condition}.
	 * @param ctx the parse tree
	 */
	void enterCondition(JFTTParser.ConditionContext ctx);
	/**
	 * Exit a parse tree produced by {@link JFTTParser#condition}.
	 * @param ctx the parse tree
	 */
	void exitCondition(JFTTParser.ConditionContext ctx);
	/**
	 * Enter a parse tree produced by {@link JFTTParser#value}.
	 * @param ctx the parse tree
	 */
	void enterValue(JFTTParser.ValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link JFTTParser#value}.
	 * @param ctx the parse tree
	 */
	void exitValue(JFTTParser.ValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link JFTTParser#identifier}.
	 * @param ctx the parse tree
	 */
	void enterIdentifier(JFTTParser.IdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link JFTTParser#identifier}.
	 * @param ctx the parse tree
	 */
	void exitIdentifier(JFTTParser.IdentifierContext ctx);
}