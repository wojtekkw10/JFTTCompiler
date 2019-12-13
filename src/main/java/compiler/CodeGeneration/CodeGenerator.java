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

            //Checking what kind of identifier
            if(ctx.identifier().NUM()!=null){
                //READ a(5);
                long num = Long.parseLong(ctx.identifier().NUM().getText());
                long shift = num - symbolTable.get(name).getRangeStart();
                long location = symbolTable.get(name).location+shift;
                generatedCode.add(new Command(CommandType.GET, 0));
                generatedCode.add(new Command(CommandType.STORE, location));
                System.out.println("Variable placed at "+location);
            }
            else if(ctx.identifier().PIDENTIFIER(1)!=null){
                //READ a(b);
                // _______table_rangeStart_rangeEnd_tableLocation________
                Symbol b = symbolTable.get(ctx.identifier().PIDENTIFIER(1).getText());

                Symbol a = symbolTable.get(ctx.identifier().PIDENTIFIER(0).getText());
                long aRangeStartLocation = a.location+a.getRangeLength();
                long aLocation = a.location+a.getRangeLength()+2;

                Symbol tmp = memoryManager.getFreeSpace();

                long memoryLocation = tmp.location;
                generatedCode.add(new Command(CommandType.LOAD, aRangeStartLocation));
                generatedCode.add(new Command(CommandType.STORE, memoryLocation));
                generatedCode.add(new Command(CommandType.LOAD, b.location));
                generatedCode.add(new Command(CommandType.SUB, memoryLocation));
                generatedCode.add(new Command(CommandType.STORE, memoryLocation));
                generatedCode.add(new Command(CommandType.LOAD, aLocation));
                generatedCode.add(new Command(CommandType.ADD, memoryLocation));
                generatedCode.add(new Command(CommandType.STORE, memoryLocation));
                generatedCode.add(new Command(CommandType.GET, 0));
                generatedCode.add(new Command(CommandType.STOREI, memoryLocation));

                memoryManager.removeVariable(tmp);



            } else{
                //READ n;
                generatedCode.add(new Command(CommandType.GET, 0));
                generatedCode.add(new Command(CommandType.STORE, symbolTable.get(name).location));
            }


        }
        if(ctx.WRITE()!=null){
            JFTTParser.IdentifierContext id = ctx.value().identifier();
            if(ctx.value().NUM()!=null){
                //WRITE 5;
                long NUM = Long.parseLong(ctx.value().NUM().getText());
                generatedCode.add(new Command(CommandType.PUT, NUM));
            }
            else if(id.PIDENTIFIER(1)==null && id.NUM()==null){
                //WRITE n;
                String name = id.PIDENTIFIER(0).getText();
                generatedCode.add(new Command(CommandType.LOAD, symbolTable.get(name).location));
                generatedCode.add(new Command(CommandType.PUT, 0));
            }
            else if(id.NUM()!=null){
                //WRITE a(5);
                String name = id.PIDENTIFIER(0).getText();
                long num = Long.parseLong(id.NUM().getText());
                long shift = num - symbolTable.get(name).getRangeStart();
                long location = symbolTable.get(name).location+shift;
                generatedCode.add(new Command(CommandType.LOAD, location));
                generatedCode.add(new Command(CommandType.PUT, 0));
            }
            else if(id.PIDENTIFIER(1)!=null){
                //WRITE a(b);
                Symbol b = symbolTable.get(id.PIDENTIFIER(1).getText());

                Symbol a = symbolTable.get(id.PIDENTIFIER(0).getText());
                long aRangeStartLocation = a.location+a.getRangeLength();
                long aLocation = a.location+a.getRangeLength()+2;

                Symbol tmp = memoryManager.getFreeSpace();

                long memoryLocation = tmp.location;
                generatedCode.add(new Command(CommandType.LOAD, aRangeStartLocation));
                generatedCode.add(new Command(CommandType.STORE, memoryLocation));
                generatedCode.add(new Command(CommandType.LOAD, b.location));
                generatedCode.add(new Command(CommandType.SUB, memoryLocation));
                generatedCode.add(new Command(CommandType.STORE, memoryLocation));
                generatedCode.add(new Command(CommandType.LOAD, aLocation));
                generatedCode.add(new Command(CommandType.ADD, memoryLocation));
                generatedCode.add(new Command(CommandType.STORE, memoryLocation));
                generatedCode.add(new Command(CommandType.LOADI, memoryLocation));
                generatedCode.add(new Command(CommandType.PUT, 0));

                memoryManager.removeVariable(tmp);
            }

        }
    }


}
