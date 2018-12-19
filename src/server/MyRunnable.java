package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class MyRunnable implements Runnable, Comparable<Runnable>
{
    private int size;
    private Socket client;
    private InputStream inFromClient;
    private OutputStream outToClient;
    private ClientHandler ch;

    public MyRunnable(Socket client, InputStream is, OutputStream os, ClientHandler ch) throws IOException {
        this.client = client;
        this.inFromClient = is;
        this.outToClient = os;
        this.ch = ch;
        size = is.available();

    }
    @Override
    public int compareTo(Runnable o) {
        return this.size - ((MyRunnable) o).size;
    }

    @Override
    public void run()
    {
        ch.handleClient(inFromClient, outToClient);
        try {
            inFromClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            outToClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getPriority() {
        return size;
    }

    public void setPriority(int priority) {
        this.size = priority;
    }

}
