package hmo.gen_alg.mutation.permutation;

public abstract class PermutationMutation {

    protected double mutationProb;

    /**
     * Changes the permutation array in order to achieve a mutation of that array
     * @param targetArray Array which will be mutated
     * @return mutated array which is given
     */
    public abstract int[] mutatePermutationArray(int[] targetArray);

    public void adjustMutationProb(double modifier) {
        this.mutationProb *= modifier;
    }
}
