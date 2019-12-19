package compiler.GrammarParser;

import parser.JFTTBaseListener;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class ErrorDetector extends JFTTBaseListener {
    HashMap<String, Symbol> symbolTable;
    ArrayList<Error> errors = new ArrayList<>();
    long largestNumber = 0;

    public HashMap<String, Symbol> getSymbolTable() {
        return symbolTable;
    }

    public void setSymbolTable(HashMap<String, Symbol> symbolTable) {
        this.symbolTable = symbolTable;
    }

    void addError(Error e){
        errors.add(e);
    }

    public ArrayList<Error> getErrors() {
        return errors;
    }
}
