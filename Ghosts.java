import javax.swing.ImageIcon;
import jdk.internal.vm.compiler.word.Pointer;
import jdk.tools.jmod.Main;


public class Ghosts{

    boolean playing; //muss aus Main class bestimmt werden
    String direction; //Speichert die Richtung, in die sich der Geist bewegt
    Image green, yellow, red;  //Pacman hat 4 Bilder für jede mögliche Richtung
    int numberGhosts = 3; //Jetzt statisch, muss wenn varibel aus der main class bestimmt werden
    int position[][] = new int[2][numberGhosts]; //Position der Geister
    
    public void optics(){
        //Farben für die 3 unterschiedlichen Geister     
        green = new ImageIcon("*/Bilder/greenGhost.gif").getImage();      // Farben müssen noch ertsllt werden
        yellow = new ImageIcon("*/Bilder/yellowGhost.gif").getImage();
        red = new ImageIcon("*/Bilder/redGhost.gif").getImage();
    }

    int followrange = Main.followrange;  //-> muss in main noch deklariert werden

    public void greenGhostMovement(){
        int ghostRow = 1;
        int ghostColumn = 1;

        if(calculateDistance(ghostRow, ghostColumn) < followrange ){
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
        if(calculateDistance(ghostRow, ghostColumn) < followrange ){
            huntPacman();
        } else {
            int counter = 0;
            


        }

    }

    public void huntPacman(){
        
    }

    public int calculateDistance(int ghostRow, int ghostColumn){
        Vector2Int pacmanposition = Pacman.position;


        return Math.abs(pacmanRow - ghostRow) + Math.abs(pacmanColumn - ghostColumn);
         
    }

}