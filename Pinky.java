// dziedziczy po klasie Ghost
import java.awt.Color;
import java.awt.Graphics;

public class Pinky extends Ghost {
    private Pacman pacman;

    public Pinky(int startX, int startY, Pacman pacman, Board board){
        super(startX, startY, Color.pink, board, "pinky.png");
        this.pacman = pacman;
        new Thread(this).start();// początkowa pozycja i kolor
    }


    @Override
    public void move(){
        ambushPacman();
        updatePosition();
    }

    private void ambushPacman() {
        int pacmanX = pacman.getX();
        int pacmanY = pacman.getY();
        this.moveDelay = 2;


        int targetX = pacmanX;
        int targetY = pacmanY;

        switch (pacman.direction) {
            case "left":
                targetX -= 96;
                break;
            case "right":
                targetX += 96;
                break;
            case "up":
                targetY -= 96;
                break;
            case "down":
                targetY += 96;
                break;
        }

        if (x < targetX) {
            dx = 1;
        } else if (x > targetX) {
            dx = -1;
        } else {
            dx = 0;
        }

        if (y < targetY) {
            dy = 1;
        } else if (y > targetY) {
            dy = -1;
        } else {
            dy = 0;
        }
    }
}
