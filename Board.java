import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;


public class Board extends JPanel{

    private int pacmanDx, pacmanDy; // przechowywanie kierunku ruchu pacmana
    private int score = 0;
    private int highScore = 0;  //najlepszy wynik

    private final int BLOCK_SIZE = 24;      // rozmiar pojedynczego bloku na planszy
    private final int N_BLOCKS = 15;        // liczba bloków w jednym rzędzie / kolumnie
    private final int SCREEN_SIZE = N_BLOCKS * BLOCK_SIZE;  //rozmiar całej planszy

    private volatile boolean running = true;

    private boolean[][] points;    //przechowywanie informacji o punktach na planszy.

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
        loadHighScore();
        initGame();


        // odbieranie zdarzeń z klawiatury
        addKeyListener(new TAdapter());
        setFocusable(true);
    }

    public void initGame(){
        // Tworzymy instancje Pac_mana i duchów z początkowymi instancjami
        pacman = new Pacman(7 * BLOCK_SIZE, 11 * BLOCK_SIZE);
        blinky = new Blinky(7 * BLOCK_SIZE, 5 * BLOCK_SIZE, pacman);
        pinky = new Pinky(1*BLOCK_SIZE, 1 * BLOCK_SIZE);
        inky = new Inky(13*BLOCK_SIZE, 1 * BLOCK_SIZE);
        clyde = new Clyde(1*BLOCK_SIZE, 13 * BLOCK_SIZE);


        points = new boolean[N_BLOCKS][N_BLOCKS];
        for(int i = 0 ; i < N_BLOCKS ; i++){
            for(int j = 0 ; j < N_BLOCKS ; j++){
                points[i][j] = true;
            }
        }
        score = 0;
        running = true;
    }


    public void stopGame(){
        running = false;
        if(score > highScore){
            highScore = score;
            saveHighScore();
        }

    }

    public void resetGame(){
        initGame();
        repaint();
    }

    private void loadHighScore(){
        try(BufferedReader reader = new BufferedReader(new FileReader("highscore.txt"))){
            highScore = Integer.parseInt(reader.readLine());
        }catch(IOException | NumberFormatException e){
            highScore = 0;  // jeśli plik nie istnieje lub jest uszkodzony
        }
    }

    private void saveHighScore(){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter("highscore.txt"))){
            writer.write(Integer.toString(highScore));
        }catch(IOException e){
            e.printStackTrace();
        }
    }


    //metoda rysująca zawartość planszy   wywołuje się kiedy jest potrzebna
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);   // wywołanie metody nadrzędnej
        drawMaze(g);  // rysowanie labiryntu
        drawPoints(g);

        pacman.draw(g); // rysowanie pacmana
        blinky.draw(g); // rysowanie duchów
        pinky.draw(g);
        inky.draw(g);
        clyde.draw(g);

        //rysowanie wyniku
        g.setColor(Color.white);
        g.drawString("Score: " + score, SCREEN_SIZE / 2 - 50, SCREEN_SIZE + 16);
        g.drawString("High Score: " + highScore, SCREEN_SIZE / 2 + 50, SCREEN_SIZE + 16);

        if(!running){
            g.setColor(Color.red);
            g.drawString("GAME OVER", SCREEN_SIZE / 2 - 40, SCREEN_SIZE /2);
            g.drawString("Press R to restart", SCREEN_SIZE/2 - 60, SCREEN_SIZE/2 + 20);
        }
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

    //Metoda rysująca punkty
    private void drawPoints(Graphics g){
        g.setColor(Color.white);
        for(int i = 0 ; i < N_BLOCKS ; i++){
            for(int j = 0 ; j < N_BLOCKS ; j++){
                if(points[i][j]){
                    g.fillOval(i * BLOCK_SIZE + BLOCK_SIZE / 3, j * BLOCK_SIZE + BLOCK_SIZE / 3, BLOCK_SIZE/3, BLOCK_SIZE/3);
                }
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
            } else if (key == KeyEvent.VK_R && !running){
                resetGame();
            }
        }
    }



    // PACMAN __________________________________________________________________
    //Aktualizacja pozycji Pacmana
    private void movePacman(){
        pacman.move(pacmanDx * BLOCK_SIZE, pacmanDy * BLOCK_SIZE);
        collectPoint();
        checkCollisions(); // sprawdzenie kolizji po ruchu pacmana
    }
    // Metoda do zbierania punktów
    private void collectPoint(){
        int pacmanX = pacman.getX() / BLOCK_SIZE;
        int pacmanY = pacman.getY() / BLOCK_SIZE;

        if(pacmanX >=0 && pacmanX < N_BLOCKS && pacmanY >=0 && pacmanY < N_BLOCKS){
            if(points[pacmanX][pacmanY]){
            points[pacmanX][pacmanY] = false;
            score ++;
            }
        }
    }

    //Metoda sprawdzająca kolizje
    private void checkCollisions() {
        int pacmanX = pacman.getX();
        int pacmanY = pacman.getY();

        if (collision(pacmanX, pacmanY, blinky) ||
                collision(pacmanX, pacmanY, pinky) ||
                collision(pacmanX, pacmanY, inky) ||
                collision(pacmanX, pacmanY, clyde)) {
            stopGame();
            }
    }
    private boolean collision(int pacmanX, int pacmanY, Ghost ghost){
        return pacmanX == ghost.x && pacmanY == ghost.y;
    }

    // GHOSTS ________________________________________________________________
    //aktualizacja pozycji duchów
    private void moveGhosts(){
        blinky.move();
        pinky.move();
        inky.move();
        clyde.move();
    }

    // UPDATING AGAIN AND AGAIN________________________________________________
    //wywoływanie głównej pętli gry do aktualizacji stanu gry
    public void gameUpdate(){
        if(running){
            movePacman();   // aktualizacja pozycji pacmanna
            moveGhosts();   // aktualizacja pozycji duchów
            repaint();      // przerysowanie planszy
        }
    }



}



















