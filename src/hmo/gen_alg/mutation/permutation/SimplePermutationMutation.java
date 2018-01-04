package hmo.gen_alg.mutation.permutation;

import java.util.Random;

/**
 * Permutation which swaps 1st with last, 2nd with 2nd to last, etc.. if condition for mutation is satisfied
 */
public class SimplePermutationMutation implements PermutationMutation {

    private double shouldMutate;

    public SimplePermutationMutation(double shouldMutate) {
        this.shouldMutate = shouldMutate;
    }

    public SimplePermutationMutation() {
        this.shouldMutate = 0.15;
    }

    @Override
    public int[] mutatePermutationArray(int[] targetArray) {
        Random random = new Random();
        int totalLength = targetArray.length;
        for (int i=0; i < totalLength / 2; i++) {
            double condition = random.nextDouble();
            if (condition < shouldMutate) {
                int temp = targetArray[totalLength-i-1];
                targetArray[totalLength-i-1] = targetArray[i];
                targetArray[i] = temp;
            }
        }
        return targetArray;
    }
}
