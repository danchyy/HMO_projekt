package hmo.io;

import hmo.gen_alg.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class InputReader {

    private String filePath;

    public InputReader(String filePath) {
        this.filePath = filePath;
    }

    public void parseInputFile() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        int numberOfTests = -1;
        int numberOfMachines = -1;
        int numberOfResources = -1;
        List<Test> tests = new ArrayList<>();
        List<String> allMachines = new ArrayList<>();
        List<String> allResources = new ArrayList<>();
        for (String line: lines) {
            if (line.startsWith("embedded_board")) {
                allMachines.add(line.split("\\(")[1].split("\\)")[0].trim());
            } else if (line.startsWith("resource")) {
                allResources.add(line.split("\\(")[1].split(",")[0].trim()); // ignoring the availability
            }
        }
        for (String line : lines) {
            if (line.startsWith("%")) {
                if (line.contains("tests")) {
                    numberOfTests = Integer.parseInt(line.split(":")[1].trim());
                }
                if (line.contains("machines")) {
                    numberOfMachines = Integer.parseInt(line.split(":")[1].trim());
                }
                if (line.contains("resources")) {
                    numberOfResources = Integer.parseInt(line.split(":")[1].trim());
                }
            }
            else if (line.startsWith("test")) {
                line = line.substring(5).trim(); // to remove test(
                line = line.split("\\)")[0]; // to remove ).
                String[] splittedLine = line.split(",", 3);
                String testName = splittedLine[0].trim();
                int testLength = Integer.parseInt(splittedLine[1].trim());
                String restOfLine = splittedLine[2].trim();
                String machineString = restOfLine.substring(restOfLine.indexOf('[')+1, restOfLine.indexOf(']')).trim();
                String[] availableMachines = machineString.split(",");
                if (availableMachines.length == 0) {
                    availableMachines = allMachines.toArray(new String[0]);
                }
                String resourceString = restOfLine.split("\\[")[2];
                resourceString = resourceString.substring(0, resourceString.length()-1);
                String[] availableResources = resourceString.split(",");
                if (availableResources.length == 0) {
                    availableResources = allResources.toArray(new String[0]);
                }
                tests.add(new Test(testName, testLength, availableMachines, availableResources));
            }
        }
        for (Test test: tests) {
            System.out.println(test);
        }
    }

    public static void main(String[] args) throws IOException {
        InputReader reader = new InputReader("tests/ts1.txt");
        reader.parseInputFile();
    }


}
