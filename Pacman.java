import java.awt.Color;
import java.awt.Graphics;



public class Pacman {
    private int x,y;   // Pozycja pacmana na planszy

    // konstruktor klasy pacman
    public Pacman(int StartX, int StartY){
        x = StartX;
        y = StartY;
    }

    // Metoda do przesuwania pacmana
    public void move(int dx, int dy){
        x += dx;
        y += dy;    //aktualizacja pozycji
    }

    // Metoda rysująca Pacmana
    public void draw(Graphics g){
        g.setColor(Color.yellow);
        g.fillArc(x,y,24,24,45,270);  // Pacman jako żółty łuk
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }
}

