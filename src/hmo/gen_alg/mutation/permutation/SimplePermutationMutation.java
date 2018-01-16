package hmo.gen_alg.mutation.permutation;

import java.util.Random;

/**
 * Permutation which swaps 1st with last, 2nd with 2nd to last, etc.. if condition for mutation is satisfied
 */
public class SimplePermutationMutation extends PermutationMutation {

    private double mutationProb;

    public SimplePermutationMutation(double shouldMutate) {
        this.mutationProb = shouldMutate;
    }

    public SimplePermutationMutation() {
        this.mutationProb = 0.6;
    }

    @Override
    public int[] mutatePermutationArray(int[] targetArray) {
        Random random = new Random();
        int totalLength = targetArray.length;
        for (int i=0; i < totalLength / 2; i++) {
            double condition = random.nextDouble();
            if (condition < mutationProb) {
                int temp = targetArray[totalLength-i-1];
                targetArray[totalLength-i-1] = targetArray[i];
                targetArray[i] = temp;
            }
        }
        return targetArray;
    }
}
