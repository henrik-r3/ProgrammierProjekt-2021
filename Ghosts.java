import java.awt.Graphics2D;

import javax.swing.ImageIcon;
import jdk.internal.vm.compiler.word.Pointer;
import jdk.tools.jmod.Main;


public class Ghosts{

    boolean playing; //muss aus Main class bestimmt werden 
    String direction; //Speichert die Richtung, in die sich der Geist bewegt
    Image green, yellow, red;  //Pacman hat 4 Bilder für jede mögliche Richtung
    int numberGhosts = 3; //Jetzt statisch, muss wenn varibel aus der main class bestimmt werden
    Vector2Int positiongreen = new Vector2Int(x, y);
    Vector2Int positionyellow = new Vector2Int(x, y); //Position der einzelnen Geister
    Vector2Int positionred = new Vector2Int(x, y);
    int followrange = Main.followrange;  //-> muss in main noch deklariert werden
    
    public void optics(){
        //Farben für die 3 unterschiedlichen Geister     
        green = new ImageIcon("*/Bilder/greenGhost.gif").getImage();      // Farben müssen noch erstellt werden
        yellow = new ImageIcon("*/Bilder/yellowGhost.gif").getImage();
        red = new ImageIcon("*/Bilder/redGhost.gif").getImage();
    }

    public void greenGhostMovement(){
        positiongreen.x = 0;
        positiongreen.y = 0;

        if(calculateDistance(positiongreen.x, positiongreen.y) < followrange ){
            huntPacman();
        } else {

        }
    }
    
    public void yellowGhostMovement(){
        int ghostRow = 1;
        int ghostColumn = 1;
        if(calculateDistance(ghostRow, ghostColumn) < followrange ){
            huntPacman();
        }

    }
    public void redGhostMovement(){
        int ghostRow = 1;
        int ghostColumn = 1;
        if(calculateDistance(ghostRow, ghostColumn) < 2 ){
            huntPacman();
        } else {
            int counter = 0;
            
        }

    }

    public void drawGhosts(Graphics2D g2d){

        g2d.drawImage(green,positiongreen.x, positiongreen.y);
        g2d.drawImage(yellow, positionyellow.x, positionyellow.y);
        g2d.drawImage(red, positionred.x, positionrerd.y);

    }

    public void huntPacman(){
    
            //Algorithmus zur Berechnung des Wegs zum Pacman  !!!! -> nur für den Roten, der dann die ganze Zeit folgt??? der Rest maht einfach sein Ding?
    }

    public int calculateDistance(int row, int column){
        Vector2Int pacmanposition = new Vector2Int(Pacman.x, Pacman.y);

        return Math.abs(packmanposition.x - row) + Math.abs(pacmanposition.y - column);
         
    }

}