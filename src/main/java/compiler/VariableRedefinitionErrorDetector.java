package compiler;

import parser.JFTTBaseListener;
import parser.JFTTParser;

import java.util.HashMap;

public class VariableRedefinitionErrorDetector extends ErrorDetector {

    @Override
    public void enterDeclarations(JFTTParser.DeclarationsContext ctx) {

        String name = ctx.PIDENTIFIER().getText();
        if (isDeclared(name)) System.out.println("> Error: Redefinition of an identifier: " + name);
        else {
            if (isArray(ctx)) {
                long rangeStart = getRange(ctx, 0);
                long rangeEnd = getRange(ctx, 1);
                if(rangeStart > rangeEnd) System.out.println("> Error: Incorrect array range definition");
                else{
                    Symbol s = new Symbol(IdentifierType.ARRAY);
                    s.setArray(rangeStart, rangeEnd);
                    symbolTable.put(name, s);
                }
            } else {
                symbolTable.put(name, new Symbol(IdentifierType.VARIABLE));
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
