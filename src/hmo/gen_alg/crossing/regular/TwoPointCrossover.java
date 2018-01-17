package hmo.gen_alg.crossing.regular;

import jdk.jshell.spi.ExecutionControl;

import java.util.Random;

public class TwoPointCrossover implements ArrayCrossing {

    @Override
    public int[] crossUnit(int[] firstUnit, int[] secondUnit) throws ExecutionControl.NotImplementedException {
        Random random = new Random();
        int breakingPoint = random.nextInt(firstUnit.length);
        while (breakingPoint >= 4 * firstUnit.length / 5) {
            breakingPoint = random.nextInt(firstUnit.length);
        }
        int secondBreakingPoint = random.nextInt(firstUnit.length);
        while (secondBreakingPoint <= breakingPoint ) {
            secondBreakingPoint = random.nextInt(firstUnit.length);
        }
        int[] childArray = new int[firstUnit.length];
        for (int i=0; i<childArray.length; i++) {
            if (i < breakingPoint || i >= secondBreakingPoint) {
                childArray[i] = firstUnit[i];
            } else {
                childArray[i] = secondUnit[i];
            }
        }
        return childArray;
    }
}
