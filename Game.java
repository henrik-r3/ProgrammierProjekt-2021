import java.util.ArrayList;
import java.util.Random;
import java.awt.*;
import javax.swing.JFrame;


//Handles GameObjects, causes Updates and Runs the Game in general (e.g. Pausing, etc.)
public class Game{
    public static Game instance;

    JFrame frame;
    private Graphics g;
    private Vector2Int tileSize;

    public Input input = new Input();
    public Random rnd = new Random();

    private ArrayList<GameObject> gameObjects;//pool of all GameObjects

    public Game(JFrame frame){
        //INIT -------------------------------------------------
        instance = this;
        this.frame = frame;
        gameObjects = new ArrayList<GameObject>();//add initial GameObjects

        new Map(new Vector2Int(20, 20));
        tileSize = new Vector2Int(frame.getWidth() / Map.instance.size.x, frame.getHeight() / Map.instance.size.y);

        //add Gameobjects
        Vector2Int startPos = Map.instance.getRandomPos();
        gameObjects.add( new Pacman(startPos.x, startPos.y, 3) );
        

        //RUN THE GAME -----------------------------------------
        long lastTime = System.currentTimeMillis();
        for(int g = gameObjects.size()-1; g >= 0; g--)//loop runs backwards to prevent error on deletion
            gameObjects.get(g).Start();

        while(!input.pause) {
            g = frame.getGraphics();

            long deltaTime = lastTime - System.currentTimeMillis();
            lastTime += deltaTime;
            
            //Update all GameObjects
            for(int g = gameObjects.size()-1; g >= 0; g--)//loop runs backwards to prevent error on deletion
                gameObjects.get(g).Update(deltaTime);

            drawMap();
        }
    }

    private void drawMap(){
        drawing().setColor(Color.DARK_GRAY);
        for(int x = 0; x < Map.instance.size.x; x++)
            for(int y = 0; y < Map.instance.size.y; y++){
                if(Map.instance.IsCol(new Vector2Int(x,y)))
                    drawing().fillRect(x*tileSize.x, y*tileSize.y, tileSize.x, tileSize.y);
            }
    }

    public void drawImage(Image img, Vector2Int pos){
        g.drawImage(img, pos.x, pos.y, null);
    }

    public Graphics drawing(){
        return g;
    }


    public void removeGO(GameObject go){
        gameObjects.remove(go);
    }

}