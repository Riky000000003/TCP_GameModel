package org.example;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServer
{
    static int portNumber = 1234;
    static ServerSocket serverSocket;
    public static void main( String[] args )
    {
        try {
            serverSocket = new ServerSocket(portNumber);
        } catch (Exception e) {
            e.printStackTrace();
        }

        while(true) //il server è sempre pronto a prendere richieste dai client
        {
            Socket clientSocket;
            try {
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            ClientHandler clientHandler = new ClientHandler(clientSocket);
            GameModel.getInstance().addClient(clientHandler);
            Thread thread = new Thread(clientHandler);
            thread.start();
        }
    }
}
