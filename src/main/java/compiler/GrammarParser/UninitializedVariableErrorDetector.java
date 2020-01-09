package compiler.GrammarParser;

import parser.JFTTParser;

public class UninitializedVariableErrorDetector extends ErrorDetector {

    @Override
    public void enterCommand(JFTTParser.CommandContext ctx) {
        if(ctx.FOR()!=null){
            String name = ctx.PIDENTIFIER().getText();
            Symbol s = new Symbol(IdentifierType.VARIABLE, name);
            s.isIterator = true;
            s.isInitialized = true;
            symbolTable.put(name, s);
        }

    }

    @Override
    public void enterValue(JFTTParser.ValueContext ctx) {
        if(ctx.NUM()==null){
            int line = ctx.getStart().getLine();
            String variable = ctx.identifier().PIDENTIFIER(0).getText();
            if(symbolTable.containsKey(variable) && !symbolTable.get(variable).isInitialized) errors.add(new Error(line, "Variable "+variable+" not initialized"));
        }
    }

    @Override
    public void exitCommand(JFTTParser.CommandContext ctx) {
        if(ctx.FOR()!=null){
            String name = ctx.PIDENTIFIER().getText();
            symbolTable.remove(name);
        }
        else if(ctx.ASSIGN()!=null) {
            String name = ctx.identifier().PIDENTIFIER(0).getText();
            symbolTable.get(name).isInitialized = true;
        }

    }
}