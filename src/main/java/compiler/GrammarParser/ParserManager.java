package compiler.GrammarParser;

import org.antlr.v4.runtime.tree.ParseTreeWalker;
import parser.JFTTParser;

import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.Math.abs;

public class ParserManager {
    ArrayList<ErrorDetector> errorDetectors = new ArrayList<>();
    HashMap<String, Symbol> symbolTable = new HashMap<>();
    ArrayList<Error> errors = new ArrayList<>();
    public Boolean syntaxErrors = false;
    public long largestNumber = 0;

    public void addErrorDetector(ErrorDetector errorDetector){
        errorDetectors.add(errorDetector);
    }

   public void runAll(JFTTParser parser){
       for (ErrorDetector errorDetector : errorDetectors) {
           ParseTreeWalker walker = new ParseTreeWalker();
           errorDetector.setSymbolTable(symbolTable);

           //run the parser
           walker.walk(errorDetector, parser.program());

           //getting feedback from parsers
           symbolTable = errorDetector.getSymbolTable();
           errors.addAll(errorDetector.getErrors());
           if(parser.getNumberOfSyntaxErrors()>0) {
               syntaxErrors = true;
               break;
           }
           if(abs(errorDetector.largestNumber)>largestNumber) {
               if(errorDetector.largestNumber < 0) largestNumber = -errorDetector.largestNumber;
               else largestNumber = errorDetector.largestNumber;
           }

           //reset the parser to point at the beginning
           parser.reset();
       }
   }

    public long getLargestNumber(){
        return largestNumber;
    }

    public HashMap<String, Symbol> getSymbolTable() {
        return symbolTable;
    }

    public String printErrors(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n####### ERRORS #################################\n");
        stringBuilder.append("################################################\n");
        for(Error error: errors){
            stringBuilder.append("> Error: ").append(error.message).append(" in line ").append(error.line);
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public ArrayList<Error> getErrors() {
        return errors;
    }

    public String printSymbolTable() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Symbol Table\n");
        int columnWidth = 8;
        for(String key : symbolTable.keySet()){
            stringBuilder.append("[ ").append(key);
            for(int i=0; i<columnWidth-key.length(); i++) stringBuilder.append(" ");
            stringBuilder.append(": ").append(" ").append(symbolTable.get(key).type.toString());
            if(symbolTable.get(key).type == IdentifierType.ARRAY){
                stringBuilder.append(" ").append(symbolTable.get(key).rangeStart).append(" ").append(symbolTable.get(key).rangeEnd);
            }
            stringBuilder.append(" ]\n");
        }
        return stringBuilder.toString();
    }

}
