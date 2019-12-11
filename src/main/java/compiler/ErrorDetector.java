package compiler;

import parser.JFTTBaseListener;

import java.util.HashMap;

public abstract class ErrorDetector extends JFTTBaseListener {
    HashMap<String, Symbol> symbolTable;

    public HashMap<String, Symbol> getSymbolTable() {
        return symbolTable;
    }

    public void setSymbolTable(HashMap<String, Symbol> symbolTable) {
        this.symbolTable = symbolTable;
    }
}
