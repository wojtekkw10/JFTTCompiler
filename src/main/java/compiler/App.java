/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package compiler;
import compiler.CodeGeneration.CodeGeneratorManager;
import compiler.GrammarParser.ParserManager;
import compiler.GrammarParser.UndeclaredVariableErrorDetector;
import compiler.GrammarParser.VariableRedefinitionErrorDetector;
import compiler.ProgramArgumentParser.ArgParser;
import compiler.ProgramArgumentParser.BaseArgParser;
import org.antlr.v4.runtime.*;
import parser.JFTTLexer;
import parser.JFTTParser;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class App {

    public static void main(String[] args){
        //Parsing program parameters
        BaseArgParser argumentParser = new ArgParser();
        argumentParser.parse(args);

        //Creating Lexer
        JFTTLexer lexer = new JFTTLexer(argumentParser.getCharStream());
        JFTTParser parser = new JFTTParser(new CommonTokenStream(lexer));

        //Running parser
        ParserManager parserManager = new ParserManager();
        parserManager.addErrorDetector(new VariableRedefinitionErrorDetector());
        parserManager.addErrorDetector(new UndeclaredVariableErrorDetector());
        parserManager.runAll(parser);

        if(parserManager.getErrors().size()>0 || parserManager.syntaxErrors) {
            //Printing errors
            if(parserManager.getErrors().size()>0) System.out.println(parserManager.printErrors());
            return;
        }
        else{
            //Printing the symbolTable
            System.out.println(parserManager.printSymbolTable());

            //Generate code
            CodeGeneratorManager codeGeneratorManager = new CodeGeneratorManager(parserManager.getSymbolTable(), parser);
            long largestNumber = parserManager.getLargestNumber();
            int largestPowerOf2 = (int) Math.ceil(Math.log(largestNumber)/Math.log(2));
            System.out.println("POWER: "+largestPowerOf2);
            System.out.println("POWER: "+largestNumber);
            codeGeneratorManager.assignIdentifierLocations(64);

            codeGeneratorManager.generateCode();
            System.out.println(codeGeneratorManager.printGeneratedCode(true));
            System.out.println(codeGeneratorManager.memoryManager.printMemory());

            //...

            try (PrintStream out = new PrintStream(new FileOutputStream(argumentParser.getOutputFilename()))) {
                out.print(codeGeneratorManager.printGeneratedCode(false));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        //Mnożenie w JAVA
        int a = 24;
        int b = 3;
        int mnożnik = 1;
        int left = a;
        int wynik = 0;
        int tmp2 = b;
        while(mnożnik < left) {
            mnożnik *= 2;
            tmp2 *= 2;
        }
        while(left>=b){
            while(tmp2 > left) {
                //stricte wieksze
                tmp2/=2;
                mnożnik /= 2;

            }
            wynik += mnożnik;
            left = left - tmp2;
        }

        System.out.println(wynik);


    }
}