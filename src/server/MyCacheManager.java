package server;

import classes.Solution;

import java.io.*;

public class MyCacheManager<T> implements CacheManager<Solution<T>>{

    @Override
    public boolean isFileExist(String fileName) {
        try {
            Solution<T> solution = loadFile(fileName);
            return (solution!=null&&
                    solution.getSolution()!=null&&
                    solution.getSolution().size()!=0);
        } catch (Exception e) {
           return false;
        }
    }

    @Override
    public Solution<T> loadFile(String fileName) throws FileNotFoundException, IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName+".txt"));
        Solution<T> solution= (Solution<T>)in.readObject();
        in.close();
        return solution;
    }

    @Override
    public void saveFile(String fileName, Solution<T> file) throws IOException { // from exercise 6 -b pincheta
        ObjectOutputStream out=new ObjectOutputStream(new FileOutputStream(fileName+".txt"));
        out.writeObject(file);
        out.flush();
        out.close();
    }
}
