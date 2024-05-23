// dziedziczy po klasie Ghost
import java.awt.Color;
import java.awt.Graphics;

public class Pinky extends Ghost {
    public Pinky(int startX, int startY){
        super(startX, startY, Color.pink);       // początkowa pozycja i kolor
    }


    @Override
    public void move(){
        randomMove();
        updatePosition();
    }

}
