package go;
import java.awt.*;
import java.util.*;
import java.util.List;

public class Block {
	Block(Board b, int i, int j, boolean[][] processed) {
        this.b = b;
        this.processed = processed;
        n = b.size();
        this.who = b.at(i, j);
        points = new LinkedList<Point>();
        grow(i, j);
        countLiberties();
    }
    int liberties() {
        return l;
    }
    Stone color() {
        return who;
    }
    public String toString() {
        return who + " " + points.size() + " stone(s), " + l + " liberties" + points;
    }
    static List<List<Block>> findBlocks(Board b) {
        int n = b.size();
        boolean[][] processed = new boolean[n][n];
        List<Block> blackBlocks = new LinkedList<Block>();
        List<Block> whiteBlocks = new LinkedList<Block>();
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                Stone who = b.at(i, j);
                if (!who.equals(Stone.vacant) && processed[i][j] == false) {
                    Block g = new Block(b, i, j, processed);
                    switch (who) {
                        case black:
                            blackBlocks.add(g);
                            break;
                        case white:
                            whiteBlocks.add(g);
                            break;
                        default:
                            System.out.println("findGroups " + b + g + who);
                    }
                }
            }
        List<List<Block>> blocks = new LinkedList<List<Block>>();
        blocks.add(blackBlocks);
        blocks.add(whiteBlocks);
        return blocks;
    }
    private void grow(int i, int j) {
        if (!(0 <= i && i < n && 0 <= j && j < n)) return;
        if (!processed[i][j]) {
            Stone who = b.at(i, j);
            if (who.equals(this.who) || who.equals(Stone.vacant)) processed[i][j] = true;
            if (who == this.who) {
                points.add(new Point(i, j));
                grow(i - 1, j);
                grow(i, j - 1);
                grow(i, j + 1);
                grow(i + 1, j);
            }
        }
    }
    private void countLiberties() {
        l = 0;
        processed = new boolean[n][n];
        for (int k = 0; k < points.size(); k++) {
            Point p = (Point) points.get(k);
            count(p.x - 1, p.y);
            count(p.x, p.y - 1);
            count(p.x, p.y + 1);
            count(p.x + 1, p.y);
        }
    }
    private void count(int i, int j) {
        if (0 <= i && i < n && 0 <= j && j < n && !processed[i][j]) {
            if (b.at(i, j) == Stone.vacant) l++;
            processed[i][j] = true;
        }
    }
    static List<Block> capturedStones(List<List<Block>> blockLists) {
        List<Block> dead = new LinkedList<Block>();
        List<Block> blocks = blockLists.get(0);
        if (blocks != null) for (int i = 0; i < blocks.size(); i++) {
            Block g = (Block) blocks.get(i);
            //System.out.println(g);
            if (g.liberties() == 0) dead.add(g);
        }
        blocks = blockLists.get(1);
        if (blocks != null) for (int i = 0; i < blocks.size(); i++) {
            Block g = (Block) blocks.get(i);
             //System.out.println(g);
            if (g.liberties() == 0) dead.add(g);
        }
        return dead;
    }
    private Stone who;
    List<Point> points;
    private int n;
    private int l;
    transient private Board b;
    transient private boolean[][] processed;
}