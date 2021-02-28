import java.util.*;

public class Map {
    public static Map instance;
    
    enum Tile{ empty, wall, food; }
    
    Vector2Int size;

    Tile[] map;

    public Map(Vector2Int size){
        this.size = size;
        instance = this;
        map = new Tile[size.x * size.y];
        //TODO generate map
        
        //TEST
        int[] mapI = { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                       1, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                       1, 0, 2, 2, 0, 0, 0, 0, 0, 1,
                       1, 0, 2, 2, 0, 0, 0, 0, 0, 1,
                       1, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                       1, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                       1, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                       1, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                       1, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                       1, 1, 1, 1, 1, 1, 1, 1, 1, 1
                    };
        for (int i = 0; i < mapI.length; i++) {
            map[i] = Tile.values()[mapI[i]];
        }

        printMap();
    }

    public void SetTile(Vector2Int pos, Tile tile){ map[pos.x + pos.y * size.x] = tile; }
    public Tile GetTile(Vector2Int pos){ return map[pos.x + pos.y * size.x]; }
    public boolean IsCol  (Vector2Int pos){ return GetTile(pos).equals(Tile.wall); }
    public boolean EatFood(Vector2Int pos){
        if(GetTile(pos).equals(Tile.food)){
            SetTile(pos, Tile.empty);
            return true;
        }
        return false;
    }


    private boolean inBounds(Vector2Int pos){
        return (pos.x >= 0 && pos.x < size.x) && (pos.y >= 0 && pos.y < size.y);
    }

    /*
    static int[] Random_Order(Random rnd) {//Generates Random Order
        int[] order = new int[4];
        ArrayList<Integer> available = Arrays.asList(0, 1, 2, 3);
        for (int i = 0; i < order.length; i++) {
            int pick = rnd.NextInt(available.size());
            order[i] = available.get(pick);
            available.remove(pick);
        }

        return order;
    }*/

    void generateMap() {
        ArrayList<Vector2Int> loop;
        boolean closed = false;
        while(!closed){

        }
        //generate connections within
    }


    public void printMap(){
        for(int x = 0; x < size.x; x++){
            for(int y = 0; y < size.y; y++){
                Tile tile = GetTile(new Vector2Int(x, y));
                if(tile == null)
                    System.out.print(". | ");
                else
                    System.out.print(tile.ordinal() + " | ");
            }
            System.out.println();
        }    
    }

}