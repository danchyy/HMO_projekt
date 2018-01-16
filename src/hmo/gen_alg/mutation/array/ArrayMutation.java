package hmo.gen_alg.mutation.array;

import hmo.gen_alg.Task;

import java.util.List;

public abstract class ArrayMutation {

    protected double mutationProb;

    /**
     * Mutates the target array
     * @param targetArray Array which will be mutated
     * @param tasks List of tasks which will be used for determining to which machine can task index be mutated to
     * @return Mutated array
     */
    public abstract int[] mutateArray(int[] targetArray, List<Task> tasks);

    public void adjustMutationProb(double modifier) {
        this.mutationProb *= modifier;
    }

}
