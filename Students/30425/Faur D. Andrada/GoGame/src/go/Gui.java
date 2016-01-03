package go;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import javax.swing.*;

class Gui extends JPanel {
    Gui(TopPanel game, MutableBoard board, int width, int height) {
        this.game = game;
        this.board = board;
        x0 = 10;
        y0 = 10;
        this.n = board.size();
        rectangle = new Rectangle(1, 1, n, n);
        dx = width / n;
        dy = height / n;
        for (int i = 0; i < n; i++)
            addLine(x0, y0 + i * dy, x0 + (n - 1) * dx, y0 + i * dy);
        for (int j = 0; j < n; j++)
            addLine(x0 + j * dx, y0, x0 + j * dx, y0 + (n - 1) * dy);
        addMouseListener(new MouseListener() {
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {}
            @Override public void mouseClicked(MouseEvent e) {
                Point2D.Float point = toBoardCoordinates(e.getPoint());
                Point closest = new Point(Math.round(point.x), Math.round(point.y));
                if (ispointOnBoard(closest)) {
                    double distance = point.distance(closest);
                    if (distance < .35) if (Gui.this.board.at(closest.x, closest.y) == Stone.vacant) Gui.this.game.makeMove(closest);
                    else
                        System.out.println(closest + " is occupied!");
                } else
                    System.out.println(closest + " is off the board!");
            }
        });
    }
    private Point2D.Float toBoardCoordinates(Point screen) {
        return new Point2D.Float((screen.x - x0) / (float) dx + 1, (screen.y - y0) / (float) dy + 1);
    }
    private Point toScreenCoordinates(Point board) {
        return new Point(x0 + (board.x - 1) * dx, y0 + (board.y - 1) * dy);
    }
    private boolean ispointOnBoard(Point point) {
        return rectangle.contains(point);
    }
    Move createMove(Stone stone, Point point) {
        if (ispointOnBoard(point)) return new Move(stone, point);
        else
            throw new RuntimeException(point + " is off the board!");
    }
    void makeMove(Move move) { // currently do not allow vacant moves
        repaint(); 
    }
    void unmove(Move move) {
        repaint();
    }
    void pass(Move move){
    	repaint();
    }
    private void addLine(int x1, int y1, int x2, int y2) {
        this.lines.add(new Line(new Point(x1, y1), new Point(x2, y2)));
    }

   
    private Image blackStone(int width, int height, Color color) {
        Image img = createImage(width, height);
        Graphics g = img.getGraphics();
        g.setColor(color);
        g.fillRect(0, 0, width, height);
        g.setColor(Color.black);
        g.fillOval(0, 0, width - 1, height - 1);
        g.drawOval(0, 0, width - 1, height - 1);
        // these two are different from
        // either g.fillOval(-1, -1, width+1, height+1);
        // or g.fillOval(0, 0, width-1, height-1);
        g.setColor(Color.white);
        g.drawArc(width / 5, height / 5, width * 3 / 5, height * 3 / 5, -20, -60);
        return img;
    }
    private Image whiteStone(int width, int height, Color color) {
        Image img = createImage(width, height);
        Graphics g = img.getGraphics();
        g.setColor(color);
        g.fillRect(0, 0, width, height);
        g.setColor(Color.white);
        g.fillOval(0, 0, width - 1, height - 1);
        g.setColor(Color.black);
        g.drawOval(0, 0, width - 1, height - 1);
        g.drawArc(width / 5, height / 5, width * 3 / 5, height * 3 / 5, -20, -60);
        return img;
    }
    private void paintStone(Graphics g, int i, int j) {
        if (board.at(i, j) != Stone.vacant) {
            if (board.at(i, j) == Stone.black) g.setColor(Color.black);
            else if (board.at(i, j) == Stone.white) g.setColor(Color.white);
            else
                throw new RuntimeException("oops");
            Point screen = toScreenCoordinates(new Point(i, j));
            if (black == null || white == null) g.fillOval(screen.x - dx / 2, screen.y - dy / 2, dx, dx);
            else {
                if (board.at(i, j) == Stone.black) g.drawImage(black, screen.x - dx / 2, screen.y - dy / 2, dx, dy, null);
                else
                    g.drawImage(white, screen.x - dx / 2, screen.y - dy / 2, dx, dy, null);
            }
        }
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (game != null) {
            black = blackStone(dx, dy, getBackground());
            white = whiteStone(dx, dy, getBackground());
            Color color = g.getColor();
            for (Line line : lines) {
                line.paint(g);
            }
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++)
                    paintStone(g, i, j);
            g.setColor(color);
        }
    }
    private final TopPanel game;
    private final int n;
    final MutableBoard board;
    private final int x0, y0;
    private final int dx, dy;
    private final Rectangle rectangle;
    private final Set<Line> lines = new LinkedHashSet<Line>();
    private Image black, white;
    private static final long serialVersionUID = 1L;
	
}