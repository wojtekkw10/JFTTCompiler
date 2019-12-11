package compiler;

import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import parser.JFTTLexer;
import parser.JFTTParser;

import java.util.ArrayList;
import java.util.HashMap;

public class ParserManager {
    ArrayList<ErrorDetector> errorDetectors = new ArrayList<>();
    HashMap<String, Symbol> symbolTable = new HashMap<>();

    public void addErrorDetector(ErrorDetector errorDetector){
        errorDetectors.add(errorDetector);
    }

   public void runAll(JFTTParser parser){
       for (ErrorDetector errorDetector : errorDetectors) {
           ParseTreeWalker walker = new ParseTreeWalker();
           errorDetector.setSymbolTable(symbolTable);
           walker.walk(errorDetector, parser.program());
           symbolTable = errorDetector.getSymbolTable();
           parser.reset();
       }
   }

    public HashMap<String, Symbol> getSymbolTable() {
        return symbolTable;
    }

    @Override
    public String toString() {
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
