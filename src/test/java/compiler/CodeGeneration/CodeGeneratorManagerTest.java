package compiler.CodeGeneration;

import compiler.GrammarParser.ParserManager;
import compiler.GrammarParser.UndeclaredVariableErrorDetector;
import compiler.GrammarParser.VariableRedefinitionErrorDetector;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.Test;
import parser.JFTTLexer;
import parser.JFTTParser;

import static org.junit.Assert.*;

public class CodeGeneratorManagerTest {

    @Test
    public void assignIdentifierLocations() {
/*        String query = "DECLARE a,b,c(-5:-3),d,e(1000000:1000010), f BEGIN WRITE 3; END";

        //Creating lexer
        JFTTLexer lexer = new JFTTLexer(CharStreams.fromString(query));
        JFTTParser parser = new JFTTParser(new CommonTokenStream(lexer));

        //Running parser
        ParserManager parserManager = new ParserManager();
        parserManager.addErrorDetector(new VariableRedefinitionErrorDetector());
        parserManager.addErrorDetector(new UndeclaredVariableErrorDetector());
        parserManager.runAll(parser);

        CodeGeneratorManager codeGeneratorManager = new CodeGeneratorManager(parserManager.getSymbolTable(), parser);
        codeGeneratorManager.assignIdentifierLocations();
        String result = codeGeneratorManager.printMemoryIdentifierAssigment();
        assertEquals("[ a:1 b:2 c:3 d:9 e:10 f:24 ]", result);*/
    }
}