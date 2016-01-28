package go;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
/**
 * Dialog that lets user pick size and starts the game.
 *
 */
@SuppressWarnings("serial")
public class StartDialog extends JDialog {

    private static final String TITLE = "Choose size";
    private static final int BUTTON_TEXT_SIZE = 30;
    private static final Font BUTTON_FONT = new Font(null, Font.PLAIN,
            BUTTON_TEXT_SIZE);
    private static final int GAP_SIZE = 10;

    private static final int FIRST_OPTION = 9;
    private static final int SECOND_OPTION = 19;
    private static final String CUSTOM_OPTION = "Custom";

    public StartDialog(Main main) {
        super((Frame) null, TITLE);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                System.exit(0);
            }
        });

        JPanel container = new JPanel(new GridLayout(1, 1, GAP_SIZE, GAP_SIZE));
        container.add(createOptionButton(FIRST_OPTION, main, this));
        container.add(createOptionButton(SECOND_OPTION, main, this));
        JButton customSizeBtn = new JButton(new AbstractAction(CUSTOM_OPTION) {

            @Override
            public void actionPerformed(ActionEvent e) {
                String sizeString = JOptionPane.showInputDialog("Custom size:");
                try {
                    int size = Integer.parseInt(sizeString);
                    if (size > 1 && size < 30) {
                        main.startGame(size);
                    } else {
                        throw new IllegalArgumentException();
                    }
                } catch (IllegalArgumentException ex) {
                    JOptionPane
                            .showMessageDialog(
                                    null,
                                    "Invalid input. Please enter a number between 1 and 30.");
                }
            }
        });
        customSizeBtn.setFont(BUTTON_FONT);
        container.add(customSizeBtn);
        add(container);
    }

    private JButton createOptionButton(int option, Main main,
            StartDialog parent) {
        JButton optionButton = new JButton(new AbstractAction(
                String.valueOf(option)) {

            @Override
            public void actionPerformed(ActionEvent e) {
                parent.setVisible(false);
                main.startGame(option);
            }
        });
        optionButton.setFont(BUTTON_FONT);
        return optionButton;
    }
    
}