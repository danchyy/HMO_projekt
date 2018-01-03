package hmo.gen_alg.crossing.regular;

import jdk.jshell.spi.ExecutionControl;

public interface ArrayCrossing {

    /**
     * Crosses the array part of the unit
     * @param firstUnit First unit to be crossed
     * @param secondUnit Second unit to be crossed
     * @return Crossed unit
     * @throws ExecutionControl.NotImplementedException
     */
    public int[] crossUnit(int[] firstUnit, int[] secondUnit) throws ExecutionControl.NotImplementedException;
}
