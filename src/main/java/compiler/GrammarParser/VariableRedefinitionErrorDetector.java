package compiler.GrammarParser;

import parser.JFTTParser;

import static parser.JFTTLexer.PIDENTIFIER;

public class VariableRedefinitionErrorDetector extends ErrorDetector {

    @Override
    public void enterDeclarations(JFTTParser.DeclarationsContext ctx) {
        int line = ctx.getToken(PIDENTIFIER,0).getSymbol().getLine();

        String name = ctx.PIDENTIFIER().getText();
        if (isDeclared(name)) addError(new Error(line, "Redefinition of an identifier "+name));
        else {
            if (isArray(ctx)) {
                long rangeStart = getRange(ctx, 0);
                long rangeEnd = getRange(ctx, 1);
                if(rangeStart > rangeEnd) addError(new Error(line, "Incorrect array range definition: "+name));
                else{
                    Symbol s = new Symbol(IdentifierType.ARRAY, name);
                    s.setArray(rangeStart, rangeEnd);
                    symbolTable.put(name, s);
                }
            } else {
                symbolTable.put(name, new Symbol(IdentifierType.VARIABLE, name));
            }
        }
    }


    private Boolean isArray(JFTTParser.DeclarationsContext ctx){
        return (ctx.NUM(0) != null && ctx.NUM(1) != null);
    }
    private Boolean isDeclared(String name){
        return symbolTable.containsKey(name);
    }
    private Long getRange(JFTTParser.DeclarationsContext ctx, int i){
        return Long.parseLong(ctx.NUM(i).getSymbol().getText());
    }

}
