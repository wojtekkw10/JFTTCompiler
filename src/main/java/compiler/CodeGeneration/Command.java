package compiler.CodeGeneration;

public class Command {
    CommandType command;
    long argument;

    Command(CommandType command, long argument){
        this.command = command;
        this.argument = argument;
    }
}
