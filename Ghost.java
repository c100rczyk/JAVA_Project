import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public abstract class Ghost {
    protected int x,y;   // pozycja ducha na planszy
    protected Color color;
    protected int dx, dy; //kierunek ruchu ducha
    private Random random = new Random();
    protected int moveDelay = 1; // Domyślna wartość opóźnienia ruchu
    private int moveCounter = 0; // Licznik do kontrolowania ruchu

    // Konstruktor klasy Ghost
    public Ghost(int startX, int startY, Color color){
        x = startX;
        y = startY;
        this.color = color;
        randomMove();
    }

    // Abstrakcyjna metoda ruchu, która będzie impolementowana w klasach potomnych.
    public abstract void move();

    //metoda rysująca ducha
    public void draw(Graphics g){
        g.setColor(color);
        g.fillRect(x,y,24,24); // prostokąt
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


    protected void updatePosition(){
        moveCounter++;
        if(moveCounter >= moveDelay){
            x += dx*24;
            y += dy*24;
            moveCounter = 0;
        }

    }

}

