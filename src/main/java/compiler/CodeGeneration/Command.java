package compiler.CodeGeneration;

public class Command {
    CommandType command;
    long argument;
    String comment;

    Command(CommandType command, long argument){
        this.command = command;
        this.argument = argument;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
