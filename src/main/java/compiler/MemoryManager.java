package compiler;

import compiler.GrammarParser.IdentifierType;
import compiler.GrammarParser.Symbol;

import java.util.*;

public class MemoryManager {
    ArrayList<Symbol> memory = new ArrayList<>();

    public void removeVariable(Symbol s){
        this.memory.remove(s);
    }

    public Symbol getFreeSpace(){
        long current_index = 1;
        Symbol s = new Symbol(IdentifierType.VARIABLE, "tmp");

        for(Symbol symbol: memory){
            if(symbol.location != -1){
                if(current_index==symbol.location) {
                    if(symbol.isArray()) current_index+= symbol.getRangeLength() + symbol.getAdditionalArraySpace();
                    else current_index++;
                }
                else {
                    s.location = current_index;
                    memory.add(s);
                    sort();
                    return s;
                }
            }

        }
        s.location = current_index;
        memory.add(s);
        sort();
        return s;
    }

    public void allocate(Symbol s){
        long current_index = 1;

        for(Symbol symbol: memory){
            if(symbol.location != -1){
                if(current_index==symbol.location) {
                    if(symbol.isArray()) current_index+= symbol.getRangeLength() + symbol.getAdditionalArraySpace();
                    else current_index++;
                }
                else break;
            }

        }
        s.location = current_index;
        memory.add(s);
        sort();

    }

    private void sort(){
        //Sorting by location
        memory.sort(new Comparator<Symbol>() {
            @Override
            public int compare(Symbol s1, Symbol s2) {
                if (s1.location == s2.location) return 1;
                else if (s1.location > s2.location) return 1;
                else return -1;
            }
        });

        //System.out.println(printMemory());
    }

    public String printMemory(){
        long last_memory_location = 0;
        StringBuilder stringBuilder = new StringBuilder();
        for(Symbol symbol: memory){

            while(last_memory_location<symbol.location){
                stringBuilder.append("_ ");
                last_memory_location++;
            }

            if(symbol.isArray()){
                stringBuilder.append("[").append(symbol.getName()).append(":").append(symbol.location).append(" ");
                long length = symbol.getRangeLength() - 1; //because 1 is already taken by the array name
                for(int i=0; i<length; i++){
                    stringBuilder.append("_ ");
                }
                for(int i=0; i<symbol.getAdditionalArraySpace(); i++){
                    stringBuilder.append("$ ");
                }
                stringBuilder.append("] ");
                last_memory_location+= symbol.getRangeLength()+ symbol.getAdditionalArraySpace();
            }
            else {
                stringBuilder.append(symbol.getName()+":"+symbol.location).append(" ");
                last_memory_location++;
            }
        }
        stringBuilder.append("\nWARNING: Array name represents the first memory cell\n");
        return stringBuilder.toString();
    }
}


/*MEMORY STRUCTURE
0       - accumulator
1:64    - powers of 2
65:100  - working memory
101:    - variables/arrays
 */

/*
ARRAY STRUCTURE
data
rangeStart
rangeEnd
location
 */