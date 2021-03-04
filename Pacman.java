
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

public class Pacman extends GameObject{

    private Vector2Int direction; // Speichert die Richtung, in die sich der Pacman bewegt
    private Image imgup, imgdown, imgleft, imgright; // Pacman hat 4 Bilder für jede mögliche Richtung
    private Vector2Int position = new Vector2Int(); // Position des Pacman
    private Vector2Int startposition = new Vector2Int();
    private int remaininglives;
    private boolean caught = false;
    public static Pacman instance;

    

    public Pacman(int startx, int starty, int lives) {
        position.x = startposition.x = startx;
        position.y = startposition.y = starty;
        direction = Vector2Int.down;
        remaininglives = lives;
        imgup = new ImageIcon("Bilder/PacUp.gif").getImage();
        imgdown = new ImageIcon("Bilder/PacDown.gif").getImage();
        imgleft = new ImageIcon("Bilder/PacLeft.gif").getImage();
        imgright = new ImageIcon("Bilder/PacRight.gif").getImage();
    }

    @Override
    public void Update(long deltaTime){
        direction = Game.instance.input.direction;
        calculatePosition();
        calculatelives();
    }


    public void calculatePosition() {
       
            
        if (!Map.instance.IsCol(position.Add(direction))) {
            position = position.Add(direction);
        }

        if (Map.instance.IsFood(position)) {
            Score.scoreinstance.eatsFood();
        }
        drawPacman();
            
        
    }

    public void drawPacman() {
         
        if (direction.equals(Vector2Int.down)) {
            Game.instance.drawImage(imgdown, position);
        } else if (direction.equals(Vector2Int.up)) {
            Game.instance.drawImage(imgup, position);
        } else if (direction.equals(Vector2Int.left)) {
            Game.instance.drawImage(imgleft, position);
        } else if (direction.equals(Vector2Int.right)) {
            Game.instance.drawImage(imgright, position);
        }
    }

    public void hasbeencaught(){

        caught = true;
    }

    public void calculatelives() {
        
        if (Boolean.TRUE.equals(caught)) {

            remaininglives--;
            position.x = startposition.x;
            position.y = startposition.y;
        }
        if (remaininglives < 1) {
            // stop timer, Score screen
        } else {
            drawlives();
        }
        caught = false;
    }

    public void drawlives() {

        Image heart = new ImageIcon("*/Bilder/Heart.png").getImage();

        for (int i = 0; i < remaininglives; i++) {

            Graphics g = Game.instance.drawing();
            g.drawImage(heart, heart.getWidth(null) * i + 10, 300 - (heart.getHeight(null) + 10), null);
        }
    }

    public Vector2Int getposition() {

        return position;
    }

}
