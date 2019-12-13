package compiler.GrammarParser;

public class Symbol{
    IdentifierType type;
    String name;
    int additionalArraySpace = 3;

    long value;
    Boolean isArray;

    long rangeStart;
    long rangeEnd;

    private Boolean isDefined;

    public long location;

    public Symbol(IdentifierType type, String name){
        this.name = name;
        this.type = type;
        this.isDefined = false;
        if(IdentifierType.ARRAY == type) isArray = true;
        else isArray = false;
        location = -1;
    }

    public void setArray(long rangeStart, long rangeEnd){
        this.rangeStart = rangeStart;
        this.rangeEnd = rangeEnd;
    }

    public Boolean isArray(){
        return isArray;
    }

    public long getRangeLength(){
        return rangeEnd - rangeStart + 1;
    }

    public long getRangeEnd() {
        return rangeEnd;
    }

    public long getRangeStart() {
        return rangeStart;
    }

    public String getName() {
        return name;
    }

    public int getAdditionalArraySpace() {
        return additionalArraySpace;
    }

    public void setName(String name) {
        this.name = name;
    }
}
