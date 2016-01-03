package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Zuklar on 14.11.2015.
 */
public class ServerConnection {
    private static JFrame frame;
    public static JTextField ipserver = new JTextField();
    public static JLabel ip = new JLabel("IP:");
    public static JButton connect = new JButton("Connect");
    public static JTextField nametxt = new JTextField();
    public static JLabel name = new JLabel("Name:");

    private static Integer port = 63400;
    private static Socket socket;

    public void ServerConnectionFrame()
    {
        frame = new JFrame("Connect");
        frame.setLayout(null);

        ipserver.setBounds(100,40,200,30);
        ip.setBounds(30,40,20,30);
        name.setBounds(30, 70, 100, 30);
        nametxt.setBounds(100, 70, 200, 30);
        connect.setBounds(150,100,100,50);
        connect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ConnectPressed();
            }
        });
        frame.add(ipserver);
        frame.add(ip);
        frame.add(connect);
        frame.add(name);
        frame.add(nametxt);

        frame.setVisible(true);
        frame.setSize(500,500);


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }

    public static void ConnectPressed()
    {
        frame.dispose();
        System.out.println(ipserver.getText());
        ConnectToServer();
    }

    public static void ConnectToServer()
    {
        String name, ips;
        name = nametxt.getText();
        ips = ipserver.getText();
        if (ips.isEmpty()){
            BattleField battlefield = new BattleField();
            battlefield.CreateBattleField();
        }
        else{
            try {
                socket = new Socket(ips, port);
                PrintWriter printWriter = new PrintWriter(socket.getOutputStream(),true);
                printWriter.println(name);
            }
            catch (Exception e){
                System.out.println(e);
            }

        }
    }
}
