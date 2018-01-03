package hmo.gen_alg.selection;

import hmo.gen_alg.Unit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class KTournamentSelection implements Selection {

    private int k;

    public KTournamentSelection() {
        k = 3;
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
    public List<Unit> selectUnit(List<Unit> population) {
        List<Unit> tournament = new ArrayList<>();
        List<Integer> indexes = new ArrayList<>();

        Random random = new Random();
        while (tournament.size() != this.k) {
            int index = random.nextInt(population.size());
            indexes.add(index);
            tournament.add(population.get(index));
        }
        Collections.sort(tournament);
        Collections.reverse(tournament);
        return tournament;
    }
}
