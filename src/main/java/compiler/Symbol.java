package compiler;

public class Symbol {
    IdentifierType type;

    long value;

    long rangeStart;
    long rangeEnd;

    Symbol(IdentifierType type){
        this.type = type;
    }

    void setArray(long rangeStart, long rangeEnd){
        this.rangeStart = rangeStart;
        this.rangeEnd = rangeEnd;
    }


}
