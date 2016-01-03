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
    private static Integer port = 8080;
    private static ArrayList<Socket> clientS;
    private static PrintWriter printWriter;

    public static void main(String[] args) {
	// write your code here
        clientS = new ArrayList<Socket>();
        try {
            serverSocket = new ServerSocket(port);
        }catch (IOException e) {
            System.out.println(e);
        }
        while (true) {
            try {

                clientSocket = serverSocket.accept();
                clientS.add(clientSocket);
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
        for (Socket i : clientS)
        {
            try {
                System.out.println(i);
                if (i != clientSocket) {
                    printWriter = new PrintWriter(i.getOutputStream(), true);
                    printWriter.println(msg);
                    System.out.println("aici");
                }
            }
            catch(Exception e)
            {
                System.out.println(e);
            }
        }
    }
}
