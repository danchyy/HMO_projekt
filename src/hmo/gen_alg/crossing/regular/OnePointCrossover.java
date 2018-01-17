package hmo.gen_alg.crossing.regular;

import jdk.jshell.spi.ExecutionControl;

import java.util.Random;

public class OnePointCrossover implements ArrayCrossing {
    @Override
    public int[] crossUnit(int[] firstUnit, int[] secondUnit) throws ExecutionControl.NotImplementedException {
        Random random = new Random();
        int breakingPoint = random.nextInt(firstUnit.length);
        int[] childArray = new int[firstUnit.length];
        for (int i=0; i<childArray.length; i++) {
            if (i < breakingPoint) {
                childArray[i] = firstUnit[i];
            } else {
                childArray[i] = secondUnit[i];
            }
        }
        return childArray;
    }
}
