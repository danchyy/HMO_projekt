package hmo.io;

import hmo.gen_alg.Task;
import hmo.gen_alg.Unit;

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

    public List<Task> parseInputFile() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        int numberOfTests = -1;
        int numberOfMachines = -1;
        int numberOfResources = -1;
        List<Task> tasks = new ArrayList<>();
        List<Integer> allMachines = new ArrayList<>();
        List<Integer> allResources = new ArrayList<>();
        for (String line: lines) {
            if (line.startsWith("embedded_board")) {
                String machineString = line.split("\\(")[1].split("\\)")[0].trim();
                int machine = Integer.parseInt(machineString.substring(2, machineString.length()-1));
                allMachines.add(machine);
            } else if (line.startsWith("resource")) {
                String resourceString = line.split("\\(")[1].split(",")[0].trim();
                int resource = Integer.parseInt(resourceString.substring(2, resourceString.length()-1));
                allResources.add(resource);
            }
        }
        for (String line : lines) {
            if (line.startsWith("%")) {
                if (line.contains("tasks")) {
                    numberOfTests = Integer.parseInt(line.split(":")[1].trim());
                }
                if (line.contains("machines")) {
                    numberOfMachines = Integer.parseInt(line.split(":")[1].trim());
                }
                if (line.contains("resources")) {
                    numberOfResources = Integer.parseInt(line.split(":")[1].trim());
                }
            }
            else if (line.startsWith("task")) {
                line = line.substring(5).trim(); // to remove test(
                line = line.split("\\)")[0]; // to remove ).
                String[] splittedLine = line.split(",", 3);
                String testName = splittedLine[0].trim();
                int taskIndex = Integer.parseInt(testName.substring(2, testName.length()-1));
                int taskLength = Integer.parseInt(splittedLine[1].trim());
                String restOfLine = splittedLine[2].trim();
                String machineString = restOfLine.substring(restOfLine.indexOf('[')+1, restOfLine.indexOf(']')).trim();
                String[] availableMachinesString = machineString.split(",");
                int[] availableMachines = new int[availableMachinesString.length];
                if (availableMachinesString[0].isEmpty()) {
                    availableMachines = new int[allMachines.size()];
                    for (int i=0; i < allMachines.size(); i++) {
                        availableMachines[i] = allMachines.get(i);
                    }
                } else {
                    for (int i=0; i < availableMachinesString.length; i++) {
                        availableMachines[i] = Integer.parseInt(availableMachinesString[i].substring(2, availableMachinesString[i].length()-1));
                    }
                }


                String resourceString = restOfLine.split("\\[")[2];
                resourceString = resourceString.substring(0, resourceString.length()-1);
                String[] availableResourcesString = resourceString.split(",");
                int length = -1;
                if (availableResourcesString[0].trim().isEmpty()) {
                    length = 0;
                } else {
                    length = availableResourcesString.length;
                }
                int[] availableResources = new int[length];
                if (!availableResourcesString[0].trim().isEmpty()) {
                    for (int i=0; i < availableResourcesString.length; i++) {
                        availableResources[i] = Integer.parseInt(availableResourcesString[i].substring(2, availableResourcesString[i].length()-1));
                    }
                }
                tasks.add(new Task(taskIndex, taskLength, availableMachines, availableResources));
            }
        }
        return tasks;
    }

    public static void main(String[] args) throws IOException {
        InputReader reader = new InputReader("tests/test.txt");
        reader.parseInputFile();
    }


}
