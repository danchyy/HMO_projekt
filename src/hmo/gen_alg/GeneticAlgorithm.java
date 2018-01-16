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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class GeneticAlgorithm {

    public static final int DEFAULT_POP_SIZE = 200;
    public static final int DEFAULT_GENERATIONS = 2000000;
    public static final int LOG_EVERY_N = 30000;
    public static final int CHANGE_MUTATION = 400000;
    public static final double MUTATION_FACTOR = 0.8;

    public static String DESINATION_PATH = "results/";

    public static int evaluationCounter = 0;

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
        evaluationCounter = 0;
        population = new ArrayList<>();
        for (int i=0;  i < populationSize; i++) {
            Unit unit = new Unit(tasks);
            unit.calculateFitness();
            population.add(unit);
        }
    }

    private void writeOutput(String path, List<String> lines, String time) {
        String instanceName = path.split("/")[1].split(".txt")[0];
        // res-vrijeme-instanca.txt“
        String destination = DESINATION_PATH + instanceName + "/res-" + time + "-" + instanceName + ".txt";
        try {
            Files.write(Paths.get(destination), lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run(String path) throws ExecutionControl.NotImplementedException {
        long startTime = System.currentTimeMillis();
        long elapsedTime = 0L;
        int i=0;
        boolean oneMinuteReached = false;
        boolean fiveMinutesReached = false;
        List<String> fitnessEvaluations = new ArrayList<>();
        while (true) {
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

            if (i % LOG_EVERY_N == 0) {
                Collections.sort(population);
                // Collections.reverse(population);
                double fitness = population.get(0).calculateFitness();
                System.out.println("Iteration " + i + ", best population fitness: " + fitness);

            }
            i++;

            if (i % CHANGE_MUTATION == 0) {
                arrayMutation.adjustMutationProb(MUTATION_FACTOR);
                permutationMutation.adjustMutationProb(MUTATION_FACTOR);
            }

            elapsedTime = (new Date()).getTime() - startTime;
            if (elapsedTime >= 60 * 1000 && !oneMinuteReached) {
                Collections.sort(population);
                Unit topUnit = population.get(0);
                System.out.println("Results at 1 minute mark, best population fitness: " + topUnit.calculateFitness());
                System.out.println("Count of evaluations: " + evaluationCounter);
                fitnessEvaluations.add(String.valueOf(evaluationCounter));
                oneMinuteReached = true;
                List<String> units = topUnit.evaluate(false);
                writeOutput(path, units, "1m");

            }
            if (elapsedTime >= 5 * 60 * 1000 && !fiveMinutesReached) {
                Collections.sort(population);
                Unit topUnit = population.get(0);
                System.out.println("Results at 5 minute mark, best population fitness: " + topUnit.calculateFitness());
                System.out.println("Count of evaluations: " + evaluationCounter);
                fitnessEvaluations.add(String.valueOf(evaluationCounter));
                fiveMinutesReached = true;
                topUnit.evaluate(false);
                List<String> units = topUnit.evaluate(false);
                writeOutput(path, units, "5m");
            }
            if (i == DEFAULT_GENERATIONS) {
                Collections.sort(population);
                Unit topUnit = population.get(0);
                System.out.println("Results at " + DEFAULT_GENERATIONS + ", best population fitness: " + topUnit.calculateFitness());
                System.out.println("Count of evaluations: " + evaluationCounter);
                fitnessEvaluations.add(String.valueOf(evaluationCounter));
                topUnit.evaluate(false);
                List<String> units = topUnit.evaluate(false);
                writeOutput(path, units, "ne");
                break;
            }
        }
        String instanceName = path.split("/")[1].split(".txt")[0];
        try {
            Files.write(Paths.get("results/evals/" + instanceName + "-evals.txt"), fitnessEvaluations);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException, ExecutionControl.NotImplementedException {
        for (int i=1; i < 11; i++) {
            String instanceName = "ts" + i;
            String fileName = "tests/" + instanceName + ".txt";
            System.out.println("Starting with " + fileName);
            InputReader reader = new InputReader(fileName);
            List<Task> tasks = reader.parseInputFile();
            GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(tasks);
            geneticAlgorithm.run(fileName);
        }
    }
}
