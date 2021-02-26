import java.util.*;

public class Map {
    enum Tile{ empty, wall, food; }
    
    public Map(Vector2Int size){
        this.size = size;
        map = new Tile[size.x * size.y];
        //TODO generate map
        
        //TEST
        int[] mapI = { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1
                       1, 0, 0, 0, 0, 0, 0, 0, 0, 1
                       1, 0, 2, 2, 0, 0, 0, 0, 0, 1
                       1, 0, 2, 2, 0, 0, 0, 0, 0, 1
                       1, 0, 0, 0, 0, 0, 0, 0, 0, 1
                       1, 0, 0, 0, 0, 0, 0, 0, 0, 1
                       1, 0, 0, 0, 0, 0, 0, 0, 0, 1
                       1, 0, 0, 0, 0, 0, 0, 0, 0, 1
                       1, 0, 0, 0, 0, 0, 0, 0, 0, 1
                       1, 1, 1, 1, 1, 1, 1, 1, 1, 1
                    };
        for (int i = 0; i < mapI.Length; i++) {
            map[i] = Tile.getValues()[mapI[i]];
        }

        printMap();
    }


    Vector2Int size;

    Tile[] map;
    public void SetTile(Vector2Int pos, Tile tile){ map[pos.x + pos.y * size.x] = tile; }
    public Tile GetTile(Vector2Int pos){ return map[pos.x + pos.y * size.x]; }
    public boolean IsCol  (Vector2Int pos){ return GetTile(pos).contains(Tile.wall); }


    private boolean inBounds(Vector2Int pos){
        return (pos.x >= 0 && pos.x < size.x) && (pos.y >= 0 && pos.y < size.y);
    }


    static int[] Random_Order(Random rnd) {//Generates Random Order
        int[] order = new int[4];
        ArrayList<Integer> available = Arrays.asList(0, 1, 2, 3);
        for (int i = 0; i < order.Length; i++) {
            int pick = rnd.NextInt(available.Count);
            order[i] = available.get(pick);
            available.RemoveAt(pick);
        }

        return order;
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