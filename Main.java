import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("advanced Pacman");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 1000);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        //frame.setUndecorated(true);
        frame.setVisible(true);

        
        Object[] possibilities = {null};
        /*int difficulty = (int)JOptionPane.showInputDialog(
                    frame,
                    "Wähle einen Schwierigkeitsgrad aus!\n"
                    + "\"Gib dazu eine ganze Zahl ein.\""
                    + "\"Je größer die Zahl, desto schwieriger.\"",
                    "Schwierigkeitsgrad",
                    JOptionPane.PLAIN_MESSAGE,
                    icon,
                    possibilities);*/
        String difficulty = JOptionPane.showInputDialog(
            frame,
            "Wähle einen Schwierigkeitsgrad aus!\n"
            + "\"Gib dazu eine ganze Zahl ein.\""
            + "\"Je größer die Zahl, desto schwieriger.\"",
            "Schwierigkeitsgrad",
            JOptionPane.PLAIN_MESSAGE);
        
        DrawPanel drawP = new DrawPanel();
        drawP.setSize(frame.getSize());
        drawP.setLocation(0, 0);
        frame.add(drawP);

        new Game(frame);
    }
    
}
