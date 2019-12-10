package compiler;

import org.antlr.runtime.TokenStream;
import org.antlr.v4.runtime.Token;
import parser.JFTTBaseListener;
import parser.JFTTParser;

import java.util.HashMap;

import static parser.JFTTLexer.PIDENTIFIER;

public class SymbolTableGenerator extends JFTTBaseListener {
    HashMap<String, Symbol> symbolTable = new HashMap<>();

    //Used to build the symbol Table
    //Checks for redefinitions
    @Override
    public void enterDeclarations(JFTTParser.DeclarationsContext ctx) {
        String name = ctx.PIDENTIFIER().getText();
        if (isDeclared(name)) System.out.println("> Error: Redefinition of an identifier: " + name);
        else {
            if (ctx.NUM(0) != null && ctx.NUM(1) != null) {
                long rangeStart = Long.parseLong(ctx.NUM(0).getSymbol().getText());
                long rangeEnd = Long.parseLong(ctx.NUM(1).getSymbol().getText());
                if(rangeStart>rangeEnd) System.out.println("> Error: Incorrect array range definition");
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

    //To check for using undefined variable we need FOR and Identifier


    @Override
    public void enterUpfor(JFTTParser.UpforContext ctx) {
        String name = ctx.PIDENTIFIER().getText();
        int line = ctx.getToken(PIDENTIFIER,0).getSymbol().getLine();
        if(!symbolTable.containsKey(name)) System.out.println("> Error: Undefined variable: "+name+" in line: "+line);
    }

    @Override
    public void enterDownfor(JFTTParser.DownforContext ctx) {
        String name = ctx.PIDENTIFIER().getText();
        int line = ctx.getToken(PIDENTIFIER,0).getSymbol().getLine();
        if(!symbolTable.containsKey(name)) System.out.println("> Error: Undefined variable: "+name+" in line: "+line);
    }

    @Override
    public void enterIdentifier(JFTTParser.IdentifierContext ctx) {
        String name1 = ctx.PIDENTIFIER(0).getText();
        int line = ctx.getToken(PIDENTIFIER,0).getSymbol().getLine();

        if(!symbolTable.containsKey(name1)) System.out.println("> Error: Undefined variable: "+name1+" in line: "+line);
        String name2;
        if(ctx.PIDENTIFIER(1) != null) {
            name2 = ctx.PIDENTIFIER(1).getText();
            if(!symbolTable.containsKey(name2)) System.out.println("> Error: Undefined variable: "+name2+" in line: "+line);
        }


    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Symbol Table\n");
        int columnWidth = 8;
        for(String key : symbolTable.keySet()){
            stringBuilder.append("[ ").append(key);
            for(int i=0; i<columnWidth-key.length(); i++) stringBuilder.append(" ");
            stringBuilder.append(": ").append(" ").append(symbolTable.get(key).type.toString());
            if(symbolTable.get(key).type == IdentifierType.ARRAY){
                stringBuilder.append(" ").append(symbolTable.get(key).rangeStart).append(" ").append(symbolTable.get(key).rangeEnd);
            }
            stringBuilder.append(" ]\n");
        }
        return stringBuilder.toString();
    }

    private Boolean isDeclared(String name){
        return symbolTable.containsKey(name);
    }
}
