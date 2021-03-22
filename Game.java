import java.util.ArrayList;
import java.util.Random;
import java.awt.event.*;
import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.event.*;
import javax.swing.*;


//Handles GameObjects, causes Updates and Runs the Game in general (e.g. Pausing, etc.)
public class Game{
    public static Game instance;
    public int difficulty = 5;

    JFrame frame;
    private Graphics g;
    private Vector2Int tileSize;

    public Input input = new Input();
    public Random rnd = new Random();
    public boolean isRunning = true;

    long lastTime = 0;
    private ArrayList<GameObject> gameObjects;//pool of all GameObjects

    Image food;


    public Game(JFrame frame, int difficulty){
        //INIT -------------------------------------------------
        this.difficulty = difficulty;
        instance = this;
        this.frame = frame;
        frame.addKeyListener(input);
        food = new ImageIcon("Bilder/Food.png").getImage();

        gameObjects = new ArrayList<GameObject>();//add initial GameObjects

        CreateGameFromDifficulty();

        //RUN THE GAME -----------------------------------------
        lastTime = System.currentTimeMillis();
        for(int g = gameObjects.size()-1; g >= 0; g--)//loop runs backwards to prevent error on deletion
            gameObjects.get(g).Start();

        while(isRunning){
            frame.repaint();
        }
    }

    public void CreateGameFromDifficulty(){
        new Map(new Vector2Int(difficulty*3+10, difficulty*3+10));
        tileSize = new Vector2Int(frame.getWidth() / Map.instance.size.x, frame.getHeight() / Map.instance.size.y);
        frame.setSize(tileSize.x*Map.instance.size.x, tileSize.y*Map.instance.size.y+30);//+30 für die titelleiste

        new Score();//creates a score object

        //show animation of map generation
        input.pause = true;
        lastTime = 1;
        Map.instance.generateMap(rnd, Map.instance.size.x+Map.instance.size.y);     
        input.pause = false;

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
        if(lastTime == 0 || input.pause || !isRunning)
            return;

        long currentTime = System.currentTimeMillis();
        long deltaTime = currentTime - lastTime;
        lastTime = currentTime;

        //Update all GameObjects
        for(int go = gameObjects.size()-1; go >= 0; go--)//loop runs backwards to prevent error on deletion
            gameObjects.get(go).Update(deltaTime);

        //Test if Game ends
        if(Map.instance.foodCount == 0)
            EndGame();
    }

    public void DrawGame(Graphics g){
        if(lastTime == 0)
            return;
        this.g = g;
        drawMap();

        //Draw all GameObjects
        for(int go = gameObjects.size()-1; go >= 0; go--)//loop runs backwards to prevent error on deletion
            gameObjects.get(go).Draw();

        Score.scoreinstance.drawScore();

        if(!isRunning)
            drawEnd();
    }


    public void drawMap(){
        drawing().setColor(Color.DARK_GRAY);
        for(int x = 0; x < Map.instance.size.x; x++)
            for(int y = 0; y < Map.instance.size.y; y++){
                Vector2Int pos = new Vector2Int(x,y);
                if(Map.instance.IsCol(pos))
                    drawing().fillRect(x*tileSize.x, y*tileSize.y, tileSize.x, tileSize.y);
                else if(Map.instance.GetTile(pos).equals(Map.Tile.food)){
                    drawImage(food, pos);
                }
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

    private void drawEnd(){
        int fontSize = 90;
        if(Pacman.pacinstance.Pacmanlives() > 0){//pacman wins
            String text = "YOU WIN";
            java.awt.Font font = new java.awt.Font("Consolas", java.awt.Font.PLAIN, fontSize);
            Game.instance.drawing().setFont(font);
            Game.instance.drawing().setColor(Color.YELLOW);
            int width = drawing().getFontMetrics().stringWidth(text);
            Game.instance.drawing().drawString(text, frame.getWidth()/2-width/2, 300);
        }else{//pacman loses
            String text = "GAME OVER";
            java.awt.Font font = new java.awt.Font("Consolas", java.awt.Font.PLAIN, fontSize);
            Game.instance.drawing().setFont(font);
            Game.instance.drawing().setColor(Color.RED);
            int width = drawing().getFontMetrics().stringWidth(text);
            Game.instance.drawing().drawString(text, frame.getWidth()/2-width/2, 300);
        }
    }

    public Graphics drawing(){
        return g;
    }


    public void removeGO(GameObject go){
        gameObjects.remove(go);
    }


    public void EndGame(){
        isRunning = false;
        if(Pacman.pacinstance.Pacmanlives() > 0){//pacman wins
            //destroy all ghosts
            for(int go = gameObjects.size()-1; go >= 0; go--)//loop runs backwards to prevent error on deletion
                if(gameObjects.get(go).getClass() == Ghosts.class)    
                    gameObjects.remove(go);
        }else{//pacman loses
        }
    }

}