package Manager;

import java.awt.*;
import java.awt.event.*;

public class WindowManager extends WindowAdapter {
    
    public static final int HIDE_ON_CLOSE       = 0;
    public static final int DISPOSE_ON_CLOSE    = 1;
    public static final int EXIT_ON_CLOSE       = 2;
    
    public WindowManager( Window window, int action ) {
        super();
        this.window = window;
        this.action = action;
    }

    public void windowClosing(WindowEvent e) {
        switch( action ) {
            case HIDE_ON_CLOSE : window.setVisible( false ); break;
            case DISPOSE_ON_CLOSE : window.dispose(); break;
            case EXIT_ON_CLOSE : window.dispose(); System.exit( 0 ); break;
        }
    }

    private int     action;
    private Window  window;
}