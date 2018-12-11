package algorithms;

import Interfaces.Searchable;
import Interfaces.Searcher;
import classes.Solution;
import classes.State;

import java.util.*;

public abstract class CommonSearcher<T> implements Searcher<T>{
    protected Collection<State<T>> openList;
    private int evaluatedNodes;
    public CommonSearcher(){ //create new search
        newSearch();
    }

    protected abstract void newSearch(); // Reset the search
    protected abstract boolean addToOpenList(State<T> initialState); // add to collections
    protected abstract State<T> popOpenList(); // take out from the collections

    public void setEvaluatedNodes(int evaluatedNodes) {
        this.evaluatedNodes = evaluatedNodes; // how many nodes we pass
    }

    protected int getEvaluatedNodes(){ //return what we pass
        return evaluatedNodes;
    }

    @Override
    public int getNumberOfNodesEvaluated(){ // return what we pass
        return getEvaluatedNodes();
    }


    protected Solution<T> stepsWeDid(State<T> startState,State<T> goalState) throws Exception{ // this function return all steps we did and add to collection
        ArrayList<State<T>> arrayList=new ArrayList<>();
        arrayList.add(goalState);
        while(arrayList.get(0)!=null&&!arrayList.get(0).equals(startState)&&arrayList.get(0).getCameFrom()!=null){
            arrayList.add(0,arrayList.get(0).getCameFrom());
        }
        if(arrayList.isEmpty()){
            throw new Exception("No steps taken");
        }
        return new Solution<>(arrayList);
    }

    @Override
    public Solution<T> Search(Searchable<T> s){ // from algorithm to OOP Presentation number 9
        newSearch();
        addToOpenList(s.getInitialState());
        Set<State<T>> closedSet=new HashSet<>();
        while(!openList.isEmpty()){
            State<T> n=popOpenList();// dequeue
            closedSet.add(n);
            if(s.IsGoalState(n)){
                try {
                    return stepsWeDid(s.getInitialState(),n);
                    // private method, back traces through the parents
                } catch (Exception e) {}
            }
            Collection<State<T>> successors=s.getAllPossibleStates(n);//however it is implemented
            for(State<T> state: successors){
                if(!closedSet.contains(state)){
                    if(!openList.contains(state)){
                        addToOpenList(state);
                    }
                    else{
                        if(openList.removeIf(t -> t.equals(state)&&t.getCost()>state.getCost())){
                            addToOpenList(state);
                        }
                    }
                }

            }
        }
        return null;
    }
}
