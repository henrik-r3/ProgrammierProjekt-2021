import java.awt.event.*;
public class Input extends KeyAdapter {      //in Calculate Position einbauen
    public Vector2Int direction = Vector2Int.down;
    public boolean pause = false;

    @Override
    public void keyPressed(KeyEvent e) {
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
            pause = !pause;
        }
    }
}
