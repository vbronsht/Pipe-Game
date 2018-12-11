package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServer implements Server{

    private final int port;
    private ClientHandler ch;
    private volatile boolean stop;

    public MyServer(int port) {
        this.port=port;
        stop=false;
        ch=new MyClientHandler();
    }

    private void runServer() throws IOException {
        ServerSocket server=new ServerSocket(port);
        server.setSoTimeout(1000);
        while(!stop){
            try{
                Socket aClient=server.accept(); // blocking call
                InputStream inFromClient=aClient.getInputStream();
                OutputStream outToClient=aClient.getOutputStream();
                ch.handleClient(inFromClient, outToClient);
                inFromClient.close();
                outToClient.close();
                aClient.close();
            }
            catch (Exception e) {
            }
        }
        server.close();

    }
    @Override
    public void start(ClientHandler clientHandler) {
        this.ch=clientHandler;
        new Thread(()->{
            try {
                runServer();
            } catch (Exception e) {

            }
        }).start();
    }
    @Override
    public void stop() {
        stop=true;

    }
    public static void main(String[] args) {
        //here you can change the number of threads M arg
        //     MyServer myServer = new MyServer(6400,2);
        MyServer myServer = new MyServer(6500);
        myServer.start(new MyClientHandler());
    }
}