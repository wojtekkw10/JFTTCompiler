package compiler;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.Test;
import parser.JFTTLexer;
import parser.JFTTParser;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ParserTest {

    @Test
    public void undeclaredVariableError() {
        String query = "DECLARE" +
                "n,n" +
                "BEGIN" +
                "n ASSIGN 5;" +
                "END";

        //Creating lexer
        JFTTLexer lexer = new JFTTLexer(CharStreams.fromString(query));
        JFTTParser parser = new JFTTParser(new CommonTokenStream(lexer));

        //Running parser
        ParserManager parserManager = new ParserManager();
        parserManager.addErrorDetector(new VariableRedefinitionErrorDetector());
        parserManager.addErrorDetector(new UndeclaredVariableErrorDetector());
        parserManager.runAll(parser);

        ArrayList<Error> errors = parserManager.getErrors();
        assertEquals("Should produce the Redefinition error", errors.get(0).message, "Redefinition of an identifier n");
    }

    @Test
    public void multipleUndeclaredVariablesError() {
        String query = "DECLARE" +
                "n,n,n,n,c,c" +
                "BEGIN" +
                "n ASSIGN 5;" +
                "END";

        //Creating lexer
        JFTTLexer lexer = new JFTTLexer(CharStreams.fromString(query));
        JFTTParser parser = new JFTTParser(new CommonTokenStream(lexer));

        //Running parser
        ParserManager parserManager = new ParserManager();
        parserManager.addErrorDetector(new VariableRedefinitionErrorDetector());
        parserManager.addErrorDetector(new UndeclaredVariableErrorDetector());
        parserManager.runAll(parser);

        ArrayList<Error> errors = parserManager.getErrors();
        assertEquals("Should produce the Redefinition error", errors.get(0).message, "Redefinition of an identifier c");
        assertEquals("Should produce the Redefinition error", errors.get(1).message, "Redefinition of an identifier n");
        assertEquals("Should produce the Redefinition error", errors.get(2).message, "Redefinition of an identifier n");
        assertEquals("Should produce the Redefinition error", errors.get(3).message, "Redefinition of an identifier n");
    }

    @Test
    public void variableArrayRedefinitionError() {
        String query = "DECLARE" +
                "n,c,c(1:2)" +
                "BEGIN" +
                "n ASSIGN 5;" +
                "END";

        //Creating lexer
        JFTTLexer lexer = new JFTTLexer(CharStreams.fromString(query));
        JFTTParser parser = new JFTTParser(new CommonTokenStream(lexer));

        //Running parser
        ParserManager parserManager = new ParserManager();
        parserManager.addErrorDetector(new VariableRedefinitionErrorDetector());
        parserManager.addErrorDetector(new UndeclaredVariableErrorDetector());
        parserManager.runAll(parser);

        ArrayList<Error> errors = parserManager.getErrors();
        assertEquals(errors.get(0).message, "Redefinition of an identifier c");
    }

    @Test
    public void variableUsedAsArrayError() {
        String query = "DECLARE" +
                "n, c(1:2)" +
                "BEGIN" +
                "n ASSIGN n(2);" +
                "END";

        //Creating lexer
        JFTTLexer lexer = new JFTTLexer(CharStreams.fromString(query));
        JFTTParser parser = new JFTTParser(new CommonTokenStream(lexer));

        //Running parser
        ParserManager parserManager = new ParserManager();
        parserManager.addErrorDetector(new VariableRedefinitionErrorDetector());
        parserManager.addErrorDetector(new UndeclaredVariableErrorDetector());
        parserManager.runAll(parser);

        ArrayList<Error> errors = parserManager.getErrors();
        assertEquals(errors.get(0).message, "Variable n used as an array");
    }

    @Test
    public void variableUsedAsArrayError2() {
        String query = "DECLARE n, c(1:2) BEGIN n ASSIGN n(n); END";

        //Creating lexer
        JFTTLexer lexer = new JFTTLexer(CharStreams.fromString(query));
        JFTTParser parser = new JFTTParser(new CommonTokenStream(lexer));

        //Running parser
        ParserManager parserManager = new ParserManager();
        parserManager.addErrorDetector(new VariableRedefinitionErrorDetector());
        parserManager.addErrorDetector(new UndeclaredVariableErrorDetector());
        parserManager.runAll(parser);

        ArrayList<Error> errors = parserManager.getErrors();
        assertEquals(errors.get(0).message, "Variable n used as an array");
    }

    @Test
    public void arrayUsedAsVariableError() {
        String query = "DECLARE n, c(1:2) BEGIN n ASSIGN c; END";

        //Creating lexer
        JFTTLexer lexer = new JFTTLexer(CharStreams.fromString(query));
        JFTTParser parser = new JFTTParser(new CommonTokenStream(lexer));

        //Running parser
        ParserManager parserManager = new ParserManager();
        parserManager.addErrorDetector(new VariableRedefinitionErrorDetector());
        parserManager.addErrorDetector(new UndeclaredVariableErrorDetector());
        parserManager.runAll(parser);

        ArrayList<Error> errors = parserManager.getErrors();
        assertEquals(errors.get(0).message, "Array c used as a variable");
    }

    @Test
    public void noDeclarationSection() {
        String query = "BEGIN IF 5 EQ 5 THEN WRITE 5; ENDIF END";

        //Creating lexer
        JFTTLexer lexer = new JFTTLexer(CharStreams.fromString(query));
        JFTTParser parser = new JFTTParser(new CommonTokenStream(lexer));

        //Running parser
        ParserManager parserManager = new ParserManager();
        parserManager.addErrorDetector(new VariableRedefinitionErrorDetector());
        parserManager.addErrorDetector(new UndeclaredVariableErrorDetector());
        parserManager.runAll(parser);

        ArrayList<Error> errors = parserManager.getErrors();
        assertEquals(errors.size(), 0);
    }

    @Test
    public void noDeclarationSectionErrors() {
        String query = "BEGIN a ASSIGN b(c); END";

        //Creating lexer
        JFTTLexer lexer = new JFTTLexer(CharStreams.fromString(query));
        JFTTParser parser = new JFTTParser(new CommonTokenStream(lexer));

        //Running parser
        ParserManager parserManager = new ParserManager();
        parserManager.addErrorDetector(new VariableRedefinitionErrorDetector());
        parserManager.addErrorDetector(new UndeclaredVariableErrorDetector());
        parserManager.runAll(parser);

        ArrayList<Error> errors = parserManager.getErrors();
        assertEquals(errors.size(), 3);
    }

    @Test
    public void noDeclarationSectionErrors2() {
        String query = "BEGIN a ASSIGN b(2); END";

        //Creating lexer
        JFTTLexer lexer = new JFTTLexer(CharStreams.fromString(query));
        JFTTParser parser = new JFTTParser(new CommonTokenStream(lexer));

        //Running parser
        ParserManager parserManager = new ParserManager();
        parserManager.addErrorDetector(new VariableRedefinitionErrorDetector());
        parserManager.addErrorDetector(new UndeclaredVariableErrorDetector());
        parserManager.runAll(parser);

        ArrayList<Error> errors = parserManager.getErrors();
        assertEquals(errors.size(), 2);
    }
}