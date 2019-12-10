// Generated from JFTT.g4 by ANTLR 4.7.2

    package parser;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class JFTTParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, T__30=31, 
		T__31=32, T__32=33, T__33=34, T__34=35, BLANK=36, COMMENT=37, PIDENTIFIER=38, 
		NUM=39;
	public static final int
		RULE_program = 0, RULE_declarations = 1, RULE_commands = 2, RULE_command = 3, 
		RULE_expression = 4, RULE_condition = 5, RULE_value = 6, RULE_identifier = 7;
	private static String[] makeRuleNames() {
		return new String[] {
			"program", "declarations", "commands", "command", "expression", "condition", 
			"value", "identifier"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'DECLARE'", "'BEGIN'", "'END'", "','", "'('", "':'", "')'", "'ASSIGN'", 
			"';'", "'IF'", "'THEN'", "'ELSE'", "'ENDIF'", "'WHILE'", "'DO'", "'ENDWHILE'", 
			"'ENDDO'", "'FOR'", "'FROM'", "'TO'", "'ENDFOR'", "'DOWNTO'", "'READ'", 
			"'WRITE'", "'PLUS'", "'MINUS'", "'TIMES'", "'DIV'", "'MOD'", "'EQ'", 
			"'NEQ'", "'LE'", "'GE'", "'LEQ'", "'GEQ'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			"BLANK", "COMMENT", "PIDENTIFIER", "NUM"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "JFTT.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public JFTTParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class ProgramContext extends ParserRuleContext {
		public DeclarationsContext declarations() {
			return getRuleContext(DeclarationsContext.class,0);
		}
		public CommandsContext commands() {
			return getRuleContext(CommandsContext.class,0);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JFTTListener ) ((JFTTListener)listener).enterProgram(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JFTTListener ) ((JFTTListener)listener).exitProgram(this);
		}
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		try {
			setState(26);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__0:
				enterOuterAlt(_localctx, 1);
				{
				setState(16);
				match(T__0);
				setState(17);
				declarations(0);
				setState(18);
				match(T__1);
				setState(19);
				commands(0);
				setState(20);
				match(T__2);
				}
				break;
			case T__1:
				enterOuterAlt(_localctx, 2);
				{
				setState(22);
				match(T__1);
				setState(23);
				commands(0);
				setState(24);
				match(T__2);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DeclarationsContext extends ParserRuleContext {
		public TerminalNode PIDENTIFIER() { return getToken(JFTTParser.PIDENTIFIER, 0); }
		public List<TerminalNode> NUM() { return getTokens(JFTTParser.NUM); }
		public TerminalNode NUM(int i) {
			return getToken(JFTTParser.NUM, i);
		}
		public DeclarationsContext declarations() {
			return getRuleContext(DeclarationsContext.class,0);
		}
		public DeclarationsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declarations; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JFTTListener ) ((JFTTListener)listener).enterDeclarations(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JFTTListener ) ((JFTTListener)listener).exitDeclarations(this);
		}
	}

	public final DeclarationsContext declarations() throws RecognitionException {
		return declarations(0);
	}

	private DeclarationsContext declarations(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		DeclarationsContext _localctx = new DeclarationsContext(_ctx, _parentState);
		DeclarationsContext _prevctx = _localctx;
		int _startState = 2;
		enterRecursionRule(_localctx, 2, RULE_declarations, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(36);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				{
				setState(29);
				match(PIDENTIFIER);
				}
				break;
			case 2:
				{
				setState(30);
				match(PIDENTIFIER);
				setState(31);
				match(T__4);
				setState(32);
				match(NUM);
				setState(33);
				match(T__5);
				setState(34);
				match(NUM);
				setState(35);
				match(T__6);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(51);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(49);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
					case 1:
						{
						_localctx = new DeclarationsContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_declarations);
						setState(38);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(39);
						match(T__3);
						setState(40);
						match(PIDENTIFIER);
						}
						break;
					case 2:
						{
						_localctx = new DeclarationsContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_declarations);
						setState(41);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(42);
						match(T__3);
						setState(43);
						match(PIDENTIFIER);
						setState(44);
						match(T__4);
						setState(45);
						match(NUM);
						setState(46);
						match(T__5);
						setState(47);
						match(NUM);
						setState(48);
						match(T__6);
						}
						break;
					}
					} 
				}
				setState(53);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class CommandsContext extends ParserRuleContext {
		public CommandContext command() {
			return getRuleContext(CommandContext.class,0);
		}
		public CommandsContext commands() {
			return getRuleContext(CommandsContext.class,0);
		}
		public CommandsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_commands; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JFTTListener ) ((JFTTListener)listener).enterCommands(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JFTTListener ) ((JFTTListener)listener).exitCommands(this);
		}
	}

	public final CommandsContext commands() throws RecognitionException {
		return commands(0);
	}

	private CommandsContext commands(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		CommandsContext _localctx = new CommandsContext(_ctx, _parentState);
		CommandsContext _prevctx = _localctx;
		int _startState = 4;
		enterRecursionRule(_localctx, 4, RULE_commands, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(55);
			command();
			}
			_ctx.stop = _input.LT(-1);
			setState(61);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new CommandsContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_commands);
					setState(57);
					if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
					setState(58);
					command();
					}
					} 
				}
				setState(63);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class CommandContext extends ParserRuleContext {
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ConditionContext condition() {
			return getRuleContext(ConditionContext.class,0);
		}
		public List<CommandsContext> commands() {
			return getRuleContexts(CommandsContext.class);
		}
		public CommandsContext commands(int i) {
			return getRuleContext(CommandsContext.class,i);
		}
		public TerminalNode PIDENTIFIER() { return getToken(JFTTParser.PIDENTIFIER, 0); }
		public List<ValueContext> value() {
			return getRuleContexts(ValueContext.class);
		}
		public ValueContext value(int i) {
			return getRuleContext(ValueContext.class,i);
		}
		public CommandContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_command; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JFTTListener ) ((JFTTListener)listener).enterCommand(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JFTTListener ) ((JFTTListener)listener).exitCommand(this);
		}
	}

	public final CommandContext command() throws RecognitionException {
		CommandContext _localctx = new CommandContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_command);
		try {
			setState(123);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(64);
				identifier();
				setState(65);
				match(T__7);
				setState(66);
				expression();
				setState(67);
				match(T__8);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(69);
				match(T__9);
				setState(70);
				condition();
				setState(71);
				match(T__10);
				setState(72);
				commands(0);
				setState(73);
				match(T__11);
				setState(74);
				commands(0);
				setState(75);
				match(T__12);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(77);
				match(T__9);
				setState(78);
				condition();
				setState(79);
				match(T__10);
				setState(80);
				commands(0);
				setState(81);
				match(T__12);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(83);
				match(T__13);
				setState(84);
				condition();
				setState(85);
				match(T__14);
				setState(86);
				commands(0);
				setState(87);
				match(T__15);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(89);
				match(T__14);
				setState(90);
				commands(0);
				setState(91);
				match(T__13);
				setState(92);
				condition();
				setState(93);
				match(T__16);
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(95);
				match(T__17);
				setState(96);
				match(PIDENTIFIER);
				setState(97);
				match(T__18);
				setState(98);
				value();
				setState(99);
				match(T__19);
				setState(100);
				value();
				setState(101);
				match(T__14);
				setState(102);
				commands(0);
				setState(103);
				match(T__20);
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(105);
				match(T__17);
				setState(106);
				match(PIDENTIFIER);
				setState(107);
				match(T__18);
				setState(108);
				value();
				setState(109);
				match(T__21);
				setState(110);
				value();
				setState(111);
				match(T__14);
				setState(112);
				commands(0);
				setState(113);
				match(T__20);
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(115);
				match(T__22);
				setState(116);
				identifier();
				setState(117);
				match(T__8);
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(119);
				match(T__23);
				setState(120);
				value();
				setState(121);
				match(T__8);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionContext extends ParserRuleContext {
		public List<ValueContext> value() {
			return getRuleContexts(ValueContext.class);
		}
		public ValueContext value(int i) {
			return getRuleContext(ValueContext.class,i);
		}
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JFTTListener ) ((JFTTListener)listener).enterExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JFTTListener ) ((JFTTListener)listener).exitExpression(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		ExpressionContext _localctx = new ExpressionContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_expression);
		try {
			setState(146);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(125);
				value();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(126);
				value();
				setState(127);
				match(T__24);
				setState(128);
				value();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(130);
				value();
				setState(131);
				match(T__25);
				setState(132);
				value();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(134);
				value();
				setState(135);
				match(T__26);
				setState(136);
				value();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(138);
				value();
				setState(139);
				match(T__27);
				setState(140);
				value();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(142);
				value();
				setState(143);
				match(T__28);
				setState(144);
				value();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConditionContext extends ParserRuleContext {
		public List<ValueContext> value() {
			return getRuleContexts(ValueContext.class);
		}
		public ValueContext value(int i) {
			return getRuleContext(ValueContext.class,i);
		}
		public ConditionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_condition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JFTTListener ) ((JFTTListener)listener).enterCondition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JFTTListener ) ((JFTTListener)listener).exitCondition(this);
		}
	}

	public final ConditionContext condition() throws RecognitionException {
		ConditionContext _localctx = new ConditionContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_condition);
		try {
			setState(172);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(148);
				value();
				setState(149);
				match(T__29);
				setState(150);
				value();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(152);
				value();
				setState(153);
				match(T__30);
				setState(154);
				value();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(156);
				value();
				setState(157);
				match(T__31);
				setState(158);
				value();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(160);
				value();
				setState(161);
				match(T__32);
				setState(162);
				value();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(164);
				value();
				setState(165);
				match(T__33);
				setState(166);
				value();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(168);
				value();
				setState(169);
				match(T__34);
				setState(170);
				value();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ValueContext extends ParserRuleContext {
		public TerminalNode NUM() { return getToken(JFTTParser.NUM, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public ValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JFTTListener ) ((JFTTListener)listener).enterValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JFTTListener ) ((JFTTListener)listener).exitValue(this);
		}
	}

	public final ValueContext value() throws RecognitionException {
		ValueContext _localctx = new ValueContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_value);
		try {
			setState(176);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case NUM:
				enterOuterAlt(_localctx, 1);
				{
				setState(174);
				match(NUM);
				}
				break;
			case PIDENTIFIER:
				enterOuterAlt(_localctx, 2);
				{
				setState(175);
				identifier();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IdentifierContext extends ParserRuleContext {
		public List<TerminalNode> PIDENTIFIER() { return getTokens(JFTTParser.PIDENTIFIER); }
		public TerminalNode PIDENTIFIER(int i) {
			return getToken(JFTTParser.PIDENTIFIER, i);
		}
		public TerminalNode NUM() { return getToken(JFTTParser.NUM, 0); }
		public IdentifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_identifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JFTTListener ) ((JFTTListener)listener).enterIdentifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JFTTListener ) ((JFTTListener)listener).exitIdentifier(this);
		}
	}

	public final IdentifierContext identifier() throws RecognitionException {
		IdentifierContext _localctx = new IdentifierContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_identifier);
		try {
			setState(187);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(178);
				match(PIDENTIFIER);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(179);
				match(PIDENTIFIER);
				setState(180);
				match(T__4);
				setState(181);
				match(PIDENTIFIER);
				setState(182);
				match(T__6);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(183);
				match(PIDENTIFIER);
				setState(184);
				match(T__4);
				setState(185);
				match(NUM);
				setState(186);
				match(T__6);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 1:
			return declarations_sempred((DeclarationsContext)_localctx, predIndex);
		case 2:
			return commands_sempred((CommandsContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean declarations_sempred(DeclarationsContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 4);
		case 1:
			return precpred(_ctx, 3);
		}
		return true;
	}
	private boolean commands_sempred(CommandsContext _localctx, int predIndex) {
		switch (predIndex) {
		case 2:
			return precpred(_ctx, 2);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3)\u00c0\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\3\2\3\2\3\2\3\2"+
		"\3\2\3\2\3\2\3\2\3\2\3\2\5\2\35\n\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\5"+
		"\3\'\n\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\7\3\64\n\3\f\3\16"+
		"\3\67\13\3\3\4\3\4\3\4\3\4\3\4\7\4>\n\4\f\4\16\4A\13\4\3\5\3\5\3\5\3\5"+
		"\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3"+
		"\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5"+
		"\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3"+
		"\5\3\5\3\5\5\5~\n\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3"+
		"\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\5\6\u0095\n\6\3\7\3\7\3\7\3\7\3\7\3"+
		"\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7"+
		"\3\7\5\7\u00af\n\7\3\b\3\b\5\b\u00b3\n\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3"+
		"\t\3\t\5\t\u00be\n\t\3\t\2\4\4\6\n\2\4\6\b\n\f\16\20\2\2\2\u00d1\2\34"+
		"\3\2\2\2\4&\3\2\2\2\68\3\2\2\2\b}\3\2\2\2\n\u0094\3\2\2\2\f\u00ae\3\2"+
		"\2\2\16\u00b2\3\2\2\2\20\u00bd\3\2\2\2\22\23\7\3\2\2\23\24\5\4\3\2\24"+
		"\25\7\4\2\2\25\26\5\6\4\2\26\27\7\5\2\2\27\35\3\2\2\2\30\31\7\4\2\2\31"+
		"\32\5\6\4\2\32\33\7\5\2\2\33\35\3\2\2\2\34\22\3\2\2\2\34\30\3\2\2\2\35"+
		"\3\3\2\2\2\36\37\b\3\1\2\37\'\7(\2\2 !\7(\2\2!\"\7\7\2\2\"#\7)\2\2#$\7"+
		"\b\2\2$%\7)\2\2%\'\7\t\2\2&\36\3\2\2\2& \3\2\2\2\'\65\3\2\2\2()\f\6\2"+
		"\2)*\7\6\2\2*\64\7(\2\2+,\f\5\2\2,-\7\6\2\2-.\7(\2\2./\7\7\2\2/\60\7)"+
		"\2\2\60\61\7\b\2\2\61\62\7)\2\2\62\64\7\t\2\2\63(\3\2\2\2\63+\3\2\2\2"+
		"\64\67\3\2\2\2\65\63\3\2\2\2\65\66\3\2\2\2\66\5\3\2\2\2\67\65\3\2\2\2"+
		"89\b\4\1\29:\5\b\5\2:?\3\2\2\2;<\f\4\2\2<>\5\b\5\2=;\3\2\2\2>A\3\2\2\2"+
		"?=\3\2\2\2?@\3\2\2\2@\7\3\2\2\2A?\3\2\2\2BC\5\20\t\2CD\7\n\2\2DE\5\n\6"+
		"\2EF\7\13\2\2F~\3\2\2\2GH\7\f\2\2HI\5\f\7\2IJ\7\r\2\2JK\5\6\4\2KL\7\16"+
		"\2\2LM\5\6\4\2MN\7\17\2\2N~\3\2\2\2OP\7\f\2\2PQ\5\f\7\2QR\7\r\2\2RS\5"+
		"\6\4\2ST\7\17\2\2T~\3\2\2\2UV\7\20\2\2VW\5\f\7\2WX\7\21\2\2XY\5\6\4\2"+
		"YZ\7\22\2\2Z~\3\2\2\2[\\\7\21\2\2\\]\5\6\4\2]^\7\20\2\2^_\5\f\7\2_`\7"+
		"\23\2\2`~\3\2\2\2ab\7\24\2\2bc\7(\2\2cd\7\25\2\2de\5\16\b\2ef\7\26\2\2"+
		"fg\5\16\b\2gh\7\21\2\2hi\5\6\4\2ij\7\27\2\2j~\3\2\2\2kl\7\24\2\2lm\7("+
		"\2\2mn\7\25\2\2no\5\16\b\2op\7\30\2\2pq\5\16\b\2qr\7\21\2\2rs\5\6\4\2"+
		"st\7\27\2\2t~\3\2\2\2uv\7\31\2\2vw\5\20\t\2wx\7\13\2\2x~\3\2\2\2yz\7\32"+
		"\2\2z{\5\16\b\2{|\7\13\2\2|~\3\2\2\2}B\3\2\2\2}G\3\2\2\2}O\3\2\2\2}U\3"+
		"\2\2\2}[\3\2\2\2}a\3\2\2\2}k\3\2\2\2}u\3\2\2\2}y\3\2\2\2~\t\3\2\2\2\177"+
		"\u0095\5\16\b\2\u0080\u0081\5\16\b\2\u0081\u0082\7\33\2\2\u0082\u0083"+
		"\5\16\b\2\u0083\u0095\3\2\2\2\u0084\u0085\5\16\b\2\u0085\u0086\7\34\2"+
		"\2\u0086\u0087\5\16\b\2\u0087\u0095\3\2\2\2\u0088\u0089\5\16\b\2\u0089"+
		"\u008a\7\35\2\2\u008a\u008b\5\16\b\2\u008b\u0095\3\2\2\2\u008c\u008d\5"+
		"\16\b\2\u008d\u008e\7\36\2\2\u008e\u008f\5\16\b\2\u008f\u0095\3\2\2\2"+
		"\u0090\u0091\5\16\b\2\u0091\u0092\7\37\2\2\u0092\u0093\5\16\b\2\u0093"+
		"\u0095\3\2\2\2\u0094\177\3\2\2\2\u0094\u0080\3\2\2\2\u0094\u0084\3\2\2"+
		"\2\u0094\u0088\3\2\2\2\u0094\u008c\3\2\2\2\u0094\u0090\3\2\2\2\u0095\13"+
		"\3\2\2\2\u0096\u0097\5\16\b\2\u0097\u0098\7 \2\2\u0098\u0099\5\16\b\2"+
		"\u0099\u00af\3\2\2\2\u009a\u009b\5\16\b\2\u009b\u009c\7!\2\2\u009c\u009d"+
		"\5\16\b\2\u009d\u00af\3\2\2\2\u009e\u009f\5\16\b\2\u009f\u00a0\7\"\2\2"+
		"\u00a0\u00a1\5\16\b\2\u00a1\u00af\3\2\2\2\u00a2\u00a3\5\16\b\2\u00a3\u00a4"+
		"\7#\2\2\u00a4\u00a5\5\16\b\2\u00a5\u00af\3\2\2\2\u00a6\u00a7\5\16\b\2"+
		"\u00a7\u00a8\7$\2\2\u00a8\u00a9\5\16\b\2\u00a9\u00af\3\2\2\2\u00aa\u00ab"+
		"\5\16\b\2\u00ab\u00ac\7%\2\2\u00ac\u00ad\5\16\b\2\u00ad\u00af\3\2\2\2"+
		"\u00ae\u0096\3\2\2\2\u00ae\u009a\3\2\2\2\u00ae\u009e\3\2\2\2\u00ae\u00a2"+
		"\3\2\2\2\u00ae\u00a6\3\2\2\2\u00ae\u00aa\3\2\2\2\u00af\r\3\2\2\2\u00b0"+
		"\u00b3\7)\2\2\u00b1\u00b3\5\20\t\2\u00b2\u00b0\3\2\2\2\u00b2\u00b1\3\2"+
		"\2\2\u00b3\17\3\2\2\2\u00b4\u00be\7(\2\2\u00b5\u00b6\7(\2\2\u00b6\u00b7"+
		"\7\7\2\2\u00b7\u00b8\7(\2\2\u00b8\u00be\7\t\2\2\u00b9\u00ba\7(\2\2\u00ba"+
		"\u00bb\7\7\2\2\u00bb\u00bc\7)\2\2\u00bc\u00be\7\t\2\2\u00bd\u00b4\3\2"+
		"\2\2\u00bd\u00b5\3\2\2\2\u00bd\u00b9\3\2\2\2\u00be\21\3\2\2\2\f\34&\63"+
		"\65?}\u0094\u00ae\u00b2\u00bd";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}