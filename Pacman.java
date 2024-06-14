import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.Image;
import java.io.IOException;


public class Pacman {
    private int x,y;   // Pozycja pacmana na planszy
    private Board board;
    private Image pacman_left;
    private Image pacman_right;
    private Image pacman_up;
    private Image pacman_down;
    public String direction;

    // konstruktor klasy pacman
    public Pacman(int StartX, int StartY, Board board){
        x = StartX;
        y = StartY;
        this.direction = "left";
        this.board = board;
        loadImage();
    }

    // Metoda do przesuwania pacmana
    public void move(int dx, int dy, String direction) {
        int newX = x + dx;
        int newY = y + dy;


        if (newX >= 0 && newX < board.getSCREEN_SIZE() && newY >= 0 && newY < board.getSCREEN_SIZE()) {
            x = newX;
            y = newY;    //aktualizacja pozycji
            this.direction = direction;
        }
    }

    // Metoda rysująca Pacmana
    public void draw(Graphics g) {
        Image currentImage = pacman_left; //deafultowo

        switch (direction) {
            case "left":
                currentImage = pacman_left;
                break;
            case "right":
                currentImage = pacman_right;
                break;
            case "up":
                currentImage = pacman_up;
                break;
            case "down":
            default:
                currentImage = pacman_down;
                break;
        }

        g.drawImage(currentImage, x, y, null);
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    private void loadImage(){
        try {
            pacman_left = ImageIO.read(getClass().getResourceAsStream("left.png"));
            pacman_right = ImageIO.read(getClass().getResourceAsStream("right.png"));
            pacman_up = ImageIO.read(getClass().getResourceAsStream("up.png"));
            pacman_down = ImageIO.read(getClass().getResourceAsStream("down.png"));
            int width_scaled = board.getBlockSize();
            int height_scaled = board.getBlockSize();
            pacman_left = pacman_left.getScaledInstance(width_scaled, height_scaled, Image.SCALE_SMOOTH);
            pacman_right = pacman_right.getScaledInstance(width_scaled, height_scaled, Image.SCALE_SMOOTH);
            pacman_up = pacman_up.getScaledInstance(width_scaled, height_scaled, Image.SCALE_SMOOTH);
            pacman_down = pacman_down.getScaledInstance(width_scaled, height_scaled, Image.SCALE_SMOOTH);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setDirection(String direction){
        this.direction = direction;
    }
}

