// dziedziczy po klasie Ghost
import java.awt.Color;
import java.awt.Graphics;

public class Clyde extends Ghost {
    private Pacman pacman;
    public Clyde(int startX, int startY, Pacman pacman, Board board){
        super(startX, startY, Color.orange, board, "clyde.png");
        this.pacman = pacman;// początkowa pozycja i kolor
    }


    @Override
    public void move() {
        if (Math.random() < 0.5) {
            chasePacman();
        } else {
            fleePacman();
        }
        updatePosition();
    }

    private void chasePacman() {
        int pacmanX = pacman.getX();
        int pacmanY = pacman.getY();
        this.moveDelay = 2;

        if (x < pacmanX) {
            dx = 1;
        } else if (x > pacmanX) {
            dx = -1;
        } else {
            dx = 0;
        }

        if (y < pacmanY) {
            dy = 1;
        } else if (y > pacmanY) {
            dy = -1;
        } else {
            dy = 0;
        }
    }

    private void fleePacman() {
        int pacmanX = pacman.getX();
        int pacmanY = pacman.getY();
        this.moveDelay = 2;

        if (x < pacmanX) {
            dx = -1;
        } else if (x > pacmanX) {
            dx = 1;
        } else {
            dx = 0;
        }

        if (y < pacmanY) {
            dy = -1;
        } else if (y > pacmanY) {
            dy = 1;
        } else {
            dy = 0;
        }
    }

}