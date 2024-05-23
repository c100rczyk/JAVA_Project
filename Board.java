import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class Board extends JPanel{

    private int pacmanDx, pacmanDy; // przechowywanie kierunku ruchu pacmana

    private final int BLOCK_SIZE = 24;      // rozmiar pojedynczego bloku na planszy
    private final int N_BLOCKS = 15;        // liczba bloków w jednym rzędzie / kolumnie
    private final int SCREEN_SIZE = N_BLOCKS * BLOCK_SIZE;  //rozmiar całej planszy

    // instancje
    private Pacman pacman;
    private Blinky blinky;
    private Pinky pinky;
    private Inky inky;
    private Clyde clyde;


    // konstruktor klasy Board
    public Board(){
        setPreferredSize(new Dimension(SCREEN_SIZE, SCREEN_SIZE));  //ustawienie rozmiaru planszy
        setBackground(Color.BLACK); //kolor tła czarny

        // Tworzymy instancje Pac_mana i duchów z początkowymi instancjami
        pacman = new Pacman(7 * BLOCK_SIZE, 11 * BLOCK_SIZE);
        blinky = new Blinky(7 * BLOCK_SIZE, 5 * BLOCK_SIZE);
        pinky = new Pinky(1*BLOCK_SIZE, 1 * BLOCK_SIZE);
        inky = new Inky(13*BLOCK_SIZE, 1 * BLOCK_SIZE);
        clyde = new Clyde(1*BLOCK_SIZE, 13 * BLOCK_SIZE);


        // odbieranie zdarzeń z klawiatury
        addKeyListener(new TAdapter());
        setFocusable(true);
    }

    //metoda rysująca zawartość planszy
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);   // wywołanie metody nadrzędnej
        drawMaze(g);  // rysowanie labiryntu

        pacman.draw(g); // rysowanie pacmana
        blinky.draw(g); // rysowanie duchów
        pinky.draw(g);
        inky.draw(g);
        clyde.draw(g);

    }

    //metoda rysująca labirynt
    private void drawMaze(Graphics g){
        g.setColor(Color.blue);
        for(int x = 0; x < SCREEN_SIZE; x+= BLOCK_SIZE){
            for(int y = 0; y < SCREEN_SIZE; y+= BLOCK_SIZE){
                g.drawRect(x,y,BLOCK_SIZE, BLOCK_SIZE);
            }
        }
    }

    // obsługiwanie zdarzeń z klawiatury
    private class TAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            int key = e.getKeyCode();

            //kierunek ruchu w zależności od naciśniętego klawisza
            if(key == KeyEvent.VK_LEFT){
                pacmanDx = -1;
                pacmanDy = 0;
            } else if(key == KeyEvent.VK_RIGHT){
                pacmanDx = 1;
                pacmanDy = 0;
            } else if(key == KeyEvent.VK_UP){
                pacmanDx = 0;
                pacmanDy = -1;
            } else if(key == KeyEvent.VK_DOWN){
                pacmanDx = 0;
                pacmanDy = 1;
            }
        }
    }

    //Aktualizacja pozycji Pacmana
    private void movePacman(){
        pacman.move(pacmanDx * BLOCK_SIZE, pacmanDy * BLOCK_SIZE);
    }

    //aktualizacja pozycji duchów
    private void moveGhosts(){
        blinky.move();
        pinky.move();
        inky.move();
        clyde.move();
    }


    //wywoływanie głównej pętli gry do aktualizacji stanu gry
    public void gameUpdate(){
        movePacman();   // aktualizacja pozycji pacmanna
        moveGhosts();   // aktualizacja pozycji duchów
        repaint();      // przerysowanie planszy
    }



}



















