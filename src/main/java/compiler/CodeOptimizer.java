package compiler;

import compiler.CodeGeneration.Command;
import compiler.CodeGeneration.CommandType;

import java.util.ArrayList;

public class CodeOptimizer {

    static public void optimize(ArrayList<Command> generatedCode, int numberOfLines){
        System.out.println("OPTIMIZER");

        for(int i=generatedCode.size()-numberOfLines; i<generatedCode.size(); i++){
            Command command = generatedCode.get(i);
            Command lastCommand = generatedCode.get(i-1);
            if(lastCommand.command == CommandType.STORE && command.command == CommandType.LOAD && command.argument == lastCommand.argument){
                // STORE 14
                // LOAD 14 - unnecessary
                generatedCode.remove(i);
                i--;
            }
        }
    }
}
