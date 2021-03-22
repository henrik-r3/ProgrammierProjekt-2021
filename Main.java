import javax.swing.*;
import java.awt.event.*;
import java.awt.Color;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("advanced Pacman");
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 1000);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        //frame.setUndecorated(true);
        frame.setVisible(true);

        String difficulty = JOptionPane.showInputDialog(
            frame,
            "Wähle einen Schwierigkeitsgrad aus!\n"
            + "\"Gib dazu eine ganze Zahl ein.\""
            + "\"Je größer die Zahl, desto schwieriger.\"",
            "Schwierigkeitsgrad",
            JOptionPane.PLAIN_MESSAGE);
        

        //Add buttons for the End of the Game Game
        JButton restart = new JButton("Restart");
        restart.setBackground(Color.GREEN);
        restart.setBounds(frame.getWidth()/2-50, 400, 100, 40);
        restart.setForeground(Color.BLACK);
        //restart.setMargin(new Insets(2, 2, 2, 2));
        restart.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
                Game.instance = null;
                frame.dispose();
                main(args);
            }
        });
        frame.add(restart);

        DrawPanel drawP = new DrawPanel();
        drawP.setSize(frame.getSize());
        drawP.setLocation(0, 0);
        frame.add(drawP);

        
        restart.setVisible(false);
        new Game(frame, Math.abs(Integer.parseInt(difficulty)));
        restart.setVisible(true);
    }

    
    
}
