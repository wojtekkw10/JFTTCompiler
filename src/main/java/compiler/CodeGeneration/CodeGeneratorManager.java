package compiler.CodeGeneration;

import com.ibm.icu.text.SymbolTable;
import compiler.GrammarParser.IdentifierType;
import compiler.GrammarParser.Symbol;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import parser.JFTTParser;

import java.util.ArrayList;
import java.util.HashMap;

public class CodeGeneratorManager {
    HashMap<String, Symbol> symbolTable = new HashMap<>();
    ArrayList<Command> generatedCode = new ArrayList<>();
    JFTTParser parser;

    public CodeGeneratorManager(HashMap<String, Symbol> symbolTable, JFTTParser parser){
        this.symbolTable = symbolTable;
        this.parser = parser;
    }

    public void assignIdentifierLocations(){
        int location = 1;
        for(String key : symbolTable.keySet()){
            if(symbolTable.get(key).isArray()){
                symbolTable.get(key).location = location;
                location += symbolTable.get(key).getRangeLength();
            } else{
                symbolTable.get(key).location = location;
                location++;
            }

        }
    }

    public String printMemoryIdentifierAssigment(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[ ");
        for(String key : symbolTable.keySet()){
            stringBuilder.append(key).append(":").append(symbolTable.get(key).location).append(" ");
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public void generateCode(){
        CodeGenerator codeGenerator = new CodeGenerator(symbolTable);
        ParseTreeWalker walker = new ParseTreeWalker();

        //run the parser
        walker.walk(codeGenerator, parser.program());

        //getting feedback from parsers
        generatedCode = codeGenerator.getGeneratedCode();
        generatedCode.add(new Command(CommandType.HALT, 0));
    }

    public String printGeneratedCode(){
        StringBuilder stringBuilder = new StringBuilder();
        for(Command command : generatedCode){
            if(command.command == CommandType.GET || command.command == CommandType.PUT || command.command == CommandType.HALT){
                stringBuilder.append(command.command.toString()).append("\n");
            }
            else{
                stringBuilder.append(command.command.toString()).append(" ").append(command.argument).append("\n");
            }

        }
        return stringBuilder.toString();
    }


}
