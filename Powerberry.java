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
        berryposition = findposition();
        berry = new ImageIcon("Bilder/Powerberry.png").getImage();
        berryinstance = this;
    }

    @Override
    public void Draw(){

        Ghosts.instance.powerberrystatus(isactive);
        checkcollision();
        drawpowerberry();
    }

    public void checkcollision(){

        if(berryposition == Pacman.instance.getposition()){
            Destroy();
            Score.instance.eatsPowerBerry();
        }

    }

    public Vector2Int findposition(){

        int rndx = (int) (Math.random()*10);
        int rndy = (int) (Math.random()*10);
        boolean down = false;
        boolean right = false;
        int dirswitch = 0;
        int counter = 0;
        Vector2Int position = new Vector2Int(rndx, rndy);

        while(Map.instance.IsCol(position)){
            
            if(dirswitch%2){

                if(down && !Map.instance.IsCol(position.Add(Vector2Int.down))){
                    position.Add(Vector2Int.down);

                }else if(down && Map.instance.IsCol(position.Add(Vector2Int.down))){
                    down =!down;
                    position.Add(Vector2Int.up);

                }else if(!down && !Map.instance.IsCol(position.Add(Vector2Int.up))){
                    position.Add(Vector2Int.up);

                }else if(!down && Map.instance.IsCol(position.Add(Vector2Int.up))){
                    position.Add(Vector2Int.down);
                    down = !down;
                }else{
                    break;
                }
            }else{

                if(right && !Map.instance.IsCol(position.Add(Vector2Int.right))){
                    position.Add(Vector2Int.right);

                }else if(right && Map.instance.IsCol(position.Add(Vector2Int.right))){
                    position.Add(Vector2Int.left);
                    right = !right;

                }else if(!right && !Map.instance.IsCol(position.Add(Vector2Int.left))){
                    position.Add(Vector2Int.left);

                }else if(!right && Map.instance.IsCol(position.Add(Vector2Int.left))){
                    position.Add(Vector2Int.right);
                    right = !right;

                }else{
                    break;

                }

            }

            if(counter > 100)
                break;
        }

        return position;
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