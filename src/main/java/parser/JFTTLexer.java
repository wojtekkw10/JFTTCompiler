// Generated from JFTT.g4 by ANTLR 4.7.2

    package parser;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class JFTTLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, BLANK=17, 
		COMMENT=18, PIDENTIFIER=19, NUM=20, READ=21, WRITE=22, ASSIGN=23, PLUS=24, 
		MINUS=25, TIMES=26, DIV=27, MOD=28, FOR=29, EQ=30, NEQ=31, LE=32, GE=33, 
		LEQ=34, GEQ=35, IF=36, THEN=37, ELSE=38, ENDIF=39;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
			"T__9", "T__10", "T__11", "T__12", "T__13", "T__14", "T__15", "BLANK", 
			"COMMENT", "PIDENTIFIER", "NUM", "READ", "WRITE", "ASSIGN", "PLUS", "MINUS", 
			"TIMES", "DIV", "MOD", "FOR", "EQ", "NEQ", "LE", "GE", "LEQ", "GEQ", 
			"IF", "THEN", "ELSE", "ENDIF"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'DECLARE'", "'BEGIN'", "'END'", "','", "'('", "':'", "')'", "';'", 
			"'WHILE'", "'DO'", "'ENDWHILE'", "'ENDDO'", "'FROM'", "'TO'", "'ENDFOR'", 
			"'DOWNTO'", null, null, null, null, "'READ'", "'WRITE'", "'ASSIGN'", 
			"'PLUS'", "'MINUS'", "'TIMES'", "'DIV'", "'MOD'", "'FOR'", "'EQ'", "'NEQ'", 
			"'LE'", "'GE'", "'LEQ'", "'GEQ'", "'IF'", "'THEN'", "'ELSE'", "'ENDIF'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, "BLANK", "COMMENT", "PIDENTIFIER", "NUM", 
			"READ", "WRITE", "ASSIGN", "PLUS", "MINUS", "TIMES", "DIV", "MOD", "FOR", 
			"EQ", "NEQ", "LE", "GE", "LEQ", "GEQ", "IF", "THEN", "ELSE", "ENDIF"
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


	public JFTTLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "JFTT.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2)\u010e\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\3\2\3\2\3\2\3\2\3\2"+
		"\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\5\3\5\3\6\3\6\3"+
		"\7\3\7\3\b\3\b\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\f\3\f"+
		"\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3"+
		"\16\3\16\3\17\3\17\3\17\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\21\3\21\3"+
		"\21\3\21\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\23\3\23\7\23\u00a2\n\23"+
		"\f\23\16\23\u00a5\13\23\3\23\3\23\3\23\3\23\3\24\6\24\u00ac\n\24\r\24"+
		"\16\24\u00ad\3\25\5\25\u00b1\n\25\3\25\6\25\u00b4\n\25\r\25\16\25\u00b5"+
		"\3\26\3\26\3\26\3\26\3\26\3\27\3\27\3\27\3\27\3\27\3\27\3\30\3\30\3\30"+
		"\3\30\3\30\3\30\3\30\3\31\3\31\3\31\3\31\3\31\3\32\3\32\3\32\3\32\3\32"+
		"\3\32\3\33\3\33\3\33\3\33\3\33\3\33\3\34\3\34\3\34\3\34\3\35\3\35\3\35"+
		"\3\35\3\36\3\36\3\36\3\36\3\37\3\37\3\37\3 \3 \3 \3 \3!\3!\3!\3\"\3\""+
		"\3\"\3#\3#\3#\3#\3$\3$\3$\3$\3%\3%\3%\3&\3&\3&\3&\3&\3\'\3\'\3\'\3\'\3"+
		"\'\3(\3(\3(\3(\3(\3(\3\u00a3\2)\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23"+
		"\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31"+
		"\61\32\63\33\65\34\67\359\36;\37= ?!A\"C#E$G%I&K\'M(O)\3\2\5\5\2\13\f"+
		"\17\17\"\"\4\2aac|\3\2\62;\2\u0111\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2"+
		"\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3"+
		"\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2"+
		"\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2"+
		"\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2"+
		"\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2"+
		"\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2"+
		"O\3\2\2\2\3Q\3\2\2\2\5Y\3\2\2\2\7_\3\2\2\2\tc\3\2\2\2\13e\3\2\2\2\rg\3"+
		"\2\2\2\17i\3\2\2\2\21k\3\2\2\2\23m\3\2\2\2\25s\3\2\2\2\27v\3\2\2\2\31"+
		"\177\3\2\2\2\33\u0085\3\2\2\2\35\u008a\3\2\2\2\37\u008d\3\2\2\2!\u0094"+
		"\3\2\2\2#\u009b\3\2\2\2%\u009f\3\2\2\2\'\u00ab\3\2\2\2)\u00b0\3\2\2\2"+
		"+\u00b7\3\2\2\2-\u00bc\3\2\2\2/\u00c2\3\2\2\2\61\u00c9\3\2\2\2\63\u00ce"+
		"\3\2\2\2\65\u00d4\3\2\2\2\67\u00da\3\2\2\29\u00de\3\2\2\2;\u00e2\3\2\2"+
		"\2=\u00e6\3\2\2\2?\u00e9\3\2\2\2A\u00ed\3\2\2\2C\u00f0\3\2\2\2E\u00f3"+
		"\3\2\2\2G\u00f7\3\2\2\2I\u00fb\3\2\2\2K\u00fe\3\2\2\2M\u0103\3\2\2\2O"+
		"\u0108\3\2\2\2QR\7F\2\2RS\7G\2\2ST\7E\2\2TU\7N\2\2UV\7C\2\2VW\7T\2\2W"+
		"X\7G\2\2X\4\3\2\2\2YZ\7D\2\2Z[\7G\2\2[\\\7I\2\2\\]\7K\2\2]^\7P\2\2^\6"+
		"\3\2\2\2_`\7G\2\2`a\7P\2\2ab\7F\2\2b\b\3\2\2\2cd\7.\2\2d\n\3\2\2\2ef\7"+
		"*\2\2f\f\3\2\2\2gh\7<\2\2h\16\3\2\2\2ij\7+\2\2j\20\3\2\2\2kl\7=\2\2l\22"+
		"\3\2\2\2mn\7Y\2\2no\7J\2\2op\7K\2\2pq\7N\2\2qr\7G\2\2r\24\3\2\2\2st\7"+
		"F\2\2tu\7Q\2\2u\26\3\2\2\2vw\7G\2\2wx\7P\2\2xy\7F\2\2yz\7Y\2\2z{\7J\2"+
		"\2{|\7K\2\2|}\7N\2\2}~\7G\2\2~\30\3\2\2\2\177\u0080\7G\2\2\u0080\u0081"+
		"\7P\2\2\u0081\u0082\7F\2\2\u0082\u0083\7F\2\2\u0083\u0084\7Q\2\2\u0084"+
		"\32\3\2\2\2\u0085\u0086\7H\2\2\u0086\u0087\7T\2\2\u0087\u0088\7Q\2\2\u0088"+
		"\u0089\7O\2\2\u0089\34\3\2\2\2\u008a\u008b\7V\2\2\u008b\u008c\7Q\2\2\u008c"+
		"\36\3\2\2\2\u008d\u008e\7G\2\2\u008e\u008f\7P\2\2\u008f\u0090\7F\2\2\u0090"+
		"\u0091\7H\2\2\u0091\u0092\7Q\2\2\u0092\u0093\7T\2\2\u0093 \3\2\2\2\u0094"+
		"\u0095\7F\2\2\u0095\u0096\7Q\2\2\u0096\u0097\7Y\2\2\u0097\u0098\7P\2\2"+
		"\u0098\u0099\7V\2\2\u0099\u009a\7Q\2\2\u009a\"\3\2\2\2\u009b\u009c\t\2"+
		"\2\2\u009c\u009d\3\2\2\2\u009d\u009e\b\22\2\2\u009e$\3\2\2\2\u009f\u00a3"+
		"\7]\2\2\u00a0\u00a2\13\2\2\2\u00a1\u00a0\3\2\2\2\u00a2\u00a5\3\2\2\2\u00a3"+
		"\u00a4\3\2\2\2\u00a3\u00a1\3\2\2\2\u00a4\u00a6\3\2\2\2\u00a5\u00a3\3\2"+
		"\2\2\u00a6\u00a7\7_\2\2\u00a7\u00a8\3\2\2\2\u00a8\u00a9\b\23\2\2\u00a9"+
		"&\3\2\2\2\u00aa\u00ac\t\3\2\2\u00ab\u00aa\3\2\2\2\u00ac\u00ad\3\2\2\2"+
		"\u00ad\u00ab\3\2\2\2\u00ad\u00ae\3\2\2\2\u00ae(\3\2\2\2\u00af\u00b1\7"+
		"/\2\2\u00b0\u00af\3\2\2\2\u00b0\u00b1\3\2\2\2\u00b1\u00b3\3\2\2\2\u00b2"+
		"\u00b4\t\4\2\2\u00b3\u00b2\3\2\2\2\u00b4\u00b5\3\2\2\2\u00b5\u00b3\3\2"+
		"\2\2\u00b5\u00b6\3\2\2\2\u00b6*\3\2\2\2\u00b7\u00b8\7T\2\2\u00b8\u00b9"+
		"\7G\2\2\u00b9\u00ba\7C\2\2\u00ba\u00bb\7F\2\2\u00bb,\3\2\2\2\u00bc\u00bd"+
		"\7Y\2\2\u00bd\u00be\7T\2\2\u00be\u00bf\7K\2\2\u00bf\u00c0\7V\2\2\u00c0"+
		"\u00c1\7G\2\2\u00c1.\3\2\2\2\u00c2\u00c3\7C\2\2\u00c3\u00c4\7U\2\2\u00c4"+
		"\u00c5\7U\2\2\u00c5\u00c6\7K\2\2\u00c6\u00c7\7I\2\2\u00c7\u00c8\7P\2\2"+
		"\u00c8\60\3\2\2\2\u00c9\u00ca\7R\2\2\u00ca\u00cb\7N\2\2\u00cb\u00cc\7"+
		"W\2\2\u00cc\u00cd\7U\2\2\u00cd\62\3\2\2\2\u00ce\u00cf\7O\2\2\u00cf\u00d0"+
		"\7K\2\2\u00d0\u00d1\7P\2\2\u00d1\u00d2\7W\2\2\u00d2\u00d3\7U\2\2\u00d3"+
		"\64\3\2\2\2\u00d4\u00d5\7V\2\2\u00d5\u00d6\7K\2\2\u00d6\u00d7\7O\2\2\u00d7"+
		"\u00d8\7G\2\2\u00d8\u00d9\7U\2\2\u00d9\66\3\2\2\2\u00da\u00db\7F\2\2\u00db"+
		"\u00dc\7K\2\2\u00dc\u00dd\7X\2\2\u00dd8\3\2\2\2\u00de\u00df\7O\2\2\u00df"+
		"\u00e0\7Q\2\2\u00e0\u00e1\7F\2\2\u00e1:\3\2\2\2\u00e2\u00e3\7H\2\2\u00e3"+
		"\u00e4\7Q\2\2\u00e4\u00e5\7T\2\2\u00e5<\3\2\2\2\u00e6\u00e7\7G\2\2\u00e7"+
		"\u00e8\7S\2\2\u00e8>\3\2\2\2\u00e9\u00ea\7P\2\2\u00ea\u00eb\7G\2\2\u00eb"+
		"\u00ec\7S\2\2\u00ec@\3\2\2\2\u00ed\u00ee\7N\2\2\u00ee\u00ef\7G\2\2\u00ef"+
		"B\3\2\2\2\u00f0\u00f1\7I\2\2\u00f1\u00f2\7G\2\2\u00f2D\3\2\2\2\u00f3\u00f4"+
		"\7N\2\2\u00f4\u00f5\7G\2\2\u00f5\u00f6\7S\2\2\u00f6F\3\2\2\2\u00f7\u00f8"+
		"\7I\2\2\u00f8\u00f9\7G\2\2\u00f9\u00fa\7S\2\2\u00faH\3\2\2\2\u00fb\u00fc"+
		"\7K\2\2\u00fc\u00fd\7H\2\2\u00fdJ\3\2\2\2\u00fe\u00ff\7V\2\2\u00ff\u0100"+
		"\7J\2\2\u0100\u0101\7G\2\2\u0101\u0102\7P\2\2\u0102L\3\2\2\2\u0103\u0104"+
		"\7G\2\2\u0104\u0105\7N\2\2\u0105\u0106\7U\2\2\u0106\u0107\7G\2\2\u0107"+
		"N\3\2\2\2\u0108\u0109\7G\2\2\u0109\u010a\7P\2\2\u010a\u010b\7F\2\2\u010b"+
		"\u010c\7K\2\2\u010c\u010d\7H\2\2\u010dP\3\2\2\2\7\2\u00a3\u00ad\u00b0"+
		"\u00b5\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}