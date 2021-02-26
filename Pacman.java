import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;



public class Pacman implements ActionListener{

    //muss aus Main class bestimmt werden
    boolean playing = true;

    public void pacmanOptics(){
        //Pacman hat 4 Bilder für jede mögliche Richtung
        Image up, down, left, right;

        //Hier werden die 4 Bilder geladen
        up = new ImageIcon("*/Bilder/PacUp.gif").getImage();
        down = new ImageIcon("*/Bilder/PacDown.gif").getImage();
        left = new ImageIcon("*/Bilder/PacLeft.gif").getImage();
        right = new ImageIcon("*/Bilder/PacRight.gif").getImage();
    }

    public class PacmanMovement extends KeyAdapter{
        //Wenn eine Taste gedrückt wird...
        public void keypressed(KeyEvent e){
            //... wird geguckt, welche Taste dies war...
            int key = e.getKeyCode();
            //....und wenn man spielt...    
            if(playing){
                //...bei passenden Tasten eine Aktion vorgenommen.
                if(key == KeyEvent.VK_UP){

                }else if(key == KeyEvent.VK_DOWN){

                }else if(key == KeyEvent.VK_LEFT){

                }else if(key == KeyEvent.VK_RIGHT){

                }else if(key == KeyEvent.VK_ESCAPE){

                }


            }

        }
    }

    public void pacmanPosition(){
        int pacmanRow = 0;
        int pacmanColumn = 0;
    }

}