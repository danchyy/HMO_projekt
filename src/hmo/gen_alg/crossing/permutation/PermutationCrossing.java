package hmo.gen_alg.crossing.permutation;

import hmo.gen_alg.Unit;
import jdk.jshell.spi.ExecutionControl;

import java.util.List;

public interface PermutationCrossing {

    /**
     * Creates a new permutation array with given permutation arrays of units
     * @param firstUnit First permutation array for crossing
     * @param secondUnit Second permutation array for crossing
     * @return crossed unit
     */
    public int[] crossUnit(int[] firstUnit, int[] secondUnit) throws ExecutionControl.NotImplementedException;

    /**
     * Method which creates two new permutation arrays for units
     * @param firstUnit First permutation array for crossing
     * @param secondUnit Second permutation array for crossing
     * @return Crossed units
     */
    public List<int[]> crossUnitTwoChildren(int[] firstUnit, int[] secondUnit) throws ExecutionControl.NotImplementedException;
}
