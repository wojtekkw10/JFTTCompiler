package compiler.CodeGeneration;

import compiler.GrammarParser.Symbol;
import compiler.MemoryManager;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import parser.JFTTParser;

import java.util.ArrayList;
import java.util.HashMap;

public class CodeGeneratorManager {
    HashMap<String, Symbol> symbolTable;
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

        generatedCode.addAll(generateArrayInfo());
    }

    private ArrayList<Command> generateArrayInfo(){
        ArrayList<Command> commands = new ArrayList<>();

        for(String key : symbolTable.keySet()){
            if(symbolTable.get(key).isArray()){
                Symbol s = symbolTable.get(key);

                s.locationShift = -s.getRangeStart()+s.location;

                long locationShiftLocation = s.location+s.getRangeLength();

                commands.addAll(generateNumber(s.locationShift));
                commands.add(new Command(CommandType.STORE, locationShiftLocation));

            }
        }

        return commands;

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
        ParseTreeWalker walker = new ParseTreeWalker();
        CodeGenerator codeGenerator = new CodeGenerator(symbolTable, memoryManager, generatedCode, parser, walker);
        ParseTree tree = parser.program();

        codeGenerator.visit(tree);
        generatedCode = codeGenerator.getGeneratedCode();

        generatedCode.add(new Command(CommandType.HALT, 0));

    }

    public String printGeneratedCode(Boolean withLineNumbers){
        StringBuilder stringBuilder = new StringBuilder();
        int counter = 0;
        for(Command command : generatedCode){
            if(withLineNumbers) stringBuilder.append(counter).append(": ");
            if(command.command == CommandType.GET || command.command == CommandType.PUT ||
                    command.command == CommandType.HALT || command.command == CommandType.INC ||
                    command.command == CommandType.DEC ) {
                stringBuilder.append(command.command.toString());
            }
            else{
                stringBuilder.append(command.command.toString()).append(" ").append(command.argument);
            }
            if(command.comment!=null) {
                stringBuilder.append("   # ").append(command.comment);
            }
            stringBuilder.append("\n");
            counter++;

        }
        return stringBuilder.toString();
    }

    private void generatePowersOf2Array(int maxPower){
        int arrayIndex = 1;

        //-2^0
        generatedCode.add(new Command(CommandType.SUB, 0, "Generating -1"));
        generatedCode.add(new Command(CommandType.DEC, 0));
        generatedCode.add(new Command(CommandType.STORE, arrayIndex));
        arrayIndex++;
        Symbol s0 = memoryManager.getFreeSpace();
        s0.setName("-2^0");
        symbolTable.put("-2^0", s0);

        //2^0
        generatedCode.add(new Command(CommandType.INC, 0, "Generating 1"));
        generatedCode.add(new Command(CommandType.INC, 0));
        generatedCode.add(new Command(CommandType.STORE, arrayIndex));
        arrayIndex++;
        Symbol s01 = memoryManager.getFreeSpace();
        s01.setName("2^0");
        symbolTable.put("2^0", s01);

        if(maxPower>=2){
            generatedCode.add(new Command(CommandType.INC, 0, "Generating 2"));
            generatedCode.add(new Command(CommandType.STORE, arrayIndex));
            arrayIndex++;

            Symbol s1 = memoryManager.getFreeSpace();
            s1.setName("2^1");
            symbolTable.put("2^1", s1);

            generatedCode.add(new Command(CommandType.INC, 0, "Generating 4"));
            generatedCode.add(new Command(CommandType.INC, 0));
            generatedCode.add(new Command(CommandType.STORE, arrayIndex));
            arrayIndex++;

            Symbol s2 = memoryManager.getFreeSpace();
            s2.setName("2^2");
            symbolTable.put("2^2", s2);
        }

        if(maxPower>=3){
            //The 3th power - 8
            generatedCode.add(new Command(CommandType.INC, 0, "Generating 8"));
            for(int i=0; i<3; i++) generatedCode.add(new Command(CommandType.INC, 0));
            generatedCode.add(new Command(CommandType.STORE, arrayIndex));
            arrayIndex++;

            Symbol s3 = memoryManager.getFreeSpace();
            s3.setName("2^3");
            symbolTable.put("2^3", s3);
        }

        if(maxPower>=4){
            //The 4th power - 16
            generatedCode.add(new Command(CommandType.INC, 0, "Generating 16"));
            for(int i=0; i<7; i++) generatedCode.add(new Command(CommandType.INC, 0));
            generatedCode.add(new Command(CommandType.STORE, arrayIndex));
            arrayIndex++;

            Symbol s4 = memoryManager.getFreeSpace();
            s4.setName("2^4");
            symbolTable.put("2^4", s4);
        }

        if(maxPower>4){
            for(int i=5; i<= maxPower; i++){
                generatedCode.add(new Command(CommandType.ADD, arrayIndex-1, "Generating 2^"+i));
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

    private ArrayList<Command> generateNumber(long number){
        //The number is stored in p0
        ArrayList<Command> commands = new ArrayList<>();
        commands.add(new Command(CommandType.SUB, 0));

        boolean isNegative = false;
        if(number<0) {
            number = -number;
            isNegative = true;
        }

        String stringNumber = Long.toBinaryString(number);
        int length = stringNumber.length();
        for(int i=0; i<stringNumber.length(); i++){
            int digit = Integer.parseInt(stringNumber.substring(i,i+1));
            long loc = symbolTable.get("2^"+(length-i-1)).location;
            if(digit==1){
                if(isNegative) commands.add(new Command(CommandType.SUB, loc));
                else commands.add(new Command(CommandType.ADD, loc));
            }
        }

        return commands;

    }

}
