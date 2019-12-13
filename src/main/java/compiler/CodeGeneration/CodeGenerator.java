package compiler.CodeGeneration;

import compiler.GrammarParser.Symbol;
import compiler.MemoryManager;
import parser.JFTTBaseListener;
import parser.JFTTParser;

import java.util.ArrayList;
import java.util.HashMap;

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
            String name = ctx.identifier().PIDENTIFIER(0).getText();
            generatedCode.add(new Command(CommandType.GET, 0));
            //Checking what kind of identifier
            if(ctx.identifier().NUM()!=null){
                //READ a(5);
                long num = Long.parseLong(ctx.identifier().NUM().getText());
                long shift = num - symbolTable.get(name).getRangeStart();
                long location = symbolTable.get(name).location+shift;
                generatedCode.add(new Command(CommandType.STORE, location));
                System.out.println("Variable placed at "+location);
            }
            else if(ctx.identifier().PIDENTIFIER(1)!=null){
                //READ a(b);
                // _______table_rangeStart_rangeEnd_tableLocation________
                Symbol b = symbolTable.get(ctx.identifier().PIDENTIFIER(1).getText());
                long bRangeStartLocation = b.location+b.getRangeLength();
                long bLocation = b.location+b.getRangeLength()+3;

                Symbol tmp = memoryManager.getFreeSpace();

                long memoryLocation = tmp.location;
                generatedCode.add(new Command(CommandType.LOAD, bRangeStartLocation));
                generatedCode.add(new Command(CommandType.STORE, memoryLocation));
                generatedCode.add(new Command(CommandType.LOADI, b.location));
                generatedCode.add(new Command(CommandType.SUB, memoryLocation));
                generatedCode.add(new Command(CommandType.STORE, memoryLocation));
                generatedCode.add(new Command(CommandType.LOAD, bLocation));
                generatedCode.add(new Command(CommandType.ADD, memoryLocation));
                generatedCode.add(new Command(CommandType.STORE, memoryLocation));
                generatedCode.add(new Command(CommandType.GET, 0));
                generatedCode.add(new Command(CommandType.STOREI, memoryLocation));

                memoryManager.removeVariable(tmp);



            } else{
                //READ n;
                generatedCode.add(new Command(CommandType.STORE, symbolTable.get(name).location));
            }


        }
        if(ctx.WRITE()!=null){
            String name = ctx.value().identifier().getText();
            generatedCode.add(new Command(CommandType.LOAD, symbolTable.get(name).location));
            generatedCode.add(new Command(CommandType.PUT, 0));
        }
    }
}
