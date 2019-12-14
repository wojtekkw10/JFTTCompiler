package compiler.CodeGeneration;

import compiler.GrammarParser.Symbol;
import compiler.MemoryManager;
import parser.JFTTBaseListener;
import parser.JFTTParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentMap;

public class CodeGenerator extends JFTTBaseListener {
    public ArrayList<Command> generatedCode = new ArrayList<>();
    HashMap<String, Symbol> symbolTable;
    MemoryManager memoryManager;

    public CodeGenerator(HashMap<String, Symbol> symbolTable, MemoryManager memoryManager){
        this.symbolTable = symbolTable;
        this.memoryManager = memoryManager;

    }

    public ArrayList<Command> getGeneratedCode() {
        return generatedCode;
    }

    @Override
    public void enterCommand(JFTTParser.CommandContext ctx) {


        if(ctx.READ()!=null){
            //READ identifier;

            generatedCode.add(new Command(CommandType.GET, 0));
            generatedCode.addAll(generateStoreCodeForIdentifier(ctx.identifier()));

        }
        if(ctx.WRITE()!=null){
            //WRITE NUM
            if(ctx.value().NUM()!=null){
                long number = Long.parseLong(ctx.value().NUM().getText());
                generatedCode.addAll(generateNumber(number));
            }
            else {
                //WRITE identifier
                generatedCode.addAll(generateLoadCodeForIdentifier(ctx.value().identifier()));
            }
            generatedCode.add(new Command(CommandType.PUT, 0));

        }
        if(ctx.ASSIGN()!=null){
            JFTTParser.IdentifierContext id = ctx.identifier();
            JFTTParser.ExpressionContext expr = ctx.expression();


        }
    }

    //STORES what's in the accumulator to id
    ArrayList<Command> generateStoreCodeForIdentifier(JFTTParser.IdentifierContext id){
        //identifier -> PIDENTIFIER
        //            | PIDENTIFIER'('PIDENTIFIER')'
        //            | PIDENTIFIER'('NUM')'
        ArrayList<Command> commands = new ArrayList<>();
        if(id.NUM()!=null){
            //a(5);
            String name = id.PIDENTIFIER(0).getText();
            long num = Long.parseLong(id.NUM().getText());
            long shift = num - symbolTable.get(name).getRangeStart();
            long location = symbolTable.get(name).location+shift;
            commands.add(new Command(CommandType.STORE, location));
        }
        else if(id.PIDENTIFIER(1)!=null){
            //a(b)
            Symbol b = symbolTable.get(id.PIDENTIFIER(1).getText());

            Symbol a = symbolTable.get(id.PIDENTIFIER(0).getText());
            long aRangeStartLocation = a.location+a.getRangeLength();
            long aLocation = a.location+a.getRangeLength()+2;

            Symbol tmp = memoryManager.getFreeSpace();
            long memoryLocation = tmp.location;
            Symbol tmp2 = memoryManager.getFreeSpace();

            commands.add(new Command(CommandType.STORE, tmp2.location));
            commands.add(new Command(CommandType.LOAD, aRangeStartLocation));
            commands.add(new Command(CommandType.STORE, memoryLocation));
            commands.add(new Command(CommandType.LOAD, b.location));
            commands.add(new Command(CommandType.SUB, memoryLocation));
            commands.add(new Command(CommandType.STORE, memoryLocation));
            commands.add(new Command(CommandType.LOAD, aLocation));
            commands.add(new Command(CommandType.ADD, memoryLocation));
            commands.add(new Command(CommandType.STORE, memoryLocation));
            commands.add(new Command(CommandType.LOAD, tmp2.location));
            commands.add(new Command(CommandType.STOREI, memoryLocation));

            memoryManager.removeVariable(tmp);
            memoryManager.removeVariable(tmp2);
        } else{
            //n;
            String name = id.PIDENTIFIER(0).getText();
            commands.add(new Command(CommandType.STORE, symbolTable.get(name).location));
        }

        return commands;
    }


    //LOADS the id to accumulator
    ArrayList<Command> generateLoadCodeForIdentifier(JFTTParser.IdentifierContext id){
        //identifier -> PIDENTIFIER
        //            | PIDENTIFIER'('PIDENTIFIER')'
        //            | PIDENTIFIER'('NUM')'
        ArrayList<Command> commands = new ArrayList<>();
        if(id.NUM()!=null){
            //a(5);
            String name = id.PIDENTIFIER(0).getText();
            long num = Long.parseLong(id.NUM().getText());
            long shift = num - symbolTable.get(name).getRangeStart();
            long location = symbolTable.get(name).location+shift;
            commands.add(new Command(CommandType.LOAD, location));
        }
        else if(id.PIDENTIFIER(1)!=null){
            //a(b)
            Symbol b = symbolTable.get(id.PIDENTIFIER(1).getText());

            Symbol a = symbolTable.get(id.PIDENTIFIER(0).getText());
            long aRangeStartLocation = a.location+a.getRangeLength();
            long aLocation = a.location+a.getRangeLength()+2;

            Symbol tmp = memoryManager.getFreeSpace();
            long memoryLocation = tmp.location;

            commands.add(new Command(CommandType.LOAD, aRangeStartLocation));
            commands.add(new Command(CommandType.STORE, memoryLocation));
            commands.add(new Command(CommandType.LOAD, b.location));
            commands.add(new Command(CommandType.SUB, memoryLocation));
            commands.add(new Command(CommandType.STORE, memoryLocation));
            commands.add(new Command(CommandType.LOAD, aLocation));
            commands.add(new Command(CommandType.ADD, memoryLocation));
            commands.add(new Command(CommandType.STORE, memoryLocation));
            commands.add(new Command(CommandType.LOADI, memoryLocation));

            memoryManager.removeVariable(tmp);
        } else{
            //n;
            String name = id.PIDENTIFIER(0).getText();
            commands.add(new Command(CommandType.LOAD, symbolTable.get(name).location));
        }

        return commands;
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
