import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("advanced Pacman");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        //frame.setUndecorated(true);
        frame.setVisible(true);
        
        DrawPanel drawP = new DrawPanel();
        drawP.setSize(frame.getSize());
        drawP.setLocation(0, 0);
        frame.add(drawP);

        new Game(frame);
    }
    
}
