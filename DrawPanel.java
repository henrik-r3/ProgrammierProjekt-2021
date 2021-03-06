import java.awt.*;
import javax.swing.*;

public class DrawPanel extends JPanel{   
    private static final long serialVersionUID = 1L;

    public Color BackgroundColor = Color.black;

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(BackgroundColor);
        g.fillRect(0, 0, getWidth(), getHeight());

        if(Game.instance != null){
            Game.instance.DrawGame(g);
            Game.instance.UpdateGame();
        }
    }
}
