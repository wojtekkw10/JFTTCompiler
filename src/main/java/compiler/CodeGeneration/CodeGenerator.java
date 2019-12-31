package compiler.CodeGeneration;

import compiler.CodeOptimizer;
import compiler.GrammarParser.IdentifierType;
import compiler.GrammarParser.Symbol;
import compiler.MemoryManager;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import parser.JFTTBaseVisitor;
import parser.JFTTParser;

import java.util.ArrayList;
import java.util.HashMap;

public class CodeGenerator extends JFTTBaseVisitor<Integer> {
    public ArrayList<Command> generatedCode;
    HashMap<String, Symbol> symbolTable;
    MemoryManager memoryManager;
    JFTTParser parser;
    ParseTreeWalker walker;

    public CodeGenerator(HashMap<String, Symbol> symbolTable, MemoryManager memoryManager, ArrayList<Command> generatedCode, JFTTParser parser, ParseTreeWalker walker){
        this.symbolTable = symbolTable;
        this.memoryManager = memoryManager;
        this.generatedCode = generatedCode;
        this.parser = parser;
        this.walker = walker;

    }

    public ArrayList<Command> getGeneratedCode() {
        return generatedCode;
    }


    @Override
    public Integer visitCommand(JFTTParser.CommandContext ctx) {

        if(ctx.READ()!=null){
            //READ identifier;

            generatedCode.add(new Command(CommandType.GET, 0));
            generatedCode.addAll(generateStoreCodeForIdentifier(ctx.identifier()));

        }
        if(ctx.WRITE()!=null){
            //WRITE value;
            generatedCode.addAll(generateLoadCodeForValue(ctx.value(0)));
            generatedCode.add(new Command(CommandType.PUT, 0));
        }
        if(ctx.ASSIGN()!=null){
            JFTTParser.IdentifierContext id = ctx.identifier();
            JFTTParser.ExpressionContext expr = ctx.expression();

            //FIRST expression store result in accumulator
            //THEN store accumulator in id //using function generateStoreCodeForIdentifier

            if(expr.PLUS()!=null){
                //expression -> value PLUS value
                if(expr.value(0).NUM()!=null && Long.parseLong(expr.value(0).NUM().getText())==1){
                    // a = 1 + b
                    generatedCode.addAll(generateLoadCodeForValue(expr.value(1)));
                    generatedCode.add(new Command(CommandType.INC, 0));
                    generatedCode.addAll(generateStoreCodeForIdentifier(id));
                }
                else if(expr.value(1).NUM()!=null && Long.parseLong(expr.value(1).NUM().getText())==1){
                    generatedCode.addAll(generateLoadCodeForValue(expr.value(0)));
                    generatedCode.add(new Command(CommandType.INC, 0));
                    generatedCode.addAll(generateStoreCodeForIdentifier(id));
                }else {
                    generatedCode.addAll(generateLoadCodeForValue(expr.value(0)));
                    Symbol tmp = memoryManager.getFreeSpace();
                    generatedCode.add(new Command(CommandType.STORE, tmp.location));
                    generatedCode.addAll(generateLoadCodeForValue(expr.value(1)));
                    generatedCode.add(new Command(CommandType.ADD, tmp.location));
                    generatedCode.addAll(generateStoreCodeForIdentifier(id));
                    memoryManager.removeVariable(tmp);
                }

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

                if(expr.value(1).NUM()!=null && Long.parseLong(expr.value(1).NUM().getText())==2){
                    generatedCode.addAll(generateLoadCodeForValue(expr.value(0)));
                    generatedCode.add(new Command(CommandType.SHIFT, symbolTable.get("2^0").location));
                    generatedCode.addAll(generateStoreCodeForIdentifier(id));
                }
                else if(expr.value(0).NUM()!=null && Long.parseLong(expr.value(0).NUM().getText())==2) {
                    generatedCode.addAll(generateLoadCodeForValue(expr.value(1)));
                    generatedCode.add(new Command(CommandType.SHIFT, symbolTable.get("2^0").location));
                    generatedCode.addAll(generateStoreCodeForIdentifier(id));
                }
                else {
                    Symbol a = memoryManager.getFreeSpace();
                    Symbol b = memoryManager.getFreeSpace();

                    //Copy variables to tmp memory
                    generatedCode.addAll(generateLoadCodeForValue(expr.value(0)));
                    generatedCode.add(new Command(CommandType.STORE, a.location));

                    //Copy variables to tmp memory
                    generatedCode.addAll(generateLoadCodeForValue(expr.value(1)));
                    generatedCode.add(new Command(CommandType.STORE, b.location));

                    generateMultiplicationCode(a,b);

                    generatedCode.addAll(generateStoreCodeForIdentifier(id));

                    memoryManager.removeVariable(a);
                    memoryManager.removeVariable(b);
                }



            }
            else if(expr.DIV()!=null){
                //         int a = 100;
                //        int b = 4;
                //        int mnożnik = 1;
                //        int left = a;
                //        int wynik = 0;
                //        int tmp2 = b;
                //        while(mnożnik < left) {
                //            mnożnik *= 2;
                //            tmp2 *= 2;
                //        }
                //        while(left>=b){
                //            while(tmp2 > left) {
                //                //stricte wieksze
                //                tmp2/=2;
                //                mnożnik /= 2;
                //
                //            }
                //            wynik += mnożnik;
                //            left = left - tmp2;
                //        }
                //expression -> value DIV value
                // if a < 0 isNegative - 1
                // if b < 0 isNegative + 1
                if(expr.value(1).NUM()!=null && Long.parseLong(expr.value(1).NUM().getText())==2){
                    generatedCode.addAll(generateLoadCodeForValue(expr.value(0)));
                    generatedCode.add(new Command(CommandType.SHIFT, symbolTable.get("-2^0").location));
                    generatedCode.addAll(generateStoreCodeForIdentifier(id));
                }
                else if(expr.value(0).NUM()!=null && Long.parseLong(expr.value(0).NUM().getText())==2) {
                    generatedCode.addAll(generateLoadCodeForValue(expr.value(1)));
                    generatedCode.add(new Command(CommandType.SHIFT, symbolTable.get("-2^0").location));
                    generatedCode.addAll(generateStoreCodeForIdentifier(id));
                }
                else {
                    Symbol multiplier = memoryManager.getFreeSpace();
                    Symbol remaining = memoryManager.getFreeSpace();
                    Symbol result = memoryManager.getFreeSpace();
                    Symbol shiftCounter = memoryManager.getFreeSpace();
                    Symbol b = memoryManager.getFreeSpace();
                    Symbol isNegative = memoryManager.getFreeSpace();

                    //isNegative is by default false aka = 0
                    generatedCode.add(new Command(CommandType.SUB, 0));
                    generatedCode.add(new Command(CommandType.STORE, isNegative.location));


                    //Copy variables to tmp memory
                    generatedCode.addAll(generateLoadCodeForValue(expr.value(0)));
                    generatedCode.add(new Command(CommandType.STORE, remaining.location));

                    //IF remaining < 0 flip it
                    long line0 = generatedCode.size();
                    generatedCode.add(new Command(CommandType.JPOS, line0+8));
                    generatedCode.add(new Command(CommandType.LOAD, remaining.location));
                    generatedCode.add(new Command(CommandType.SUB, remaining.location));
                    generatedCode.add(new Command(CommandType.SUB, remaining.location));
                    generatedCode.add(new Command(CommandType.STORE, remaining.location));
                    generatedCode.add(new Command(CommandType.SUB, 0));
                    generatedCode.add(new Command(CommandType.DEC, 0));
                    generatedCode.add(new Command(CommandType.STORE, isNegative.location));


                    //Copy variables to tmp memory
                    generatedCode.addAll(generateLoadCodeForValue(expr.value(1)));
                    generatedCode.add(new Command(CommandType.STORE, b.location));

                    // IF b == 0 return 0
                    long line3 = generatedCode.size();
                    generatedCode.add(new Command(CommandType.LOAD, b.location));
                    generatedCode.add(new Command(CommandType.JZERO, line3+65));

                    //IF b < 0 flip it
                    long line1 = generatedCode.size();
                    generatedCode.add(new Command(CommandType.LOAD, b.location));
                    generatedCode.add(new Command(CommandType.JPOS, line1+9));
                    generatedCode.add(new Command(CommandType.LOAD, b.location));
                    generatedCode.add(new Command(CommandType.SUB, b.location));
                    generatedCode.add(new Command(CommandType.SUB, b.location));
                    generatedCode.add(new Command(CommandType.STORE, b.location));
                    generatedCode.add(new Command(CommandType.LOAD, isNegative.location));
                    generatedCode.add(new Command(CommandType.INC, 0));
                    generatedCode.add(new Command(CommandType.STORE, isNegative.location));




                    //Cleaning tmp variables
                    generatedCode.add(new Command(CommandType.SUB, 0));
                    generatedCode.add(new Command(CommandType.STORE, result.location));

                    generatedCode.add(new Command(CommandType.INC, 0));
                    generatedCode.add(new Command(CommandType.STORE, multiplier.location));

                    Symbol tmp2 = memoryManager.getFreeSpace();
                    //tmp2 = b
                    generatedCode.add(new Command(CommandType.LOAD, b.location));
                    generatedCode.add(new Command(CommandType.STORE, tmp2.location));

                    //while(mnożnik - left <= 0) mnożnik *= 2;

                    generatedCode.add(new Command(CommandType.LOAD, multiplier.location));
                    generatedCode.add(new Command(CommandType.SUB, remaining.location));
                    long JPOSLine = generatedCode.size();

                    //LOOP
                    generatedCode.add(new Command(CommandType.JPOS, JPOSLine+13, "while(mnożnik - left <= 0) mnożnik *= 2"));
                    //UPDATE multiplier
                    generatedCode.add(new Command(CommandType.LOAD, multiplier.location));
                    generatedCode.add(new Command(CommandType.SHIFT, symbolTable.get("2^0").location)); //p1 = 1
                    generatedCode.add(new Command(CommandType.STORE, multiplier.location));
                    //UPDATE shiftCounter
                    generatedCode.add(new Command(CommandType.LOAD, shiftCounter.location));
                    generatedCode.add(new Command(CommandType.INC, 0));
                    generatedCode.add(new Command(CommandType.STORE,shiftCounter.location));

                    generatedCode.add(new Command(CommandType.LOAD, tmp2.location));
                    generatedCode.add(new Command(CommandType.SHIFT, symbolTable.get("2^0").location));
                    generatedCode.add(new Command(CommandType.STORE, tmp2.location));

                    generatedCode.add(new Command(CommandType.LOAD, multiplier.location));
                    generatedCode.add(new Command(CommandType.SUB, remaining.location));
                    generatedCode.add(new Command(CommandType.JUMP, JPOSLine));
                    //ENDLOOP


                    //LOOP
                    generatedCode.add(new Command(CommandType.LOAD, remaining.location));
                    generatedCode.add(new Command(CommandType.SUB, b.location));
                    long loopLine = generatedCode.size();
                    generatedCode.add(new Command(CommandType.JNEG, loopLine+25));

                    //INNER LOOP
                    generatedCode.add(new Command(CommandType.LOAD, tmp2.location));
                    generatedCode.add(new Command(CommandType.SUB, remaining.location));
                    generatedCode.add(new Command(CommandType.DEC, 0));
                    long innerLoopLine = generatedCode.size();
                    generatedCode.add(new Command(CommandType.JNEG, innerLoopLine+11));

                    generatedCode.add(new Command(CommandType.LOAD, tmp2.location));
                    generatedCode.add(new Command(CommandType.SHIFT, symbolTable.get("-2^0").location));
                    generatedCode.add(new Command(CommandType.STORE, tmp2.location));

                    generatedCode.add(new Command(CommandType.LOAD, multiplier.location));
                    generatedCode.add(new Command(CommandType.SHIFT, symbolTable.get("-2^0").location));
                    generatedCode.add(new Command(CommandType.STORE, multiplier.location));
                    //generatedCode.add(new Command(CommandType.PUT, multiplier.location));

                    //condition
                    generatedCode.add(new Command(CommandType.LOAD, tmp2.location));
                    generatedCode.add(new Command(CommandType.SUB, remaining.location));
                    generatedCode.add(new Command(CommandType.DEC, 0));
                    generatedCode.add(new Command(CommandType.JUMP, innerLoopLine));

                    generatedCode.add(new Command(CommandType.LOAD, result.location));
                    generatedCode.add(new Command(CommandType.ADD, multiplier.location));
                    generatedCode.add(new Command(CommandType.STORE, result.location));

                    generatedCode.add(new Command(CommandType.LOAD, multiplier.location));
                    //generatedCode.add(new Command(CommandType.PUT, 0));

                    generatedCode.add(new Command(CommandType.LOAD, remaining.location));
                    generatedCode.add(new Command(CommandType.SUB, tmp2.location));
                    generatedCode.add(new Command(CommandType.STORE, remaining.location));

                    //condition
                    generatedCode.add(new Command(CommandType.LOAD, remaining.location));
                    generatedCode.add(new Command(CommandType.SUB, b.location));
                    generatedCode.add(new Command(CommandType.JUMP, loopLine));

                    // if isNegative flip the result
                    generatedCode.add(new Command(CommandType.LOAD, isNegative.location));
                    //generatedCode.add(new Command(CommandType.PUT, isNegative.location));
                    long line2 = generatedCode.size();
                    generatedCode.add(new Command(CommandType.JZERO, line2 + 5));
                    generatedCode.add(new Command(CommandType.LOAD, result.location));
                    generatedCode.add(new Command(CommandType.SUB, result.location));
                    generatedCode.add(new Command(CommandType.SUB, result.location));
                    generatedCode.add(new Command(CommandType.STORE, result.location));

                    //if b == 0 return 0
                    generatedCode.add(new Command(CommandType.LOAD, b.location));
                    long line5 = generatedCode.size();
                    generatedCode.add(new Command(CommandType.JPOS, line5+4));
                    generatedCode.add(new Command(CommandType.JNEG, line5+4));
                    generatedCode.add(new Command(CommandType.SUB, 0));
                    generatedCode.add(new Command(CommandType.STORE, result.location));





                    generatedCode.add(new Command(CommandType.LOAD, result.location));
                    generatedCode.addAll(generateStoreCodeForIdentifier(id));

                    memoryManager.removeVariable(tmp2);
                    memoryManager.removeVariable(multiplier);
                    memoryManager.removeVariable(remaining);
                    memoryManager.removeVariable(result);
                    memoryManager.removeVariable(shiftCounter);
                    memoryManager.removeVariable(b);
                    memoryManager.removeVariable(isNegative);
                }


            }
            else if(expr.MOD()!=null){
                //expression -> value MOD value
                //         int a = 100;
                //        int b = 4;
                //        int mnożnik = 1;
                //        int left = a;
                //        int wynik = 0;
                //        while(mnożnik < left) {
                //            mnożnik *= 2;
                //        }
                //        int tmp2 = mnożnik * b;
                //        while(left>=b){
                //            while(tmp2 > left) {
                //                //stricte wieksze
                //                tmp2/=2;
                //                mnożnik /= 2;
                //
                //            }
                //            wynik += mnożnik;
                //            left = left - tmp2;
                //        }
                //expression -> value DIV value
                Symbol multiplier = memoryManager.getFreeSpace();
                Symbol remaining = memoryManager.getFreeSpace();
                Symbol result = memoryManager.getFreeSpace();
                Symbol shiftCounter = memoryManager.getFreeSpace();
                Symbol b = memoryManager.getFreeSpace();
                Symbol isNegative = memoryManager.getFreeSpace();

                //isNegative is by default false aka = 0
                generatedCode.add(new Command(CommandType.SUB, 0));
                generatedCode.add(new Command(CommandType.STORE, isNegative.location));


                //Copy variables to tmp memory
                generatedCode.addAll(generateLoadCodeForValue(expr.value(0)));
                generatedCode.add(new Command(CommandType.STORE, remaining.location));

                //IF remaining < 0 flip it
                long line0 = generatedCode.size();
                generatedCode.add(new Command(CommandType.JPOS, line0+5));
                generatedCode.add(new Command(CommandType.LOAD, remaining.location));
                generatedCode.add(new Command(CommandType.SUB, remaining.location));
                generatedCode.add(new Command(CommandType.SUB, remaining.location));
                generatedCode.add(new Command(CommandType.STORE, remaining.location));


                //Copy variables to tmp memory
                generatedCode.addAll(generateLoadCodeForValue(expr.value(1)));
                generatedCode.add(new Command(CommandType.STORE, b.location));

                // IF b == 0 return 0
                long line3 = generatedCode.size();
                generatedCode.add(new Command(CommandType.LOAD, b.location));
                generatedCode.add(new Command(CommandType.JZERO, line3+64));

                //IF b < 0 flip it
                long line1 = generatedCode.size();
                generatedCode.add(new Command(CommandType.LOAD, b.location));
                generatedCode.add(new Command(CommandType.JPOS, line1+8));
                generatedCode.add(new Command(CommandType.LOAD, b.location));
                generatedCode.add(new Command(CommandType.SUB, b.location));
                generatedCode.add(new Command(CommandType.SUB, b.location));
                generatedCode.add(new Command(CommandType.STORE, b.location));
                generatedCode.add(new Command(CommandType.LOAD, symbolTable.get("2^0").location));
                generatedCode.add(new Command(CommandType.STORE, isNegative.location));




                //Cleaning tmp variables
                generatedCode.add(new Command(CommandType.SUB, 0));
                generatedCode.add(new Command(CommandType.STORE, result.location));

                generatedCode.add(new Command(CommandType.INC, 0));
                generatedCode.add(new Command(CommandType.STORE, multiplier.location));

                Symbol tmp2 = memoryManager.getFreeSpace();
                //tmp2 = b
                generatedCode.add(new Command(CommandType.LOAD, b.location));
                generatedCode.add(new Command(CommandType.STORE, tmp2.location));

                //while(mnożnik - left <= 0) mnożnik *= 2;

                generatedCode.add(new Command(CommandType.LOAD, multiplier.location));
                generatedCode.add(new Command(CommandType.SUB, remaining.location));
                long JPOSLine = generatedCode.size();

                //LOOP
                generatedCode.add(new Command(CommandType.JPOS, JPOSLine+13));
                //UPDATE multiplier
                generatedCode.add(new Command(CommandType.LOAD, multiplier.location));
                generatedCode.add(new Command(CommandType.SHIFT, symbolTable.get("2^0").location)); //p1 = 1
                generatedCode.add(new Command(CommandType.STORE, multiplier.location));
                //UPDATE shiftCounter
                generatedCode.add(new Command(CommandType.LOAD, shiftCounter.location));
                generatedCode.add(new Command(CommandType.INC, 0));
                generatedCode.add(new Command(CommandType.STORE,shiftCounter.location));

                generatedCode.add(new Command(CommandType.LOAD, tmp2.location));
                generatedCode.add(new Command(CommandType.SHIFT, symbolTable.get("2^0").location));
                generatedCode.add(new Command(CommandType.STORE, tmp2.location));

                generatedCode.add(new Command(CommandType.LOAD, multiplier.location));
                generatedCode.add(new Command(CommandType.SUB, remaining.location));
                generatedCode.add(new Command(CommandType.JUMP, JPOSLine));
                //ENDLOOP


                //LOOP
                generatedCode.add(new Command(CommandType.LOAD, remaining.location));
                generatedCode.add(new Command(CommandType.SUB, b.location));
                long loopLine = generatedCode.size();
                generatedCode.add(new Command(CommandType.JNEG, loopLine+25));

                //INNER LOOP
                generatedCode.add(new Command(CommandType.LOAD, tmp2.location));
                generatedCode.add(new Command(CommandType.SUB, remaining.location));
                generatedCode.add(new Command(CommandType.DEC, 0));
                long innerLoopLine = generatedCode.size();
                generatedCode.add(new Command(CommandType.JNEG, innerLoopLine+11));

                generatedCode.add(new Command(CommandType.LOAD, tmp2.location));
                generatedCode.add(new Command(CommandType.SHIFT, symbolTable.get("-2^0").location));
                generatedCode.add(new Command(CommandType.STORE, tmp2.location));

                generatedCode.add(new Command(CommandType.LOAD, multiplier.location));
                generatedCode.add(new Command(CommandType.SHIFT, symbolTable.get("-2^0").location));
                generatedCode.add(new Command(CommandType.STORE, multiplier.location));
                //generatedCode.add(new Command(CommandType.PUT, multiplier.location));

                //condition
                generatedCode.add(new Command(CommandType.LOAD, tmp2.location));
                generatedCode.add(new Command(CommandType.SUB, remaining.location));
                generatedCode.add(new Command(CommandType.DEC, 0));
                generatedCode.add(new Command(CommandType.JUMP, innerLoopLine));

                generatedCode.add(new Command(CommandType.LOAD, result.location));
                generatedCode.add(new Command(CommandType.ADD, multiplier.location));
                generatedCode.add(new Command(CommandType.STORE, result.location));

                generatedCode.add(new Command(CommandType.LOAD, multiplier.location));
                //generatedCode.add(new Command(CommandType.PUT, 0));

                generatedCode.add(new Command(CommandType.LOAD, remaining.location));
                generatedCode.add(new Command(CommandType.SUB, tmp2.location));
                generatedCode.add(new Command(CommandType.STORE, remaining.location));

                //condition
                generatedCode.add(new Command(CommandType.LOAD, remaining.location));
                generatedCode.add(new Command(CommandType.SUB, b.location));
                generatedCode.add(new Command(CommandType.JUMP, loopLine));

                // if isNegative flip the result
                generatedCode.add(new Command(CommandType.LOAD, isNegative.location));

                //generatedCode.add(new Command(CommandType.PUT, isNegative.location));
                long line2 = generatedCode.size();
                generatedCode.add(new Command(CommandType.JZERO, line2 + 5));
                generatedCode.add(new Command(CommandType.LOAD, remaining.location));
                generatedCode.add(new Command(CommandType.SUB, remaining.location));
                generatedCode.add(new Command(CommandType.SUB, remaining.location));
                generatedCode.add(new Command(CommandType.STORE, remaining.location));

                //if b == 0 return 0
                generatedCode.add(new Command(CommandType.LOAD, b.location));
                long line5 = generatedCode.size();
                generatedCode.add(new Command(CommandType.JPOS, line5+4));
                generatedCode.add(new Command(CommandType.JNEG, line5+4));
                generatedCode.add(new Command(CommandType.SUB, 0));
                generatedCode.add(new Command(CommandType.STORE, remaining.location));





                generatedCode.add(new Command(CommandType.LOAD, remaining.location));
                generatedCode.addAll(generateStoreCodeForIdentifier(id));

                memoryManager.removeVariable(tmp2);
                memoryManager.removeVariable(multiplier);
                memoryManager.removeVariable(remaining);
                memoryManager.removeVariable(result);
                memoryManager.removeVariable(shiftCounter);
                memoryManager.removeVariable(b);
                memoryManager.removeVariable(isNegative);
            }
            else{

                generatedCode.addAll(generateLoadCodeForValue(expr.value(0)));
                generatedCode.addAll(generateStoreCodeForIdentifier(id));

            }


        }
        else if(ctx.IF()!=null){
            //IF condition THEN commands ENDIF
            if(ctx.ELSE()==null){

                int line = generatedCode.size();

                int conditionLineStart = generatedCode.size();
                generatedCode.addAll(line, generateConditionCode(ctx.condition().value(0), ctx.condition().value(1), ctx.condition()));
                int conditionLineEnd = generatedCode.size();
                int shift = conditionLineEnd-conditionLineStart;
                //shift = 0;

                visitCommands(ctx.commands(0));

                long lineAfter = generatedCode.size();

                if(ctx.condition().EQ()!=null){
                    generatedCode.get(conditionLineEnd-1).argument = lineAfter;
                    generatedCode.get(conditionLineEnd-2).argument = lineAfter;
                }
                else if(ctx.condition().NEQ()!=null){
                    generatedCode.get(conditionLineEnd-1).argument = lineAfter;
                }
                else if(ctx.condition().LE()!=null || ctx.condition().GE()!=null){
                    generatedCode.get(conditionLineEnd-1).argument = lineAfter;
                    generatedCode.get(conditionLineEnd-2).argument = lineAfter;
                }
                else if(ctx.condition().LEQ()!=null || ctx.condition().GEQ()!=null){
                    generatedCode.get(conditionLineEnd-1).argument = lineAfter;
                }
                return 0;
            }
            else if(ctx.ELSE()!=null){

                int line = generatedCode.size();

                int conditionLineStart = generatedCode.size();
                generatedCode.addAll(line, generateConditionCode(ctx.condition().value(0), ctx.condition().value(1), ctx.condition()));
                int conditionLineEnd = generatedCode.size();


                visitCommands(ctx.commands(0));

                int secondConditionLineStart = generatedCode.size();
                generatedCode.addAll(generateOppositeConditionCode(ctx.condition().value(0),ctx.condition().value(1), ctx.condition()));
                int secondConditionLineEnd = generatedCode.size();

                visitCommands(ctx.commands(1));


                long lineAfter = generatedCode.size();

                if(ctx.condition().EQ()!=null){
                    generatedCode.get(conditionLineEnd-1).argument = secondConditionLineStart;
                    generatedCode.get(conditionLineEnd-2).argument = secondConditionLineStart;

                    generatedCode.get(secondConditionLineEnd-1).argument = lineAfter;
                } else if(ctx.condition().NEQ()!=null){
                    generatedCode.get(conditionLineEnd-1).argument = secondConditionLineStart;

                    generatedCode.get(secondConditionLineEnd-1).argument = lineAfter;
                    generatedCode.get(secondConditionLineEnd-2).argument = lineAfter;
                } else if(ctx.condition().LE()!=null){
                    generatedCode.get(conditionLineEnd-1).argument = secondConditionLineStart;
                    generatedCode.get(conditionLineEnd-2).argument = secondConditionLineStart;

                    generatedCode.get(secondConditionLineEnd-1).argument = lineAfter;
                }  else if(ctx.condition().GE()!=null){
                    generatedCode.get(conditionLineEnd-1).argument = secondConditionLineStart;
                    generatedCode.get(conditionLineEnd-2).argument = secondConditionLineStart;

                    generatedCode.get(secondConditionLineEnd-1).argument = lineAfter;
                }  else if(ctx.condition().LEQ()!=null){
                    generatedCode.get(conditionLineEnd-1).argument = secondConditionLineStart;

                    generatedCode.get(secondConditionLineEnd-1).argument = lineAfter;
                    generatedCode.get(secondConditionLineEnd-2).argument = lineAfter;
                } else if(ctx.condition().GEQ()!=null){
                    generatedCode.get(conditionLineEnd-1).argument = secondConditionLineStart;

                    generatedCode.get(secondConditionLineEnd-1).argument = lineAfter;
                    generatedCode.get(secondConditionLineEnd-2).argument = lineAfter;
                }



            }
        }
        else if(ctx.ENDWHILE()!=null){
            int line = generatedCode.size();

            int conditionLineStart = generatedCode.size();
            generatedCode.addAll(line, generateConditionCode(ctx.condition().value(0),ctx.condition().value(1), ctx.condition()));
            int conditionLineEnd = generatedCode.size();

            visitCommands(ctx.commands(0));

            generatedCode.add(new Command(CommandType.JUMP, conditionLineStart));

            long lineAfter = generatedCode.size();

            if(ctx.condition().EQ()!=null){
                generatedCode.get(conditionLineEnd-1).argument = lineAfter;
                generatedCode.get(conditionLineEnd-2).argument = lineAfter;
            }
            else if(ctx.condition().NEQ()!=null){
                generatedCode.get(conditionLineEnd-1).argument = lineAfter;
            }
            else if(ctx.condition().LE()!=null || ctx.condition().GE()!=null){
                generatedCode.get(conditionLineEnd-1).argument = lineAfter;
                generatedCode.get(conditionLineEnd-2).argument = lineAfter;
            }
            else if(ctx.condition().LEQ()!=null || ctx.condition().GEQ()!=null){
                generatedCode.get(conditionLineEnd-1).argument = lineAfter;
            }
        }
        else if(ctx.ENDDO()!=null){
            int line = generatedCode.size();

            visitCommands(ctx.commands(0));

            int conditionLineStart = generatedCode.size();
            generatedCode.addAll(generateConditionCode(ctx.condition().value(0),ctx.condition().value(1), ctx.condition()));
            int conditionLineEnd = generatedCode.size();

            int jumpLine = generatedCode.size();
            generatedCode.add(new Command(CommandType.JUMP, line));

            long lineAfter = generatedCode.size();

            if(ctx.condition().EQ()!=null){
                generatedCode.get(conditionLineEnd-1).argument = lineAfter;
                generatedCode.get(conditionLineEnd-2).argument = lineAfter;
            }
            else if(ctx.condition().NEQ()!=null){
                generatedCode.get(conditionLineEnd-1).argument = lineAfter;
            }
            else if(ctx.condition().LE()!=null || ctx.condition().GE()!=null){
                generatedCode.get(conditionLineEnd-1).argument = lineAfter;
                generatedCode.get(conditionLineEnd-2).argument = lineAfter;
            }
            else if(ctx.condition().LEQ()!=null || ctx.condition().GEQ()!=null){
                generatedCode.get(conditionLineEnd-1).argument = lineAfter;
            }
        }
        else if(ctx.TO()!=null){
            //FOR PIDENTIFIER FROM value TO value DO commands ENDFOR
            Symbol iterator = memoryManager.getFreeSpace();
            Symbol a = memoryManager.getFreeSpace();
            Symbol b = memoryManager.getFreeSpace();

            generatedCode.addAll(generateLoadCodeForValue(ctx.value(0)));
            generatedCode.add(new Command(CommandType.STORE, a.location));
            generatedCode.add(new Command(CommandType.STORE, iterator.location));
            generatedCode.addAll(generateLoadCodeForValue(ctx.value(1)));
            generatedCode.add(new Command(CommandType.STORE, b.location));

            //loading the iterator to the symbol Table
            String iterator_name = ctx.PIDENTIFIER().getText();
            Symbol s = new Symbol(IdentifierType.VARIABLE, iterator_name);
            s.location = iterator.location;

            symbolTable.put(iterator_name, s);
            int conditionStartLine = generatedCode.size();
            generatedCode.add(new Command(CommandType.LOAD, iterator.location));
            generatedCode.add(new Command(CommandType.SUB, b.location));
            generatedCode.add(new Command(CommandType.JPOS, 0));
            int conditionEndLine = generatedCode.size();

            visitCommands(ctx.commands(0));

            generatedCode.add(new Command(CommandType.LOAD, iterator.location));
            generatedCode.add(new Command(CommandType.INC, 0));
            generatedCode.add(new Command(CommandType.STORE, iterator.location));
            generatedCode.add(new Command(CommandType.JUMP, conditionStartLine));

            int instructionEndLine = generatedCode.size();

            generatedCode.get(conditionEndLine - 1).argument = instructionEndLine;

            symbolTable.remove(iterator_name);

            memoryManager.removeVariable(a);
            memoryManager.removeVariable(b);
            memoryManager.removeVariable(iterator);

        }
        else if(ctx.DOWNTO()!=null){
            //FOR PIDENTIFIER FROM value DOWNTO value DO commands ENDFOR
            Symbol iterator = memoryManager.getFreeSpace();
            Symbol a = memoryManager.getFreeSpace();
            Symbol b = memoryManager.getFreeSpace();

            generatedCode.addAll(generateLoadCodeForValue(ctx.value(0)));
            generatedCode.add(new Command(CommandType.STORE, a.location));
            generatedCode.add(new Command(CommandType.STORE, iterator.location));
            generatedCode.addAll(generateLoadCodeForValue(ctx.value(1)));
            generatedCode.add(new Command(CommandType.STORE, b.location));

            //loading the iterator to the symbol Table
            String iterator_name = ctx.PIDENTIFIER().getText();
            Symbol s = new Symbol(IdentifierType.VARIABLE, iterator_name);
            s.location = iterator.location;

            symbolTable.put(iterator_name, s);
            int conditionStartLine = generatedCode.size();
            generatedCode.add(new Command(CommandType.LOAD, iterator.location));
            generatedCode.add(new Command(CommandType.SUB, b.location));
            generatedCode.add(new Command(CommandType.JNEG, 0));
            int conditionEndLine = generatedCode.size();

            visitCommands(ctx.commands(0));

            generatedCode.add(new Command(CommandType.LOAD, iterator.location));
            generatedCode.add(new Command(CommandType.DEC, 0));
            generatedCode.add(new Command(CommandType.STORE, iterator.location));
            generatedCode.add(new Command(CommandType.JUMP, conditionStartLine));

            int instructionEndLine = generatedCode.size();

            generatedCode.get(conditionEndLine - 1).argument = instructionEndLine;

            symbolTable.remove(iterator_name);

            memoryManager.removeVariable(a);
            memoryManager.removeVariable(b);
            memoryManager.removeVariable(iterator);
        }
        return 0;

    }

    ArrayList<Command> generateConditionCode(JFTTParser.ValueContext a1, JFTTParser.ValueContext b1, JFTTParser.ConditionContext ctx){
        ArrayList<Command> commands = new ArrayList<>();
        Symbol a = memoryManager.getFreeSpace();
        Symbol b = memoryManager.getFreeSpace();

        commands.addAll(generateLoadCodeForValue(a1));
        commands.add(new Command(CommandType.STORE, a.location));
        commands.addAll(generateLoadCodeForValue(b1));
        commands.add(new Command(CommandType.STORE, b.location));

        if(ctx.EQ()!=null){
            commands.add(new Command(CommandType.LOAD, a.location));
            commands.add(new Command(CommandType.SUB, b.location));
            commands.add(new Command(CommandType.JPOS, 0));
            commands.add(new Command(CommandType.JNEG, 0));
        }
        else if(ctx.NEQ()!=null){
            commands.add(new Command(CommandType.LOAD, a.location));
            commands.add(new Command(CommandType.SUB, b.location));
            commands.add(new Command(CommandType.JZERO, 0));
        } else if(ctx.LE()!=null){
            commands.add(new Command(CommandType.LOAD, a.location));
            commands.add(new Command(CommandType.SUB, b.location));
            commands.add(new Command(CommandType.JPOS, 0));
            commands.add(new Command(CommandType.JZERO, 0));
        } else if(ctx.GE()!=null){
            commands.add(new Command(CommandType.LOAD, a.location));
            commands.add(new Command(CommandType.SUB, b.location));
            commands.add(new Command(CommandType.JNEG, 0));
            commands.add(new Command(CommandType.JZERO, 0));
        } else if(ctx.LEQ()!=null){
            commands.add(new Command(CommandType.LOAD, a.location));
            commands.add(new Command(CommandType.SUB, b.location));
            commands.add(new Command(CommandType.JPOS, 0));
        } else if(ctx.GEQ()!=null){
            commands.add(new Command(CommandType.LOAD, a.location));
            commands.add(new Command(CommandType.SUB, b.location));
            commands.add(new Command(CommandType.JNEG, 0));
        }

        memoryManager.removeVariable(a);
        memoryManager.removeVariable(b);


        return commands;
    }

    ArrayList<Command> generateOppositeConditionCode(JFTTParser.ValueContext a1, JFTTParser.ValueContext b1, JFTTParser.ConditionContext ctx){
        ArrayList<Command> commands = new ArrayList<>();
        Symbol a = memoryManager.getFreeSpace();
        Symbol b = memoryManager.getFreeSpace();

        generatedCode.addAll(generateLoadCodeForValue(a1));
        generatedCode.add(new Command(CommandType.STORE, a.location));
        generatedCode.addAll(generateLoadCodeForValue(b1));
        generatedCode.add(new Command(CommandType.STORE, b.location));
        if(ctx.NEQ()!=null){
            commands.add(new Command(CommandType.LOAD, a.location));
            commands.add(new Command(CommandType.SUB, b.location));
            commands.add(new Command(CommandType.JPOS, 0));
            commands.add(new Command(CommandType.JNEG, 0));
        }
        else if(ctx.EQ()!=null){
            commands.add(new Command(CommandType.LOAD, a.location));
            commands.add(new Command(CommandType.SUB, b.location));
            commands.add(new Command(CommandType.JZERO, 0));
        } else if(ctx.GEQ()!=null){
            commands.add(new Command(CommandType.LOAD, a.location));
            commands.add(new Command(CommandType.SUB, b.location));
            commands.add(new Command(CommandType.JPOS, 0));
            commands.add(new Command(CommandType.JZERO, 0));
        } else if(ctx.LEQ()!=null){
            commands.add(new Command(CommandType.LOAD, a.location));
            commands.add(new Command(CommandType.SUB, b.location));
            commands.add(new Command(CommandType.JNEG, 0));
            commands.add(new Command(CommandType.JZERO, 0));
        } else if(ctx.GE()!=null){
            commands.add(new Command(CommandType.LOAD, a.location));
            commands.add(new Command(CommandType.SUB, b.location));
            commands.add(new Command(CommandType.JPOS, 0));
        } else if(ctx.LE()!=null){
            commands.add(new Command(CommandType.LOAD, a.location));
            commands.add(new Command(CommandType.SUB, b.location));
            commands.add(new Command(CommandType.JNEG, 0));
        }

        memoryManager.removeVariable(a);
        memoryManager.removeVariable(b);

        return commands;
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

            long aLocationShift = a.location+a.getRangeLength();

            Symbol tmp = memoryManager.getFreeSpace();
            long memoryLocation = tmp.location;
            Symbol tmp2 = memoryManager.getFreeSpace();


            commands.add(new Command(CommandType.STORE, tmp2.location));
            commands.add(new Command(CommandType.LOAD, b.location));
            commands.add(new Command(CommandType.ADD, aLocationShift));
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
            Long number = 0L;
            try{
                number = Long.parseLong(value.NUM().getText());
            } catch(NumberFormatException e) {
                System.out.println("Number out of long long scope");
            }
            commands.addAll(generateNumber(number));
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
            long aLocationShift = a.location+a.getRangeLength();

            Symbol tmp = memoryManager.getFreeSpace();

            commands.add(new Command(CommandType.LOAD, b.location));
            commands.add(new Command(CommandType.ADD, aLocationShift));
            commands.add(new Command(CommandType.LOADI,0));

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
        commands.add(new Command(CommandType.SUB, 0, "Generating number: "+number));

        Boolean isNegative = false;
        if(number<0) {
            number = -number;
            isNegative = true;
        }

        String stringNumber = "0";
        try{
            stringNumber = Long.toBinaryString(number);
        } catch(NumberFormatException e) {
            System.out.println("Number out of long long scope");
        }

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

    private void generateMultiplicationCode(Symbol a1, Symbol b){
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
        Symbol multiplier = memoryManager.getFreeSpace();
        Symbol remaining = memoryManager.getFreeSpace();
        Symbol result = memoryManager.getFreeSpace();
        Symbol shiftCounter = memoryManager.getFreeSpace();
        Symbol a = memoryManager.getFreeSpace();
        Symbol isNegative = memoryManager.getFreeSpace();

        //Copy variables to tmp memory
        generatedCode.add(new Command(CommandType.LOAD, a1.location));
        generatedCode.add(new Command(CommandType.STORE, a.location));

        //Copy variables to tmp memory
        generatedCode.add(new Command(CommandType.LOAD, b.location));
        generatedCode.add(new Command(CommandType.STORE, remaining.location));
        // if remaining < 0 remaining = - remaining
        long line = generatedCode.size();
        generatedCode.add(new Command(CommandType.JPOS, line+7));
        generatedCode.add(new Command(CommandType.SUB, remaining.location));
        generatedCode.add(new Command(CommandType.SUB, remaining.location));
        generatedCode.add(new Command(CommandType.STORE, remaining.location));
        generatedCode.add(new Command(CommandType.SUB, 0));
        generatedCode.add(new Command(CommandType.INC, 0));
        generatedCode.add(new Command(CommandType.STORE, isNegative.location));

        //Clean the variables
        generatedCode.add(new Command(CommandType.SUB, 0));
        generatedCode.add(new Command(CommandType.STORE, result.location));
        generatedCode.add(new Command(CommandType.STORE, shiftCounter.location));
        generatedCode.add(new Command(CommandType.STORE, isNegative.location));

        generatedCode.add(new Command(CommandType.INC, 0));
        generatedCode.add(new Command(CommandType.STORE, multiplier.location));

        //while(mnożnik - left <= 0) mnożnik *= 2;

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

        // if negative flip the result
        //IF b < 0 b = -b;
        generatedCode.add(new Command(CommandType.LOAD, isNegative.location));
        long lineResult = generatedCode.size();
        generatedCode.add(new Command(CommandType.JZERO, lineResult+5));
        generatedCode.add(new Command(CommandType.LOAD, result.location));
        generatedCode.add(new Command(CommandType.SUB, result.location));
        generatedCode.add(new Command(CommandType.SUB, result.location));
        generatedCode.add(new Command(CommandType.STORE, result.location));

        generatedCode.add(new Command(CommandType.LOAD, result.location));

        memoryManager.removeVariable(multiplier);
        memoryManager.removeVariable(remaining);
        memoryManager.removeVariable(result);
        memoryManager.removeVariable(shiftCounter);
        memoryManager.removeVariable(a);
        memoryManager.removeVariable(isNegative);
    }

}
