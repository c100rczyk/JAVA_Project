// dziedziczy po klasie Ghost
import java.awt.Color;
import java.awt.Graphics;

public class Clyde extends Ghost {
    public Clyde(int startX, int startY){
        super(startX, startY, Color.orange);       // początkowa pozycja i kolor
    }


    @Override
    public void move(){
        randomMove();
        updatePosition();
    }

}