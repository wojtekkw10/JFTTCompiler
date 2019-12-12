package compiler;

public class Error {
    int line;
    String message;

    Error(int line, String message){
        this.line = line;
        this.message = message;
    }

    @Override
    public String toString() {
        return "> Error: "+message+" in line "+line;
    }
}
