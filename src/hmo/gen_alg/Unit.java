package hmo.gen_alg;

import hmo.gen_alg.fitnessCalculators.BasicFitnessEvaluator;
import hmo.gen_alg.fitnessCalculators.FitnessCalculator;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Unit implements Comparable{

    /**
     * Array which will hold the bits for genetic algorithm
     */
    private int[] array;

    /**
     * Array which tells the order of task completion
     */
    private int[] permutationArray;

    /**
     * Fitness of this unit
     */
    private double fitness;

    private boolean fitnessInit;

    /**
     * List of tasks so that this unit can compute
     */
    private List<Task> tasks;

    private FitnessCalculator fitnessCalculator;

    private void shufflePermutationArray() {
        Random random = new Random();
        for (int i=permutationArray.length - 1; i > 0; i--) {
            int randInt = random.nextInt(i+1);
            int temp = permutationArray[randInt];
            permutationArray[randInt] = permutationArray[i];
            permutationArray[i] = temp;
        }
    }

    public Unit(List<Task> tasks) {
        this.tasks = tasks;
        array = new int[tasks.size()];
        permutationArray = new int[tasks.size()];
        for (int i=0; i < permutationArray.length; i++) {
            permutationArray[i] = i;
        }
        shufflePermutationArray();
        for (int i=0; i < array.length; i++) {
            array[i] = tasks.get(i).getRandomMachine();
        }
        fitnessCalculator = new BasicFitnessEvaluator();
        fitnessInit = false;
    }

    public Unit(int[] array, int[] permutationArray, List<Task> tasks) {
        this.array = array;
        this.permutationArray = permutationArray;
        this.tasks = tasks;
        fitnessCalculator = new BasicFitnessEvaluator();
        fitnessInit = false;
    }


    public double calculateFitness() {
         fitness = fitnessCalculator.calculateFitness(array, permutationArray, tasks);
         return fitness;
    }

    public void evaluate() {
        List<String> runtimeInfo = fitnessCalculator.evaluate(array, permutationArray, tasks);
        for (String s : runtimeInfo) {
            System.out.println(s);
        }
    }

    public int[] getArray() {
        return array;
    }

    public int[] getPermutationArray() {
        return permutationArray;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Unit)) {
            return false;
        } else {
            Unit other = (Unit) obj;
            for (int i=0; i < array.length; i++) {
                if (this.array[i] != other.array[i] || this.permutationArray[i] != other.permutationArray[i]) {
                    return false;
                }
            }
            if (other.fitness - fitness > 1e-5) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int compareTo(Object o) {
        if (!(o instanceof Unit)){
            throw new IllegalArgumentException("Object must be of type Unit");
        }
        Unit other = (Unit) o;
        if ( this.fitness < other.fitness ) {
            return -1;
        } else if (this.fitness > other.fitness) {
            return 1;
        }
        return 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i < permutationArray.length; i++) {
            sb.append(permutationArray[i]);
            sb.append(" ");
        }
        return sb.toString();
    }
}
