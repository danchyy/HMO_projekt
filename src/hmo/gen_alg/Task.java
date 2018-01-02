package hmo.gen_alg;

import java.util.Random;

public class Task {

    private byte[] array;

    private Integer name;

    private int length;

    private Integer[] availableMachines;

    private Integer[] availableResources;

    private Integer currentMachine;

    private Integer currentResource;

    private Random randomGenerator;

    public Task(Integer name, int length, Integer[] availableMachines, Integer[] availableResources) {
        this.name = name;
        this.length = length;
        this.availableMachines = availableMachines;
        this.availableResources = availableResources;
        this.currentMachine = availableMachines[0];
        this.currentResource = availableResources[0];
        this.randomGenerator = new Random();
    }

    public void selectRandomMachine() {
        int machineIndex = randomGenerator.nextInt(this.availableMachines.length);
        this.currentMachine = availableMachines[machineIndex];
    }

    public void selectRandomResource() {
        int resourceIndex = randomGenerator.nextInt(this.availableResources.length);
        this.currentResource = availableResources[resourceIndex];
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
