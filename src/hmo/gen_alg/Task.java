package hmo.gen_alg;

import java.util.Random;

public class Task {

    private Integer name;

    private int length;

    private int[] availableMachines;

    private int[] requiredResources;

    private Random randomGenerator;

    public Task(Integer name, int length, int[] availableMachines, int[] requiredResources) {
        this.name = name;
        this.length = length;
        this.availableMachines = availableMachines;
        this.requiredResources = requiredResources;
        this.randomGenerator = new Random();
    }

    public int getRandomMachine() {
        return availableMachines[randomGenerator.nextInt(this.availableMachines.length)];
    }

    public int getRandomResource() {
        return requiredResources[randomGenerator.nextInt(this.requiredResources.length)];
    }

    public int getNumberOfMachines() {
        return availableMachines.length;
    }

    public int getNumberOfResources() {
        return requiredResources.length;
    }

    public int getLength() {
        return length;
    }

    public int[] getRequiredResources() {
        return requiredResources;
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
        for (Integer resource: requiredResources) {
            sb.append(resource);
            sb.append(",");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append("]");
        return sb.toString();
    }
}
