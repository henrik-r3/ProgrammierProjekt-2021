import java.awt.Image;
import javax.swing.ImageIcon;

import org.jcp.xml.dsig.internal.MacOutputStream;

public class Powerberry extends GameObject{

    private boolean isactive = false;
    private Image berry;
    private int amountberries;
    private Vector2Int berryposition = new Vector2Int();
    public static Powerberry berryinstance;
    

    public Powerberry(int number){
        amountberries = number;
        berryposition = Map.instance.getRandomPos();
        berry = new ImageIcon("Bilder/Powerberry.png").getImage();
        berryinstance = this;
    }


    long moveTimer = 300;
    long timer = 0;

    @Override 
    public void Update(long deltaTime){

        timer += deltaTime;
        if(timer >= moveTimer)
        {   
            timer = 0;
            checkcollision();  
            Ghost.instance.powerberrystatus(isactive);
        }

    }

    @Override
    public void Draw(){

        drawpowerberry();
    }

    public void checkcollision(){

        if(berryposition == Pacman.instance.getposition()){
            Destroy();
            Score.instance.eatsPowerBerry();
        }

    }

    public void drawpowerberry(){

        Game.instance.drawImage(berry, pos);
    }

    public void setberrystatus(boolean activated){

        isactive = activated;
    }

    public boolean getberrystatus(){

        return isactive;
    }
}