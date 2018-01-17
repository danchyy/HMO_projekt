package hmo.gen_alg.crossing.permutation;

import hmo.gen_alg.Task;
import hmo.gen_alg.Unit;
import hmo.io.InputReader;
import jdk.jshell.spi.ExecutionControl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OrderCrossover2 implements PermutationCrossing {
    @Override
    public int[] crossUnit(int[] firstUnit, int[] secondUnit) throws ExecutionControl.NotImplementedException {
        Random random = new Random();
        int firstBreakPoint = random.nextInt(firstUnit.length);
        while (firstBreakPoint + 10 >= firstUnit.length) {
            firstBreakPoint = random.nextInt(firstUnit.length);
        }
        int secondBreakPoint = random.nextInt(secondUnit.length);
        while (secondBreakPoint < firstBreakPoint + 3) {
            secondBreakPoint = random.nextInt(secondUnit.length);
        }
        int[] childArray = new int[firstUnit.length];
        List<Integer> added = new ArrayList<>();
        for (int i=firstBreakPoint; i < secondBreakPoint; i++) {
            childArray[i] = firstUnit[i];
            added.add(firstUnit[i]);
        }
        int childCounter = secondBreakPoint;
        for (int i=secondBreakPoint; i < firstUnit.length; i++) {
            if (!added.contains(secondUnit[i])) {
                childArray[childCounter] = secondUnit[i];
                childCounter++;
                added.add(secondUnit[i]);
            }
        }
        for (int i=0; i <secondBreakPoint; i++) {
            if (!added.contains(secondUnit[i])) {
                if (childCounter == firstUnit.length) {
                    childCounter = 0;
                }
                childArray[childCounter] = secondUnit[i];
                childCounter++;
                added.add(secondUnit[i]);
            }
        }
        return childArray;
    }

    @Override
    public List<int[]> crossUnitTwoChildren(int[] firstUnit, int[] secondUnit) throws ExecutionControl.NotImplementedException {
        return null;
    }

    public static void main(String[] args) throws IOException, ExecutionControl.NotImplementedException {
        InputReader reader = new InputReader("tests/test.txt");
        List<Task> tasks = reader.parseInputFile();
        for (int i=0; i < 10000; i++) {
            Unit first = new Unit(tasks);
            Unit second = new Unit(tasks);
            OrderCrossover2 cross = new OrderCrossover2();
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
