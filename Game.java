import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;


public class Game {
    public static void main(String[] args) {
        //główny wątek graficzny gry
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();     // tworznie i wyświetlanie GUI gry
            }
        });

//        //uruchomienie wątkku
//        Runnable runnable = new SimpleCounter();
//        Thread thread = new Thread(runnable);
//        thread.start();

    }


    //Główne okno gry
    private static void createAndShowGUI() {
        javax.swing.JFrame frame = new javax.swing.JFrame("Pac-Man");
        frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);  // domyślna operacja zamykania okna

        Board board = new Board();   // tworznie instancji planszy gry
        frame.add(board);   //dodanie planszy do głównego okna
        frame.pack();   // Dopasowanie okna do zawartości
        frame.setVisible(true); //wyświetlenie okna

        // Dodajemy WindowListener, aby zatrzymywać wątek gry po zamknięciu okna
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                board.stopGame(); // Zatrzymujemy grę
            }
        });


        // wątek do uruchamiania głównej pętli gry
        new Thread(new Runnable(){
            public void run(){
                while(true){
                    board.gameUpdate();
                    try{
                        Thread.sleep(40);       // co 40 ms
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        }).start();


    }



}


