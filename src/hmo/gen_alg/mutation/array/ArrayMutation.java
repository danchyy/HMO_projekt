package hmo.gen_alg.mutation.array;

import hmo.gen_alg.Task;

import java.util.List;

public interface ArrayMutation {

    /**
     * Mutates the target array
     * @param targetArray Array which will be mutated
     * @param tasks List of tasks which will be used for determining to which machine can task index be mutated to
     * @return Mutated array
     */
    public int[] mutateArray(int[] targetArray, List<Task> tasks);
}
