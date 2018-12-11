package server;

import Interfaces.Searchable;
import classes.Solution;

public interface Solver<T> {
    // the solve method
    public Solution<T> solve(Searchable<T> s);

}
