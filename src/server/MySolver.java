package server;

import Interfaces.Searchable;
import Interfaces.Searcher;
import adapters.SearcherAdapter;
import algorithms.DepthFirstSearch;
import classes.Solution;

public class MySolver<T> implements Solver<T> {

    Searcher<T> searcher;

    public MySolver(Searcher<T> searcher) {
        this.searcher = searcher;
    }
    @Override
    public Solution<T> solve(Searchable<T> s) {
        return searcher.Search(s);
    }
}
