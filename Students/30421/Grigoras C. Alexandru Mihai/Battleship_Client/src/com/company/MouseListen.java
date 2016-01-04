package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

/**
 * Created by Zuklar on 06-Dec-15.
 */
public class MouseListen extends MouseAdapter {

    private static boolean enable = true;
    private final int i,j;
    JButton [][]Field = BattleField.Field;
    int [][]FieldMatrix = BattleField.FieldMatrix;
    public static int orientation = 1;
    private static ImageIcon []imgu = new ImageIcon[3];
    private static ImageIcon []imgl = new ImageIcon[3];
    private static ImageIcon imgNULL= new ImageIcon();
    private static int ct = 0;
    private static int nrof3boats = 0;
    public static boolean ready = false;

    public MouseListen(int i, int j) {
        this.i = i;
        this.j = j;
        imgu[0] = new ImageIcon("images/png/headu.png");
        imgu[1] = new ImageIcon("images/png/bodyu.png");
        imgu[2] = new ImageIcon("images/png/tailu.png");

        imgl[0] = new ImageIcon("images/png/headl.png");
        imgl[1] = new ImageIcon("images/png/bodyl.png");
        imgl[2] = new ImageIcon("images/png/taill.png");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        int k;
        if (i<6 + ct && checkIfShip() && orientation == 1 && ct<4) {
            //System.out.println(ct);
            //System.out.println(i);
            for (k = 0; k < 5 - ct; k++) {
                if (k == 0) {
                    Field[i][j].setIcon(imgu[0]);
                } else if (k == 4 - ct) {
                    Field[i + k][j].setIcon(imgu[2]);
                } else {

                    Field[i + k][j].setIcon(imgu[1]);
                }
            }
        }
        else if (j<6 + ct && checkIfShip() && orientation == -1 && ct<4)
        {
            for (k = 0; k < 5 - ct; k++) {
                if (k == 0) {
                    Field[i][j].setIcon(imgl[0]);
                } else if (k == 4 - ct) {
                    Field[i][j + k].setIcon(imgl[2]);
                } else {

                    Field[i][j + k].setIcon(imgl[1]);
                }
            }
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        int k;
        if (i<6 +ct && enable && checkIfShip() && orientation == 1) {
            for (k = 0; k < 5 - ct; k++) {
                Field[i + k][j].setIcon(imgNULL);
            }
        }
        else if (j<6 +ct && checkIfShip() && orientation == -1){
            for (k=0; k < 5 - ct; k++) {
                Field[i][j + k].setIcon(imgNULL);
            }
        }
        else
        {
            enable = true;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e){
        int k;
        if(SwingUtilities.isLeftMouseButton(e)) {
            if (orientation == 1) {
                for (k = 0; k < 5 - ct; k++) {
                    FieldMatrix[i + k][j] = 1;
                }
                enable = false;
            }
            else {
                for (k = 0; k < 5 - ct; k++) {
                    FieldMatrix[i][j + k] = 1;
                }
                enable = false;
            }
            if (ct == 2 && nrof3boats == 0)
            {
                nrof3boats++;
            }
            else {
                ct++;
                if (ct == 4)
                {
                    ready = true;
                }
            }
        }

    }

    private boolean checkIfShip(){
        if (orientation == 1 &&  i<6 + ct){
            for (int k = 0; k < 5 - ct; k++) {
                if (FieldMatrix[i + k][j] == 1)
                    return false;
            }

        }
        if (orientation == -1 && j<6 + ct){
            for (int k = 0; k < 5 - ct; k++) {
                if (FieldMatrix[i][j + k] == 1)
                    return false;
            }
        }
        return true;//  de modifiact
    }

}
