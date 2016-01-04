package com.company;

import javafx.scene.layout.Pane;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Zuklar on 14.11.2015.
 */
public class BattleField {

    private static int lines = 10;
    private static int columns = 10;
    JFrame frame = new JFrame("BattleField");
    public static JButton [][]Field = new JButton[lines][columns];
    public static int [][]FieldMatrix = new int [lines][columns];


    public void CreateBattleField()
    {
        frame.setLayout(null);
        int i, j;
        JButton swich = new JButton();
        AtackArea atackArea = new AtackArea(frame);
        for (i = 0; i < lines; i++)
        {
            for (j = 0 ;j < columns; j++)
            {
                Field[i][j] = new JButton();
                Field[i][j].setBounds(40 + 40 * j, 40 + 40 * i, 40, 40);
                Field[i][j].setBackground(new Color(31, 194, 193));
                Field[i][j].addMouseListener(new MouseListen(i,j));
                frame.add(Field[i][j]);
            }
        }

        swich.setBounds(450, 60, 70, 70);

        ImageIcon img = new ImageIcon("images/rotate.png");
        swich.setIcon(img);
        swich.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MouseListen.orientation *= -1;
            }
        });
        frame.add(swich);
        frame.setSize(1200,700);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        while (MouseListen.ready == false){
            
        }
    }
}
