
import java.awt.Image;
import javax.swing.ImageIcon;

public class Pacman extends GameObject{

    private Vector2Int direction; // Speichert die Richtung, in die sich der Pacman bewegt
    private Image imgup, imgdown, imgleft, imgright; // Pacman hat 4 Bilder für jede mögliche Richtung
    private Vector2Int position = new Vector2Int(); // Position des Pacman
    private Vector2Int startposition = new Vector2Int(); // Startposition des Pacman
    private int remaininglives; // Leben des Pacman
    private boolean caught = false; // wurde Pacman gefangen?
    private boolean huntingghosts; // Jagt er die Geister?
    public static Pacman pacinstance; // Für Abfrage durch andere Klassen (statischer Zugriff)

    
    //Konstruktor für Pacman, bekommt Startposition und Anzahl an Leben (3 Stück) übergeben
    public Pacman(int startx, int starty, int lives) {
        position.x = startposition.x = startx; //Startposition und aktuelle Position werden auf übergeben Daten gesetzt
        position.y = startposition.y = starty;
        direction = Vector2Int.down;
        remaininglives = lives;
        huntingghosts = false;
        //Die einzelnen Bilder werden geladen
        imgup = new ImageIcon("Bilder/PacUp.gif").getImage();
        imgdown = new ImageIcon("Bilder/PacDown.gif").getImage();
        imgleft = new ImageIcon("Bilder/PacLeft.gif").getImage();
        imgright = new ImageIcon("Bilder/PacRight.gif").getImage();
        pacinstance = this;
    }


    long moveTimer = 300;
    long timer = 0;


    @Override
    public void Update(long deltaTime){     
        //Richtung wird dabei permanent aus der Input Klasse abgerufen
        direction = new Vector2Int(Game.instance.input.direction.x, -Game.instance.input.direction.y);

        //Pacman wird etwa 3 Mal pro Sekunde aktualisiert
        timer += deltaTime;
        if(timer >= moveTimer)
        {   
            timer = 0;
            //Leben und Position neu berechnen
            calculatePosition();  
            calculatelives();
        }

    }

    @Override
    public void Draw(){
        //Pacman und siene Leben werden dauerhaft gezeichnet
        drawPacman();
        drawlives();

    }


    public void calculatePosition() {
        
        
            //Solange keine Kollision mit Wand entsteht: Pacman in "direction" verschieben
        if (!Map.instance.IsCol(position.Add(direction))) {
            position = position.Add(direction);
        }
        //wenn Feld Essen ist
        if (Map.instance.RemoveFood(position)) {
            Score.scoreinstance.eatsFood();//Score erhöhen

        }        
    }

    public void drawPacman() {
         //Pacman zeichnen, für jede Richtung anders
        if (direction.equals(Vector2Int.up)) {
            Game.instance.drawImage(imgdown, position);
        } else if (direction.equals(Vector2Int.down)) {
            Game.instance.drawImage(imgup, position);
        } else if (direction.equals(Vector2Int.left)) {
            Game.instance.drawImage(imgleft, position);
        } else if (direction.equals(Vector2Int.right)) {
            Game.instance.drawImage(imgright, position);
        }
    }

    public void hasbeencaught(){
        //Wenn Berührung mit Geistern
        caught = true;
    }

    public void calculatelives() {
        

        if (Boolean.TRUE.equals(caught)) {
            //wenn Pacman gefangen wurde: Leben abziehen und zu Startposition
            remaininglives--;
            position.x = startposition.x;
            position.y = startposition.y;
        }
        //wenn Pacman keine Leben hat wird Spiel beendet 
        if (remaininglives < 1) {
            Destroy();
            Game.instance.EndGame();
            
        }

        caught = false; //Wieder auf falsch setzen
    }

    public void drawlives() {
        //Bild der Herzen laden
        Image heart = new ImageIcon("Bilder/Heart.png").getImage();
        //Für Anzahl an Leben Herzen zeichnen
        for (int i = 0; i < remaininglives; i++) {
            //Immer verschoben (Nicht alle Herzen an einer Stelle)
            Game.instance.drawImage(heart, new Vector2Int(i , Map.instance.size.y-1));
            
        }
    }

    public Vector2Int getposition() {
        //Position des Pacman ausgeben
        return new Vector2Int(position.x , position.y);
    }

    public void sethunting(boolean activated){
        //Sagen, ob Pacman jagt oder nicht
        huntingghosts = activated;

    }

    public boolean gethunting(){
        //Abfragen ob Pacman jagt
        return huntingghosts;
    }


    //Gibt Leen des Pacman aus
    public int Pacmanlives(){

        return remaininglives;
    }

}
