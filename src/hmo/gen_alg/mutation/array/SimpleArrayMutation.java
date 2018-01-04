package hmo.gen_alg.mutation.array;

import hmo.gen_alg.Task;

import java.util.List;
import java.util.Random;

public class SimpleArrayMutation implements ArrayMutation {

    private double mutationProb;

    public SimpleArrayMutation(double mutationProb) {
        this.mutationProb = mutationProb;
    }

    public SimpleArrayMutation() {
        this.mutationProb = 0.15;
    }

    @Override
    public int[] mutateArray(int[] targetArray, List<Task> tasks) {
        Random random = new Random();
        for (int i=0; i < targetArray.length; i++) {
            if (tasks.get(i).getNumberOfMachines() == 1) {
                continue;
            }
            double condition = random.nextDouble();
            if (condition < this.mutationProb) {
                while (true) {
                    int randomMachine = tasks.get(i).getRandomMachine();
                    if (randomMachine != targetArray[i]) {
                        targetArray[i] = randomMachine;
                        break;
                    }
                }
            }
        }
        return targetArray;
    }
}
