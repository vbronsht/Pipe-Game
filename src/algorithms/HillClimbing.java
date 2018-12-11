package algorithms;

import Interfaces.Searchable;
import Interfaces.Searcher;
import classes.Solution;
import classes.State;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class HillClimbing<T> implements Searcher<T> {

    private Random random = new Random();
    private long timeToRun=5000;

    @Override
    public Solution<T> Search(Searchable<T> s) {
        State<T> next = s.getInitialState();
        Solution<T> result = new Solution<>();
        long time0 = System.currentTimeMillis();
        while (System.currentTimeMillis() - time0 < timeToRun) {

            Collection<State<T>> neighbors = s.getAllPossibleStates(next);
            if (Math.random() < 0.7&&!s.IsGoalState(next)) {
                int grade = 0;
                for (State<T> state : neighbors) {
                    int g = (int)(state.getCost() *random.nextInt());
                    if (g > grade) {
                        grade = g;
                        next = state;
                        result.add(state);
                    }
                }
            } else {
                ArrayList<State<T>> states=new ArrayList<>(neighbors);
                next = states.get(new Random().nextInt(neighbors.size()));
            }
        }
        return result;

    }

    @Override
    public int getNumberOfNodesEvaluated() { // D"r Eli presentation
        return 0;
    }
}