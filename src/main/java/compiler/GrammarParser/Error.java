package compiler.GrammarParser;

public class Error {
    int line;
    public String message;

    Error(int line, String message){
        this.line = line;
        this.message = message;
    }

    @Override
    public String toString() {
        return "> Error: "+message+" in line "+line;
    }
}
