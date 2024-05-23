// dziedziczy po klasie Ghost

import java.awt.Color;
import java.awt.Graphics;

public class Blinky extends Ghost {

    private Pacman pacman;  //referencja do pacmana

    public Blinky(int startX, int startY){
        super(startX, startY, Color.red);       // początkowa pozycja i kolor
        this.pacman = pacman;   // przypisanie referencji do pacmana
    }


    @Override
    public void move(){
        chasePacman();  // podążanie za pacmanem
        updatePosition();
    }

    private void chasePacman(){
        int pacmanX = pacman.getX();
        int pacmanY = pacman.getY();

        if (x < pacmanX){
            dx = 1;
        } else if (x > pacmanX){
            dx = -1;
        } else {
            dx = 0;
        }

        if (y < pacmanY){
            dy = 1;
        } else if (y > pacmanY){
            dy = -1;
        } else{
            dy = 0;
        }
    }

}
