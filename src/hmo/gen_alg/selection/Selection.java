package hmo.gen_alg.selection;

import hmo.gen_alg.Unit;

import java.util.List;

public interface Selection {

    /**
     * Method which needs to select an unit which will be used for crossing
     * @param population Population of units upon which the selection which will be determined
     * @return Unit which will be selected for crossing
     */
    public Unit selectUnit(List<Unit> population);
}
