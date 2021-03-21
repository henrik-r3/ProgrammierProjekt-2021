import java.awt.event.*;

// Hört dauerhaft ab, ob eine Taste gedrückt wurde
public class Input extends KeyAdapter {      
    public Vector2Int direction = Vector2Int.down; //Standardrichtung ist abwärts
    public boolean pause = false;

    @Override
    public void keyPressed(KeyEvent e) {
        //Wird eine Pfeiltaste gedrückt, wird die Laufrichtung des Pacman entsprechend gesetzt
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_UP) {
            direction = Vector2Int.up;
        } else if (key == KeyEvent.VK_DOWN) {
            direction = Vector2Int.down;
        } else if (key == KeyEvent.VK_LEFT) {
            direction = Vector2Int.left;
        } else if (key == KeyEvent.VK_RIGHT) {
            direction = Vector2Int.right;
        } else if (key == KeyEvent.VK_ESCAPE) {
            pause = !pause; //Spiel wird angehalten (Keine Bewegung)
        }
    }
}
