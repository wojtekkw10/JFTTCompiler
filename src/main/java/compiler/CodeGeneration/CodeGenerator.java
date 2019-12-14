package compiler.CodeGeneration;

import compiler.GrammarParser.Symbol;
import compiler.MemoryManager;
import parser.JFTTBaseListener;
import parser.JFTTParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentMap;

public class CodeGenerator extends JFTTBaseListener {
    public ArrayList<Command> generatedCode;
    HashMap<String, Symbol> symbolTable;
    MemoryManager memoryManager;

    public CodeGenerator(HashMap<String, Symbol> symbolTable, MemoryManager memoryManager, ArrayList<Command> generatedCode){
        this.symbolTable = symbolTable;
        this.memoryManager = memoryManager;
        this.generatedCode = generatedCode;

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
            //WRITE value;
            generatedCode.addAll(generateLoadCodeForValue(ctx.value()));
            generatedCode.add(new Command(CommandType.PUT, 0));
        }
        if(ctx.ASSIGN()!=null){
            JFTTParser.IdentifierContext id = ctx.identifier();
            JFTTParser.ExpressionContext expr = ctx.expression();

            //FIRST expression store result in accumulator
            //THEN store accumulator in id //using function generateStoreCodeForIdentifier

            if(expr.PLUS()!=null){
                //expression -> value PLUS value
                generatedCode.addAll(generateLoadCodeForValue(expr.value(0)));
                Symbol tmp = memoryManager.getFreeSpace();
                generatedCode.add(new Command(CommandType.STORE, tmp.location));
                generatedCode.addAll(generateLoadCodeForValue(expr.value(1)));
                generatedCode.add(new Command(CommandType.ADD, tmp.location));
                generatedCode.addAll(generateStoreCodeForIdentifier(id));
                memoryManager.removeVariable(tmp);
            }
            else if(expr.MINUS()!=null){
                //expression -> value MINUS value
                generatedCode.addAll(generateLoadCodeForValue(expr.value(1)));
                Symbol tmp = memoryManager.getFreeSpace();
                generatedCode.add(new Command(CommandType.STORE, tmp.location));
                generatedCode.addAll(generateLoadCodeForValue(expr.value(0)));
                generatedCode.add(new Command(CommandType.SUB, tmp.location));
                generatedCode.addAll(generateStoreCodeForIdentifier(id));
                memoryManager.removeVariable(tmp);

            }
            else if(expr.TIMES()!=null){
                //        //Mnożenie w JAVA
                //        int a = 5;
                //        int b = 46;
                //        int mnożnik = 1;
                //        int left = b;
                //        int wynik = 0;
                //        while(mnożnik - left <= 0) mnożnik *= 2;
                //        while(left>0){
                //            while(mnożnik - 1  > left) {
                //                //stricte wieksze
                //                mnożnik /= 2;
                //            }
                //            wynik += mnożnik * a;
                //            left = left - mnożnik;
                //        }

                //expression -> value TIMES value
                Symbol multiplier = memoryManager.getFreeSpace();
                Symbol remaining = memoryManager.getFreeSpace();
                Symbol result = memoryManager.getFreeSpace();
                Symbol shiftCounter = memoryManager.getFreeSpace();
                Symbol a = memoryManager.getFreeSpace();

                //Copy variables to tmp memory
                generatedCode.addAll(generateLoadCodeForValue(expr.value(0)));
                generatedCode.add(new Command(CommandType.STORE, a.location));

                //Copy variables to tmp memory
                generatedCode.addAll(generateLoadCodeForValue(expr.value(1)));
                generatedCode.add(new Command(CommandType.STORE, remaining.location));

                generatedCode.add(new Command(CommandType.SUB, 0));
                generatedCode.add(new Command(CommandType.INC, 0));
                generatedCode.add(new Command(CommandType.STORE, multiplier.location));

                //while(mnożnik - left <= 0) mnożnik *= 2;
                Command c = new Command(CommandType.COMMENT, 0);
                c.setComment("Multiplication");
                //generatedCode.add(c);

                generatedCode.add(new Command(CommandType.LOAD, multiplier.location));
                generatedCode.add(new Command(CommandType.SUB, remaining.location));
                long JPOSLine = generatedCode.size();

                //LOOP
                generatedCode.add(new Command(CommandType.JPOS, JPOSLine+10));
                //UPDATE multiplier
                generatedCode.add(new Command(CommandType.LOAD, multiplier.location));
                generatedCode.add(new Command(CommandType.SHIFT, symbolTable.get("2^0").location)); //p1 = 1
                generatedCode.add(new Command(CommandType.STORE, multiplier.location));
                //UPDATE shiftCounter
                generatedCode.add(new Command(CommandType.LOAD, shiftCounter.location));
                generatedCode.add(new Command(CommandType.INC, 0)); //p1 = 1
                generatedCode.add(new Command(CommandType.STORE,shiftCounter.location));

                generatedCode.add(new Command(CommandType.LOAD, multiplier.location));
                generatedCode.add(new Command(CommandType.SUB, remaining.location));
                generatedCode.add(new Command(CommandType.JUMP, JPOSLine));
                //ENDLOOP

                //LOOP
                generatedCode.add(new Command(CommandType.LOAD, remaining.location));
                generatedCode.add(new Command(CommandType.DEC,0));

                long LoopLine = generatedCode.size();
                generatedCode.add(new Command(CommandType.JNEG, LoopLine+27));

                //INNER LOOP
                generatedCode.add(new Command(CommandType.LOAD, multiplier.location));
                generatedCode.add(new Command(CommandType.SUB, remaining.location));
                generatedCode.add(new Command(CommandType.DEC, remaining.location));

                //generatedCode.add(new Command(CommandType.INC, remaining.location));
                long innerLoopLine = generatedCode.size();
                generatedCode.add(new Command(CommandType.JNEG, innerLoopLine+11));
                //UPDATE multiplier
                generatedCode.add(new Command(CommandType.LOAD, multiplier.location));
                generatedCode.add(new Command(CommandType.SHIFT, symbolTable.get("-2^0").location));
                generatedCode.add(new Command(CommandType.STORE, multiplier.location));
                //UPDATE shiftCounter
                generatedCode.add(new Command(CommandType.LOAD, shiftCounter.location));
                generatedCode.add(new Command(CommandType.DEC, 0));
                generatedCode.add(new Command(CommandType.STORE,shiftCounter.location));

                //condition
                generatedCode.add(new Command(CommandType.LOAD, multiplier.location));
                generatedCode.add(new Command(CommandType.SUB, remaining.location));
                generatedCode.add(new Command(CommandType.DEC, remaining.location));
                generatedCode.add(new Command(CommandType.JUMP, innerLoopLine));
                //END INNERLOOP


                //Result += multiplier * a;
                Symbol tmp = memoryManager.getFreeSpace();
                generatedCode.add(new Command(CommandType.LOAD, a.location));

                generatedCode.add(new Command(CommandType.SHIFT, shiftCounter.location));

                generatedCode.add(new Command(CommandType.STORE, tmp.location));
                generatedCode.add(new Command(CommandType.LOAD, result.location));
                generatedCode.add(new Command(CommandType.ADD, tmp.location));
                generatedCode.add(new Command(CommandType.STORE, result.location));
                memoryManager.removeVariable(tmp);

                //left -= multiplier;
                generatedCode.add(new Command(CommandType.LOAD, remaining.location));
                generatedCode.add(new Command(CommandType.SUB, multiplier.location));
                generatedCode.add(new Command(CommandType.STORE, remaining.location)); //1



                //condition
                generatedCode.add(new Command(CommandType.LOAD, remaining.location));
                generatedCode.add(new Command(CommandType.DEC,0));
                generatedCode.add(new Command(CommandType.JUMP, LoopLine));


                generatedCode.add(new Command(CommandType.LOAD, result.location));
                generatedCode.addAll(generateStoreCodeForIdentifier(id));

                memoryManager.removeVariable(multiplier);
                memoryManager.removeVariable(remaining);
                memoryManager.removeVariable(result);
                memoryManager.removeVariable(shiftCounter);
                memoryManager.removeVariable(a);

            }
            else if(expr.DIV()!=null){
                //expression -> value DIV value

            }
            else if(expr.MOD()!=null){
                //expression -> value MOD value

            }
            else{
                //expression -> value
                generatedCode.addAll(generateLoadCodeForValue(expr.value(0)));
                generatedCode.addAll(generateStoreCodeForIdentifier(id));
            }


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
    ArrayList<Command> generateLoadCodeForValue(JFTTParser.ValueContext value){
        //identifier -> PIDENTIFIER
        //            | PIDENTIFIER'('PIDENTIFIER')'
        //            | PIDENTIFIER'('NUM')'
        ArrayList<Command> commands = new ArrayList<>();
        JFTTParser.IdentifierContext id = value.identifier();

        if(value.NUM()!=null){
            long number = Long.parseLong(value.NUM().getText());
            generatedCode.addAll(generateNumber(number));
        }
        else if(id.NUM()!=null){
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
