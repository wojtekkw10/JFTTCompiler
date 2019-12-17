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
		T__9=10, T__10=11, T__11=12, T__12=13, BLANK=14, COMMENT=15, PIDENTIFIER=16, 
		NUM=17, READ=18, WRITE=19, ASSIGN=20, PLUS=21, MINUS=22, TIMES=23, DIV=24, 
		MOD=25, FOR=26, EQ=27, NEQ=28, LE=29, GE=30, LEQ=31, GEQ=32, IF=33, THEN=34, 
		ELSE=35, ENDIF=36, WHILE=37, DO=38, ENDWHILE=39;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
			"T__9", "T__10", "T__11", "T__12", "BLANK", "COMMENT", "PIDENTIFIER", 
			"NUM", "READ", "WRITE", "ASSIGN", "PLUS", "MINUS", "TIMES", "DIV", "MOD", 
			"FOR", "EQ", "NEQ", "LE", "GE", "LEQ", "GEQ", "IF", "THEN", "ELSE", "ENDIF", 
			"WHILE", "DO", "ENDWHILE"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'DECLARE'", "'BEGIN'", "'END'", "','", "'('", "':'", "')'", "';'", 
			"'ENDDO'", "'FROM'", "'TO'", "'ENDFOR'", "'DOWNTO'", null, null, null, 
			null, "'READ'", "'WRITE'", "'ASSIGN'", "'PLUS'", "'MINUS'", "'TIMES'", 
			"'DIV'", "'MOD'", "'FOR'", "'EQ'", "'NEQ'", "'LE'", "'GE'", "'LEQ'", 
			"'GEQ'", "'IF'", "'THEN'", "'ELSE'", "'ENDIF'", "'WHILE'", "'DO'", "'ENDWHILE'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, "BLANK", "COMMENT", "PIDENTIFIER", "NUM", "READ", "WRITE", 
			"ASSIGN", "PLUS", "MINUS", "TIMES", "DIV", "MOD", "FOR", "EQ", "NEQ", 
			"LE", "GE", "LEQ", "GEQ", "IF", "THEN", "ELSE", "ENDIF", "WHILE", "DO", 
			"ENDWHILE"
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
		"\7\3\7\3\b\3\b\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13"+
		"\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\16"+
		"\3\16\3\17\3\17\3\17\3\17\3\20\3\20\7\20\u0090\n\20\f\20\16\20\u0093\13"+
		"\20\3\20\3\20\3\20\3\20\3\21\6\21\u009a\n\21\r\21\16\21\u009b\3\22\5\22"+
		"\u009f\n\22\3\22\6\22\u00a2\n\22\r\22\16\22\u00a3\3\23\3\23\3\23\3\23"+
		"\3\23\3\24\3\24\3\24\3\24\3\24\3\24\3\25\3\25\3\25\3\25\3\25\3\25\3\25"+
		"\3\26\3\26\3\26\3\26\3\26\3\27\3\27\3\27\3\27\3\27\3\27\3\30\3\30\3\30"+
		"\3\30\3\30\3\30\3\31\3\31\3\31\3\31\3\32\3\32\3\32\3\32\3\33\3\33\3\33"+
		"\3\33\3\34\3\34\3\34\3\35\3\35\3\35\3\35\3\36\3\36\3\36\3\37\3\37\3\37"+
		"\3 \3 \3 \3 \3!\3!\3!\3!\3\"\3\"\3\"\3#\3#\3#\3#\3#\3$\3$\3$\3$\3$\3%"+
		"\3%\3%\3%\3%\3%\3&\3&\3&\3&\3&\3&\3\'\3\'\3\'\3(\3(\3(\3(\3(\3(\3(\3("+
		"\3(\3\u0091\2)\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16"+
		"\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33\65\34"+
		"\67\359\36;\37= ?!A\"C#E$G%I&K\'M(O)\3\2\5\5\2\13\f\17\17\"\"\4\2aac|"+
		"\3\2\62;\2\u0111\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13"+
		"\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2"+
		"\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2"+
		"!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3"+
		"\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2"+
		"\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E"+
		"\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2\3Q\3\2"+
		"\2\2\5Y\3\2\2\2\7_\3\2\2\2\tc\3\2\2\2\13e\3\2\2\2\rg\3\2\2\2\17i\3\2\2"+
		"\2\21k\3\2\2\2\23m\3\2\2\2\25s\3\2\2\2\27x\3\2\2\2\31{\3\2\2\2\33\u0082"+
		"\3\2\2\2\35\u0089\3\2\2\2\37\u008d\3\2\2\2!\u0099\3\2\2\2#\u009e\3\2\2"+
		"\2%\u00a5\3\2\2\2\'\u00aa\3\2\2\2)\u00b0\3\2\2\2+\u00b7\3\2\2\2-\u00bc"+
		"\3\2\2\2/\u00c2\3\2\2\2\61\u00c8\3\2\2\2\63\u00cc\3\2\2\2\65\u00d0\3\2"+
		"\2\2\67\u00d4\3\2\2\29\u00d7\3\2\2\2;\u00db\3\2\2\2=\u00de\3\2\2\2?\u00e1"+
		"\3\2\2\2A\u00e5\3\2\2\2C\u00e9\3\2\2\2E\u00ec\3\2\2\2G\u00f1\3\2\2\2I"+
		"\u00f6\3\2\2\2K\u00fc\3\2\2\2M\u0102\3\2\2\2O\u0105\3\2\2\2QR\7F\2\2R"+
		"S\7G\2\2ST\7E\2\2TU\7N\2\2UV\7C\2\2VW\7T\2\2WX\7G\2\2X\4\3\2\2\2YZ\7D"+
		"\2\2Z[\7G\2\2[\\\7I\2\2\\]\7K\2\2]^\7P\2\2^\6\3\2\2\2_`\7G\2\2`a\7P\2"+
		"\2ab\7F\2\2b\b\3\2\2\2cd\7.\2\2d\n\3\2\2\2ef\7*\2\2f\f\3\2\2\2gh\7<\2"+
		"\2h\16\3\2\2\2ij\7+\2\2j\20\3\2\2\2kl\7=\2\2l\22\3\2\2\2mn\7G\2\2no\7"+
		"P\2\2op\7F\2\2pq\7F\2\2qr\7Q\2\2r\24\3\2\2\2st\7H\2\2tu\7T\2\2uv\7Q\2"+
		"\2vw\7O\2\2w\26\3\2\2\2xy\7V\2\2yz\7Q\2\2z\30\3\2\2\2{|\7G\2\2|}\7P\2"+
		"\2}~\7F\2\2~\177\7H\2\2\177\u0080\7Q\2\2\u0080\u0081\7T\2\2\u0081\32\3"+
		"\2\2\2\u0082\u0083\7F\2\2\u0083\u0084\7Q\2\2\u0084\u0085\7Y\2\2\u0085"+
		"\u0086\7P\2\2\u0086\u0087\7V\2\2\u0087\u0088\7Q\2\2\u0088\34\3\2\2\2\u0089"+
		"\u008a\t\2\2\2\u008a\u008b\3\2\2\2\u008b\u008c\b\17\2\2\u008c\36\3\2\2"+
		"\2\u008d\u0091\7]\2\2\u008e\u0090\13\2\2\2\u008f\u008e\3\2\2\2\u0090\u0093"+
		"\3\2\2\2\u0091\u0092\3\2\2\2\u0091\u008f\3\2\2\2\u0092\u0094\3\2\2\2\u0093"+
		"\u0091\3\2\2\2\u0094\u0095\7_\2\2\u0095\u0096\3\2\2\2\u0096\u0097\b\20"+
		"\2\2\u0097 \3\2\2\2\u0098\u009a\t\3\2\2\u0099\u0098\3\2\2\2\u009a\u009b"+
		"\3\2\2\2\u009b\u0099\3\2\2\2\u009b\u009c\3\2\2\2\u009c\"\3\2\2\2\u009d"+
		"\u009f\7/\2\2\u009e\u009d\3\2\2\2\u009e\u009f\3\2\2\2\u009f\u00a1\3\2"+
		"\2\2\u00a0\u00a2\t\4\2\2\u00a1\u00a0\3\2\2\2\u00a2\u00a3\3\2\2\2\u00a3"+
		"\u00a1\3\2\2\2\u00a3\u00a4\3\2\2\2\u00a4$\3\2\2\2\u00a5\u00a6\7T\2\2\u00a6"+
		"\u00a7\7G\2\2\u00a7\u00a8\7C\2\2\u00a8\u00a9\7F\2\2\u00a9&\3\2\2\2\u00aa"+
		"\u00ab\7Y\2\2\u00ab\u00ac\7T\2\2\u00ac\u00ad\7K\2\2\u00ad\u00ae\7V\2\2"+
		"\u00ae\u00af\7G\2\2\u00af(\3\2\2\2\u00b0\u00b1\7C\2\2\u00b1\u00b2\7U\2"+
		"\2\u00b2\u00b3\7U\2\2\u00b3\u00b4\7K\2\2\u00b4\u00b5\7I\2\2\u00b5\u00b6"+
		"\7P\2\2\u00b6*\3\2\2\2\u00b7\u00b8\7R\2\2\u00b8\u00b9\7N\2\2\u00b9\u00ba"+
		"\7W\2\2\u00ba\u00bb\7U\2\2\u00bb,\3\2\2\2\u00bc\u00bd\7O\2\2\u00bd\u00be"+
		"\7K\2\2\u00be\u00bf\7P\2\2\u00bf\u00c0\7W\2\2\u00c0\u00c1\7U\2\2\u00c1"+
		".\3\2\2\2\u00c2\u00c3\7V\2\2\u00c3\u00c4\7K\2\2\u00c4\u00c5\7O\2\2\u00c5"+
		"\u00c6\7G\2\2\u00c6\u00c7\7U\2\2\u00c7\60\3\2\2\2\u00c8\u00c9\7F\2\2\u00c9"+
		"\u00ca\7K\2\2\u00ca\u00cb\7X\2\2\u00cb\62\3\2\2\2\u00cc\u00cd\7O\2\2\u00cd"+
		"\u00ce\7Q\2\2\u00ce\u00cf\7F\2\2\u00cf\64\3\2\2\2\u00d0\u00d1\7H\2\2\u00d1"+
		"\u00d2\7Q\2\2\u00d2\u00d3\7T\2\2\u00d3\66\3\2\2\2\u00d4\u00d5\7G\2\2\u00d5"+
		"\u00d6\7S\2\2\u00d68\3\2\2\2\u00d7\u00d8\7P\2\2\u00d8\u00d9\7G\2\2\u00d9"+
		"\u00da\7S\2\2\u00da:\3\2\2\2\u00db\u00dc\7N\2\2\u00dc\u00dd\7G\2\2\u00dd"+
		"<\3\2\2\2\u00de\u00df\7I\2\2\u00df\u00e0\7G\2\2\u00e0>\3\2\2\2\u00e1\u00e2"+
		"\7N\2\2\u00e2\u00e3\7G\2\2\u00e3\u00e4\7S\2\2\u00e4@\3\2\2\2\u00e5\u00e6"+
		"\7I\2\2\u00e6\u00e7\7G\2\2\u00e7\u00e8\7S\2\2\u00e8B\3\2\2\2\u00e9\u00ea"+
		"\7K\2\2\u00ea\u00eb\7H\2\2\u00ebD\3\2\2\2\u00ec\u00ed\7V\2\2\u00ed\u00ee"+
		"\7J\2\2\u00ee\u00ef\7G\2\2\u00ef\u00f0\7P\2\2\u00f0F\3\2\2\2\u00f1\u00f2"+
		"\7G\2\2\u00f2\u00f3\7N\2\2\u00f3\u00f4\7U\2\2\u00f4\u00f5\7G\2\2\u00f5"+
		"H\3\2\2\2\u00f6\u00f7\7G\2\2\u00f7\u00f8\7P\2\2\u00f8\u00f9\7F\2\2\u00f9"+
		"\u00fa\7K\2\2\u00fa\u00fb\7H\2\2\u00fbJ\3\2\2\2\u00fc\u00fd\7Y\2\2\u00fd"+
		"\u00fe\7J\2\2\u00fe\u00ff\7K\2\2\u00ff\u0100\7N\2\2\u0100\u0101\7G\2\2"+
		"\u0101L\3\2\2\2\u0102\u0103\7F\2\2\u0103\u0104\7Q\2\2\u0104N\3\2\2\2\u0105"+
		"\u0106\7G\2\2\u0106\u0107\7P\2\2\u0107\u0108\7F\2\2\u0108\u0109\7Y\2\2"+
		"\u0109\u010a\7J\2\2\u010a\u010b\7K\2\2\u010b\u010c\7N\2\2\u010c\u010d"+
		"\7G\2\2\u010dP\3\2\2\2\7\2\u0091\u009b\u009e\u00a3\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}