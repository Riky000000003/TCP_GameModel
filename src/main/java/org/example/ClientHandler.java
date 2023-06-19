package org.example;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private InetAddress address;

    private PrintWriter out = null; // allocate to write answer to client.


    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
        this.address = clientSocket.getInetAddress();

        System.out.println("Connected from " + address);
    }


    public boolean manage() {
        BufferedReader in =  null;
        try {
            in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            //throw new RuntimeException(e);
            return false;
        }
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            //e.printStackTrace();
            return false;
        }

        String s = "";

        while (true) {
            try {
                if ((s = in.readLine()) == null) break;
                System.out.println(s);
                //out.println(s);
                GameModel.getInstance().sendToAll(s);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


        return true;

    }

    @Override
    public void run() {
        manage();
        GameModel.getInstance().removeClient(this);
    }

    void sendMsg(String msg){
        if (out != null){
            this.out.println(msg);
            this.out.flush();
        }

    }
}