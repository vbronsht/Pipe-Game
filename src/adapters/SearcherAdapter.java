package adapters;

import Interfaces.Searchable;
import Interfaces.Searcher;
import classes.Solution;
import server.Solver;

public class SearcherAdapter<T> implements Solver<T> { // cover from search to solver

    private Searcher<T> searcher;
    public SearcherAdapter(Searcher<T> searcher){
        this.searcher=searcher;
    }

    @Override
    public Solution<T> solve(Searchable<T> s) {
        return searcher.Search(s);
    }
}
