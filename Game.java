import java.util.ArrayList;
import java.util.Arrays;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;


//Handles GameObjects, causes Updates and Runs the Game in general (e.g. Pausing, etc.)
public class Game{
    public static Game instance;

    JFrame frame;
    private Graphics g;
    boolean test = true;

    private ArrayList<GameObject> gameObjects;//pool of all GameObjects

    public Game(JFrame frame, GameObject[] initialGameObjects){
        //INIT -------------------------------------------------
        instance = this;
        this.frame = frame;
        gameObjects = new ArrayList<GameObject>(Arrays.asList(initialGameObjects));//add initial GameObjects

        new Map(new Vector2Int(20, 20));
        

        //RUN THE GAME -----------------------------------------
        long lastTime = System.currentTimeMillis();
        for(int g = gameObjects.size()-1; g >= 0; g--)//loop runs backwards to prevent error on deletion
            gameObjects.get(g).Start();

        boolean run = true;
        while(run) {
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
        Vector2Int tileSize = new Vector2Int(frame.getWidth() / Map.instance.size.x, frame.getHeight() / Map.instance.size.y);
        for(int x = 0; x < Map.instance.size.x; x++)
            for(int y = 0; y < Map.instance.size.y; y++){
                if(Map.instance.IsCol(new Vector2Int(x,y)))
                    drawing().fillRect(x*tileSize.x, y*tileSize.y, tileSize.x, tileSize.y);
            }
    }

    public Graphics drawing(){
        return g;
    }


    public void removeGO(GameObject go){
        gameObjects.remove(go);
    }

}