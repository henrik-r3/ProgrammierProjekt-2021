
import java.awt.*;
import java.security.KeyPair;
import java.util.Vector;

import javax.swing.ImageIcon;

public class Ghosts extends GameObject{


    private Image skin;
    private Image scared;
    private String color;
    private int numberGhosts = 0;
    private Vector2Int position = new Vector2Int(), startposition = new Vector2Int();
    private Vector2Int direction;  
    private int followrange;  
    private boolean powerberry = false;
    private int keephunting;
    private int keeprunning;
    private int countchanges;
    private AStar.Grid grid;

    public Ghosts(int startx, int starty, int range, String colorselected ){
       this.position.x = this.startposition.x = startx;
       this.position.y = this.startposition.y = starty;
       this.followrange = range;
       this.direction = Vector2Int.down;
       this.keephunting = 0;
       this.countchanges = 0;
       this.keeprunning = 0;
       if(colorselected.equals("green")){
           this.skin = new ImageIcon("Bilder/greenGhost.png").getImage();
           this.color = colorselected;
       }else if(colorselected.equals("yellow")){
           this.skin = new ImageIcon("Bilder/yellowGhost.png").getImage();
           this.color = colorselected;
       }else if(colorselected.equals("red")){
           this.skin = new ImageIcon("Bilder/redGhost.png").getImage();
           this.color = colorselected;
       }else if(colorselected.equals("pink")){
           this.skin = new ImageIcon("Bilder/pinkGhost.png").getImage();
           this.color = colorselected;
       }
       this.scared = new ImageIcon("Bilder/scaredGhost.png").getImage();
       grid = new AStar.Grid();

    }


    long moveTimer = 310;
    long timer = 0;

    @Override
    public void Update(long deltaTime){
        timer += deltaTime;
        if(timer >= moveTimer)
        {   
            timer = 0;
            powerberry = Pacman.pacinstance.gethunting();
            selectGhostmovement();

        }

    }

    @Override
    public void Draw(){

        if(powerberry)
            drawGhosts(scared, this.position.x, this.position.y);
        else
            drawGhosts(this.skin, this.position.x, this.position.y);


    }

    public void selectGhostmovement(){


            if(powerberry){
                if(calculateDistance(this.position.x, this.position.y) < 2 ){
                    Score.scoreinstance.eatsGhost();
                    this.position = startposition;
                }
                this.position = runfromPacman(this.position.x, this.position.y, this.color);
            }else{
                countchanges = 0;
                keeprunning = 0;
                if(calculateDistance(this.position.x, this.position.y) < 2 ){
                    Pacman.pacinstance.hasbeencaught();
                }
                if(calculateDistance(this.position.x, this.position.y) <= followrange){
                    this.position = huntPacman(this.position.x, this.position.y);
                    keephunting++;
        
                }else if(keephunting != 0){
                    if(keephunting > 5){
                        keephunting = 0;
                    }else{
                        keephunting++;

                    }
                    this.position = huntPacman(this.position.x, this.position.y);

                }else{
                    if(this.color.equals("green")){
                        this.position = greenGhostMovement(this.position);

                    }else if(this.color.equals("yellow")){
                        this.position = yellowGhostMovement(this.position);

                    }else if(this.color.equals("red")){
                        this.position = redGhostMovement(this.position);

                    }else if(this.color.equals("pink")){
                        this.position = pinkGhostMovement(this.position);

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
               if(!Map.instance.IsCol(oldposition.Add(Vector2Int.down)) && cangodown(oldposition)){
                    this.direction = Vector2Int.down;
               }else if(!Map.instance.IsCol(oldposition.Add(Vector2Int.left)) && cangoleft(oldposition)){
                    this.direction = Vector2Int.left;
               }else if(!Map.instance.IsCol(oldposition.Add(Vector2Int.right)) && cangoright(oldposition)){
                   this.direction = Vector2Int.right;
               }else{
                   this.direction = Vector2Int.up;
               }

           }else if(possibilities == 2){
                rand = (int) (Math.random() * 1);
                if(rand == 0 && !Map.instance.IsCol(oldposition.Add(Vector2Int.down)) && cangodown(oldposition)){
                    this.direction = Vector2Int.down;
                }else{
                    rand = (int) (Math.random() * 1);
                    if(rand == 0){
                        if(!Map.instance.IsCol(oldposition.Add(Vector2Int.right)) && cangoright(oldposition)){
                            this.direction = Vector2Int.right;
                        }else if(!Map.instance.IsCol(oldposition.Add(Vector2Int.left)) && cangoleft(oldposition)){
                            this.direction = Vector2Int.left;
                        }else if (!Map.instance.IsCol(oldposition.Add(Vector2Int.down)) && cangodown(oldposition)){
                            this.direction = Vector2Int.down;
                        }else{
                            this.direction = Vector2Int.up;
                        }    
                    }else{
                        if(!Map.instance.IsCol(oldposition.Add(Vector2Int.left)) && cangoleft(oldposition)){
                            this.direction = Vector2Int.left;
                        }else if(!Map.instance.IsCol(oldposition.Add(Vector2Int.right)) && cangoright(oldposition)){
                            this.direction = Vector2Int.right;
                        }else if (!Map.instance.IsCol(oldposition.Add(Vector2Int.down)) && cangodown(oldposition)){
                            this.direction = Vector2Int.down;
                        }else{
                            this.direction = Vector2Int.up;
                        }
                    }
                }

           }else if(possibilities == 3){
                rand = (int) (Math.random() * possibilities - 1);
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
                if(!Map.instance.IsCol(oldposition.Add(Vector2Int.up)) && cangoup(oldposition)){
                     this.direction = Vector2Int.up;
                }else if(!Map.instance.IsCol(oldposition.Add(Vector2Int.left)) && cangoleft(oldposition)){
                     this.direction = Vector2Int.left;
                }else if(!Map.instance.IsCol(oldposition.Add(Vector2Int.right)) && cangoright(oldposition)){
                    this.direction = Vector2Int.right;
                }else{
                    this.direction = Vector2Int.down;
                }

            }else if(possibilities == 2){
                rand = (int) (Math.random() * possibilities - 1);
                if(rand == 0 && !Map.instance.IsCol(oldposition.Add(Vector2Int.up)) && cangoup(oldposition)){
                    this.direction = Vector2Int.up;
                }else{
                    rand = (int) (Math.random());
                    if(rand == 0){
                        if(!Map.instance.IsCol(oldposition.Add(Vector2Int.right)) && cangoright(oldposition)){
                            this.direction = Vector2Int.right;
                        }else if(!Map.instance.IsCol(oldposition.Add(Vector2Int.left)) && cangoleft(oldposition)){
                            this.direction = Vector2Int.left;
                        }else if (!Map.instance.IsCol(oldposition.Add(Vector2Int.up)) && cangoup(oldposition)){
                            this.direction = Vector2Int.up;
                        }else{
                            this.direction = Vector2Int.down;
                        }
                    }else{
                        if(!Map.instance.IsCol(oldposition.Add(Vector2Int.left)) && cangoleft(oldposition)){
                            this.direction = Vector2Int.left;
                        }else if(!Map.instance.IsCol(oldposition.Add(Vector2Int.right)) && cangoright(oldposition)){
                            this.direction = Vector2Int.right;
                        }else if (!Map.instance.IsCol(oldposition.Add(Vector2Int.up)) && cangoup(oldposition)){
                            this.direction = Vector2Int.up;
                        }else{
                            this.direction = Vector2Int.down;
                        }
                    }
                }
                

            }else if(possibilities == 3){
                rand = (int) (Math.random() * possibilities - 1);
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
                if(!Map.instance.IsCol(oldposition.Add(Vector2Int.left)) && cangoleft(oldposition)){
                     this.direction = Vector2Int.left;
                }else if(!Map.instance.IsCol(oldposition.Add(Vector2Int.up)) && cangoup(oldposition)){
                     this.direction = Vector2Int.up;
                }else if(!Map.instance.IsCol(oldposition.Add(Vector2Int.down)) && cangodown(oldposition)){
                    this.direction = Vector2Int.down;
                }else{
                    this.direction = Vector2Int.right;
                }

            }else if(possibilities == 2){
                rand = (int) (Math.random() * possibilities - 1);
                if(rand == 0 && !Map.instance.IsCol(oldposition.Add(Vector2Int.left)) && cangoleft(oldposition)){
                    this.direction = Vector2Int.left;
                }else{
                    rand = (int) (Math.random());
                    if(rand == 0){
                        if(!Map.instance.IsCol(oldposition.Add(Vector2Int.up)) && cangoup(oldposition)){
                            this.direction = Vector2Int.up;
                        }else if(!Map.instance.IsCol(oldposition.Add(Vector2Int.down)) && cangodown(oldposition)){
                            this.direction = Vector2Int.down;
                        }else if (!Map.instance.IsCol(oldposition.Add(Vector2Int.left)) && cangoleft(oldposition)){
                            this.direction = Vector2Int.left;
                        }else{
                            this.direction = Vector2Int.right;
                        }
                    }else{
                        if(!Map.instance.IsCol(oldposition.Add(Vector2Int.down)) && cangodown(oldposition)){
                            this.direction = Vector2Int.down;
                        }else if(!Map.instance.IsCol(oldposition.Add(Vector2Int.up)) && cangoup(oldposition)){
                            this.direction = Vector2Int.up;
                        }else if (!Map.instance.IsCol(oldposition.Add(Vector2Int.left)) && cangoleft(oldposition)){
                            this.direction = Vector2Int.left;
                        }else{
                            this.direction = Vector2Int.right;
                        }
                    }
                }

            }else if(possibilities == 3){
                rand = (int) (Math.random() * possibilities - 1);
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
                if(!Map.instance.IsCol(oldposition.Add(Vector2Int.right)) && cangoright(oldposition)){
                     this.direction = Vector2Int.right;
                }else if(!Map.instance.IsCol(oldposition.Add(Vector2Int.up)) && cangoup(oldposition)){
                     this.direction = Vector2Int.up;
                }else if(!Map.instance.IsCol(oldposition.Add(Vector2Int.down)) && cangodown(oldposition)){
                    this.direction = Vector2Int.down;
                }else{
                    this.direction = Vector2Int.left;
                }

            }else if(possibilities == 2){
                rand = (int) (Math.random() * possibilities - 1);
                if(rand == 0 && !Map.instance.IsCol(oldposition.Add(Vector2Int.right)) && cangoright(oldposition)){
                    this.direction = Vector2Int.right;
                }else{
                    rand = (int) (Math.random());
                    if(rand == 0){
                        if(!Map.instance.IsCol(oldposition.Add(Vector2Int.up)) && cangoup(oldposition)){
                            this.direction = Vector2Int.up;
                        }else if(!Map.instance.IsCol(oldposition.Add(Vector2Int.down)) && cangodown(oldposition)){
                            this.direction = Vector2Int.down;
                        }else if (!Map.instance.IsCol(oldposition.Add(Vector2Int.right)) && cangoright(oldposition)){
                            this.direction = Vector2Int.right;
                        }else{
                            this.direction = Vector2Int.left;
                        }
                    }else{
                        if(!Map.instance.IsCol(oldposition.Add(Vector2Int.down)) && cangodown(oldposition)){
                            this.direction = Vector2Int.down;
                        }else if(!Map.instance.IsCol(oldposition.Add(Vector2Int.up)) && cangoup(oldposition)){
                            this.direction = Vector2Int.up;
                        }else if (!Map.instance.IsCol(oldposition.Add(Vector2Int.right)) && cangoright(oldposition)){
                            this.direction = Vector2Int.right;
                        }else{
                            this.direction = Vector2Int.left;
                        }
                    }
                }

            }else if(possibilities == 3){
                rand = (int) (Math.random() * possibilities - 1);
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

        if (this.direction == Vector2Int.down && Map.instance.IsCol(this.position.Add(this.direction))){
            
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


        }else if (this.direction == Vector2Int.up && Map.instance.IsCol(this.position.Add(this.direction))){

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
            

        }else if (this.direction == Vector2Int.left && Map.instance.IsCol(this.position.Add(this.direction))){

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


        }else if (this.direction == Vector2Int.right && Map.instance.IsCol(this.position.Add(this.direction))){
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

        oldposition = oldposition.Add(direction);
        return oldposition;
    }

    public Vector2Int redGhostMovement(Vector2Int oldposition){

        oldposition = huntPacman(oldposition.x, oldposition.y);

        return oldposition;
    }

    public Vector2Int pinkGhostMovement(Vector2Int oldposition){
        
        Vector2Int pacmanposition = Pacman.pacinstance.getposition();
        int rnd = (int) (Math.random()*3);
        boolean althunt = false;
        
        if(rnd == 0){
            pacmanposition.x += 2;
        }else if(rnd == 1){
            pacmanposition.x -= 2;
        }else if(rnd == 2){
            pacmanposition.y += 2;
        }else if(rnd == 3){
            pacmanposition.y -= 2;
        }

        if(this.direction == Vector2Int.down){
            
            if(oldposition.y < pacmanposition.y && !Map.instance.IsCol(oldposition.Add(Vector2Int.down))){
                this.direction = Vector2Int.down;
            }else if(oldposition.x < pacmanposition.x && !Map.instance.IsCol(oldposition.Add(Vector2Int.right))){
                this.direction = Vector2Int.right;
            }else if(oldposition.x > pacmanposition.x && !Map.instance.IsCol(oldposition.Add(Vector2Int.left))){
                this.direction = Vector2Int.left;
            }else{

                Vector2Int newposition = new Vector2Int(huntPacman(oldposition.x, oldposition.y));

                if(oldposition.Add(Vector2Int.down).equals(newposition)){
                    this.direction = Vector2Int.down;
                }else if(oldposition.Add(Vector2Int.right).equals(newposition)){
                    this.direction = Vector2Int.right;
                }else if(oldposition.Add(Vector2Int.left).equals(newposition)){
                    this.direction = Vector2Int.left;
                }else if(oldposition.Add(Vector2Int.up).equals(newposition)){
                    this.direction = Vector2Int.up;
                }
                
            }

        }else if(this.direction == Vector2Int.up){
            if(oldposition.y > pacmanposition.y && !Map.instance.IsCol(oldposition.Add(Vector2Int.up))){
                this.direction = Vector2Int.up;
            }else if(oldposition.x < pacmanposition.x && !Map.instance.IsCol(oldposition.Add(Vector2Int.right))){
                this.direction = Vector2Int.right;
            }else if(oldposition.x > pacmanposition.x && !Map.instance.IsCol(oldposition.Add(Vector2Int.left))){
                this.direction = Vector2Int.left;
            }else{
                
                Vector2Int newposition = new Vector2Int(huntPacman(oldposition.x, oldposition.y));

                if(oldposition.Add(Vector2Int.up).equals(newposition)){
                    this.direction = Vector2Int.up;
                }else if(oldposition.Add(Vector2Int.right).equals(newposition)){
                    this.direction = Vector2Int.right;
                }else if(oldposition.Add(Vector2Int.left).equals(newposition)){
                    this.direction = Vector2Int.left;
                }else if(oldposition.Add(Vector2Int.down).equals(newposition)){
                    this.direction = Vector2Int.down;
                }                
            }

        }else if(this.direction == Vector2Int.right){
            if(oldposition.x < pacmanposition.x && !Map.instance.IsCol(oldposition.Add(Vector2Int.right))){
                this.direction = Vector2Int.right;
            }else if(oldposition.y < pacmanposition.y && !Map.instance.IsCol(oldposition.Add(Vector2Int.down))){
                this.direction = Vector2Int.down;
            }else if(oldposition.y > pacmanposition.y && !Map.instance.IsCol(oldposition.Add(Vector2Int.up))){
                this.direction = Vector2Int.up;                    
            }else{
                Vector2Int newposition = new Vector2Int(huntPacman(oldposition.x, oldposition.y));

                if(oldposition.Add(Vector2Int.right).equals(newposition)){
                    this.direction = Vector2Int.right;
                }else if(oldposition.Add(Vector2Int.up).equals(newposition)){
                    this.direction = Vector2Int.up;
                }else if(oldposition.Add(Vector2Int.down).equals(newposition)){
                    this.direction = Vector2Int.down;
                }else if(oldposition.Add(Vector2Int.left).equals(newposition)){
                    this.direction = Vector2Int.left;
                }
                    
            }

        }else if(this.direction == Vector2Int.left){
            if(oldposition.x > pacmanposition.x && !Map.instance.IsCol(oldposition.Add(Vector2Int.left))){
                this.direction = Vector2Int.left;
            }else if(oldposition.y < pacmanposition.y && !Map.instance.IsCol(oldposition.Add(Vector2Int.down))){
                this.direction = Vector2Int.down;
            }else if(oldposition.y > pacmanposition.y && !Map.instance.IsCol(oldposition.Add(Vector2Int.up))){
                this.direction = Vector2Int.up;                   
            }else{
                Vector2Int newposition = new Vector2Int(huntPacman(oldposition.x, oldposition.y));

                if(oldposition.Add(Vector2Int.left).equals(newposition)){
                    this.direction = Vector2Int.left;
                }else if(oldposition.Add(Vector2Int.up).equals(newposition)){
                    this.direction = Vector2Int.up;
                }else if(oldposition.Add(Vector2Int.down).equals(newposition)){
                    this.direction = Vector2Int.down;
                }else if(oldposition.Add(Vector2Int.right).equals(newposition)){
                    this.direction = Vector2Int.right;
                }
                   
            }
        }
        
        oldposition = oldposition.Add(direction);
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
        Vector2Int drawvector = new Vector2Int(posx, posy);
        Game.instance.drawImage(skinImage, drawvector);
    }

    public int calculateDistance(int posx, int posy){

        Vector2Int ghostposition = new Vector2Int(posx, posy);
        Vector2Int pacmanposition = Pacman.pacinstance.getposition();

        return Math.abs(pacmanposition.x - ghostposition.x) + Math.abs(pacmanposition.y - ghostposition.y);         
    }

    public Vector2Int huntPacman(int ghostx, int ghosty){
        Vector2Int ghostposition = new Vector2Int(ghostx, ghosty);
        Vector2Int pacmanposition = Pacman.pacinstance.getposition();
        Vector2Int[] gohere = AStar.FindShortestPath(ghostposition, pacmanposition, grid);

        if(gohere.length == 0)
            return ghostposition;


        if(ghostposition.Add(Vector2Int.up) == gohere[0]){
            this.direction = Vector2Int.up;
        }else if(ghostposition.Add(Vector2Int.down) == gohere[0]){
            this.direction = Vector2Int.down;
        }else if(ghostposition.Add(Vector2Int.left) == gohere[0]){
            this.direction = Vector2Int.left;
        }else if(ghostposition.Add(Vector2Int.right) == gohere[0]){
            this.direction = Vector2Int.right;
        }

        ghostposition = gohere[0];

        return ghostposition;
    }

    public Vector2Int runfromPacman(int ghostx, int ghosty, String color){
        Vector2Int ghostposition = new Vector2Int(ghostx, ghosty);
        Vector2Int pacmanposition = Pacman.pacinstance.getposition();

        if(countchanges > 3){
            countchanges = 0;
            keeprunning++;
        }
        if(keeprunning != 0){
            if(keeprunning > 5){
                keeprunning = 0;
            }else{
                keeprunning++;
            }
            ghostposition = greenGhostMovement(ghostposition);
        }else{

            if(ghostposition.x > pacmanposition.x || ghostposition.x == pacmanposition.x && !Map.instance.IsCol(position.Add(Vector2Int.right)) && cangoright(ghostposition)){
                ghostposition.Add(Vector2Int.right);
                if(!direction.equals(Vector2Int.right)){
                    countchanges++;
                    direction = Vector2Int.right;
                }
            }else if(ghostposition.x < pacmanposition.x || ghostposition.x == pacmanposition.x && !Map.instance.IsCol(position.Add(Vector2Int.left)) && cangoleft(ghostposition)){
                ghostposition.Add(Vector2Int.left);
                if(!direction.equals(Vector2Int.left)){
                    countchanges++;
                    direction = Vector2Int.left;
                }
            }else if(ghostposition.y > pacmanposition.y || ghostposition.y == pacmanposition.y && !Map.instance.IsCol(position.Add(Vector2Int.down)) && cangodown(ghostposition)){
                ghostposition.Add(Vector2Int.down);
                if(!direction.equals(Vector2Int.down)){
                    countchanges++;
                    direction = Vector2Int.down;
                }
            }else if(ghostposition.y < pacmanposition.y || ghostposition.y == pacmanposition.y && !Map.instance.IsCol(position.Add(Vector2Int.up)) && cangoup(ghostposition)){
               ghostposition.Add(Vector2Int.up);
               if(!direction.equals(Vector2Int.up)){
                countchanges++;
                direction = Vector2Int.up;
                }
            }else{
            
                ghostposition = greenGhostMovement(ghostposition);
                countchanges++;
            }
        }
        
        return ghostposition;

    }

    public boolean cangoright(Vector2Int checkfurther){

        if (!Map.instance.IsCol(checkfurther.Add(Vector2Int.right).Add(Vector2Int.right)) 
        || !Map.instance.IsCol(checkfurther.Add(Vector2Int.right).Add(Vector2Int.up))                
        || !Map.instance.IsCol(checkfurther.Add(Vector2Int.right).Add(Vector2Int.down))){

            return true;
        }
        return false;
    }

    public boolean cangoleft(Vector2Int checkfurther){

        if (!Map.instance.IsCol(checkfurther.Add(Vector2Int.left).Add(Vector2Int.left)) 
        || !Map.instance.IsCol(checkfurther.Add(Vector2Int.left).Add(Vector2Int.up))                
        || !Map.instance.IsCol(checkfurther.Add(Vector2Int.left).Add(Vector2Int.down))){

            return true;
        }
        return false;
    }

    public boolean cangoup(Vector2Int checkfurther){
        
        if (!Map.instance.IsCol(checkfurther.Add(Vector2Int.up).Add(Vector2Int.up)) 
        || !Map.instance.IsCol(checkfurther.Add(Vector2Int.up).Add(Vector2Int.left))                
        || !Map.instance.IsCol(checkfurther.Add(Vector2Int.up).Add(Vector2Int.right))){

            return true;
        }
        return false;
    }

    public boolean cangodown(Vector2Int checkfurther){

        if (!Map.instance.IsCol(checkfurther.Add(Vector2Int.down).Add(Vector2Int.down)) 
        || !Map.instance.IsCol(checkfurther.Add(Vector2Int.down).Add(Vector2Int.left))                
        || !Map.instance.IsCol(checkfurther.Add(Vector2Int.down).Add(Vector2Int.right))){

            return true;
        }  
        return false;
    }

    public int getnumberGhosts(){

        return numberGhosts;
    }

}
