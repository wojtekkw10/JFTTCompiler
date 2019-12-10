// Generated from Parser.g by ANTLR 4.7.2

    package parser;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ParserParser}.
 */
public interface ParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ParserParser#main}.
	 * @param ctx the parse tree
	 */
	void enterMain(ParserParser.MainContext ctx);
	/**
	 * Exit a parse tree produced by {@link ParserParser#main}.
	 * @param ctx the parse tree
	 */
	void exitMain(ParserParser.MainContext ctx);
	/**
	 * Enter a parse tree produced by {@link ParserParser#name}.
	 * @param ctx the parse tree
	 */
	void enterName(ParserParser.NameContext ctx);
	/**
	 * Exit a parse tree produced by {@link ParserParser#name}.
	 * @param ctx the parse tree
	 */
	void exitName(ParserParser.NameContext ctx);
}