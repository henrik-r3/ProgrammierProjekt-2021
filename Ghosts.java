import javax.swing.ImageIcon;

import jdk.internal.vm.compiler.word.Pointer;
import jdk.tools.jmod.Main;

public class Ghosts{
    

    public ghostOptics(){
        Image green, blue, yellow, red;
        
      //green = new ImageIcon("*/Bilder/Ghost.gif").getImage();      -> Farben müssen noch ertsllt werden
      //blue = new ImageIcon("*/Bilder/Ghost.gif").getImage();
        yellow = new ImageIcon("*/Bilder/Ghost.gif").getImage();
      //red = new ImageIcon("*/Bilder/Ghost.gif").getImage();
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
    public void blueGhostMovement(){
        int ghostRow = 1;
        int ghostColumn = 1;
        if(calculateDistance(ghostRow, ghostColumn) < followrange ){
            huntPacman();
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
        int pacmanRow = Pacman.pacmanRow;
        int pacmanColumn = Pacman.pacmanColumn;

        return Math.abs(pacmanRow - ghostRow) + Math.abs(pacmanColumn - ghostColumn);
         
    }

}