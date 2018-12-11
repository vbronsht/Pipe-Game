package searchables;

import Interfaces.Searchable;
import classes.PipeGameBoard;
import classes.State;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class PipeGameBoardSearchable implements Searchable<PipeGameBoard> {

    private PipeGameBoard pipeGameBoard;

    public PipeGameBoardSearchable(PipeGameBoard pipeGameBoard){
        this.pipeGameBoard = pipeGameBoard;
    }

    @Override
    public State<PipeGameBoard> getInitialState(){
        return new State<>(pipeGameBoard);
    }

    @Override
    public Collection<State<PipeGameBoard>> getAllPossibleStates(State<PipeGameBoard> s) {
        //System.out.println(s.toString());
        Collection<PipeGameBoard> gameBoardCollection=s.getState().getAllRotation();
        Collection<State<PipeGameBoard>> stateCollection=new ArrayList<>();
        for(PipeGameBoard pipeGameBoard:gameBoardCollection){
            State<PipeGameBoard> boardState=new State<>(pipeGameBoard,s.getCost()+1,s);
            stateCollection.add(boardState);
        }
        return stateCollection;
    }

    @Override
    public Boolean IsGoalState(State<PipeGameBoard> s){
        return s.getState().isSolved();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof PipeGameBoardSearchable))
            return false;
        if (obj == this)
            return true;
        return this.equals(((PipeGameBoardSearchable) obj));
    }

    public boolean equals(PipeGameBoardSearchable obj) {
        return this.pipeGameBoard.toString().equals(obj.pipeGameBoard.toString());
    }

    @Override
    public int hashCode() {
        return this.pipeGameBoard.toString().hashCode();
    }
}
