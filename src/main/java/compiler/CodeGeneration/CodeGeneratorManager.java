package compiler.CodeGeneration;

import compiler.GrammarParser.Symbol;
import compiler.MemoryManager;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import parser.JFTTParser;

import java.util.ArrayList;
import java.util.HashMap;

public class CodeGeneratorManager {
    HashMap<String, Symbol> symbolTable = new HashMap<>();
    ArrayList<Command> generatedCode = new ArrayList<>();
    JFTTParser parser;
    public MemoryManager memoryManager = new MemoryManager();

    public CodeGeneratorManager(HashMap<String, Symbol> symbolTable, JFTTParser parser){
        this.symbolTable = symbolTable;
        this.parser = parser;
    }

    public void assignIdentifierLocations(int maxPower){
        generatePowersOf2Array(maxPower);

        for(String key : symbolTable.keySet()){
            if(symbolTable.get(key).location == -1){
                memoryManager.allocate(symbolTable.get(key));
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
        CodeGenerator codeGenerator = new CodeGenerator(symbolTable, memoryManager);
        ParseTreeWalker walker = new ParseTreeWalker();

        //run the parser
        walker.walk(codeGenerator, parser.program());

        //getting feedback from parsers
        generatedCode.addAll(codeGenerator.getGeneratedCode());


        for(int i=0; i<50; i++){
            generatedCode.add(new Command(CommandType.LOAD, i));
            generatedCode.add(new Command(CommandType.PUT, 0));
        }

        generatedCode.add(new Command(CommandType.HALT, 0));
    }

    public String printGeneratedCode(){
        StringBuilder stringBuilder = new StringBuilder();
        for(Command command : generatedCode){
            if(command.command == CommandType.GET || command.command == CommandType.PUT ||
                    command.command == CommandType.HALT || command.command == CommandType.INC ||
                    command.command == CommandType.DEC )
            {
                stringBuilder.append(command.command.toString()).append("\n");
            }
            else{
                stringBuilder.append(command.command.toString()).append(" ").append(command.argument).append("\n");
            }

        }
        return stringBuilder.toString();
    }

    public void generatePowersOf2Array(int maxPower){
        //ASSEMBLER
        if(maxPower<4) System.out.println("MaxPower should be at least 4");

        int arrayIndex = 1;

        //The first 3 powers: 1,2,4
        generatedCode.add(new Command(CommandType.SUB, 0));
        generatedCode.add(new Command(CommandType.INC, 0));
        generatedCode.add(new Command(CommandType.STORE, arrayIndex));
        arrayIndex++;

        generatedCode.add(new Command(CommandType.INC, 0));
        generatedCode.add(new Command(CommandType.STORE, arrayIndex));
        arrayIndex++;

        generatedCode.add(new Command(CommandType.INC, 0));
        generatedCode.add(new Command(CommandType.INC, 0));
        generatedCode.add(new Command(CommandType.STORE, arrayIndex));
        arrayIndex++;

        //The 3th power - 8
        for(int i=0; i<4; i++) generatedCode.add(new Command(CommandType.INC, 0));
        generatedCode.add(new Command(CommandType.STORE, arrayIndex));
        arrayIndex++;

        //The 4th power - 16
        for(int i=0; i<8; i++) generatedCode.add(new Command(CommandType.INC, 0));
        generatedCode.add(new Command(CommandType.STORE, arrayIndex));
        arrayIndex++;

        //Updating SymbolTable
        for(int i=0; i<= 4;i++){
            Symbol s = memoryManager.getFreeSpace();
            s.setName("2^"+i);
            symbolTable.put("2^"+i, s);
        }



        if(maxPower>4){
            for(int i=5; i<= maxPower; i++){
                generatedCode.add(new Command(CommandType.ADD, arrayIndex-1));
                generatedCode.add(new Command(CommandType.STORE, arrayIndex));
                arrayIndex++;
            }
            //Updating SymbolTable
            for(int i=5; i<= maxPower;i++){
                Symbol s = memoryManager.getFreeSpace();
                s.setName("2^"+i);
                symbolTable.put("2^"+i, s);
            }
        }





    }


}
