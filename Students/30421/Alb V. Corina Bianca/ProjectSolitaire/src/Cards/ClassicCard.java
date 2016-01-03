package Cards;

import java.awt.*;
import java.awt.image.*;

import java.awt.geom.RoundRectangle2D;

public class ClassicCard extends Card {

    public static final String  STRING_HIDDEN  = "X";
    
    public static final int     BORDER_ARC = 20;
    public static final Color   CARD_COLOR = Color.blue;

    public ClassicCard( ClassicCard card ) {
        super();
        this.suit = card.suit;
        this.value = card.value;
        this.setLocation( card.getLocation() );
        this.setSize( card.getSize() );
        if( card.isFaceDown() )
            this.turnFaceDown();
        else
            this.turnFaceUp();
    }

    public ClassicCard( Value value, Suit suit ) {
        super();
        this.suit = suit;
        this.value = value;
        this.legal = false;
        turnFaceDown();
    }

    public boolean isLegal() {
        return legal;
    }

    public void setLegal( boolean legal ) {
        this.legal = legal;
    }

    public void setImageObserver( ImageObserver imgObserver ) {
        this.imgObserver = imgObserver;
    }

    public Color getColor() {
        return ( suit == Suit.SPADE || suit == Suit.CLUB ) ? Color.black : Color.red;
    }

    public Value getValue() {
        return value;
    }

    public Suit getSuit() {
        return suit;
    }

    public boolean equals(Object obj) {
        return
            isFaceDown() == ( (ClassicCard)obj ).isFaceDown() &&
            suit == ( (ClassicCard)obj ).suit &&
            value == ( (ClassicCard)obj ).value;
    }

    public String toString() {
        StringBuffer strBufTemp = new StringBuffer();
        if( isFaceDown() )
            strBufTemp.append( STRING_HIDDEN );
        strBufTemp.append( value.toString() );
        strBufTemp.append( suit.toString() );
        if( isFaceDown() )
            strBufTemp.append( STRING_HIDDEN );
        return strBufTemp.toString();
    }

    public void paint( Graphics g, boolean hint ) {
        Point location = getLocation();

        //Background
        RoundRectangle2D border = new RoundRectangle2D.Double(
            location.x, location.y, getSize().width - 1, getSize().height - 1, BORDER_ARC, BORDER_ARC);

        g.setClip(border); // Don't draw outside the lines

        if( isFaceDown() ) {
            g.setColor( CARD_COLOR );
            g.fillRect( location.x, location.y, getSize().width - 1, getSize().height - 1 );
        }
        else {
            g.setColor( Color.white );
            g.fillRect( location.x, location.y, getSize().width - 1, getSize().height - 1 );
            
            String text = suit+" "+value;
            
            BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = img.createGraphics();
            Font font = new Font("Arial", Font.PLAIN, 30);
            g2d.setFont(font);
            FontMetrics fm = g2d.getFontMetrics();
            int width = fm.stringWidth(text);
            int height = fm.getHeight();
            g2d.dispose();

            img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            g2d = img.createGraphics();

            g2d.setFont(font);
            fm = g2d.getFontMetrics();
            g2d.setColor(getColor());
            g2d.drawString(text, 0, fm.getAscent());
            g2d.dispose();
           
            g.drawImage( img, location.x + 3, location.y + 3, imgObserver );    
           
        }

        g.setClip(null); // OK, you can draw anywhere again

        // Frame
        g.setColor( Color.black );
        g.drawRoundRect(location.x, location.y, getSize().width - 1, getSize().height - 1, BORDER_ARC, BORDER_ARC);
    }
    
    private Suit            suit;
    private Value           value;
    private boolean         legal;

    private ImageObserver   imgObserver;
}