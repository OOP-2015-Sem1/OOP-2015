package go;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Builds UI and starts the game.
 *
 */
/*
public class Main {

public static final String TITLE = "";
public static final int BORDER_SIZE = 25;

public static void main(String[] args) {
    new Main().init();
}

private void init() {
    JFrame f = new JFrame();
    f.setTitle(TITLE);

    JPanel container = new JPanel();
    container.setBackground(Color.GRAY);
    container.setLayout(new BorderLayout());
    f.add(container);
    container.setBorder(BorderFactory.createEmptyBorder(BORDER_SIZE, BORDER_SIZE, BORDER_SIZE, BORDER_SIZE));

    GameBoard board = new GameBoard();
    container.add(board);

    f.pack();
    f.setResizable(false);
    f.setLocationByPlatform(true);
    f.setVisible(true);
}}
*/
public class Main {

    public static final String TITLE = "Simple Go";
    public static final int OUTSIDE_BORDER_SIZE = 25;

    private StartDialog startDialog;

    public static void main(String[] args) {
        new Main().init();
    }

    private void init() {
        startDialog = new StartDialog(this);
        startDialog.pack();
        startDialog.setLocationByPlatform(true);
        startDialog.setVisible(true);
    }

    public void startGame(int size) {
        JFrame f = new JFrame();
        f.setTitle(TITLE);

        f.add(createMainContainer(size));

        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                startDialog.setVisible(true);
            }
        });
        f.pack();
        f.setResizable(false);
        f.setLocationByPlatform(true);
        f.setVisible(true);
    }

    private JPanel createMainContainer(int size) {
        JPanel container = new JPanel();
        container.setBackground(Color.GRAY);
        container.setLayout(new BorderLayout());
        container.setBorder(BorderFactory.createEmptyBorder(
                OUTSIDE_BORDER_SIZE, OUTSIDE_BORDER_SIZE, OUTSIDE_BORDER_SIZE,
                OUTSIDE_BORDER_SIZE));

        GameBoard board = new GameBoard(size);
        container.add(board, BorderLayout.CENTER);
        container.add(createBottomContainer(board), BorderLayout.SOUTH);

        return container;
    }

    private JPanel createBottomContainer(GameBoard board) {
        JPanel bottomContainer = new JPanel();
        @SuppressWarnings("serial")
		JButton passButton = new JButton(new AbstractAction("Pass") {

            @Override
            public void actionPerformed(ActionEvent e) {
                board.pass();
            }
        });
        bottomContainer.add(passButton, BorderLayout.SOUTH);
        return bottomContainer;
    }
    
   
}
