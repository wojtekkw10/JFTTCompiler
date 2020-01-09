package compiler.GrammarParser;

import parser.JFTTParser;
import static java.lang.Math.abs;
import static parser.JFTTLexer.PIDENTIFIER;

public class UndeclaredVariableErrorDetector extends ErrorDetector {

    @Override
    public void enterCommand(JFTTParser.CommandContext ctx) {
        if(ctx.FOR()!=null){
            String name = ctx.PIDENTIFIER().getText();
            int line = ctx.getStart().getLine();
            if(symbolTable.containsKey(name)) errors.add(new Error(line, "Iterator name "+name+" redefinition"));
            else {
                Symbol s = new Symbol(IdentifierType.VARIABLE, name);
                s.isIterator = true;
                symbolTable.put(name, s);
            }


        }
        else if(ctx.ASSIGN()!=null){
            String name = ctx.identifier().PIDENTIFIER(0).getText();
            int line = ctx.getStart().getLine();

            if(symbolTable.get(name)!= null && symbolTable.get(name).isIterator) errors.add(new Error(line, "Iterator "+name+" updated"));

        }
        else if(ctx.READ()!=null){
            String name = ctx.identifier().PIDENTIFIER(0).getText();
            int line = ctx.getStart().getLine();
            if(symbolTable.get(name)!= null && !symbolTable.get(name).isArray())  symbolTable.get(name).isInitialized = true;
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
            if(symbolTable.get(name).isIterator)
            symbolTable.remove(name);

        }
    }

    @Override
    public void enterIdentifier(JFTTParser.IdentifierContext ctx) {
        String name1 = ctx.PIDENTIFIER(0).getText();
        String name2 = null;
        int line = ctx.getToken(PIDENTIFIER,0).getSymbol().getLine();

        if(!symbolTable.containsKey(name1)) addError(new Error(line, "Variable "+name1+" undeclared"));

        if(ctx.PIDENTIFIER(1)!= null) name2 = ctx.PIDENTIFIER(1).getText();


        if(ctx.PIDENTIFIER(1)!= null && !symbolTable.containsKey(name2)) addError(new Error(line, "Variable "+name2+" undeclared"));

        if(ctx.PIDENTIFIER(1)!= null && symbolTable.containsKey(name2) && symbolTable.get(name2).isArray) addError(new Error(line, "Array "+name2+" used as a variable"));

        if((ctx.PIDENTIFIER(1) == null && ctx.NUM()== null ) && symbolTable.containsKey(name1) && symbolTable.get(name1).isArray()) {
            addError(new Error(line, "Array "+name1+" used as a variable"));
        }

        if(ctx.PIDENTIFIER(1) != null || ctx.NUM()!=null) {
            if(symbolTable.containsKey(name1) && !symbolTable.get(name1).isArray()) addError(new Error(line, "Variable "+name1+" used as an array"));
        }


        if(ctx.NUM()!=null && symbolTable.get(name1).isArray())
        {
            String name = ctx.PIDENTIFIER(0).getText();
            long index = Long.parseLong(ctx.NUM().getText());
            Symbol s = symbolTable.get(name);
            if(s.getRangeStart() > index || s.getRangeEnd() < index) addError(new Error(line, "Array index out of scope"));
        }


    }
}
