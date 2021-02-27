
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;


public class Pacman implements ActionListener{

    private boolean playing; //muss aus Main class bestimmt werden
    private String direction; //Speichert die Richtung, in die sich der Pacman bewegt
    private Image imgup, imgdown, imgleft, imgright;  //Pacman hat 4 Bilder für jede mögliche Richtung
    private Vector2Int position = new Vector2Int(x, y); //Position des Pacman
    private int remaininglives;

    public void Pacman1(int startx, int starty, int lives){
        this.position.x = startx;
        this.position.y = starty;
        this.direction = "down";
        this.remaininglives = lives;
    }

    public void optics(){
        //Hier werden die 4 Bilder geladen
         imgup = new ImageIcon("*/Bilder/PacUp.gif").getImage();
         imgdown = new ImageIcon("*/Bilder/PacDown.gif").getImage();
         imgleft = new ImageIcon("*/Bilder/PacLeft.gif").getImage();
         imgright = new ImageIcon("*/Bilder/PacRight.gif").getImage();      
    }

    public class Movement extends KeyAdapter{
        public void keypressed(KeyEvent e){
            int key = e.getKeyCode(); 
            if(playing){
                if(key == KeyEvent.VK_UP){
                    this.direction = "up";  
                }else if(key == KeyEvent.VK_DOWN){
                    this.direction = "down";     
                }else if(key == KeyEvent.VK_LEFT){
                    this.direction = "left";     
                }else if(key == KeyEvent.VK_RIGHT){
                    this.direction = "right"; 
                }else if(key == KeyEvent.VK_ESCAPE){
                    //Stop the timer, der muss noch implementiert werden
                }
            }
        }
    }

    public void drawPacman(){
        Graphics g = new Graphics();
        if(direction.equals("down")){
            g.drawImage(imgdown, position.x, position.y - 1, this);
        }else if(direction.equals("up")){
            g.drawImage(imgup, position.x, position.y + 1, this);
        }else if(direction.equals("left")){
            g.drawImage(imgleft, position.x -1, position.y, this);
        }else if(direction.equals("right")){
            g.drawImage(imgright, position.x + 1, position.y, this);
        }
        int x = position.x;
        int y = position.y;
    }

    public void calculatelives(){

        Boolean caught = Ghosts.hasbeencaught;
        if(caught){

            this.remaininglives--;
        }
        if(this.remaininglives < 1){
            //stop timer, Score screen
        }else{
            drawlives();
        }
    }

    public void drawlives(){

        Image heart = new ImageIcon("*/Bilder/Heart.png").getImage();
    
        for(int i = 0; i < this.remaininglives; i++){
            Graphics g = new Graphics();
            g.drawImage(heart, heart.getWidth(null)* i + 10 , 300 - (heart.getHeight(null) + 10) , null);   
        }
    }

    public Vector2Int getposition(){

        return this.position;
    }

    public void setDirection(String newdirection){
        this.direction = newdirection;

    }

}
