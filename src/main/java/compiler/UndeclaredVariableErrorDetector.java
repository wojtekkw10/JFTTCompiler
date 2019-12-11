package compiler;

import parser.JFTTBaseListener;
import parser.JFTTParser;

import java.util.HashMap;

import static parser.JFTTLexer.PIDENTIFIER;

public class UndeclaredVariableErrorDetector extends ErrorDetector {

    @Override
    public void enterUpfor(JFTTParser.UpforContext ctx) {
        String name = ctx.PIDENTIFIER().getText();
        symbolTable.put(name, new Symbol(IdentifierType.VARIABLE));
    }

    @Override
    public void exitUpfor(JFTTParser.UpforContext ctx) {
        String name = ctx.PIDENTIFIER().getText();
        symbolTable.remove(name);
    }

    @Override
    public void enterDownfor(JFTTParser.DownforContext ctx) {
        String name = ctx.PIDENTIFIER().getText();
        symbolTable.put(name, new Symbol(IdentifierType.VARIABLE));
    }

    @Override
    public void exitDownfor(JFTTParser.DownforContext ctx) {
        String name = ctx.PIDENTIFIER().getText();
        symbolTable.remove(name);
    }

    @Override
    public void enterIdentifier(JFTTParser.IdentifierContext ctx) {
        String name1 = ctx.PIDENTIFIER(0).getText();
        int line = ctx.getToken(PIDENTIFIER,0).getSymbol().getLine();

        if(!symbolTable.containsKey(name1)) System.out.println("> Error: Undefined variable: "+name1+" in line: "+line);

        if(ctx.PIDENTIFIER(1) == null && symbolTable.get(name1).isArray())  System.out.println("> Error: Array used as a variable: "+name1+" in line: "+line);

        String name2;
        if(ctx.PIDENTIFIER(1) != null) {
            name2 = ctx.PIDENTIFIER(1).getText();
            if(!symbolTable.containsKey(name2)) System.out.println("> Error: Undefined variable: "+name2+" in line: "+line);

            if(!symbolTable.get(name1).isArray()) System.out.println("> Error: Variable used as an array: "+name1+" in line: "+line);
        }


    }
}
