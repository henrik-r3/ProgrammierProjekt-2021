import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JFrame;

//Handles GameObjects, causes Updates and Runs the Game in general (e.g. Pausing, etc.)
public class Game{
    public static Game instance;

    JFrame frame;

    private ArrayList<GameObject> gameObjects;//pool of all GameObjects

    public Game(JFrame frame, GameObject[] initialGameObjects){
        //INIT -------------------------------------------------
        instance = this;
        this.frame = frame;
        gameObjects = new ArrayList<GameObject>(Arrays.asList(initialGameObjects));//add initial GameObjects

        new Map(new Vector2Int(10, 10));
        

        //RUN THE GAME -----------------------------------------
        long lastTime = System.currentTimeMillis();
        for(int g = gameObjects.size()-1; g >= 0; g--)//loop runs backwards to prevent error on deletion
            gameObjects.get(g).Start();

        boolean run = true;
        while(run) {
            long deltaTime = lastTime - System.currentTimeMillis();
            lastTime += deltaTime;
            
            //Update all GameObjects
            for(int g = gameObjects.size()-1; g >= 0; g--)//loop runs backwards to prevent error on deletion
                gameObjects.get(g).Update(deltaTime);

            frame.repaint();
        }
    }

    public void RemoveGO(GameObject go){
        gameObjects.remove(go);
    }

}