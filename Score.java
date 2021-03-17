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

    //Wenn Methode gerufen wird, wird Score zurückgegeben
    public int getScore(){

        return scoreval;
    }

}