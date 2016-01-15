package com.company;

import javax.sound.sampled.Port;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Main {

    private static ServerSocket serverSocket;
    private static Socket clientSocket;
    private static Integer port = 63400;
    private static ArrayList<Client> clientS;
    private static PrintWriter printWriter;

    public static void main(String[] args) {
	// write your code here
        BufferedReader bufferedReader;
        String playerName;
        try {
            serverSocket = new ServerSocket(port);
        }catch (IOException e) {
            System.out.println(e);
        }
        while (true) {
            try {

                clientSocket = serverSocket.accept();
                //clientS.clientS = clientSocket;
                bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                playerName = bufferedReader.readLine();
                clientS.add(new Client(clientSocket, playerName));
                // Create a reader
                Thread client = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ListenClient(clientSocket);
                    }
                });
                client.start();
            } catch (IOException e) {
                System.out.println(e);
            }
        }

    }

    private static void ListenClient(Socket clientSocket)
    {
        try {
            BufferedReader bufferedReader;
            String inputLine;
            bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            // Get the client message
            while ((inputLine = bufferedReader.readLine()) != null) {
                System.out.println("Am primit de la " + clientSocket.getRemoteSocketAddress() + ' ' + inputLine);
                SendMsg(clientSocket, inputLine);
            }
        }catch (IOException e) {
            System.out.println(e);
        }
    }

    private static void SendMsg(Socket clientSocket, String msg)
    {
        for (Client i : clientS)
        {
            try {
                System.out.println(i);
                if (i.clientS != clientSocket) {
                    printWriter = new PrintWriter(i.clientS.getOutputStream(), true);
                    printWriter.println(msg);
                }
            }
            catch(Exception e)
            {
                System.out.println(e);
            }
        }
    }
}
