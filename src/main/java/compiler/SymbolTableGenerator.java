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
        //declarations',' PIDENTIFIER
        //declarations',' PIDENTIFIER'('NUM':'NUM')'

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

    //To check for using undefined variable we need FOR and Identifier

    //Sprawdzenie 'FOR' PIDENTIFIER 'FROM' value 'TO' value 'DO' commands 'ENDFOR';
    @Override
    public void enterUpfor(JFTTParser.UpforContext ctx) {
        //Niezdefiniowana zmienna
        String name = ctx.PIDENTIFIER().getText();
        //int line = ctx.getToken(PIDENTIFIER,0).getSymbol().getLine();
        //if(!symbolTable.containsKey(name)) System.out.println("> Error: Undefined variable: "+name+" in line: "+line);
        symbolTable.put(name, new Symbol(IdentifierType.VARIABLE));

        //Niewłaściwe użycie zmiennej
        //if(symbolTable.get(name).isArray()) System.out.println("> Error: Array used as a variable: "+name+" in line: "+line);

    }

    @Override
    public void exitUpfor(JFTTParser.UpforContext ctx) {
        String name = ctx.PIDENTIFIER().getText();
        symbolTable.remove(name);
    }

    //Sprawdzenie 'FOR' PIDENTIFIER 'FROM' value 'DOWNTO' value 'DO' commands 'ENDFOR';
    @Override
    public void enterDownfor(JFTTParser.DownforContext ctx) {
        //Niezdefiniowana zmienna - iterator moze byc niezdefiniowany
        String name = ctx.PIDENTIFIER().getText();
        //int line = ctx.getToken(PIDENTIFIER,0).getSymbol().getLine();
        //if(!symbolTable.containsKey(name)) System.out.println("> Error: Undefined variable: "+name+" in line: "+line);
        symbolTable.put(name, new Symbol(IdentifierType.VARIABLE));

        //Niewłaściwe użycie zmiennej
        //if(symbolTable.get(name).isArray()) System.out.println("> Error: Array used as a variable: "+name+" in line: "+line);
    }

    @Override
    public void exitDownfor(JFTTParser.DownforContext ctx) {
        String name = ctx.PIDENTIFIER().getText();
        symbolTable.remove(name);
    }

    //Sprawdzenie wyrażeń
    //PIDENTIFIER
    //PIDENTIFIER'('PIDENTIFIER')'
    @Override
    public void enterIdentifier(JFTTParser.IdentifierContext ctx) {
        //Niezdefiniowana zmienna
        String name1 = ctx.PIDENTIFIER(0).getText();
        int line = ctx.getToken(PIDENTIFIER,0).getSymbol().getLine();
        if(!symbolTable.containsKey(name1)) System.out.println("> Error: Undefined variable: "+name1+" in line: "+line);

        if(ctx.PIDENTIFIER(1) == null && symbolTable.get(name1).isArray())  System.out.println("> Error: Array used as a variable: "+name1+" in line: "+line);

        String name2;
        if(ctx.PIDENTIFIER(1) != null) {
            name2 = ctx.PIDENTIFIER(1).getText();
            if(!symbolTable.containsKey(name2)) System.out.println("> Error: Undefined variable: "+name2+" in line: "+line);

            //Niewłaściwe użycie zmiennej
            if(!symbolTable.get(name1).isArray()) System.out.println("> Error: Variable used as an array: "+name1+" in line: "+line);
        }


    }




    //Funkcje pomocnicze
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

    private Boolean isArray(JFTTParser.DeclarationsContext ctx){
        return (ctx.NUM(0) != null && ctx.NUM(1) != null);
    }
    private Long getRange(JFTTParser.DeclarationsContext ctx, int i){
        return Long.parseLong(ctx.NUM(i).getSymbol().getText());
    }
}
