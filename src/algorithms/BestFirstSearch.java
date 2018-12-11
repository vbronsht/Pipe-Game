package algorithms;

import classes.State;
import java.util.*;

public class BestFirstSearch<T> extends CommonSearcher<T>{

    @Override
    protected void newSearch() {
        super.openList= new PriorityQueue<>();
        super.setEvaluatedNodes(0);
    }

    @Override
    protected boolean addToOpenList(State<T> initialState) {
        return super.openList.add(initialState);
    }

    @Override
    protected State<T> popOpenList() {
        super.setEvaluatedNodes(super.getNumberOfNodesEvaluated()+1);
        return ((PriorityQueue<State<T>>)openList).poll();
    }
}
