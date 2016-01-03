package com.company;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Zuklar on 10-Dec-15.
 */
public class AtackArea {
    private static int lines = 10;
    private static int columns = 10;
    public static JButton[][]Field = new JButton[lines][columns];

    public AtackArea(JFrame frame)
    {
        int i,j;

        for (i = 0; i < lines; i++)
        {
            for (j = 0 ;j < columns; j++)
            {
                Field[i][j] = new JButton();
                Field[i][j].setBounds(600 + 40 * j, 40 + 40 * i, 40, 40);
                Field[i][j].setBackground(new Color(31, 194, 193));
                //Field[i][j].addMouseListener(new MouseListen(i,j));
                frame.add(Field[i][j]);
            }
        }
    }

}
