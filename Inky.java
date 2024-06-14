// dziedziczy po klasie Ghost
import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Inky extends Ghost {
    private Pacman pacman;
    private Ghost[] otherGhosts;
    private Random random = new Random();
    public Inky(int startX, int startY, Pacman pacman,  Board board, Ghost[] otherGhosts){
        super(startX, startY, Color.cyan, board, "inky.png");
        this.pacman = pacman;
        this.otherGhosts = otherGhosts;
        new Thread(this).start();// początkowa pozycja i kolor
    }


    @Override
    public void move(){
        mimicOtherGhost();
        updatePosition();
    }

 private void mimicOtherGhost() {
        Ghost ghostToMimic = otherGhosts[random.nextInt(otherGhosts.length)];
        this.dx = ghostToMimic.dx;
        this.dy = ghostToMimic.dy;
        this.moveDelay = 2;
    }


}
