package classes;

import java.util.ArrayList;
import java.util.Collection;

public class Solution<T>{

    private ArrayList<State<T>> solution;
    public Solution() {
        solution=new ArrayList<>();
    }
    public Solution(Collection<State<T>> arrayList) {
        solution=new ArrayList<>(arrayList);
    }

    public void add(State<T> state) {
        solution.add(state);
    }

    public ArrayList<State<T>> getSolution() {
        return solution;
    }
}
