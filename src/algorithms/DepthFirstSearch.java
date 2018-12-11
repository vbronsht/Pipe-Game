package algorithms;

import classes.State;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class DepthFirstSearch<T> extends CommonSearcher<T> {
    @Override
    protected void newSearch() {
        super.openList= new Stack<>();
        super.setEvaluatedNodes(0);
    }

    @Override
    protected boolean addToOpenList(State<T> initialState) {
        ((Stack<State<T>>)openList).push(initialState);
        return true;
    }

    @Override
    protected State<T> popOpenList() {
        super.setEvaluatedNodes(super.getNumberOfNodesEvaluated()+1);
        return ((Stack<State<T>>)openList).pop();
    }
}
