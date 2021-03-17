import java.awt.Image;
import javax.swing.ImageIcon;

public class Powerberry extends GameObject{

    private boolean isactive = false;
    private Image berry;
    private int amountberries;
    private Vector2Int berryposition = new Vector2Int();
    

    public Powerberry(int startx, int starty){
        berryposition.x = startx;
        berryposition.y = starty;
        berry = new ImageIcon("Bilder/Powerberry.png").getImage();
    }


    long checkTimer = 300;
    long timer = 0;
    long berryactive = 0;
    long berrystop = 7000;

    @Override 
    public void Update(long deltaTime){

        timer += deltaTime;
        if(timer >= checkTimer)
        {   
            timer = 0;
            checkcollision();  

        }

        if(isactive){
            berryactive += deltaTime;
            if(berryactive > berrystop){

                isactive = false;
                Pacman.pacinstance.sethunting(isactive);
                Destroy();
            }
        }
    }

    @Override
    public void Draw(){
        if(!isactive)
            drawpowerberry();
    }

    public void checkcollision(){

        if(berryposition.equals(Pacman.pacinstance.getposition())){
            
            Score.scoreinstance.eatsPowerBerry();
            isactive = true;
            Pacman.pacinstance.sethunting(true);
            amountberries--;
            if(amountberries == 0)
                Score.scoreinstance.ateallpowerberries();
        }

    }

    public void drawpowerberry(){

        Game.instance.drawImage(berry, berryposition);
    }

    public void setamountberries(int number){

        amountberries = number;
    }

    public void setberrystatus(boolean activated){

        isactive = activated;
    }

    public boolean getberrystatus(){

        return isactive;
    }
}