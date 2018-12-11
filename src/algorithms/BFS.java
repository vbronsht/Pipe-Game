package algorithms;

import classes.State;

import java.util.LinkedList;
import java.util.Queue;

public class BFS<T> extends CommonSearcher<T>{
    @Override
    protected void newSearch(){
        super.openList= new LinkedList<>();
        super.setEvaluatedNodes(0);
    }

    @Override
    protected boolean addToOpenList(State<T> initialState){
        return openList.add(initialState);
    }

    @Override
    protected State<T> popOpenList(){
        super.setEvaluatedNodes(super.getNumberOfNodesEvaluated()+1);
        return ((Queue<State<T>>)openList).poll();
    }
}
