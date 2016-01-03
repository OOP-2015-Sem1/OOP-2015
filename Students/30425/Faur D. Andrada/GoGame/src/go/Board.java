package go;

enum Stone {
    vacant, black, white; 
    Stone otherColor() {
        if (this == black) return white;
        else if (this == white) return black;
        else
            return vacant;
    }
}


public interface Board {
int size();
Stone at(int x, int y);
}
