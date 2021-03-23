import java.awt.*;
import javax.swing.ImageIcon;
import java.util.Random;

public class Ghosts extends GameObject{

    //Attribute der Geister
    private Image skin;
    private Image scared;
    private String color;
    private int numberGhosts = 0;
    private Vector2Int position = new Vector2Int(), startposition = new Vector2Int();
    private Vector2Int direction;  
    private int followrange;  
    public Random rnd = new Random();
    private boolean powerberry = false;
    private int keephunting;
    private int powerberrystepcounter;
    private int keeprunning;
    private int countchanges;
    private int blinks;
    private AStar.Grid grid;

    //Konstruktor für Geister, bekommt Startposition, Reichweite und Farbe übergeben
    public Ghosts(int startx, int starty, int range, String colorselected ){
       this.position.x = this.startposition.x = startx;
       this.position.y = this.startposition.y = starty;
       this.followrange = range;
       this.direction = Vector2Int.down;
       this.keephunting = 0;
       this.countchanges = 0;
       this.keeprunning = 0;
       this.blinks = 2;
       //Je nach angegebener Farbe bekommt der Geist ein anderes Aussehen
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


    long moveTimer = 401;//prime number -> first common step at 123107 steps
    long timer = 0;

    @Override
    public void Update(long deltaTime){ //Updates der Geister erfolgen minimal langsamer (50 ms) als die des Pacman
        timer += deltaTime;
        if(timer >= moveTimer)
        {   
            timer = 0;
            //wird überprüft, ob Geister gejagt werden und dann ausgewählt, wie sich Geister verhalten sollen
            powerberry = Pacman.pacinstance.gethunting();
            selectGhostmovement();
            powerberrystepcounter++;

        }

    }

    @Override
    public void Draw(){

        //wenn Powerbeere aktiv ist wird Geist blau
        if(powerberry){
            if(powerberrystepcounter < ((int)7000/moveTimer) - blinks*2){
                drawGhosts(scared, this.position.x, this.position.y);
            }else{
                if(powerberrystepcounter%2 == 0){
                    drawGhosts(this.skin, this.position.x, this.position.y);
                }else{
                    drawGhosts(scared, this.position.x, this.position.y);
                }
            } 
        }else{ //Ansonsten mit normalem Aussehen gezeichnet
            drawGhosts(this.skin, this.position.x, this.position.y);
            powerberrystepcounter = 0;
        }    

    }

    public void selectGhostmovement(){

            //Ist Powerberry aktiv, so rennen Geister weg
            if(powerberry){
                keephunting = 0;
                //Ist die Distanz zum Pacman 0, hat dieser den Geist gefressen, Punkte werden dem Spieler gegeben, Geist geht zum Start zurück
                if(calculateDistance(this.position.x, this.position.y) < 1 ){
                    Score.scoreinstance.eatsGhost();
                    this.position = startposition;
                }
                this.position = runfromPacman(this.position.x, this.position.y, this.color);

                //wenn Powerberry nicht aktiv ist
            }else{
                countchanges = 0;
                keeprunning = 0;
                //Ist Geist direkt an Pacman dran, so stirbt der Pacman, Methode in Pacman-Klasse wird gerufen
                if(calculateDistance(this.position.x, this.position.y) < 2 ){
                    Pacman.pacinstance.hasbeencaught();
                }
                //Gerät Pacman in die followrange (Reichweite), so rennt Geist dem Pacman hinterher, kepohunting wird aktiviert
                if(calculateDistance(this.position.x, this.position.y) <= followrange){
                    this.position = huntPacman(this.position.x, this.position.y);
                    keephunting++;
                
                    //Geist soll Pacman kurze Zeit lang (4 Zyklen) weiter hinterherlaufen
                }else if(keephunting != 0){
                    if(keephunting > 3){
                        keephunting = 0;
                    }else{
                        keephunting++;

                    }
                    this.position = huntPacman(this.position.x, this.position.y);

                    //Andernfalls wird das Verhalten des Geistes nach der Farbe des Geistes bestimmt
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
            /*Bei dem Verhalten der Geister wurde jeweils nur eine Richtung vollständig asukommentiert
                Sonst wurde nur die Reichtung, in die sich der Geist bewegt, notiert
                Das Verhalten ist für die anderen Richtung identisch, nur dass die "Ausweichmöglichkeiten"oder Richtung geändert sind
            */
        
    }

    public Vector2Int greenGhostMovement(Vector2Int oldposition){ //grüner Geist biegt immer zufällig ab
        int rand = 0;
        int possibilities = determinepossiblities(oldposition);


        //Geist bewegt sich nach unten
        if(this.direction.equals(Vector2Int.down)){

            //Hat Gesit "keine" Möglichkeit, dreht er um
           if(possibilities == 0){
               this.direction = Vector2Int.up;

               //Hat Geist 1 Möglichkeit wird geschaut, welche das ist und die Richtung geändert
           }else if(possibilities == 1){
               if(!Map.instance.IsCol(oldposition.Add(Vector2Int.down)) && furtherdown(oldposition)){
                    this.direction = Vector2Int.down;
               }else if(!Map.instance.IsCol(oldposition.Add(Vector2Int.left)) && furtherleft(oldposition)){
                    this.direction = Vector2Int.left;
               }else if(!Map.instance.IsCol(oldposition.Add(Vector2Int.right)) && furtherright(oldposition)){
                   this.direction = Vector2Int.right;
               }else{
                   this.direction = Vector2Int.up;
               }

               //Geist hat 2 Möglichkeiten
           }else if(possibilities == 2){
                rand = rnd.nextInt(possibilities);
                //Eine Zufallszahl wurde gebildet. Ist diese 0, so versucht der Geist, weiter nach unten zu gehen
                if(rand == 0 && !Map.instance.IsCol(oldposition.Add(Vector2Int.down)) && furtherdown(oldposition)){
                    this.direction = Vector2Int.down;
                }else{
                    //Kann der Geist nicht nach unten gehen oder war die Zufallszahl nicht 0 so wird eine weitere Zufallszahl gebildet
                    rand = rnd.nextInt(2);
                    if(rand == 0){
                        //Ist diese 0, so wird zuerst geschaut, ob der Geist nach rechts gehen kann
                        if(!Map.instance.IsCol(oldposition.Add(Vector2Int.right)) && furtherright(oldposition)){
                            this.direction = Vector2Int.right;
                            //kann der nicht nach rechts gehen, so wird die linke Seite überprüft und dann die Richtung nach unten
                        }else if(!Map.instance.IsCol(oldposition.Add(Vector2Int.left)) && furtherleft(oldposition)){
                            this.direction = Vector2Int.left;
                        }else if (!Map.instance.IsCol(oldposition.Add(Vector2Int.down)) && furtherdown(oldposition)){
                            this.direction = Vector2Int.down;
                        }else{
                            //Geht aus irgendeinem Grund nichts (sollte nicht passieren), so dreht Geist um
                            this.direction = Vector2Int.up;
                        }    
                    }else{
                        //Ist die Zufallszahl 1 so wird erst nach links statt nach recht überprüft
                        if(!Map.instance.IsCol(oldposition.Add(Vector2Int.left)) && furtherleft(oldposition)){
                            this.direction = Vector2Int.left;
                            //dann wird nach links und nach unten überprüft
                        }else if(!Map.instance.IsCol(oldposition.Add(Vector2Int.right)) && furtherright(oldposition)){
                            this.direction = Vector2Int.right;
                        }else if (!Map.instance.IsCol(oldposition.Add(Vector2Int.down)) && furtherdown(oldposition)){
                            this.direction = Vector2Int.down;
                        }else{
                            //Zum Schluss wieder die Ausweichmöglichkeit, nach oben zu gehen
                            this.direction = Vector2Int.up;
                        }
                    }
                }


                //Hat der Geist 3 Möglichkeiten, so wird mithilfe einer Zufallszahl eine Richtung gewählt
           }else if(possibilities == 3){
                rand = rnd.nextInt(possibilities);
                if(rand == 0){
                    this.direction = Vector2Int.down;
                }else if(rand == 1){
                    this.direction = Vector2Int.right;
                }else{
                    this.direction = Vector2Int.left;
                }

           }

           //Geist bewegt sich nach oben
        }else if(this.direction.equals(Vector2Int.up)){
            
           if(possibilities == 0){
                this.direction = Vector2Int.down;

            }else if(possibilities == 1){
                if(!Map.instance.IsCol(oldposition.Add(Vector2Int.up)) && furtherup(oldposition)){
                     this.direction = Vector2Int.up;
                }else if(!Map.instance.IsCol(oldposition.Add(Vector2Int.left)) && furtherleft(oldposition)){
                     this.direction = Vector2Int.left;
                }else if(!Map.instance.IsCol(oldposition.Add(Vector2Int.right)) && furtherright(oldposition)){
                    this.direction = Vector2Int.right;
                }else{
                    this.direction = Vector2Int.down;
                }

            }else if(possibilities == 2){
                rand = rnd.nextInt(possibilities);
                if(rand == 0 && !Map.instance.IsCol(oldposition.Add(Vector2Int.up)) && furtherup(oldposition)){
                    this.direction = Vector2Int.up;
                }else{
                    rand = rnd.nextInt(2);
                    if(rand == 0){
                        if(!Map.instance.IsCol(oldposition.Add(Vector2Int.right)) && furtherright(oldposition)){
                            this.direction = Vector2Int.right;
                        }else if(!Map.instance.IsCol(oldposition.Add(Vector2Int.left)) && furtherleft(oldposition)){
                            this.direction = Vector2Int.left;
                        }else if (!Map.instance.IsCol(oldposition.Add(Vector2Int.up)) && furtherup(oldposition)){
                            this.direction = Vector2Int.up;
                        }else{
                            this.direction = Vector2Int.down;
                        }
                    }else{
                        if(!Map.instance.IsCol(oldposition.Add(Vector2Int.left)) && furtherleft(oldposition)){
                            this.direction = Vector2Int.left;
                        }else if(!Map.instance.IsCol(oldposition.Add(Vector2Int.right)) && furtherright(oldposition)){
                            this.direction = Vector2Int.right;
                        }else if (!Map.instance.IsCol(oldposition.Add(Vector2Int.up)) && furtherup(oldposition)){
                            this.direction = Vector2Int.up;
                        }else{
                            this.direction = Vector2Int.down;
                        }
                    }
                }
                

            }else if(possibilities == 3){
                rand = rnd.nextInt(possibilities);
                if(rand == 0){
                 this.direction = Vector2Int.up;
                }else if(rand == 1){
                 this.direction = Vector2Int.right;
                }else{
                 this.direction = Vector2Int.left;
                }

            }   

            //Geist bewegt sich nach links
        }else if(this.direction.equals(Vector2Int.left)){
            if(possibilities == 0){
                this.direction = Vector2Int.right;

            }else if(possibilities == 1){
                if(!Map.instance.IsCol(oldposition.Add(Vector2Int.left)) && furtherleft(oldposition)){
                     this.direction = Vector2Int.left;
                }else if(!Map.instance.IsCol(oldposition.Add(Vector2Int.up)) && furtherup(oldposition)){
                     this.direction = Vector2Int.up;
                }else if(!Map.instance.IsCol(oldposition.Add(Vector2Int.down)) && furtherdown(oldposition)){
                    this.direction = Vector2Int.down;
                }else{
                    this.direction = Vector2Int.right;
                }

            }else if(possibilities == 2){
                rand = rnd.nextInt(possibilities);
                if(rand == 0 && !Map.instance.IsCol(oldposition.Add(Vector2Int.left)) && furtherleft(oldposition)){
                    this.direction = Vector2Int.left;
                }else{
                    rand = rnd.nextInt(2);
                    if(rand == 0){
                        if(!Map.instance.IsCol(oldposition.Add(Vector2Int.up)) && furtherup(oldposition)){
                            this.direction = Vector2Int.up;
                        }else if(!Map.instance.IsCol(oldposition.Add(Vector2Int.down)) && furtherdown(oldposition)){
                            this.direction = Vector2Int.down;
                        }else if (!Map.instance.IsCol(oldposition.Add(Vector2Int.left)) && furtherleft(oldposition)){
                            this.direction = Vector2Int.left;
                        }else{
                            this.direction = Vector2Int.right;
                        }
                    }else{
                        if(!Map.instance.IsCol(oldposition.Add(Vector2Int.down)) && furtherdown(oldposition)){
                            this.direction = Vector2Int.down;
                        }else if(!Map.instance.IsCol(oldposition.Add(Vector2Int.up)) && furtherup(oldposition)){
                            this.direction = Vector2Int.up;
                        }else if (!Map.instance.IsCol(oldposition.Add(Vector2Int.left)) && furtherleft(oldposition)){
                            this.direction = Vector2Int.left;
                        }else{
                            this.direction = Vector2Int.right;
                        }
                    }
                }

            }else if(possibilities == 3){
                rand = rnd.nextInt(possibilities);
                if(rand == 0){
                 this.direction = Vector2Int.left;
                }else if(rand == 1){
                 this.direction = Vector2Int.up;
                }else{
                 this.direction = Vector2Int.down;
                }

            }

            //Geist bewegt sich nach rechts
        }else if(this.direction.equals(Vector2Int.right)){
            if(possibilities == 0){
                this.direction = Vector2Int.left;

            }else if(possibilities == 1){
                if(!Map.instance.IsCol(oldposition.Add(Vector2Int.right)) && furtherright(oldposition)){
                     this.direction = Vector2Int.right;
                }else if(!Map.instance.IsCol(oldposition.Add(Vector2Int.up)) && furtherup(oldposition)){
                     this.direction = Vector2Int.up;
                }else if(!Map.instance.IsCol(oldposition.Add(Vector2Int.down)) && furtherdown(oldposition)){
                    this.direction = Vector2Int.down;
                }else{
                    this.direction = Vector2Int.left;
                }

            }else if(possibilities == 2){
                rand = rnd.nextInt(possibilities);
                if(rand == 0 && !Map.instance.IsCol(oldposition.Add(Vector2Int.right)) && furtherright(oldposition)){
                    this.direction = Vector2Int.right;
                }else{
                    rand = rnd.nextInt(2);
                    if(rand == 0){
                        if(!Map.instance.IsCol(oldposition.Add(Vector2Int.up)) && furtherup(oldposition)){
                            this.direction = Vector2Int.up;
                        }else if(!Map.instance.IsCol(oldposition.Add(Vector2Int.down)) && furtherdown(oldposition)){
                            this.direction = Vector2Int.down;
                        }else if (!Map.instance.IsCol(oldposition.Add(Vector2Int.right)) && furtherright(oldposition)){
                            this.direction = Vector2Int.right;
                        }else{
                            this.direction = Vector2Int.left;
                        }
                    }else{
                        if(!Map.instance.IsCol(oldposition.Add(Vector2Int.down)) && furtherdown(oldposition)){
                            this.direction = Vector2Int.down;
                        }else if(!Map.instance.IsCol(oldposition.Add(Vector2Int.up)) && furtherup(oldposition)){
                            this.direction = Vector2Int.up;
                        }else if (!Map.instance.IsCol(oldposition.Add(Vector2Int.right)) && furtherright(oldposition)){
                            this.direction = Vector2Int.right;
                        }else{
                            this.direction = Vector2Int.left;
                        }
                    }
                }

            }else if(possibilities == 3){
                rand = rnd.nextInt(possibilities);
                if(rand == 0){
                 this.direction = Vector2Int.right;
                }else if(rand == 1){
                 this.direction = Vector2Int.up;
                }else{
                 this.direction = Vector2Int.down;
                }

            }
        }

        oldposition = oldposition.Add(direction);
        return oldposition;
    }

    
    public Vector2Int yellowGhostMovement(Vector2Int oldposition){ //Gelber Geist ändert seine Richtung nur, wenn er gegen eine Wand läuft
        int rand;
        int possibilities = determinepossiblities(oldposition); 

        //Geist geht aktuell runter 
        if (this.direction == Vector2Int.down && Map.instance.IsCol(this.position.Add(this.direction))){
            
            //Gibt es "keine" Möglichkeit, dreht Geist um
            if(possibilities == 0){
                direction = Vector2Int.up;   
            }else{
                //Ansonsten wird aus den gegebenen Möglichkeiten eine Zufallszahl erstellt
                rand = rnd.nextInt(possibilities);                    
                if(rand == 0){
                    //ist diese 0 versucht Geist rechts zu gehen
                    if(!Map.instance.IsCol(oldposition.Add(Vector2Int.right))){
                        direction = Vector2Int.right;
                    }else{
                        //geht dies nicht, geht er nach links
                        direction = Vector2Int.left;
                    }
                }else{
                    //wenn Geist mehr als 1 Möglichkeit hat und die Zufallszahl nicht 0 ist, geht er nach links
                    direction = Vector2Int.left;
                }   
            }

            //Geist geht gerade nach oben
        }else if (this.direction == Vector2Int.up && Map.instance.IsCol(this.position.Add(this.direction))){

            if(possibilities == 0){
                direction = Vector2Int.down;   
            }else{
                rand = rnd.nextInt(possibilities);                    
                if(rand == 0){
                    if(!Map.instance.IsCol(oldposition.Add(Vector2Int.right))){
                        direction = Vector2Int.right;
                    }else{
                        direction = Vector2Int.left;
                    }
                }else{
                    direction = Vector2Int.left;
                }   
            }
            
            //GEist geht gerade nach links
        }else if (this.direction == Vector2Int.left && Map.instance.IsCol(this.position.Add(this.direction))){

            if(possibilities == 0){
                direction = Vector2Int.right;   
            }else{
                rand = rnd.nextInt(possibilities);                    
                if(rand == 0){
                    if(!Map.instance.IsCol(oldposition.Add(Vector2Int.up))){
                        direction = Vector2Int.up;
                    }else{
                        direction = Vector2Int.down;
                    }
                }else{
                    direction = Vector2Int.down;
                }          
            }

            //Geist geht gerade nach rechts
        }else if (this.direction == Vector2Int.right && Map.instance.IsCol(this.position.Add(this.direction))){
            if(possibilities == 0){
                direction = Vector2Int.left;   
            }else{
                rand = rnd.nextInt(possibilities);                    
                if(rand == 0){
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

    public Vector2Int redGhostMovement(Vector2Int oldposition){ //Roter Geist jagt Pacman permanent
        
        oldposition = huntPacman(oldposition.x, oldposition.y);

        return oldposition;
    }

    public Vector2Int pinkGhostMovement(Vector2Int oldposition){ //Pinker Geist geht in die Nähe von Pacman
        
        Vector2Int pacmanposition = Pacman.pacinstance.getposition();
        int rand = rnd.nextInt(4);
        
        //dazu wird die Position für Pacman mithilfe einer Zufallszahl manipuliert
        if(rand == 0){
            pacmanposition.x += 2;
        }else if(rand == 1){
            pacmanposition.x -= 2;
        }else if(rand == 2){
            pacmanposition.y += 2;
        }else if(rand == 3){
            pacmanposition.y -= 2;
        }


        //Geist geht gerade nach unten
        if(this.direction == Vector2Int.down){
            
            //Pacman ist unter ihm unter er kann nach unten gehen
            if(oldposition.y < pacmanposition.y && !Map.instance.IsCol(oldposition.Add(Vector2Int.down))){
                this.direction = Vector2Int.down;
            //Pacman ist rechts von ihm und er kann nach rechts gehen
            }else if(oldposition.x < pacmanposition.x && !Map.instance.IsCol(oldposition.Add(Vector2Int.right))){
                this.direction = Vector2Int.right;
            //Pacman ist links von ihm und er kann nach links gehen
            }else if(oldposition.x > pacmanposition.x && !Map.instance.IsCol(oldposition.Add(Vector2Int.left))){
                this.direction = Vector2Int.left;
            }else{
                //Geht als dies nicht (Möglicherweise eine Sackgasse oder ein Bogen) wird das Jagdverhalten aktiviert
                Vector2Int newposition = huntPacman(oldposition.x, oldposition.y);

                //dann wird geguckt, wie sich die neue zu der alten Position verändert hat und die Richtung angegeben
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

            //Geist geht gerade nach oben
        }else if(this.direction == Vector2Int.up){
            if(oldposition.y > pacmanposition.y && !Map.instance.IsCol(oldposition.Add(Vector2Int.up))){
                this.direction = Vector2Int.up;
            }else if(oldposition.x < pacmanposition.x && !Map.instance.IsCol(oldposition.Add(Vector2Int.right))){
                this.direction = Vector2Int.right;
            }else if(oldposition.x > pacmanposition.x && !Map.instance.IsCol(oldposition.Add(Vector2Int.left))){
                this.direction = Vector2Int.left;
            }else{
                
                Vector2Int newposition = huntPacman(oldposition.x, oldposition.y);

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

            //Geist geht gerade nach rechts
        }else if(this.direction == Vector2Int.right){
            if(oldposition.x < pacmanposition.x && !Map.instance.IsCol(oldposition.Add(Vector2Int.right))){
                this.direction = Vector2Int.right;
            }else if(oldposition.y < pacmanposition.y && !Map.instance.IsCol(oldposition.Add(Vector2Int.down))){
                this.direction = Vector2Int.down;
            }else if(oldposition.y > pacmanposition.y && !Map.instance.IsCol(oldposition.Add(Vector2Int.up))){
                this.direction = Vector2Int.up;                    
            }else{
                Vector2Int newposition = huntPacman(oldposition.x, oldposition.y);

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

            //Geist geht gerade nach links
        }else if(this.direction == Vector2Int.left){
            if(oldposition.x > pacmanposition.x && !Map.instance.IsCol(oldposition.Add(Vector2Int.left))){
                this.direction = Vector2Int.left;
            }else if(oldposition.y < pacmanposition.y && !Map.instance.IsCol(oldposition.Add(Vector2Int.down))){
                this.direction = Vector2Int.down;
            }else if(oldposition.y > pacmanposition.y && !Map.instance.IsCol(oldposition.Add(Vector2Int.up))){
                this.direction = Vector2Int.up;                   
            }else{
                Vector2Int newposition = huntPacman(oldposition.x, oldposition.y);

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
        
        //Richtung wird zu Position addiert
        oldposition = oldposition.Add(direction);
        return oldposition;
    }

    //Die Anzahl an Möglichkeiten, die der Geist hat, wird berechnet
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

        //Der Wert wird um 1 reduziert, da Geister nicht vom einen zum anderen in die entgegengesetzte Richtung laufen sollen
        return possibilities - 1;
    }
    
    //Geister werden gezeichnet
    public void drawGhosts(Image skinImage, int posx, int posy){
        Vector2Int drawvector = new Vector2Int(posx, posy);
        Game.instance.drawImage(skinImage, drawvector);
    }

    //Distanz der Geister zu Pacman wird berechnet
    public int calculateDistance(int posx, int posy){

        Vector2Int ghostposition = new Vector2Int(posx, posy);
        Vector2Int pacmanposition = Pacman.pacinstance.getposition();

        //Absoluter Wert der Distanz (nichtnegativ) wird zurückgegeben
        return Math.abs(pacmanposition.x - ghostposition.x) + Math.abs(pacmanposition.y - ghostposition.y);         
    }

    //Verhalten wenn Pacman gejagt wird 
    public Vector2Int huntPacman(int ghostx, int ghosty){
        Vector2Int ghostposition = new Vector2Int(ghostx, ghosty);
        Vector2Int pacmanposition = Pacman.pacinstance.getposition();
        //dazu wird eine Funktion aus AStar aufgerufen, Position von Pacman und Geist werden übergeben
        Vector2Int[] gohere = AStar.FindShortestPath(ghostposition, pacmanposition, grid);

        if(gohere.length == 0)
            return ghostposition;

        //Danach wird ein Abgleich mit der neuen Position gemacht, um Richtung zu bestimmen (willkürliche Veränderungen vermeiden)
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

    //Geister sollen vor Pacman weglaufen
    public Vector2Int runfromPacman(int ghostx, int ghosty, String color){
        Vector2Int ghostposition = new Vector2Int(ghostx, ghosty);
        Vector2Int pacmanposition = Pacman.pacinstance.getposition();

        /*Es wird geguckt, wie oft der Geist seine Reichtung geändert hat
            Hat der Geist seine Richtung mehr als 4 Mal geändert (countchanges), dann wird keeprunning aktiviert
            Dadurch verhält sich der weglaufende Geist für 5 Itterationen wie der grüne Geist (läuft evtl nicht mehr weg)
            das soll verhindern, dass der Geist in einer Ecke oder Schleife gefangen wird und sich dort nur hin und her bewegt
            Das kann passieren, da der Geist versucht, die größtmögliche Distanz zum Pacman aufzubauen, was aber möglicherweise nicht möglich ist
        */
        if(countchanges > 4){
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
            
            //wenn Geist weiter rechts ist und rechts gehen kann
            if(ghostposition.x > pacmanposition.x && !Map.instance.IsCol(ghostposition.Add(Vector2Int.right)) && furtherright(ghostposition)){
                ghostposition = ghostposition.Add(Vector2Int.right);
                if(!direction.equals(Vector2Int.right)){
                    countchanges++;
                    direction = Vector2Int.right;
                }
                //wenn Geist weiter links ist und nach links gehen kann
            }else if(ghostposition.x < pacmanposition.x && !Map.instance.IsCol(ghostposition.Add(Vector2Int.left)) && furtherleft(ghostposition)){
                ghostposition = ghostposition.Add(Vector2Int.left);
                if(!direction.equals(Vector2Int.left)){
                    countchanges++;
                    direction = Vector2Int.left;
                }
                //Wenn Geist weiter unten ist und nach unten gehen kann
            }else if(ghostposition.y < pacmanposition.y && !Map.instance.IsCol(ghostposition.Add(Vector2Int.down)) && furtherdown(ghostposition)){
                ghostposition = ghostposition.Add(Vector2Int.down);
                if(!direction.equals(Vector2Int.down)){
                    countchanges++;
                    direction = Vector2Int.down;
                }
                //wenn Geist weiter oben ist und nach oben gehen kann
            }else if(ghostposition.y > pacmanposition.y && !Map.instance.IsCol(ghostposition.Add(Vector2Int.up)) && furtherup(ghostposition)){
                ghostposition = ghostposition.Add(Vector2Int.up);
                if(!direction.equals(Vector2Int.up)){
                    countchanges++;
                    direction = Vector2Int.up;
                }
                //Wenn y identisch ist wird geguckt, ob er nach Oben oder unten gehen kann
            }else if(ghostposition.y == pacmanposition.y){
                if(!Map.instance.IsCol(ghostposition.Add(Vector2Int.up)) && furtherup(ghostposition)){
                    ghostposition = ghostposition.Add(Vector2Int.up);
                if(!direction.equals(Vector2Int.up)){
                    countchanges++;
                    direction = Vector2Int.up;
                }
                }else if(!Map.instance.IsCol(ghostposition.Add(Vector2Int.down)) && furtherdown(ghostposition)){
                    ghostposition = ghostposition.Add(Vector2Int.down);
                    if(!direction.equals(Vector2Int.down)){
                        countchanges++;
                        direction = Vector2Int.down;
                    }
                }
               //wenn x identisch ist, wird geguckt, ob er nach rechts oder links gehen kann
            }else if(ghostposition.x == pacmanposition.x){
                if(!Map.instance.IsCol(ghostposition.Add(Vector2Int.left)) && furtherleft(ghostposition)){
                    ghostposition = ghostposition.Add(Vector2Int.left);
                if(!direction.equals(Vector2Int.left)){
                    countchanges++;
                    direction = Vector2Int.left;
                }
                }else if(!Map.instance.IsCol(ghostposition.Add(Vector2Int.right)) && furtherright(ghostposition)){
                    ghostposition = ghostposition.Add(Vector2Int.right);
                    if(!direction.equals(Vector2Int.right)){
                        countchanges++;
                        direction = Vector2Int.right;
                    }
                }
            }else{
                
                //Sollte nicht gehen (eigentlich asugeschlossen), dann verhalten wie der grüne Geist
                ghostposition = greenGhostMovement(ghostposition);
                countchanges++;
            }
        }
        
        return ghostposition;

    }

    //Wird überprüft, ob Geist weiter rechts gehen kann (oder ob Sackgasse)
    public boolean furtherright(Vector2Int checkfurther){

        if (!Map.instance.IsCol(checkfurther.Add(Vector2Int.right).Add(Vector2Int.right)) 
        || !Map.instance.IsCol(checkfurther.Add(Vector2Int.right).Add(Vector2Int.up))                
        || !Map.instance.IsCol(checkfurther.Add(Vector2Int.right).Add(Vector2Int.down))){

            return true;
        }
        return false;
    }

    //Wird überprüft, ob Geist weiter links gehen kann (oder ob Sackgasse)
    public boolean furtherleft(Vector2Int checkfurther){

        if (!Map.instance.IsCol(checkfurther.Add(Vector2Int.left).Add(Vector2Int.left)) 
        || !Map.instance.IsCol(checkfurther.Add(Vector2Int.left).Add(Vector2Int.up))                
        || !Map.instance.IsCol(checkfurther.Add(Vector2Int.left).Add(Vector2Int.down))){

            return true;
        }
        return false;
    }

    //Wird überprüft, ob Geist weiter hoch gehen kann (oder ob Sackgasse)
    public boolean furtherup(Vector2Int checkfurther){
        
        if (!Map.instance.IsCol(checkfurther.Add(Vector2Int.up).Add(Vector2Int.up)) 
        || !Map.instance.IsCol(checkfurther.Add(Vector2Int.up).Add(Vector2Int.left))                
        || !Map.instance.IsCol(checkfurther.Add(Vector2Int.up).Add(Vector2Int.right))){

            return true;
        }
        return false;
    }

    //Wird überprüft, ob Geist weiter runter gehen kann (oder ob Sackgasse)
    public boolean furtherdown(Vector2Int checkfurther){

        if (!Map.instance.IsCol(checkfurther.Add(Vector2Int.down).Add(Vector2Int.down)) 
        || !Map.instance.IsCol(checkfurther.Add(Vector2Int.down).Add(Vector2Int.left))                
        || !Map.instance.IsCol(checkfurther.Add(Vector2Int.down).Add(Vector2Int.right))){

            return true;
        }  
        return false;
    }


    //Anzahl an Geistern kann abgefragt werden
    public int getnumberGhosts(){

        return numberGhosts;
    }

}