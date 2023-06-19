package org.example;

import java.util.ArrayList;
import java.util.List;

public class GameModel
{
    private static GameModel INSTANCE;

    private MyTimer myTimer;
    List<ClientHandler> clientList = new ArrayList<>();

    private GameModel(){
        this.myTimer = new MyTimer();
        this.myTimer.start();
    }

    public static GameModel getInstance()
    {
        if(INSTANCE==null)
        {
            INSTANCE = new GameModel();
        }
        return INSTANCE;
    }

    void addClient(ClientHandler client)
    {
        this.clientList.add(client);
        System.out.println("Size: "+ this.clientList.size());
    }

    void removeClient(ClientHandler client)
    {
        this.clientList.remove(client);
    }

    void sendToAll(String msg)
    {
        for (ClientHandler c: this.clientList
             ) {
            c.sendMsg(msg);
        }
    }
}
