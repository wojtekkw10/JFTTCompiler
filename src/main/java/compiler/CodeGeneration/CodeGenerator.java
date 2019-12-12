package compiler.CodeGeneration;

import compiler.GrammarParser.Symbol;
import parser.JFTTBaseListener;
import parser.JFTTParser;

import java.util.ArrayList;
import java.util.HashMap;

public class CodeGenerator extends JFTTBaseListener {
    public ArrayList<Command> generatedCode = new ArrayList<>();
    HashMap<String, Symbol> symbolTable = new HashMap<>();

    public CodeGenerator(HashMap<String, Symbol> symbolTable){
        this.symbolTable = symbolTable;
    }

    public ArrayList<Command> getGeneratedCode() {
        return generatedCode;
    }

    @Override
    public void enterCommand(JFTTParser.CommandContext ctx) {
        if(ctx.READ()!=null){
            String name = ctx.identifier().getText();
            generatedCode.add(new Command(CommandType.GET, 0));
            generatedCode.add(new Command(CommandType.STORE, symbolTable.get(name).location));
        }
    }
}
