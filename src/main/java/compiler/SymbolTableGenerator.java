package compiler;

import parser.JFTTBaseListener;
import parser.JFTTParser;

import java.util.HashMap;

public class SymbolTableGenerator extends JFTTBaseListener {
    HashMap<String, Symbol> symbolTable = new HashMap<>();

    @Override
    public void enterDeclarations(JFTTParser.DeclarationsContext ctx) {
        String name = ctx.PIDENTIFIER().getText();
        if(ctx.NUM(0) != null){
            long rangeStart = Long.parseLong( ctx.NUM(0).getSymbol().getText());
            long rangeEnd = Long.parseLong( ctx.NUM(1).getSymbol().getText());
            Symbol s = new Symbol(IdentifierType.ARRAY);
            s.setArray(rangeStart, rangeEnd);
            symbolTable.put(name, s);

        } else{
            symbolTable.put(name, new Symbol(IdentifierType.VARIABLE));
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
}
