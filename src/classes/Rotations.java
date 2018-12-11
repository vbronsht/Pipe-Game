package classes;

import server.Solver;

import java.util.ArrayList;
import java.util.Collection;

public class Rotations{

    Collection<String> collection;
    public Rotations(PipeGameBoard problem,PipeGameBoard solution) {
        collection=new ArrayList<>();
        Helper helper=new Helper(0,0,0);
        for(int i = 0 ; i < problem.getRows() ; i++ ){
            for(int j = 0 ; j < problem.getColumns() ; j++){
                helper.row=i;
                helper.col=j;
                Tile tile;
                try {
                    tile = problem.getTile(i,j);
                    int r;
                    for(r=0;r<tile.countRotations();r++){
                        if(tile.equals(solution.getTile(i,j)))
                            break;
                        tile.rotate();
                    }
                    helper.round=r%tile.countRotations();
                    if(helper.round!=0)
                        collection.add(helper.toString());
                } catch (Exception ignored) {}
            }
        }
    }

    public Collection<String> returnAllRotations(){
        return collection;
    }

    private class Helper{
        public int row;
        public int col;
        public int round;

        public Helper(int row, int col, int round) {
            this.row = row;
            this.col = col;
            this.round = round;
        }

        @Override
        public String toString() {
            return row+","+col+","+round;
        }
    }
}
