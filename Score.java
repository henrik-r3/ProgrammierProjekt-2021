public class Score{

    private int scoreval;
    public static Score instance;

    public Userscore(){
        scoreval = 0;
        instance = this;
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