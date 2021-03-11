import java.awt.Image;
import javax.swing.ImageIcon;

public class Powerberry extends GameObject{

    private boolean isactive = false;
    private Image berry;
    public static Powerberry berryinstance;
    

    public Powerberry(){
        berry = new ImageIcon("Bilder/Powerberry.png").getImage();
        berryinstance = this;
    }

    @Override
    public void Draw(){
        drawpowerberry();
    }

    public void drawpowerberry(){


    }
    
    public void setberrystatus(boolean activated){

        isactive = activated;
    }

    public boolean getberrystatus(){

        return isactive;
    }
}