package compiler;

import org.antlr.v4.runtime.CharStream;

import java.util.HashMap;

public abstract class BaseArgParser {
    String outputFilename = "";
    String inputFilename = "";
    CharStream charStream;

    abstract void parse(String[] input);

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
