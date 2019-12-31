package compiler.CodeGeneration;

public class Command {
    public CommandType command;
    public long argument;
    String comment;

    Command(CommandType command, long argument){
        this.command = command;
        this.argument = argument;
    }

    Command(CommandType command, long argument, String comment){
        this.command = command;
        this.argument = argument;
        this.comment = comment;
    }

}
