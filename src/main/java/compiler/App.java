/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package compiler;
import org.antlr.v4.runtime.*;
import parser.JFTTLexer;
import parser.JFTTParser;

public class App {

    public static void main(String[] args){
        //Parsing program parameters
        BaseArgParser argumentParser = new ArgParser();
        argumentParser.parse(args);

        //Creating Lexer
        JFTTLexer lexer = new JFTTLexer(argumentParser.getCharStream());
        JFTTParser parser = new JFTTParser(new CommonTokenStream(lexer));


        ParserManager parserManager = new ParserManager();
        parserManager.addErrorDetector(new VariableRedefinitionErrorDetector());
        parserManager.addErrorDetector(new UndeclaredVariableErrorDetector());
        parserManager.runAll(parser);

        System.out.println(parserManager);


    }
}

//TODO: refactor
//TODO: different stages of checking for errors in different classes that have common function check()
//TODO: Put them in an array and run in a sequence