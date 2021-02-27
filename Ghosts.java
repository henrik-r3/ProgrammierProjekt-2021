
import java.awt.Graphics;
import javax.swing.ImageIcon;


public class Ghosts{

    private boolean playing; 
    private boolean caught = false; 
    private String direction; 
    private Image skin;
    private short color;
    private int numberGhosts = 0;
    private Vector2Int position = new Vector2Int(x, y); 
    private int followrange;  //muss in main noch deklariert werden

    public void Ghost(int startx, int starty, int range, int colorselected ){
       this.position.x = startx;
       this.position.y = starty;
       this.followrange = range;
       this.direction = "down";
       this.counter = 0;
       if(colorselected == 0){
           this.skin = new ImageIcon("*/Bilder/greenGhost.png").getImage();
           color = 0;
       }else if(colorselected == 1){
           this.skin = new ImageIcon("*/Bilder/yellowGhost.png").getImage();
           color = 1;
       }else if(colorselected == 2){
           this.skin = new ImageIcon("*/Bilder/redGhost.png").getImage();
           color = 2;
       }
       numberGhosts += 1;
    }

    public void selectGhostmovement(){
        if(playing){
            if(calculateDistance(this.position.x, this.position.y) < 2 ){
                caught = true;
            }else if(calculateDistance(this.position.x, this.position.y) <= followrange){
                this.position = huntPacman(this.position.x, this.position.y, this.color);
                drawGhosts(this.skin, this.position.x, this.position.y);
            }else{
                if(this.color == 0){
                    this.position = greenGhostMovement(this.position);
                    drawGhosts(this.skin, this.position.x, this.position.y);
                }else if(this.color == 1){
                    this.position = yellowGhostMovement(this.position);
                    drawGhosts(this.skin, this.position.x, this.position.y);
                }else if(this.color == 2){
                    this.position = redGhostMovement(this.position);
                    drawGhosts(this.skin, this.position.x, this.position.y);
                } 
            }
        }
    }

    public Vector2Int greenGhostMovement(Vector2Int oldposition){
        Vector2Int newPosition = new Vector2Int(oldposition);
        int rand;
        int possibilities;

        //Algortithmus der Geister muss noch erstellt werden
        if(direction.equals("down")){

        }else if(direction.equals("up")){

        }else if(direction.equals("left")){

        }else if(direction.equals("right")){

        }

        return newPosition;
    }
    
    public Vector2Int yellowGhostMovement(Vector2Int oldposition){
        Vector2Int newPosition = new Vector2Int(oldposition);

        //Algortithmus der Geister muss noch erstellt werden

        return newPosition;
    }

    public Vector2Int redGhostMovement(Vector2Int oldposition){
        Vector2Int newPosition = new Vector2Int(oldposition);

        //Algortithmus der Geister muss noch erstellt werden

        return newPosition;
    }

    public void drawGhosts(Image skinImage, int posx, int posy){
        Graphics g = new Graphics();
        g.drawImage(skinImage,posx, posy);
    }

    public int calculateDistance(int posx, int posy){

        Vector2Int ghostposition = new Vector2Int(posx, posy);
        Vector2Int pacmanposition = new Vector2Int(Pacman.getposition.x, Pacman.getposition.y);

        return Math.abs(packmanposition.x - ghostposition.x) + Math.abs(pacmanposition.y - ghostposition.y);         
    }

    public Vector2Int huntPacman(int ghostx, int ghosty, short color){
        Vector2Int ghostposition = new Vector2Int(ghostx, ghosty);
        Vector2Int pacmanposition = new Vector2Int(Pacman.getposition);

        if(ghostposition.x > pacmanposition.x && ghostposition.x - 1 != Map.Tile.wall){
            ghostposition.x--;
        }else if(ghostposition.x < pacmanposition.x && ghostposition.x + 1 != Map.Tile.wall){
            ghostposition.x++;
        }else if(ghostposition.y > pacmanposition.y && ghostposition.y - 1 != Map.Tile.wall){
            ghostposition.y--;
        }else if(ghostposition.y < pacmanposition.y && ghostposition.y + 1 != Map.Tile.wall){
            ghostposition.y++;
        }else{
            if(color == 0){
                ghostposition = greenGhostMovement(ghostposition);
            }else if(color == 1){
                ghostposition = yellowGhostMovement(ghostposition);
            }else if(color == 2){
                ghostposition = redGhostMovement(ghostposition);
            }
        }
        return ghostposition;
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