package com.company;

import java.net.Socket;

/**
 * Created by Zuklar on 03-Jan-16.
 */
public class Client {
    public Socket clientS;
    public String name;
    boolean turn = false;
    boolean ready = false;
    public Client(Socket clientS, String name)
    {
        this.clientS = clientS;
        this.name = name;
    }
}
