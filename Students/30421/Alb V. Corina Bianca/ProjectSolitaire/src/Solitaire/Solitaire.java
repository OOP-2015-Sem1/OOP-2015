package Solitaire;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Cards.ClassicCard;
import Cards.ClassicDeck;
import Cards.Stack;
import Manager.WindowManager;

public class Solitaire extends Frame
{
    public static final int FOUNDATION_STACK = 4;

    public static final int TABLEAU_STACK = 7;
    
    public static final int FREED_CARDS_CNT = 1;

    public static final Point DECK_POS = new Point( 5, 5 );
    public static final Point REVEALED_CARDS_POS = new Point( DECK_POS.x + ClassicCard.DEFAULT_WIDTH + 5, 5 );
    public static final Point FOUNDATION_STACK_POS = new Point( REVEALED_CARDS_POS.x + ClassicCard.DEFAULT_WIDTH + 92, DECK_POS.y );
    public static final Point TABLEAU_STACK_POS = new Point( DECK_POS.x, FOUNDATION_STACK_POS.y + ClassicCard.DEFAULT_HEIGHT + 5 );

    public static final Color TABLE_COLOR = new Color( 0, 150, 0 );
    
    public Solitaire( )
    {
        super();
        setLayout( new BorderLayout( 0, 0 ) );
        setResizable( false );

        //Table
        table = new Table();
        add("Center", table);
        MouseManager mouseManager = new MouseManager();
        table.addMouseListener( mouseManager );
        table.addMouseMotionListener( mouseManager );

        setSize((ClassicCard.DEFAULT_WIDTH + 5) * TABLEAU_STACK + 10 + getInsets().left + getInsets().right + 3,560);

        addWindowListener(
            new SolitaireWindowManager( this,
               WindowManager.EXIT_ON_CLOSE ) );

        newGame();
        setVisible( true );
    }

    public void setVisible(boolean b)
    {
        Dimension scrSize = getToolkit().getScreenSize();
        Dimension size = getSize();
        if(b)
        {
            setLocation( (scrSize.width - size.width) / 2, (scrSize.height - size.height) / 2 );
        }
        super.setVisible(b);
    }

    public void setLocale( ) {
        if( table != null )
            table.repaint();
    }

    private void pushGameState( GameState state ) {
        gameStates.add( state );
    }

    public static void main( String[] args ) {
            Solitaire sol = new Solitaire( );
            sol.setLocale(  );
    }

    public void newGame() {
      
        deck = new ClassicDeck( table );
        deck.setLocation( DECK_POS.x, DECK_POS.y );

        revealedCards = new Stack();
        revealedCards.setLocation( REVEALED_CARDS_POS.x, REVEALED_CARDS_POS.y );

        seqStack = new SequentialStack[ FOUNDATION_STACK ];
        for ( int i = 0; i < FOUNDATION_STACK; i++ ) {
            seqStack[ i ] = new SequentialStack();
            seqStack[ i ].setLocation( FOUNDATION_STACK_POS.x + i * (ClassicCard.DEFAULT_WIDTH + 5), FOUNDATION_STACK_POS.y );
        }

        solStack = new SolitaireStack[ TABLEAU_STACK ];
        for ( int i = 0; i < TABLEAU_STACK; i++ ) {
            solStack[ i ] = new SolitaireStack();
            solStack[ i ].setSpreadingDirection( Stack.SPREAD_SOUTH );
            solStack[ i ].setSpreadingDelta( 20 );
            solStack[ i ].setLocation( TABLEAU_STACK_POS.x + i * (ClassicCard.DEFAULT_WIDTH + 5), TABLEAU_STACK_POS.y );
        }

        currStack = new Stack();
        currStack.setSpreadingDirection( Stack.SPREAD_SOUTH );
        currStack.setSpreadingDelta( 20 );

        distributeCards();
        if( table != null )
            table.repaint();
    }

    public void getNewCards() {

    	//First, restore the deck if it's empty.
        if( deck.isEmpty() ) {
            while ( !revealedCards.isEmpty() ) {
                ClassicCard c = ((ClassicCard)revealedCards.pop());
                c.turnFaceDown();
                deck.push( c );
            }
        }

        for( int i = 0; !deck.isEmpty() && i < FREED_CARDS_CNT; i++ ) {
            ClassicCard c = ((ClassicCard)deck.pop());
            c.turnFaceUp();
            revealedCards.push( c );
        }
        // Save the state of the game after the move
        pushGameState( new GameState( deck, revealedCards, solStack, seqStack, null, null, null ) );

        // Flag which cards can be moved legally
        GameState gs = new GameState( deck, revealedCards, solStack, seqStack );
        legalGs = gs.legalMoves(  );

        if( table != null )
            table.repaint();
    }

    public void play( Stack curr, Stack src, Stack dst ) {
        if( curr != null )
            curr.reverse();
        if( dst != null && dst.isValid( curr ) ) {
            for( ; !curr.isEmpty(); )
                dst.push( curr.pop() );
            if( !src.isEmpty() && src.top().isFaceDown() ) {
                ClassicCard topCard = ((ClassicCard)src.top());
                topCard.turnFaceUp();
            }
            // Save the state of the game after the move
            pushGameState( new GameState( deck, revealedCards, solStack, seqStack, null, null, null ) );
            
            // Flag which cards can be moved legally
            GameState gs = new GameState( deck, revealedCards, solStack, seqStack );
            legalGs = gs.legalMoves(  );
            
        }
        else {
            while ( !curr.isEmpty() )
                src.push( curr.pop() );
        }
        if( table != null )
            table.repaint();
    }

    private void distributeCards() {
        for( int i = 0; i < TABLEAU_STACK; i++ ) {
            ClassicCard c = ((ClassicCard)deck.pop());
            c.turnFaceUp();
            solStack[ i ].push( c );
            for( int j = i+1; j < TABLEAU_STACK; j++ )
                solStack[ j ].push( deck.pop() );
        }
        
        // Save the initial game state
        pushGameState( new GameState( deck, revealedCards, solStack, seqStack ) );
        
        // Flag which cards can be moved legally
        GameState gs = new GameState( deck, revealedCards, solStack, seqStack );
        legalGs = gs.legalMoves(  );
    }

    private boolean isGameWon() {
        boolean gameWon = deck.isEmpty() && revealedCards.isEmpty();
        if( gameWon )
            for( int i = 0; i < TABLEAU_STACK && gameWon; i++ )
                gameWon = gameWon && solStack[ i ].isEmpty();
        return( gameWon );
    }

    class MouseManager extends MouseAdapter implements MouseMotionListener {
        public void mouseMoved(MouseEvent e) {
        }

        public void mouseDragged(MouseEvent e) {
            if( currStack != null && translation != null ) {
                Point p = e.getPoint();
                currStack.setLocation( p.x - translation.x, p.y - translation.y );
                table.repaint();
            }
        }

        public void mousePressed(MouseEvent e) {
            if( !e.isMetaDown() && !e.isControlDown() && !e.isShiftDown() ) {
                ClassicCard c = null;
                Point p = e.getPoint();

                if( deck.contains( p ) )
                    getNewCards();
                else {
                    if( !revealedCards.isEmpty() && revealedCards.top().contains( p ) ) {
                        src = revealedCards;
                        c = ((ClassicCard)src.top());
                    }
                    else {
                        for( int i = 0; i < TABLEAU_STACK && src == null; i++ ) {
                            if( !solStack[ i ].isEmpty() && solStack[ i ].contains( p ) ) {
                                src = solStack[ i ];
                                c = ((ClassicCard)src.getClickedCard( p ));
                            }
                        }
                        for( int i = 0; i < FOUNDATION_STACK && src == null; i++ ) {
                            if( !seqStack[ i ].isEmpty() && seqStack[ i ].contains( p ) ) {
                                src = seqStack[ i ];
                                c = ((ClassicCard)src.top());
                            }
                        }
                    }
                    //We don't allow to drag hidden cards
                    if( c != null && c.isFaceDown() ) {
                        src = null;
                        c = null;
                    }
                    if( src != null && c != null ) {
                        Point loc = c.getLocation();
                        translation = new Point( p.x - loc.x, p.y - loc.y );
                        currStack = src.pop( c );
                        currStack.reverse();
                        curr = currStack;
                    }
                }
            }
        }

        public void mouseReleased(MouseEvent e) {
            Point p = e.getPoint();

            for( int i = 0; i < TABLEAU_STACK && dst == null; i++ ) {
                if( solStack[ i ].contains( p ) )
                    dst = solStack[ i ];
            }
            for( int i = 0; i < FOUNDATION_STACK && dst == null; i++ ) {
                if( seqStack[ i ].contains( p ) )
                    dst = seqStack[ i ];
            }
            if( curr != null && src != null )
                play( curr, src, dst );
            curr = src = dst = null;
        }

        private   Stack   curr;
        private   Stack   src;
        private   Stack   dst;
        private   Point   translation;
    }

    class Table extends Canvas
    {
        public void update( Graphics g ) {
            paint( g );
        }

        public void paint( Graphics g ) {
            //Create offscreen
            Dimension dim = this.getSize();
            if( offscreen == null ) {
                offscreen = this.createImage( dim.width, dim.height );
                offscreenGr = (Graphics2D)offscreen.getGraphics();
            }

            //Draw background
            offscreenGr.setColor( TABLE_COLOR );
            offscreenGr.fillRect( 0, 0, dim.width, dim.height );

            //Draw deck
            if( deck != null )
                if( deck.isEmpty() ) {
                    Point loc = deck.getLocation();
                    offscreenGr.setColor( Color.darkGray );
                    offscreenGr.fillRect( loc.x, loc.y, ClassicCard.DEFAULT_WIDTH, ClassicCard.DEFAULT_HEIGHT );
                    offscreenGr.setColor( Color.black );
                    offscreenGr.drawRect( loc.x, loc.y, ClassicCard.DEFAULT_WIDTH, ClassicCard.DEFAULT_HEIGHT );
                }
                else {
                    deck.top().paint( offscreenGr, false);
                }

            //Draw revealedCards
            if( revealedCards != null && !revealedCards.isEmpty() )
                revealedCards.top().paint( offscreenGr, false);

            //Draw sequential stacks
            if( seqStack != null )
                for( int i = 0; i < Solitaire.FOUNDATION_STACK; i++ )
                    seqStack[ i ].paint( offscreenGr, false );

            //Draw solitaire stacks
            if( solStack != null )
                for( int i = 0; i < Solitaire.TABLEAU_STACK; i++ )
                    solStack[ i ].paint( offscreenGr, false );

            //Draw current stack
            if( currStack != null && !currStack.isEmpty())
                currStack.paint( offscreenGr, false );
            
            g.drawImage( offscreen, 0, 0, this );
        }

        public void destroy() {
            offscreenGr.dispose();
        }

        private Image       offscreen;
        private Graphics2D  offscreenGr;
    }

    class SolitaireWindowManager extends WindowManager {

        SolitaireWindowManager( Window window, int action ) {
            super( window, action );
        }
    }
     
    protected   Stack               currStack;
    protected   ClassicDeck         deck;
    protected   Stack               revealedCards;
    protected   SolitaireStack[]    solStack;
    protected   SequentialStack[]   seqStack;
    protected   Table               table;
    
    // Holds the state of the solitaire game after each move
    protected ArrayList<GameState>  gameStates = new ArrayList<GameState>();
    
    // Holds all the legal moves from a given position (held as game states after move)
    protected ArrayList<GameState>  legalGs = new ArrayList<GameState>();
    
    static protected ResourceBundle resBundle;
}