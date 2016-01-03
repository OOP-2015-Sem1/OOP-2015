package chess.game;

import chess.resources.Table;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Paul on 22.11.2015.
 */
public class Menu extends JFrame {

    //game table
    private  Table gametable;

    //interface stuff
    private JTextField name;
    private JTextField color;
    private JTextField port;
    private JTextField ip;
    private JButton startButton;

    //connection stuff
    private int PORT;
    private String IP;
    private String USERNAME;

    //constructor
    public Menu(){
        super("Chess Options");

        setLayout(new FlowLayout());

        name = new JTextField("Insert name");
        color = new JTextField("White");
        port = new JTextField("8080");
        ip = new JTextField("localhost");
        startButton = new JButton("Start game");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(color.getText().equals("White")||color.getText().equals("Black")) {
                    newGame();
                }
            }
        });

        add(name);
        add(color);
        add(port);
        add(ip);
        add(startButton);


    }

    //initializes the game table
    public  void restart(){
        gametable= new Table(color.getText());
    }

    //starts new game
    public void newGame(){
        USERNAME = name.getText();
        PORT = Integer.parseInt(port.getText());
        IP = ip.getText();


        restart();

        Connection connection = new Connection(PORT,IP,USERNAME);
        UI tableUI = new UI("Chess", gametable);

        connection.start(gametable.getType());

        //we link the connection to the game
        tableUI.setConn(connection);
        connection.setUI(tableUI);

        tableUI.updateTable();
        tableUI.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        tableUI.setSize(800, 800);
        tableUI.setVisible(true);
        setVisible(false);
        System.out.println("a intrat");
    }
}
