package compiler.ProgramArgumentParser;

import org.antlr.v4.runtime.CharStream;

public abstract class BaseArgParser {
    String outputFilename = "";
    String inputFilename = "";
    CharStream charStream;

    public abstract void parse(String[] input);

    public String getInputFilename() {
        return inputFilename;
    }

    public String getOutputFilename() {
        return outputFilename;
    }

    public CharStream getCharStream() {
        return charStream;
    }
}
