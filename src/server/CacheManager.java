package server;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface CacheManager<T>{
    public boolean isFileExist(String fileName);
    public T loadFile(String fileName) throws FileNotFoundException, IOException, ClassNotFoundException;
    public void saveFile(String fileName,T file) throws IOException;
}
