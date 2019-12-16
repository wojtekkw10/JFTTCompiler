package compiler.GrammarParser;

import parser.JFTTParser;

import static parser.JFTTLexer.PIDENTIFIER;

public class UndeclaredVariableErrorDetector extends ErrorDetector {

    @Override
    public void enterCommand(JFTTParser.CommandContext ctx) {
        if(ctx.FOR()!=null){
            String name = ctx.PIDENTIFIER().getText();
            symbolTable.put(name, new Symbol(IdentifierType.VARIABLE, name));
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


    }
}
