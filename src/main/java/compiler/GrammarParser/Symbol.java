package compiler.GrammarParser;

public class Symbol {
    IdentifierType type;

    long value;
    Boolean isArray;

    long rangeStart;
    long rangeEnd;

    private Boolean isDefined;

    public int location;

    Symbol(IdentifierType type){
        this.type = type;
        this.isDefined = false;
        if(IdentifierType.ARRAY == type) isArray = true;
        else isArray = false;
    }

    void setArray(long rangeStart, long rangeEnd){
        this.rangeStart = rangeStart;
        this.rangeEnd = rangeEnd;
    }

    public Boolean isArray(){
        return isArray;
    }

    public long getRangeLength(){
        return rangeEnd - rangeStart + 1;
    }


}
