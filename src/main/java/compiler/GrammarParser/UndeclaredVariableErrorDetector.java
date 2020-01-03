package compiler.GrammarParser;

import parser.JFTTParser;
import static java.lang.Math.abs;
import static parser.JFTTLexer.PIDENTIFIER;

public class UndeclaredVariableErrorDetector extends ErrorDetector {

    @Override
    public void enterCommand(JFTTParser.CommandContext ctx) {
        if(ctx.FOR()!=null){
            String name = ctx.PIDENTIFIER().getText();
            Symbol s = new Symbol(IdentifierType.VARIABLE, name);
            symbolTable.put(name, s);

        }
        else if(ctx.ASSIGN()!=null){
            String name = ctx.identifier().PIDENTIFIER(0).getText();
            String v1 = ctx.expression().value(0).getText();
            String v2 = null;
            if(ctx.expression().value(1)!=null) v2 = ctx.expression().value(1).getText();
            int line = ctx.getStart().getLine();

            if(symbolTable.get(name)!= null && symbolTable.get(name).isIterator) errors.add(new Error(line, "Iterator "+name+" updated"));

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

        if((ctx.PIDENTIFIER(1) == null && ctx.NUM()== null ) && symbolTable.get(name1).isArray()) addError(new Error(line, "Array "+name1+" used as a variable"));

        if(ctx.PIDENTIFIER(1) != null || ctx.NUM()!=null) {
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
