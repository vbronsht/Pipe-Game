package classes;

import enums.Direction;

import java.util.*;
import java.util.function.Predicate;

public class PipeGameBoard{

    private Tile[][] board;
    private int rows;
    private int columns;

    public PipeGameBoard(){
        this(new Tile[0][0],0,0);
    }

    public PipeGameBoard(Tile[][] board, int rows, int columns){ // Constructor
        setRows(0);
        setColumns(0);
        setBoard(board);
    }

    public PipeGameBoard(PipeGameBoard pipeGameBoard) {
        this(pipeGameBoard.getBoard(),pipeGameBoard.getRows(),pipeGameBoard.getColumns());
    }

    public PipeGameBoard(String stBoard){
        if(stBoard == null || stBoard.isEmpty()){
            setRows(0);
            setColumns(0);
            setBoard(new Tile[this.rows][this.columns]);
        }
        else{
            String[] strboard=stBoard.split("\n");
            char[][] chsboard=new char[strboard.length][];
            for(int i=0;i<strboard.length;i++){
                chsboard[i]=strboard[i].replaceAll("[\n\r]", "").toCharArray();
            }
            setBoard(chsboard);
        }
    }

    protected Tile getTile(int row,int col) throws Exception {
        if(0<=row&&row<this.rows&&0<=col&&col<this.columns)
            return this.board[row][col];
        throw new Exception("Out of Board");
    }

    public PipeGameBoard defaultPipeGameBoard(){
        PipeGameBoard clone = this.clone();
        for(int i = 0 ; i < this.rows ; i++){
            for(int j = 0 ; j < this.columns ; j++){
                try {
                    clone.board[i][j]=clone.getTile(i,j).defaultTile();
                } catch (Exception ignored) { }
            }
        }

        return clone;
    }

    public Collection<PipeGameBoard> getAllRotation(){
        Collection<PipeGameBoard> gameBoardCollection=new ArrayList<>();
        for(int i = 0 ; i < this.rows ; i++){
            for(int j = 0 ; j < this.columns ; j++){
                PipeGameBoard clone = this.clone();
                try {
                    Tile tile = clone.getTile(i, j);
                    if (!tile.isBlank() && !tile.isStart() && !tile.isGoal()) {

                        int rotationcount = tile.countRotations();
                        for (int r = 0; r < rotationcount; r++) {
                            clone.getTile(i, j).rotate();
                            //System.out.println("working on row:"+i+" col:"+j+" tile:"+tile.toString());
                            if(clone.isTilesConnectedToStart(clone.getTile(i, j))) {
                                //System.out.println("tile in row:"+i+" col:"+j+" tile:"+tile.toString()+" conncted to start");
                                PipeGameBoard pipeGameBoard = clone.clone();
                                gameBoardCollection.add(pipeGameBoard);
                            }
                        }
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        //System.out.println("size of gameBoardCollection:"+gameBoardCollection.size());
        return gameBoardCollection;
    }

    public boolean isSolved(){ // is solve this function check if we any goal connected to start
        Collection<Tile> tilesgoals=getGoalsIndex(); // we create collection of all goals on the board
        boolean flag;
        for(Tile tgoal:tilesgoals){ // we ues for each to Search on goals
            flag=isTilesConnected(tgoal, new Predicate<Tile>() {

                @Override
                public boolean test(Tile t) {
                    return t.isStart();
                }
            });
            if(!flag){
                return false; // not connected return false
            }
        }
        return true; //found return for start the game
    }

    public boolean isTilesConnectedToStart(Tile tile) {
        return isTilesConnected(tile, Tile::isStart); // !!!----here is lambda expression----!!! here we check if we connected to the start
    }
    public boolean isTilesConnectedToGoal(Tile tile) {
        return isTilesConnected(tile, Tile::isGoal); // !!!----here is lambda expression----!!! here we check if we connected to the goal
    }

    public Collection<Tile> getNeighbors(final Tile tile)
    {
        Collection<Tile> arrayList=new ArrayList<>();
        int row=tile.getRow();
        int col=tile.getColumn();
        Tile tile2=null;
        Collection<Direction> directions=tile.getDirections();
        for(Direction d:directions)
        {
            tile2=getTileByTileAndDir(tile,d);
            if(tile2!=null)
            {
                if(isTilesNeighbors(tile,tile2))
                {
                    arrayList.add(tile2);
                }
            }
        }
        return arrayList;
    }

    private Tile getTileByTileAndDir(Tile tile,Direction direction)
    {
        try {
        switch (direction) {
            case up:
            {
                return (this.getTile(tile.getRow()-1, tile.getColumn()));
            }
            case down:
            {
                return(this.getTile(tile.getRow()+1, tile.getColumn()));
            }
            case left:
            {
                return(this.getTile(tile.getRow(), tile.getColumn()-1));
            }
            case right:
            {
                return (this.getTile(tile.getRow(), tile.getColumn()+1));
            }
            default:
            {
                return null;
            }
        }
        } catch (Exception e) {
            return null;
        }
    }

    public boolean isTilesNeighbors(Tile t1,Tile t2)
    {
        try {
            int row1=t1.getRow();
            int col1=t1.getColumn();
            int row2=t2.getRow();
            int col2=t2.getColumn();
            if(row2==row1-1&&col2==col1)
            {
                if(t1.getDirections().contains(Direction.up)
                        &&t2.getDirections().contains(Direction.down))
                {
                    return true;
                }
            }
            else if(row1==row2)
            {
                if(col2==col1-1)
                {
                    if(t1.getDirections().contains(Direction.left)
                            &&t2.getDirections().contains(Direction.right))
                    {
                        return true;
                    }
                }
                else if(col2==col1)
                {
                    throw new Exception("Error get the same tiles");
                }
                else if(col1+1==col2)
                {
                    if(t1.getDirections().contains(Direction.right)
                            &&t2.getDirections().contains(Direction.left))
                    {
                        return true;
                    }
                }
                else
                {
                    return false;
                }
            }
            else if(row1+1==row2&&col2==col1)
            {
                if(t1.getDirections().contains(Direction.down)
                        &&t2.getDirections().contains(Direction.up))
                {
                    return true;
                }
            }
        }catch (Exception e) {
            return false;
        }
        return false;
    }

    private boolean isTilesConnected(Tile t, Predicate<Tile> tilePredicate){ // this function check the connects batten pipes
        Queue<Tile> queue=new LinkedList<Tile>();
        HashSet<Tile> closedSet=new HashSet<Tile>();
        queue.add(t);
        while(!queue.isEmpty())
        {
            Tile n=queue.remove();// dequeue
            closedSet.add(n);
            try {
                if(tilePredicate.test(n))
                {
                    return true;
                }
                Collection<Tile> successors=getNeighbors(n);
                for(Tile tile:successors) {
                    if(tile!=null&&!closedSet.contains(tile))
                    {
                        if(!queue.contains(tile)){
                            queue.add(tile);
                        }
                    }
                }
            }catch (Exception e) {
                //e.printStackTrace();
            }
        }
        return false;
    }

    private Collection<Tile> getConnecteds(Tile tile){ // get all connectors tiles on the board from specific tile
        Collection<Tile> arrayList=new ArrayList<>(); // Create Collection of tiles
        int row=tile.getRow();
        int col=tile.getColumn();
        Tile tile2;
        Collection<Direction> directions=tile.getDirections(); // get directions from this specific tile
        for(Direction d:directions){
            try {
                tile2=getTileByTileAndDir(tile,d);
                if(tile2!=null){ // if have tile this direction so add this tile to Collection
                    if(tile.isTilesAreConnect(tile2))
                    {
                        arrayList.add(tile2);
                    }
                }
            }catch (Exception e) {
            }
        }

        return arrayList;
    }

    public Collection<Tile> getGoalsIndex() {
        return getTilesIndexByPredicate(Tile::isGoal);
    }
    public Collection<Tile> getStartsIndex() {
        return getTilesIndexByPredicate(Tile::isStart);
    }

    private Collection<Tile> getTilesIndexByPredicate(Predicate<Tile> tilePredicate)
    {
        Collection<Tile> tileCollection=new ArrayList<>(); // create collection of tiles
        for(int i = 0 ; i < this.rows ; i++) { // run on all row
            for (int j = 0; j < this.columns; j++){ // run on all columns
                try {
                    Tile tile=this.getTile(i,j); // get every tile - create new pointer
                    if(tilePredicate.test(tile)){ // check if this tile pass the test
                        tileCollection.add(tile); // if yes so add the goal to collection
                    }
                } catch (Exception ignored){ //java love to do that

                }
            }
        }
        return tileCollection;
    }

    protected PipeGameBoard clone(){
        PipeGameBoard pipeGameBoard=new PipeGameBoard();
        pipeGameBoard.rows=this.rows;
        pipeGameBoard.columns=this.columns;
        pipeGameBoard.board=new Tile[rows][columns];
        for(int i=0;i<rows;i++)
        {
            for(int j=0;j<columns;j++)
            {
                try {
                    pipeGameBoard.board[i][j]=new Tile(this.getTile(i, j).getCh(),i,j);
                }catch (Exception e) {}
            }
        }
        return pipeGameBoard;
    }

    @Override
    public String toString(){
        StringBuilder stringBuilder=new StringBuilder();
        for(int i = 0 ; i < this.rows ; i++) {
            for (int j = 0; j < this.columns; j++){
                try {
                    stringBuilder.append(this.getTile(i,j).toString());
                } catch (Exception ignored) { }
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public Tile[][] getBoard() {
        return board;
    }

    public void setBoard(Tile[][] board) {
        this.board=new Tile[board.length][];
        for(int i=0;i<board.length;i++)
        {
            this.board[i] = Arrays.copyOf(board[i], board[i].length);
        }
    }
    private void setBoard(char[][] chsboard) {
        setRows(chsboard.length);
        setColumns(chsboard[0].length);
        this.board=new Tile[this.rows][this.columns];
        for(int row=0;row<this.rows;row++)
        {
            for(int column=0;column<this.columns;column++)
            {
                this.board[row][column]=new Tile(chsboard[row][column],row,column);
            }
        }
    }

    public int getRows() {
        return rows;
    }

    private void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    private void setColumns(int columns) {
        this.columns = columns;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof PipeGameBoard))
            return false;
        if (obj == this)
            return true;
        return this.equals(((PipeGameBoard) obj));
    }

    public boolean equals(PipeGameBoard pipeGameBoard) {
        return this.toString().equals(pipeGameBoard.toString());
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }


}
