package compiler.CodeGeneration;

import compiler.GrammarParser.ParserManager;
import compiler.GrammarParser.UndeclaredVariableErrorDetector;
import compiler.GrammarParser.VariableRedefinitionErrorDetector;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.Test;
import parser.JFTTLexer;
import parser.JFTTParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class FullCodeGenerationTest {

    public void testCodeGenerationForProgram(String programPath, String expectedResultPath, String inputPath) {

        try {

            Process p2 = Runtime.getRuntime().exec(new String[]{"bash","-c","java -jar build/libs/compiler-all.jar " +
                    programPath + " src/test/java/compiler/CodeGeneration/machineCode"});
            p2.waitFor();

            String command = "vm/maszyna-wirtualna-cln src/test/java/compiler/CodeGeneration/machineCode" +
                    ">src/test/java/compiler/CodeGeneration/vmOutput ";
            if(!inputPath.equals("")) command += "<"+ inputPath;

            Process p = Runtime.getRuntime().exec(new String[]{"bash","-c", command});
            p.waitFor(5, TimeUnit.SECONDS);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        ArrayList<Integer> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("src/test/java/compiler/CodeGeneration/vmOutput")))
        {
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null)
            {
                // if line contains a result, remove the '> ' and add to lines
                if(sCurrentLine.startsWith(">"))
                    lines.add(Integer.parseInt(sCurrentLine.substring(2)));
                if(sCurrentLine.startsWith("? >"))
                    lines.add(Integer.parseInt(sCurrentLine.substring(4)));
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        ArrayList<Integer> expectedlines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(expectedResultPath)))
        {
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null)
            {
                // if line contains a result, remove the '> ' and add to lines
                expectedlines.add(Integer.parseInt(sCurrentLine));
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        assertEquals(expectedlines, lines);
    }

    @Test
    public void codeGenerationCorrectnessFunctionalTest() {

        try {
            Process p = Runtime.getRuntime().exec(new String[]{"bash","-c","make shadowjar"});
            p.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        testCodeGenerationForProgram("src/test/java/compiler/CodeGeneration/ultimateTest", "src/test/java/compiler/CodeGeneration/ultimateTestExpectedResult", "");
        testCodeGenerationForProgram("programs/p1.imp", "programs/p1Expected", "");
        testCodeGenerationForProgram("programs/program0.imp", "programs/program0Expected", "programs/program0Input");
        testCodeGenerationForProgram("programs/program1.imp", "programs/program1Expected", "");
        testCodeGenerationForProgram("programs/program2.imp", "programs/program2Expected", "programs/program2Input");

        try {
            Process p = Runtime.getRuntime().exec(new String[]{"bash","-c","rm src/test/java/compiler/CodeGeneration/machineCode"});
            Process p2 = Runtime.getRuntime().exec(new String[]{"bash","-c","rm src/test/java/compiler/CodeGeneration/vmOutput"});
            p.waitFor();
            p2.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
