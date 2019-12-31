package compiler.GrammarParser;

import org.antlr.v4.runtime.Token;
import parser.JFTTParser;

import java.awt.desktop.SystemEventListener;

import static java.lang.Math.abs;
import static parser.JFTTLexer.FOR;
import static parser.JFTTLexer.PIDENTIFIER;

public class UndeclaredVariableErrorDetector extends ErrorDetector {

    @Override
    public void enterCommand(JFTTParser.CommandContext ctx) {
        if(ctx.FOR()!=null){
            String name = ctx.PIDENTIFIER().getText();
            Symbol s = new Symbol(IdentifierType.VARIABLE, name);
            s.isIterator = true;
            s.isInitialized = true;
            symbolTable.put(name, s);


            String v1 = ctx.value(0).getText();
            String v2 = ctx.value(1).getText();
            int line = ctx.getStart().getLine();

            if(symbolTable.get(v1)!= null && !symbolTable.get(v1).isInitialized && !symbolTable.get(v1).isIterator) errors.add(new Error(line, "Variable "+v1+" not initialized"));
            if(symbolTable.get(v2)!= null && !symbolTable.get(v2).isInitialized && !symbolTable.get(v2).isIterator) errors.add(new Error(line, "Variable "+v2+" not initialized"));
        }
        else if(ctx.ASSIGN()!=null){
            String name = ctx.identifier().PIDENTIFIER(0).getText();
            String v1 = ctx.expression().value(0).getText();
            String v2 = null;
            if(ctx.expression().value(1)!=null) v2 = ctx.expression().value(1).getText();
            int line = ctx.getStart().getLine();

            Boolean v1Initialized = false;
            Boolean v2Initialized = false;
            if(symbolTable.get(v1)!= null && symbolTable.get(v1).isInitialized && !symbolTable.get(v1).isIterator) v1Initialized = true;
            if(symbolTable.get(v2)!= null && symbolTable.get(v2).isInitialized && !symbolTable.get(v2).isIterator) v2Initialized = true;
            if(symbolTable.get(v1)!= null && symbolTable.get(v1).isIterator) v1Initialized = true;
            if(symbolTable.get(v2)!= null && symbolTable.get(v2).isIterator) v2Initialized = true;
            if(ctx.expression().value(0).NUM()!=null) {
                v1Initialized = true;
            }

            if(ctx.expression().value(0).identifier()!=null){
                if(ctx.expression().value(0).identifier().PIDENTIFIER(1) != null){
                    String v3 = ctx.expression().value(0).identifier().PIDENTIFIER(1).getText();

                    if(symbolTable.get(v3)!= null && symbolTable.get(v3).isInitialized) {
                        System.out.println("VARIABLE: v3: "+v3);
                        v1Initialized = true;
                    }
                }
                else if(ctx.expression().value(0).NUM() != null){
                    v1Initialized = true;
                }
            }



            if(symbolTable.get(name)!= null && !symbolTable.get(name).isIterator) symbolTable.get(name).isInitialized = true;

            if(symbolTable.get(name)!= null && symbolTable.get(name).isIterator) errors.add(new Error(line, "Iterator "+name+" updated"));

            if(!v1Initialized) errors.add(new Error(line, "Variable "+v1+" not initialized"));
            else symbolTable.get(name).isInitialized = true;
            if(v2!=null && symbolTable.get(v2)!= null && !v2Initialized && !symbolTable.get(v2).isIterator) errors.add(new Error(line, "Variable "+v2+" not initialized"));
        }
        else if(ctx.WRITE()!=null){
            String name = ctx.value(0).getText();
            int line = ctx.getStart().getLine();
            if(symbolTable.get(name)!=null && !symbolTable.get(name).isInitialized) errors.add(new Error(line, "Variable "+name+" not initialized"));
        }
        else if(ctx.READ()!=null){
            String name = ctx.identifier().getText();
            symbolTable.get(name).isInitialized = true;
        }
        else if(ctx.IF()!=null){
            String v1 = ctx.condition().value(0).getText();
            String v2 = ctx.condition().value(1).getText();
            int line = ctx.getStart().getLine();

            if(symbolTable.get(v1)!= null && !symbolTable.get(v1).isInitialized && !symbolTable.get(v1).isIterator) errors.add(new Error(line, "Variable "+v1+" not initialized"));
            if(symbolTable.get(v2)!= null && !symbolTable.get(v2).isInitialized && !symbolTable.get(v2).isIterator) errors.add(new Error(line, "Variable "+v2+" not initialized"));
        }
    }

    @Override
    public void enterValue(JFTTParser.ValueContext ctx) {
        if(ctx.NUM()!=null){
            long number = 0;
            try{
                number = Long.parseLong(ctx.NUM().getText());
            } catch(NumberFormatException e){
                int line = ctx.getStart().getLine();
                errors.add(new Error(line, "Number out of long long scope"));
            }

            if(abs(number) > largestNumber) {
                if(number<0) largestNumber = -number;
                else largestNumber = number;
            }
        }
    }

    @Override
    public void exitCommand(JFTTParser.CommandContext ctx) {
        if(ctx.FOR()!=null){
            String name = ctx.PIDENTIFIER().getText();
            symbolTable.remove(name);
        }
    }

    @Override
    public void enterIdentifier(JFTTParser.IdentifierContext ctx) {
        String name1 = ctx.PIDENTIFIER(0).getText();
        int line = ctx.getToken(PIDENTIFIER,0).getSymbol().getLine();

        if(!symbolTable.containsKey(name1)) {
            addError(new Error(line, "Undeclared variable "+name1));
        }else{
            if((ctx.PIDENTIFIER(1) == null && ctx.NUM()== null ) && symbolTable.get(name1).isArray()) addError(new Error(line, "Array "+name1+" used as a variable"));
        }

        String name2;
        if(ctx.PIDENTIFIER(1) != null || ctx.NUM()!=null) {
            if(ctx.PIDENTIFIER(1) != null){
                name2 = ctx.PIDENTIFIER(1).getText();
                if(!symbolTable.containsKey(name2)) addError(new Error(line, "Undeclared variable "+name2));
            }

            if(symbolTable.containsKey(name1) && !symbolTable.get(name1).isArray()) addError(new Error(line, "Variable "+name1+" used as an array"));
        }


        if(ctx.NUM()!=null)
        {
            String name = ctx.PIDENTIFIER(0).getText();
            long index = Long.parseLong(ctx.NUM().getText());
            Symbol s = symbolTable.get(name);
            if(s.getRangeStart() > index || s.getRangeEnd() < index) addError(new Error(line, "Array index out of scope"));
        }


    }
}
