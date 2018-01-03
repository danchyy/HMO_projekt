package hmo.gen_alg.crossing.regular;

import jdk.jshell.spi.ExecutionControl;

import java.util.Random;

public class UniformCrossing implements ArrayCrossing {

    @Override
    public int[] crossUnit(int[] firstUnit, int[] secondUnit) throws ExecutionControl.NotImplementedException {
        int[] childArray = new int[firstUnit.length];
        Random random = new Random();
        for (int i=0; i < firstUnit.length; i++) {
            double number = random.nextDouble();
            if (number < 0.5) {
                childArray[i] = firstUnit[i];
            } else {
                childArray[i] = secondUnit[i];
            }
        }
        return childArray;
    }
}
