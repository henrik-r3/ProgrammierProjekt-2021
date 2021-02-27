
import java.awt.Graphics2D;
import javax.swing.ImageIcon;
import jdk.tools.jmod.Main;


public class Ghosts{

    private boolean playing; //muss aus Main class bestimmt werden 
    private boolean caught = false; //ob Ghost PAcman berühert hat
    private String direction; //Speichert die Richtung, in die sich der Geist bewegt
    private Image skin;  //Geister haben 3 Bilder für jede mögliche Farbe 0 = grün, 1 = gelb, 2 = rot; --> FArben müssen noch erstellt werden
    private short color;
    private int numberGhosts = 0; //Jetzt statisch, muss wenn varibel aus der main class bestimmt werden
    private Vector2Int position = new Vector2Int(x, y); //Position der einzelnen Geister
    private int followrange = Main.followrange;  //-> muss in main noch deklariert werden

    public void Ghost(int startx, int starty, int range, int colorselected ){
       this.position.x = startx;
       this.position.y = starty;
       this.followrange = range;
       if(colorselected == 0){
           this.skin = new ImageIcon("*/Bilder/greenGhost.gif").getImage();
           color = 0;
       }else if(colorselected == 1){
           this.skin = new ImageIcon("*/Bilder/yellowGhost.gif").getImage();
           color = 1;
       }else if(colorselected == 2){
           this.skin = new ImageIcon("*/Bilder/redGhost.gif").getImage();
           color = 2;
       }
       numberGhosts += 1;
    }

    public void selectGhostmovement(){
        if(playing){
            if(calculateDistance(this.position.x, this.position.y) < 2 ){
                caught = true;
            }else if(calculateDistance(this.position.x, this.position.y) <= followrange){
                this.position = huntPacman(this.position.x, this.position.y);
                drawGhosts(this.skin, this.position.x, this.position.y);
            }else{
                if(this.color == 0){
                    this.position = greenGhostMovement(posiion.x, position.y);
                    drawGhosts(this.skin, this.position.x, this.position.y);
                }else if(this.color == 1){
                    this.position = yellowGhostMovement(position.x, position.y);
                    drawGhosts(this.skin, this.position.x, this.position.y);
                }else if(this.color == 2){
                    this.position = redGhostMovement(position.x, position.y);
                    drawGhosts(this.skin, this.position.x, this.position.y);
                }    
            }
        }
    }

    public Vector2Int greenGhostMovement(int posx, int posy){
        Vector2Int newPosition = new Vector2Int(posx, posy);

        return newPosition;
    }
    
    public Vector2Int yellowGhostMovement(int posx, int posy){
        Vector2Int newPosition = new Vector2Int(posx, posy);

        return newPosition;
    }

    public Vector2Int redGhostMovement(int posx, int posy){
        Vector2Int newPosition = new Vector2Int(posx, posy);

        return newPosition;
    }

    public void drawGhosts(Image skinImage, int posx, int posy){

        g2d.drawImage(skinImage,posx, posy);
    }

    public int calculateDistance(int column, int row){

        Vector2Int ghostposition = new Vector2Int(column, row);
        Vector2Int pacmanposition = new Vector2Int(Pacman.x, Pacman.y);

        return Math.abs(packmanposition.x - ghostposition.x) + Math.abs(pacmanposition.y - ghostposition.y);         
    }

    public Vector2Int huntPacman(int ghostx, int ghosty){
        Vector2Int newPosition = new Vector2Int(ghostx, ghosty);
            //Algorithmus zur Berechnung des Wegs zum Pacman  !!!! -> nur für den Roten, der dann die ganze Zeit folgt??? der Rest maht einfach sein Ding?

        return newPosition;
    }

    public boolean hasbeencaught(){

        if(caught){
            caught = false;
            return true;
        } else{
            return caught;
        }
    }

    public void gameactive(boolean isplaying){

        playing = isplaying;
    }

}