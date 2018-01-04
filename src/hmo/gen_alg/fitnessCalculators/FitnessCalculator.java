package hmo.gen_alg.fitnessCalculators;

import hmo.gen_alg.Task;

import java.util.List;

public interface FitnessCalculator {

    int MACHINE_INDEX = 0;
    int START_TIME_INDEX = 1;
    int END_INDEX = 2;


    public double calculateFitness(int[] taskArray, int[] permutationArray, List<Task> tasks);

    /**
     * Prints the output of running of tests along with fitness calculation
     * @param taskArray
     * @param permutationArray
     * @param tasks
     * @return
     */
    public List<String> evaluate(int[] taskArray, int[] permutationArray, List<Task> tasks);

}
