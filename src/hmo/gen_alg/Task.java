package hmo.gen_alg;

import java.util.Random;

public class Task {

    private Integer name;

    private int length;

    private Integer[] availableMachines;

    private Integer[] availableResources;

    private Random randomGenerator;

    public Task(Integer name, int length, Integer[] availableMachines, Integer[] availableResources) {
        this.name = name;
        this.length = length;
        this.availableMachines = availableMachines;
        this.availableResources = availableResources;
        this.randomGenerator = new Random();
    }

    public int getRandomMachine() {
        return availableMachines[randomGenerator.nextInt(this.availableMachines.length)];
    }

    public int getRandomResource() {
        return availableResources[randomGenerator.nextInt(this.availableResources.length)];
    }

    public int getNumberOfMachines() {
        return availableMachines.length;
    }

    public int getNumberOfResources() {
        return availableResources.length;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.name + ", ");
        sb.append(this.length);
        sb.append(", ");
        sb.append("[");
        for (Integer machine: availableMachines) {
            sb.append(machine);
            sb.append(",");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append("], [");
        for (Integer resource: availableResources) {
            sb.append(resource);
            sb.append(",");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append("]");
        return sb.toString();
    }
}
