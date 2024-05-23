// dziedziczy po klasie Ghost
import java.awt.Color;
import java.awt.Graphics;

public class Inky extends Ghost {
    public Inky(int startX, int startY){
        super(startX, startY, Color.cyan);       // początkowa pozycja i kolor
    }


    @Override
    public void move(){
        randomMove();
        updatePosition();
    }

}
