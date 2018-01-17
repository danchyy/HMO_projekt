package hmo.gen_alg.selection;

import hmo.gen_alg.Unit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class KTournamentSelection implements Selection {

    private int k;

    public KTournamentSelection() {
        k = 7;
    }

    public KTournamentSelection(int k) {
        this.k = k;
    }

    /**
     * Returns the selected tournament from population which is sorted based on their fitness.
     * That tournament can be used to select top 2 for crossing and eliminating the third and replacing it with child
     * @param population Population of units upon which the selection which will be determined
     * @return Sorted tournament
     */
    @Override
    public Unit selectUnit(List<Unit> population) {

        Unit selected = null;

        Random random = new Random();
        int i=0;
        List<Integer> indexes = new ArrayList<>();
        while (i < this.k) {
            int index = random.nextInt(population.size());
            Unit current = population.get(index);
            if (indexes.contains(index)) {
                continue;
            }
            i++;
            if ( selected == null || current.calculateFitness() < selected.calculateFitness()) {
                selected = current;
            }
        }
        return selected;
    }
}
