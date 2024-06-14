import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.Image;
import java.io.IOException;

public abstract class Ghost implements Runnable {
    protected int x,y;   // pozycja ducha na planszy
    protected Color color;
    protected int dx, dy; //kierunek ruchu ducha
    private Random random = new Random();
    protected int moveDelay = 1; // Domyślna wartość opóźnienia ruchu
    private int moveCounter = 0; // Licznik do kontrolowania ruchu
    protected Board board;
    private Image ghost_image;

    // Konstruktor klasy Ghost
    public Ghost(int startX, int startY, Color color, Board board, String image_path){
        x = startX;
        y = startY;
        this.color = color;
        this.board = board;
        loadImage(image_path);
        randomMove();
    }

    // Abstrakcyjna metoda ruchu, która będzie impolementowana w klasach potomnych.
    public abstract void move();

    //metoda rysująca ducha
    public void draw(Graphics g){
    g.drawImage(ghost_image, x, y, board.getBlockSize(), board.getBlockSize(), null);
    }



    protected void randomMove(){
        int direction = random.nextInt(4);

        switch(direction){
            case 0:
                dx = -1;
                dy = 0;
                break;
            case 1:
                dx = 1;
                dy = 0;
                break;
            case 2:
                dx = 0;
                dy = -1;
                break;
            case 3:
                dx = 0;
                dy = 1;
                break;
        }
    }


     protected void updatePosition() {
        moveCounter++;
        if (moveCounter >= moveDelay) {
            int newX = x + dx * 24;
            int newY = y + dy * 24;


            if (newX >= 0 && newX < board.getSCREEN_SIZE() && newY >= 0 && newY < board.getSCREEN_SIZE() &&
                board.getMaze()[newX / board.getBlockSize()][newY / board.getBlockSize()] == 0) {
                x = newX;
                y = newY;
            } else {

                randomMove();
            }

            moveCounter = 0;
        }
    }

    private void loadImage(String image_path){
        try{
            ghost_image = ImageIO.read(getClass().getResourceAsStream(image_path));
            int width_scaled = board.getBlockSize();
            int height_scaled = board.getBlockSize();
            ghost_image = ghost_image.getScaledInstance(width_scaled, height_scaled, Image.SCALE_SMOOTH);
        }catch(IOException e){
            e.printStackTrace();
        }

    }

    @Override
    public void run(){
        while(true){
            move();
            try{
                Thread.sleep(300);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

}

