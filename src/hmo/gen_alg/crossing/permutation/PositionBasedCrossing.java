package hmo.gen_alg.crossing.permutation;

import hmo.gen_alg.Task;
import hmo.gen_alg.Unit;
import hmo.io.InputReader;
import jdk.jshell.spi.ExecutionControl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * PermutationCrossing which determines which genes will be taken for child in first parent firstly. After that it takes
 * random genes from second parent which arent taken, and in the end it fills the child with genes from first parent.
 * Uniform crossing is in charge of crossing the task array.
 */
public class PositionBasedCrossing implements PermutationCrossing {


    @Override
    public int[] crossUnit(int[] firstUnit, int[] secondUnit) {
        Random random = new Random();
        int[] childPermutation = new int[firstUnit.length];

        List<Integer> addedTasks = new ArrayList<>();
        int[] addedIndexes = new int[firstUnit.length];
        for (int i=0; i < firstUnit.length; i++) {
            int randNumber = random.nextInt(2);
            if (randNumber == 0) {
                int taskIndex = firstUnit[i];
                childPermutation[i] = taskIndex;
                addedTasks.add(taskIndex);
                addedIndexes[i] = 1;
            }
        }
        List<Integer> tasksToAdd = new ArrayList<>();
        for (int i=0; i < firstUnit.length; i++) {
            int taskIndex = secondUnit[i];
            if (!addedTasks.contains(taskIndex)) {
                tasksToAdd.add(taskIndex);
            }
        }
        for (int i=0; i < childPermutation.length; i++) {
            if (addedIndexes[i] != 1) {
                childPermutation[i] = tasksToAdd.remove(0);
            }
        }

        return childPermutation;
    }

    /**
     * Not implemented
     * @param firstUnit First unit for crossing
     * @param secondUnit Second unit for crossing
     * @return
     */
    @Override
    public List<int[]> crossUnitTwoChildren(int[] firstUnit, int[] secondUnit) throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("This method isn't implemented");
    }

    public static void main(String[] args) throws IOException {
        InputReader reader = new InputReader("tests/ts1.txt");
        List<Task> tasks = reader.parseInputFile();
        for (int i=0; i < 10000; i++) {
            Unit first = new Unit(tasks);
            Unit second = new Unit(tasks);
            PositionBasedCrossing cross = new PositionBasedCrossing();
            int[] child = cross.crossUnit(first.getPermutationArray(), second.getPermutationArray());
//            System.out.println("First parent");
//            System.out.println(first);
//            System.out.println("Second parent");
//            System.out.println(second);
//            System.out.println("Child");
            StringBuilder sb = new StringBuilder();
            List<Integer> indexes = new ArrayList<>();
            for (int j = 0; j < child.length; j++) {
                sb.append(child[j]);
                sb.append(" ");
                if (!indexes.contains(child[j])) {
                    indexes.add(child[j]);
                } else {
                    System.out.println("FOUND DUPLICATE");
                }
            }

//            System.out.println(sb.toString());
        }
    }

}
