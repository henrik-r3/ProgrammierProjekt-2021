public class Score{

    private int scoreval;
    public static Score scoreinstance;

    public Score(){
        scoreval = 0;
        scoreinstance = this;
    }

    public void eatsFood(){

        scoreval++;
    }

    public void eatsPowerBerry(){

        scoreval+= 100;
    }

    public void ateallpowerberries(){

        scoreval += 400;
    }

    public void eatsGhost(){

        scoreval += 200;
    }

    public int getScore(){

        return scoreval;
    }

}