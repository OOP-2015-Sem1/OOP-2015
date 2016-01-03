package chess.ui;

import chess.logic.Piece;
import chess.logic.Table;

import javax.print.DocFlavor;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Paul on 10.12.2015.
 */



public  class Connection {
    final private int PORT;
    final private String IP;
    final private String USERNAME;

    private UI ui;

    private static Socket socket;
    private static PrintWriter printWriter;
    private static BufferedReader bufferedReader;
    private static String inputLine;

    private String color="White";
    private boolean ncmd=false;
    private String command = "";

    void setUI(UI ui){
        this.ui = ui;
    }

    public Connection(int PORT, String IP,String username){
        this.PORT = PORT;
        this.IP = IP;
        this.USERNAME = username;


    }

    public  void start(String color){
        this.color= color;
        listen();
    }

    public void sendClick(int x, int y){
        String com= "" ;
        com = "["+ USERNAME + "] [" + Integer.toString(x) +","+ Integer.toString(y)+"]";
        sendCommand(com);
        System.out.println(com);
    }

    public void sendCommand(String com){
        printWriter.println(com);
    }

    public void receive(String command){
            String move = "\\[(.+)\\] \\[(.+)\\] \\[(.+),(.+)\\] \\[(.+),(.+)\\]";
            String select = "\\[(.+)\\] \\[(.+)\\] \\[(.+),(.+)\\]";
            String deselect = "\\[(.+)\\] \\[(.+)\\]";

            String click = "\\[(.+)\\] \\[(.+),(.+)\\]";
            Pattern p = Pattern.compile(click);
            Matcher m = p.matcher(command);
            boolean b = m.matches();



            if (b) {
                int x = Integer.parseInt(m.group(2));
                int y = Integer.parseInt(m.group(3));
                x = 7 - x;
                y = 7 - y;
                if(!m.group(1).equals(USERNAME)) {
                    ui.receive(ui.getGametable(), x, y);

                }
            }

    }

    public void listen() {

        int i = 0;
        String text = "";
        Scanner scan = new Scanner(System.in);
        try
        {
            socket = new Socket(IP, PORT);
            printWriter = new PrintWriter(socket.getOutputStream(),true);
            Thread client = new Thread(new Runnable() {
                @Override
                public void run() {
                    getMessage();
                }
            });
            client.start();

        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

    public void getMessage()
    {
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // Get the client messages
            while ((inputLine = bufferedReader.readLine()) != null) {
                receive(inputLine);
            }
        }catch (IOException e) {
            System.out.println(e);
        }
    }
}
