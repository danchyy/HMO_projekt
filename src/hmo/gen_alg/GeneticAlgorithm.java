package hmo.gen_alg;

import hmo.gen_alg.crossing.permutation.PermutationCrossing;
import hmo.gen_alg.crossing.permutation.PositionBasedCrossing;
import hmo.gen_alg.crossing.regular.ArrayCrossing;
import hmo.gen_alg.crossing.regular.UniformCrossing;
import hmo.gen_alg.mutation.array.ArrayMutation;
import hmo.gen_alg.mutation.array.SimpleArrayMutation;
import hmo.gen_alg.mutation.permutation.PermutationMutation;
import hmo.gen_alg.mutation.permutation.SimplePermutationMutation;
import hmo.gen_alg.selection.KTournamentSelection;
import hmo.gen_alg.selection.Selection;
import hmo.io.InputReader;
import jdk.jshell.spi.ExecutionControl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GeneticAlgorithm {

    public static final int DEFAULT_POP_SIZE = 100;
    public static final int DEFAULT_GENERATIONS = 500000;
    public static final int LOG_EVERY_N = 1000;

    private PermutationMutation permutationMutation;
    private PermutationCrossing permutationCrossing;

    private ArrayMutation arrayMutation;
    private ArrayCrossing arrayCrossing;

    private List<Task> tasks;

    private Selection selection;

    private int populationSize;

    private int generations;

    private List<Unit> population;

    public GeneticAlgorithm(List<Task> tasks) {
        this(new SimplePermutationMutation(), new PositionBasedCrossing(), new SimpleArrayMutation(), new UniformCrossing(), tasks, new KTournamentSelection(), DEFAULT_POP_SIZE, DEFAULT_GENERATIONS);
    }

    public GeneticAlgorithm(PermutationMutation permutationMutation, PermutationCrossing permutationCrossing, ArrayMutation arrayMutation, ArrayCrossing arrayCrossing, List<Task> tasks, Selection selection, int populationSize, int generations) {
        this.permutationMutation = permutationMutation;
        this.permutationCrossing = permutationCrossing;
        this.arrayMutation = arrayMutation;
        this.arrayCrossing = arrayCrossing;
        this.tasks = tasks;
        this.selection = selection;
        this.populationSize = populationSize;
        this.generations = generations;

        init();
    }

    private void init() {
        population = new ArrayList<>();
        for (int i=0;  i < populationSize; i++) {
            Unit unit = new Unit(tasks);
            unit.calculateFitness();
            population.add(unit);
        }
    }

    public void run() throws ExecutionControl.NotImplementedException {
        for (int i=0; i < generations; i++) {
            // Tournament is sorted
            List<Unit> tournament = selection.selectUnit(population);

            Unit bestUnit = tournament.get(0);
            Unit secondBestUnit = tournament.get(1);

            Unit worstUnit = tournament.get(tournament.size()-1);

            // GETTING THE TASK ARRAY FOR CHILD
            int[] childArray = arrayCrossing.crossUnit(bestUnit.getArray(), secondBestUnit.getArray());

            // GETTING THE PERMUTATION ARRAY FOR CHILD
            int[] childPermutationArray = permutationCrossing.crossUnit(bestUnit.getPermutationArray(), secondBestUnit.getPermutationArray());

            // MUTATING THE ARRAY OF TASKS
            childArray = arrayMutation.mutateArray(childArray, tasks);

            // MUTATING THE PERMUTATION ARRAY
            childPermutationArray = permutationMutation.mutatePermutationArray(childPermutationArray);

            // Calculating fitness
            Unit childUnit = new Unit(childArray, childPermutationArray, tasks);
            childUnit.calculateFitness();

            population.remove(worstUnit);

            population.add(childUnit);

            if (i > 0 && i % LOG_EVERY_N == 0) {
                Collections.sort(population);
                // Collections.reverse(population);
                double fitness = population.get(0).calculateFitness();
                System.out.println("Iteration " + i + ", best population fitness: " + fitness);

            }
        }
        Collections.sort(population);
        Unit bestUnit = population.get(0);
        System.out.println("Iteration " + generations + ", best population fitness: " + bestUnit.calculateFitness());
        bestUnit.evaluate();

    }

    public static void main(String[] args) throws IOException, ExecutionControl.NotImplementedException {
        InputReader reader = new InputReader("tests/test2.txt");
        List<Task> tasks = reader.parseInputFile();
        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(tasks);
        geneticAlgorithm.run();
    }
}
