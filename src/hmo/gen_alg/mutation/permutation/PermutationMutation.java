package hmo.gen_alg.mutation.permutation;

public interface PermutationMutation {

    /**
     * Changes the permutation array in order to achieve a mutation of that array
     * @param targetArray Array which will be mutated
     * @return mutated array which is given
     */
    public int[] mutatePermutationArray(int[] targetArray);
}
