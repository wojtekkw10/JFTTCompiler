package compiler.ProgramArgumentParser;

import org.antlr.v4.runtime.CharStreams;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class ArgParser extends BaseArgParser {

    @Override
    public boolean parse(String[] args) {
        String inputFilename;
        String outputFilename;

        //Check if there is enough arguments
        if(args.length>1){
            inputFilename = args[0];
            outputFilename = args[1];
        } else {
            System.err.println("> Error: Not enough parameters");
            return false;
        }

        //Check if the arguments are not empty
        HashMap<String, String> result;
        if( inputFilename.isEmpty() || outputFilename.isEmpty() ) {
            System.err.println("> Error: Empty parameters");
            return false;
        }
        else {
            //Check if input file exists
            File inputFile = new File(inputFilename);
            if( !inputFile.exists() )  {
                System.err.println("> Error: Input file does not exist");
                return false;
            }
            else {
                //If everything ok then return filenames
                this.inputFilename = inputFilename;
                this.outputFilename = outputFilename;
                try {
                    this.charStream = CharStreams.fromFileName(inputFilename);
                } catch (IOException e) {
                    System.err.println("> Error: Couldn't load the input file");
                    e.printStackTrace();
                    return false;
                }
            }
        }
        return true;
    }
}
