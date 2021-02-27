
import java.awt.Graphics;
import javax.swing.ImageIcon;



public class Ghosts{

    private boolean playing; 
    private boolean caught = false; 
    private Image skin;
    private short color;
    private int numberGhosts = 0;
    private Vector2Int position = new Vector2Int(x, y);
    private Vector2Int direction;  
    private int followrange;  //muss in main noch deklariert werden

    public Ghost(int startx, int starty, int range, int colorselected ){
       this.position.x = startx;
       this.position.y = starty;
       this.followrange = range;
       this.direction = Vector2Int.down;
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
        int rand = 0;
        int possibilities = determinepossiblities(oldposition);

        if(this.direction.equals(Vector2Int.down)){


           if(possibilities == 0){
               this.direction = Vector2Int.up;

           }else if(possibilities == 1){
                this.direction = Vector2Int.down;

           }else if(possibilities == 2){
                rand = (int) (Math.Random() * possibilities - 1);
                if(rand == 0){
                    this.direction = Vector2Int.down;
                }else if(rand == 1){
                    if(!Map.instance.IsCol(oldposition.Add(Vector2Int.right))){
                        this.direction = Vector2Int.right;
                    }else{
                        this.direction = Vector2Int.left;
                    }
                }

           }else if(possibilities == 3){
                rand = (int) (Math.Random() * possibilities - 1);
                if(rand == 0){
                    this.direction = Vector2Int.down;
                }else if(rand == 1){
                    this.direction = Vector2Int.right;
                }else if(rand == 2){
                    this.direction = Vector2Int.left;
                }

           }
        }   


        if(this.direction.equals(Vector2Int.up)){
            
           if(possibilities == 0){
                this.direction = Vector2Int.down;

            }else if(possibilities == 1){
                this.direction = Vector2Int.up;

            }else if(possibilities == 2){
                rand = (int) (Math.Random() * possibilities - 1);
                if(rand == 0){
                 this.direction = Vector2Int.up;
                }else if(rand == 1){
                    if(!Map.instance.IsCol(oldposition.Add(Vector2Int.right))){
                     this.direction = Vector2Int.right;
                    }else{
                     this.direction = Vector2Int.left;
                    }
                }

            }else if(possibilities == 3){
                rand = (int) (Math.Random() * possibilities - 1);
                if(rand == 0){
                 this.direction = Vector2Int.up;
                }else if(rand == 1){
                 this.direction = Vector2Int.right;
                }else if(rand == 2){
                 this.direction = Vector2Int.left;
                }

            }   
        }


        if(this.direction.equals(Vector2Int.left)){
            if(possibilities == 0){
                this.direction = Vector2Int.right;

            }else if(possibilities == 1){
                this.direction = Vector2Int.left;

            }else if(possibilities == 2){
                rand = (int) (Math.Random() * possibilities - 1);
                if(rand == 0){
                 this.direction = Vector2Int.left;
                }else if(rand == 1){
                    if(!Map.instance.IsCol(oldposition.Add(Vector2Int.up))){
                     this.direction = Vector2Int.up;
                    }else{
                     this.direction = Vector2Int.down;
                    }
                }

            }else if(possibilities == 3){
                rand = (int) (Math.Random() * possibilities - 1);
                if(rand == 0){
                 this.direction = Vector2Int.left;
                }else if(rand == 1){
                 this.direction = Vector2Int.up;
                }else if(rand == 2){
                 this.direction = Vector2Int.down;
                }

            }
        }
        if(this.direction.equals(Vector2Int.right)){
            if(possibilities == 0){
                this.direction = Vector2Int.left;

            }else if(possibilities == 1){
                this.direction = Vector2Int.right;

            }else if(possibilities == 2){
                rand = (int) (Math.Random() * possibilities - 1);
                if(rand == 0){
                 this.direction = Vector2Int.right;
                }else if(rand == 1){
                    if(!Map.instance.IsCol(oldposition.Add(Vector2Int.up))){
                     this.direction = Vector2Int.up;
                    }else{
                     this.direction = Vector2Int.down;
                    }
                }

            }else if(possibilities == 3){
                rand = (int) (Math.Random() * possibilities - 1);
                if(rand == 0){
                 this.direction = Vector2Int.right;
                }else if(rand == 1){
                 this.direction = Vector2Int.up;
                }else if(rand == 2){
                 this.direction = Vector2Int.down;
                }

            }
        }

        oldposition = oldposition.Add(direction);
        return oldposition;
    }
    
    public Vector2Int yellowGhostMovement(Vector2Int oldposition){
        int rnd;
        int possibilities = determinepossiblities(oldposition); 

        if (this.direction == Vector2Int.down){
            if(Map.instance.IsCol(this.position.Add(this.direction))){
                if(possibilities == 0){
                    direction = Vector2Int.up;   
                }else{
                    rnd = (int) (Math.random()* (possibilities - 1));                    
                    if(rnd == 0){
                        if(!Map.instance.IsCol(oldposition.Add(Vector2Int.right))){
                            direction = Vector2Int.right;
                        }else{
                            direction = Vector2Int.left;
                        }
                    }else{
                        direction = Vector2Int.left;
                    }
                    
                }
            }


        }else if (this.direction == Vector2Int.up){
            if(Map.instance.IsCol(this.position.Add(this.direction))){
                if(possibilities == 0){
                    direction = Vector2Int.down;   
                }else{
                    rnd = (int) (Math.random()* (possibilities - 1));                    
                    if(rnd == 0){
                        if(!Map.instance.IsCol(oldposition.Add(Vector2Int.right))){
                            direction = Vector2Int.right;
                        }else{
                            direction = Vector2Int.left;
                        }
                    }else{
                        direction = Vector2Int.left;
                    }
                    
                }
            }
            

        }else if (this.direction == Vector2Int.left){
            if(Map.instance.IsCol(this.position.Add(this.direction))){
                if(possibilities == 0){
                    direction = Vector2Int.right;   
                }else{
                    rnd = (int) (Math.random()* (possibilities - 1));                    
                    if(rnd == 0){
                        if(!Map.instance.IsCol(oldposition.Add(Vector2Int.up))){
                            direction = Vector2Int.up;
                        }else{
                            direction = Vector2Int.down;
                        }
                    }else{
                        direction = Vector2Int.down;
                    }
                    
                }
            }


        }else if (this.direction == Vector2Int.right){
            if(Map.instance.IsCol(this.position.Add(this.direction))){
                if(possibilities == 0){
                    direction = Vector2Int.left;   
                }else{
                    rnd = (int) (Math.random()* (possibilities - 1));                    
                    if(rnd == 0){
                        if(!Map.instance.IsCol(oldposition.Add(Vector2Int.up))){
                            direction = Vector2Int.up;
                        }else{
                            direction = Vector2Int.down;
                        }
                    }else{
                        direction = Vector2Int.down;
                    }
                    
                }
            }
        }

        oldposition = oldposition.Add(direction);
        return oldposition;
    }

    public Vector2Int redGhostMovement(Vector2Int oldposition){

        oldposition = huntPacman(oldposition.x, oldposition.y, this.color);

        return oldposition;
    }
    
    public int determinepossiblities(Vector2Int currentposition){
        int possibilities = 0;
        
        if(!Map.instance.IsCol(currentposition.Add(Vector2Int.down))){
            possibilities++;
        }
        if(!Map.instance.IsCol(currentposition.Add(Vector2Int.up))){
            possibilities++;
        }
        if(!Map.instance.IsCol(currentposition.Add(Vector2Int.left))){
            possibilities++;
        }
        if(!Map.instance.IsCol(currentposition.Add(Vector2Int.right))){
            possibilities++;
        }

        return possibilities - 1;
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

        if(ghostposition.x > pacmanposition.x && Map.instance.IsCol(position.Add(Vector2Int.left))){
            ghostposition.x--;
        }else if(ghostposition.x < pacmanposition.x && Map.instance.IsCol(position.Add(Vector2Int.right))){
            ghostposition.x++;
        }else if(ghostposition.y > pacmanposition.y && Map.instance.IsCol(position.Add(Vector2Int.up))){
            ghostposition.y--;
        }else if(ghostposition.y < pacmanposition.y && Map.instance.IsCol(position.Add(Vector2Int.down))){
            ghostposition.y++;
        }else{
            if(color == 0){
                ghostposition = greenGhostMovement(ghostposition);
            }else if(color == 1){
                ghostposition = yellowGhostMovement(ghostposition);
            }else if(color == 2){
                ghostposition = greenGhostMovement(ghostposition);
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

    public int getnumberGhosts(){

        return numberGhosts;
    }

}
