package server;

import algorithms.DepthFirstSearch;
import classes.PipeGameBoard;
import classes.Rotations;
import classes.Solution;
import classes.State;
import searchables.PipeGameBoardSearchable;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;

public class MyClientHandler implements ClientHandler {

    Solver<PipeGameBoard> mySolver=new MySolver<>(new DepthFirstSearch<>());
    CacheManager<Solution<PipeGameBoard>> cacheManager= new MyCacheManager<>();

    @Override
    public void handleClient(InputStream inFromClient, OutputStream outToClient) {
        BufferedReader clientInput=new BufferedReader(new InputStreamReader(inFromClient));
        StringWriter stringWriter=new StringWriter(); // get string problem
        PrintWriter outToServer=new PrintWriter(stringWriter);
        PrintWriter outtoClient=new PrintWriter(outToClient);

        readInputsAndSend(clientInput, outToServer, "done");
        String fromClient = stringWriter.getBuffer().toString(); //get text from the client

        PipeGameBoard gameBoardProblem =new PipeGameBoard(fromClient);
        PipeGameBoard gameBoardSolution=solveBoard(gameBoardProblem);
        Rotations rotations=new Rotations(gameBoardProblem,gameBoardSolution);
        Collection<String> commends=rotations.returnAllRotations();

        for (String commend : commends) {
            //System.out.println("commend: "+commend);
            outtoClient.println(commend);
            outtoClient.flush();
        }
        //System.out.println("done client");
        outtoClient.println("done");
        outtoClient.flush();
        outtoClient.close();

    }


    private void readInputsAndSend(BufferedReader in, PrintWriter out, String exitStr){
        try {
            String line;
            while(!(line=in.readLine()).equals(exitStr)){

                out.println(line);
                out.flush();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }



    public PipeGameBoard solveBoard(PipeGameBoard pipeGameBoardProblem) {
        String nameofproblem=pipeGameBoardProblem.defaultPipeGameBoard().toString(); //give name problem
        Solution<PipeGameBoard> solution=new Solution<>(); // create solution
        if(this.cacheManager.isFileExist(nameofproblem)){ // check if file exist in cache
            try {
                solution=this.cacheManager.loadFile(nameofproblem); // if exist load the file
            } catch (Exception e) {
                //e.printStackTrace();
            }
        }else { //else solve the problem and save in the cache
            solution=mySolver.solve(new PipeGameBoardSearchable(pipeGameBoardProblem));
            try {
                this.cacheManager.saveFile(nameofproblem,solution);
            } catch (Exception e) {
                //e.printStackTrace();
            }

        }
        ArrayList<State<PipeGameBoard>> pipeGameBoardSolution=solution.getSolution(); // get the solution and return the goal state
        return pipeGameBoardSolution.get(pipeGameBoardSolution.size()-1).getState();
    }
}



//public class MainTrain {
//    // run your server here
//    static Server s;
//    public static void runServer(int port){
//        s=new MyServer(port);
//        s.start(new MyClientHandler());
//    }
//    // stop your server here
//    public static void stopServer(){
//        s.stop();
//    }
//    public static void main(String[] args) {
//        int port=6400;
//        runServer(port);
//        //stopServer();
//        System.out.println("done");
//    }
//}