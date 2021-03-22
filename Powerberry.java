import java.awt.Image;
import javax.swing.ImageIcon;

public class Powerberry extends GameObject{

    //Attribute für Powerberry
    private boolean isactive = false;
    private Image berry;
    public int amountberries;
    private Vector2Int berryposition = new Vector2Int();
    

    //Konstruktor für Powerberry, bekommt Position übergeben
    public Powerberry(int startx, int starty){
        berryposition.x = startx;
        berryposition.y = starty;
        berry = new ImageIcon("Bilder/Powerberry.png").getImage(); //Bild wird geladen
    }

    //Powerbeere wird alle 300 Millisekunden (Bewegungsintervall des Pacman) aktualisiert
    long checkTimer = 300;
    long timer = 0;
    //Powerbeere soll 7 Sekunden lang anhalten
    long berryactive = 0;
    long berrystop = 7000;


    @Override 
    public void Update(long deltaTime){

        //Alle 300 Millisekunden nach Kollision mit Pacman schauen
        timer += deltaTime;
        if(timer >= checkTimer)
        {   
            timer = 0;
            checkcollision();  

        }

        //Beere soll 7 Sekunden lang anhalten
        if(isactive){
            berryactive += deltaTime;
            isactive = true;
            if(berryactive > berrystop){

                isactive = false;
                Pacman.pacinstance.sethunting(isactive); //Dann soll Pacman soll nicht mehr jagen
                Destroy(); //Beere wird vollständig entfernt
            }
        }
    }

    @Override
    public void Draw(){
        //Solange Powerbeere nicht aktiv ist, wird sie gezeichnet
        if(!isactive)
            drawpowerberry();
    }

    public void checkcollision(){
        //Guckt, ob Pacman die Powerbeere berühert
        if(berryposition.equals(Pacman.pacinstance.getposition())){
            
            Score.scoreinstance.eatsPowerBerry();//Score erhöhen
            isactive = true;//ist nun aktiv -> nicht mehr zeichnen, nach 7 Sekunden entfernen
            Pacman.pacinstance.sethunting(true);//Pacman soll nun Geister jagen können
            amountberries--;//Weniger Beeren vorhanden
            if(amountberries == 0)
                Score.scoreinstance.ateallpowerberries();//Keine Beere mehr vorhanden -> Extrapunkte
        }

    }

    //Powerbeere wird gezeichnet
    public void drawpowerberry(){

        Game.instance.drawImage(berry, berryposition);
    }

    //Gibt die anfangs gesetzte Anzahl an Powerberrys an 
    public void setamountberries(int number){

        amountberries = number;
    }

}