import javax.swing.ImageIcon;

import jdk.tools.jmod.Main;

public class Ghosts{
     int followrange = Main.followrange;  //-> muss in main noch deklariert werden

    public ghostOptics(){
        Image green, blue, yellow, red;
        
      //green = new ImageIcon("*/Bilder/Ghost.gif").getImage();      -> Farben müssen noch ertsllt werden
      //blue = new ImageIcon("*/Bilder/Ghost.gif").getImage();
        yellow = new ImageIcon("*/Bilder/Ghost.gif").getImage();
      //red = new ImageIcon("*/Bilder/Ghost.gif").getImage();
    }



    public void greenGhostMovement(){
        if(calculateDistance() < followrange ){
            huntPacman();
        } else {
            
        }
    }
    public void blueGhostMovement(){
        if(calculateDistance() < followrange ){
            huntPacman();
        }

    }
    public void yellowGhostMovement(){
        if(calculateDistance() < followrange ){
            huntPacman();
        }

    }
    public void redGhostMovement(){
        if(calculateDistance() < followrange ){
            huntPacman();
        }

    }

    public void huntPacman(){

    }

    public int calculateDistance(){

    }

}