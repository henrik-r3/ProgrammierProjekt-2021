import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;



public class Pacman implements ActionListener{

                            
    boolean playing; //muss aus Main class bestimmt werden
    String direction; //Speichert die Richtung, in die sich der Pacman bewegt
    Image imgup, imgdown, imgleft, imgright;  //Pacman hat 4 Bilder für jede mögliche Richtung
    Vector2Int position = new Vector2Int(x, y); //Position des Pacman


    public void optics(){
        //Hier werden die 4 Bilder geladen
         imgup = new ImageIcon("*/Bilder/PacUp.gif").getImage();
         imgdown = new ImageIcon("*/Bilder/PacDown.gif").getImage();
         imgleft = new ImageIcon("*/Bilder/PacLeft.gif").getImage();
         imgright = new ImageIcon("*/Bilder/PacRight.gif").getImage();
         
    }

    public class Movement extends KeyAdapter{
        //Wenn eine Taste gedrückt wird...
        public void keypressed(KeyEvent e){
            //... wird geguckt, welche Taste dies war...
            int key = e.getKeyCode();
            //....und wenn man spielt...    
            if(playing){
                //...bei passenden Tasten eine Aktion vorgenommen.
                if(key == KeyEvent.VK_UP){
                    direction = "up";  
                }else if(key == KeyEvent.VK_DOWN){
                    direction = "down";     
                }else if(key == KeyEvent.VK_LEFT){
                    direction = "left";     
                }else if(key == KeyEvent.VK_RIGHT){
                    direction = "right"; 
                }else if(key == KeyEvent.VK_ESCAPE){
                    //Stop the timer
                }
            }
        }

    }
    //Soll den PAcman zeichnen
    public void drawPacman(Graphics2D g2d){
        if(direction.equals("down")){
            g2d.drawImage(imgdown, position.x, position.y - 1, this);
        }else if(direction.equals("up")){
            g2d.drawImage(imgup, position.x, position.y + 1, this);
        }else if(direction.equals("left")){
            g2d.drawImage(imgleft, position.x -1, position.y, this);
        }else if(direction.equals("right")){
            g2d.drawImage(imgright, position.x + 1, position.y, this);
        }
        int x = position.x;
        int y = position.y;
    }

    public void lives(Graphics2D g2d){

        Image heart = new ImageIcon("*/Bilder/Heart.png").getImage();
        int lives; //aus MAin bestimmen
    
        for(int i = 0; i < lives; i++){
            g2d.drawImage(heart, heart.getWidth(observer)*i + 20 , 300 - heart.getHeight(observer), this);
    
        }
    }

}
