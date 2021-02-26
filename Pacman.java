import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;



public class Pacman implements ActionListener{

                            
    boolean playing = true; //muss aus Main class bestimmt werden

    public void pacmanOptics(){
        //Pacman hat 4 Bilder für jede mögliche Richtung
        //Hier werden die 4 Bilder geladen
        Image pacup = new ImageIcon("*/Bilder/PacUp.gif").getImage();
        Image pacdown = new ImageIcon("*/Bilder/PacDown.gif").getImage();
        Image pacleft = new ImageIcon("*/Bilder/PacLeft.gif").getImage();
        Image pacright = new ImageIcon("*/Bilder/PacRight.gif").getImage();
    }

    String pacdirection = "";
    public class PacmanMovement extends KeyAdapter{
        //Wenn eine Taste gedrückt wird...
        public void keypressed(KeyEvent e){
            //... wird geguckt, welche Taste dies war...
            int key = e.getKeyCode();
            //....und wenn man spielt...    
            if(playing){
                //...bei passenden Tasten eine Aktion vorgenommen.
                if(key == KeyEvent.VK_UP){
                    pacdirection = "up";  
                }else if(key == KeyEvent.VK_DOWN){
                    pacdirection = "down";     
                }else if(key == KeyEvent.VK_LEFT){
                    pacdirection = "left";     
                }else if(key == KeyEvent.VK_RIGHT){
                    pacdirection = "right"; 
                }else if(key == KeyEvent.VK_ESCAPE){
                    //Stop the timer
                }
            }
        }

    }
    public void drawPacman(Graphics2D g2d){
        if(pacdirection.equals("down")){
            g2d.drawImage(pacDown, op, x, y);
        }

    }

    public void pacmanPosition(){
        int pacmanRow = 0;
        int pacmanColumn = 0;
    }

}