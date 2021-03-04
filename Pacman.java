
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.event.*;

public class Pacman extends GameObject implements ActionListener{

    private boolean playing; // muss aus Main class bestimmt werden
    private Vector2Int direction; // Speichert die Richtung, in die sich der Pacman bewegt
    private Image imgup, imgdown, imgleft, imgright; // Pacman hat 4 Bilder für jede mögliche Richtung
    private Vector2Int position = new Vector2Int(); // Position des Pacman
    private Vector2Int startposition = new Vector2Int();
    private int remaininglives;
    public static Pacman instance;
    

    public Pacman(int startx, int starty, int lives) {
        position.x = startposition.x = startx;
        position.y = startposition.y = starty;
        direction = Vector2Int.down;
        remaininglives = lives;
        imgup = new ImageIcon("*/Bilder/PacUp.gif").getImage();
        imgdown = new ImageIcon("*/Bilder/PacDown.gif").getImage();
        imgleft = new ImageIcon("*/Bilder/PacLeft.gif").getImage();
        imgright = new ImageIcon("*/Bilder/PacRight.gif").getImage();
    }

    @Override
    public void Update(long deltaTime){
        calculatePosition();
        calculatelives();

    }


    public void calculatePosition() {
        if (playing) {
            
            if (!Map.instance.IsCol(position.Add(direction))) {
                position = position.Add(direction);
            }

            if (Map.instance.IsFood(position)) {
                Score.scoreinstance.eatsFood();
            }
            drawPacman();
            
        }
    }

    public void drawPacman() {
        Graphics g = Game.instance.drawing(); 
        if (direction.equals(Vector2Int.down)) {
            g.drawImage(imgdown, position.x, position.y, null);
        } else if (direction.equals(Vector2Int.up)) {
            g.drawImage(imgup, position.x, position.y, null);
        } else if (direction.equals(Vector2Int.left)) {
            g.drawImage(imgleft, position.x, position.y, null);
        } else if (direction.equals(Vector2Int.right)) {
            g.drawImage(imgright, position.x, position.y, null);
        }
    }

    public void calculatelives() {

        Boolean caught = Ghosts.hasbeencaught();
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

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
    
    }
}
