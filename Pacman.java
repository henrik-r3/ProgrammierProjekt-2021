
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;


public class Pacman implements ActionListener{

    private boolean playing; //muss aus Main class bestimmt werden
    private Vector2Int direction; //Speichert die Richtung, in die sich der Pacman bewegt
    private Image imgup, imgdown, imgleft, imgright;  //Pacman hat 4 Bilder für jede mögliche Richtung
    private Vector2Int position = new Vector2Int(); //Position des Pacman
    private Vector2Int startposition = new Vector2Int();
    private int remaininglives;

    public Pacman(int startx, int starty, int lives){
        this.position.x = this.startposition.x = startx;
        this.position.y = this.startposition.y = starty;
        this.direction = Vector2Int.down;
        this.remaininglives = lives;
        this.imgup = new ImageIcon("*/Bilder/PacUp.gif").getImage();
        this.imgdown = new ImageIcon("*/Bilder/PacDown.gif").getImage();
        this.imgleft = new ImageIcon("*/Bilder/PacLeft.gif").getImage();
        this.imgright = new ImageIcon("*/Bilder/PacRight.gif").getImage(); 
    }

    public class Movement extends KeyAdapter{
        public void keypressed(KeyEvent e){
            int key = e.getKeyCode(); 
            if(playing){
                if(key == KeyEvent.VK_UP){
                    this.direction = Vector2Int.up;
                }else if(key == KeyEvent.VK_DOWN){
                    this.direction = Vector2Int.down;     
                }else if(key == KeyEvent.VK_LEFT){
                    this.direction = Vector2Int.left;     
                }else if(key == KeyEvent.VK_RIGHT){
                    this.direction = Vector2Int.right; 
                }else if(key == KeyEvent.VK_ESCAPE){
                    //Stop the timer, der muss noch implementiert werden
                }
            }
        }
    }

    public void calculatePosition(){
        if(playing){
            if(!Map.instance.IsCol(position.Add(direction))){
                position = position.Add(direction);
            }
            drawPacman();
        }
    }

    public void drawPacman(){
        Graphics g = new Graphics();
        if(this.direction.equals(Vector2Int.down)){
            g.drawImage(this.imgdown, this.position.x, this.position.y, null);
        }else if(this.direction.equals(Vector2Int.up)){
            g.drawImage(this.imgup, this.position.x, this.position.y, null);
        }else if(this.direction.equals(Vector2Int.left)){
            g.drawImage(this.imgleft, this.position.x, this.position.y, null);
        }else if(this.direction.equals(Vector2Int.right)){
            g.drawImage(this.imgright, this.position.x, this.position.y, null);
        }
    }

    public void calculatelives(){

        Boolean caught = Ghosts.hasbeencaught;
        if(caught){

            this.remaininglives--;
            this.position.x = this.startposition.x;
            this.position.y = this.startposition.y;
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

    public void gameactive(boolean isplaying){

        playing = isplaying;
    }
}
