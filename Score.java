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

        scoreval+= 10;
    }

    public void eatsGhost(){

        scoreval += 100;
    }

    public int getScore(){

        return scoreval;
    }

}