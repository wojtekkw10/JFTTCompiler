package compiler.CodeGeneration;

import compiler.GrammarParser.Symbol;
import compiler.MemoryManager;
import org.antlr.v4.runtime.tree.ParseTree;
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

        generatedCode.addAll(generateArrayInfo());
    }

    private ArrayList<Command> generateArrayInfo(){
        ArrayList<Command> commands = new ArrayList<>();

        for(String key : symbolTable.keySet()){
            if(symbolTable.get(key).isArray()){
                Symbol s = symbolTable.get(key);
                long rangeStart = s.getRangeStart();
                long rangeEnd = s.getRangeEnd();
                long location = s.location;

                long rangeStartLocation = s.location+s.getRangeLength();
                long rangeEndLocation = s.location+s.getRangeLength()+1;
                long locationLocation = s.location+s.getRangeLength()+2;

                commands.addAll(generateNumber(rangeStart));
                commands.add(new Command(CommandType.STORE, rangeStartLocation));

                commands.addAll(generateNumber(rangeEnd));
                commands.add(new Command(CommandType.STORE, rangeEndLocation));

                commands.addAll(generateNumber(location));
                commands.add(new Command(CommandType.STORE, locationLocation));

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
        Integer answer = new CodeGenerator(symbolTable, memoryManager, generatedCode, parser, walker).visit(tree);
        //run the parser
        //walker.walk(codeGenerator, parser.program());

        //getting feedback from parsers
        generatedCode = codeGenerator.getGeneratedCode();


        for(int i=0; i<51; i++){
            //generatedCode.add(new Command(CommandType.LOAD, i));
            //generatedCode.add(new Command(CommandType.PUT, 0));
        }

        generatedCode.add(new Command(CommandType.HALT, 0));

    }

    public String printGeneratedCode(Boolean withLineNumbers){
        StringBuilder stringBuilder = new StringBuilder();
        int counter = 0;
        for(Command command : generatedCode){
            if(withLineNumbers) stringBuilder.append(counter).append(": ");
            if(command.command == CommandType.GET || command.command == CommandType.PUT ||
                    command.command == CommandType.HALT || command.command == CommandType.INC ||
                    command.command == CommandType.DEC )
            {
                stringBuilder.append(command.command.toString()).append("\n");
            } else if(command.command == CommandType.COMMENT){
                stringBuilder.append("#").append(command.comment).append("\n");
            }
            else{
                stringBuilder.append(command.command.toString()).append(" ").append(command.argument).append("\n");
            }
            counter++;

        }
        return stringBuilder.toString();
    }

    private void generatePowersOf2Array(int maxPower){
        //ASSEMBLER
        if(maxPower<4) System.out.println("MaxPower should be at least 4");

        int arrayIndex = 1;
        //-1
        generatedCode.add(new Command(CommandType.SUB, 0));
        generatedCode.add(new Command(CommandType.DEC, 0));
        generatedCode.add(new Command(CommandType.STORE, arrayIndex));
        arrayIndex++;
        Symbol ss = memoryManager.getFreeSpace();
        ss.setName("-2^0");
        symbolTable.put("-2^0", ss);

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

    private ArrayList<Command> generateNumber(long number){
        //The number is stored in p0
        ArrayList<Command> commands = new ArrayList<>();
        commands.add(new Command(CommandType.SUB, 0));

        Boolean isNegative = false;
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
