import java.util.ArrayList;
import java.util.Random;
import java.awt.*;

import javax.swing.JFrame;


//Handles GameObjects, causes Updates and Runs the Game in general (e.g. Pausing, etc.)
public class Game{
    public static Game instance;
    public int difficulty = 1;

    JFrame frame;
    private Graphics g;
    private Vector2Int tileSize;

    public Input input = new Input();
    public Random rnd = new Random();

    long lastTime = 0;
    private ArrayList<GameObject> gameObjects;//pool of all GameObjects


    public Game(JFrame frame){
        //INIT -------------------------------------------------
        instance = this;
        this.frame = frame;
        frame.addKeyListener(input);

        gameObjects = new ArrayList<GameObject>();//add initial GameObjects

        CreateGameFromDifficulty();

        //RUN THE GAME -----------------------------------------
        lastTime = System.currentTimeMillis();
        for(int g = gameObjects.size()-1; g >= 0; g--)//loop runs backwards to prevent error on deletion
            gameObjects.get(g).Start();

        while(true){
            frame.repaint();
        }
    }

    public void CreateGameFromDifficulty(){
        new Map(new Vector2Int(difficulty*5+10, difficulty*5+10));
        tileSize = new Vector2Int(frame.getWidth() / Map.instance.size.x, frame.getHeight() / Map.instance.size.y);
        frame.setSize(tileSize.x*Map.instance.size.x, tileSize.y*Map.instance.size.y+30);//+50 für die titelleiste

        new Score();//creates a score object

        System.out.println("afdgkdjhgnf");
        Map.instance.generateMap(rnd, Map.instance.size.x+Map.instance.size.y);     
        System.out.println("kääpä");

        //add Gameobjects
        Vector2Int startPos = Map.instance.getRandomPos();
        gameObjects.add( new Pacman(startPos.x, startPos.y, 3) );

        int GhostCount = (int)(difficulty * 1.2);
        int PowerberryCount = (int)(difficulty * 1.4);

        //create ghosts
        String[] ghostColors = {"green", "pink", "red", "yellow"};
        for(int i = 0; i < GhostCount; i++)
        {
            startPos = Map.instance.getRandomPos();
            gameObjects.add( new Ghosts(startPos.x, startPos.y, 5, ghostColors[rnd.nextInt(ghostColors.length)]));
        }

        //create powerberrys
        for(int i = 0; i < PowerberryCount; i++)
        {
            startPos = Map.instance.getRandomPos();
            gameObjects.add( new Powerberry(startPos.x, startPos.y));
        }
        
    }


    public void UpdateGame(){
        if(lastTime == 0 || input.pause)
            return;

        long currentTime = System.currentTimeMillis();
        long deltaTime = currentTime - lastTime;
        lastTime = currentTime;

        //Update all GameObjects
        for(int go = gameObjects.size()-1; go >= 0; go--)//loop runs backwards to prevent error on deletion
            gameObjects.get(go).Update(deltaTime);
    }

    public void DrawGame(Graphics g){
        if(lastTime == 0)
            return;
        this.g = g;
        drawMap();

        //Draw all GameObjects
        for(int go = gameObjects.size()-1; go >= 0; go--)//loop runs backwards to prevent error on deletion
            gameObjects.get(go).Draw();
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
        g.drawImage(img, pos.x*tileSize.x + (tileSize.x-img.getWidth(frame))/2, pos.y*tileSize.y + (tileSize.y-img.getHeight(frame))/2, frame);
    }

    public void drawPath(Vector2Int[] path){
        drawing().setColor(Color.green);
        for(int i = 0; i < path.length; i++){
            drawing().fillRect(path[i].x*tileSize.x, path[i].y*tileSize.y, tileSize.x, tileSize.y);
        }
    }

    public Graphics drawing(){
        return g;
    }


    public void removeGO(GameObject go){
        gameObjects.remove(go);
    }

}