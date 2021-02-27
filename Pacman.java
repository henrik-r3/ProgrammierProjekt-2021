
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;


public class Pacman implements ActionListener{

    private boolean playing; //muss aus Main class bestimmt werden
    private String direction; //Speichert die Richtung, in die sich der Pacman bewegt
    private Image imgup, imgdown, imgleft, imgright;  //Pacman hat 4 Bilder für jede mögliche Richtung
    private Vector2Int position = new Vector2Int(x, y); //Position des Pacman
    private Vector2Int startposition = new Vector2Int(x, y);
    private int remaininglives;

    public void Pacman1(int startx, int starty, int lives){
        this.position.x = this.startposition.x = startx;
        this.position.y = this.startposition.y = starty;
        this.direction = "down";
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

    public void calculatePosition(){
        if(playing){
            if(this.direction.equals("down") && this.position.y + 1 != Map.Tile.wall){
                this.position.y++;
            }else if(this.direction.equals("up") && this.position.y - 1 != Map.Tile.wall){
                this.position.y--;
            }else if(this.direction.equals("right") && this.position.x + 1 != Map.Tile.wall){
                this.position.x++;
            }else if(this.direction.equals("left") && this.position.x - 1 != Map.Tile.wall){
                this.position.x--;
            }
            drawPacman();
        }
    }

    public void drawPacman(){
        Graphics g = new Graphics();
        if(direction.equals("down")){
            g.drawImage(this.imgdown, this.position.x, this.position.y, null);
        }else if(this.direction.equals("up")){
            g.drawImage(this.imgup, this.position.x, this.position.y, null);
        }else if(this.direction.equals("left")){
            g.drawImage(this.imgleft, this.position.x, this.position.y, null);
        }else if(this.direction.equals("right")){
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
