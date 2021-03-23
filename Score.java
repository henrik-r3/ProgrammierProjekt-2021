import java.awt.Color;

public class Score{

    private int scoreval;
    public static Score scoreinstance; //for non-static access

    //Objekt Score wird erstellt und hat am Anfang den Wert 0
    public Score(){
        scoreval = 0;
        scoreinstance = this;
    }

    //Wenn Methode gerufen wird, wird Score um 1 erhöht
    public void eatsFood(){

        scoreval++;
    }

    //Wenn Methode gerufen wird, wird Score um 100 erhöht
    public void eatsPowerBerry(){

        scoreval+= 100;
    }

    //Wenn Methode gerufen wird, wird Score um 400 erhöht
    public void ateallpowerberries(){

        scoreval += 400;
    }

    //Wenn Methode gerufen wird, wird Score um 200 erhöht
    public void eatsGhost(){

        scoreval += 200;
    }

    public void hasHealth(int health){
        scoreval += 300*health;
    }

    //Wenn Methode gerufen wird, wird Score zurückgegeben
    public int getScore(){
        return scoreval;
    }

    public void drawScore(){
        String score = Integer.toString(getScore());
        java.awt.Font font = new java.awt.Font("Consolas", java.awt.Font.PLAIN, 35);
        Game.instance.drawing().setFont(font);
        Game.instance.drawing().setColor(Color.white);
        Game.instance.drawing().drawString(score, 5, 35);
    }

}