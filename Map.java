import java.util.*;

public class Map {
    public static Map instance;
    
    enum Tile{ empty, wall, food; }
    
    Vector2Int size;

    Tile[] map;

    public Map(Vector2Int size){
        this.size = size;
        map = new Tile[size.x * size.y];

        Random rnd = new Random();//use one random object to make controll over seed possible
        generateMap(rnd);
        
        /*
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
        }*/

        printMap();

        instance = this;
    }

    public void SetTile(Vector2Int pos, Tile tile){ map[pos.x + pos.y * size.x] = tile; }
    public Tile GetTile(Vector2Int pos){ return map[pos.x + pos.y * size.x]; }
    public boolean IsCol  (Vector2Int pos){ return GetTile(pos).equals(Tile.wall); }


    private boolean inBounds(Vector2Int pos){
        return (pos.x >= 0 && pos.x < size.x) && (pos.y >= 0 && pos.y < size.y);
    }


    /*static int[] Random_Order(Random rnd) {//Generates Random Order
        int[] order = new int[4];
        ArrayList<Integer> available = Arrays.asList({0, 1, 2, 3});
        for (int i = 0; i < order.length; i++) {
            int pick = rnd.nextInt(available.size());
            order[i] = available.get(pick);
            available.remove(pick);
        }

        return order;
    }*/

    void generateMap(Random rnd) {
        Vector2Int startPos = new Vector2Int(1, 1);
        Vector2Int pos = startPos;
        boolean closed = false;
        while(!closed){
            AStar.Grid grid = new AStar.Grid();
            ArrayList<Vector2Int> possibleDirs = new ArrayList<Vector2Int>();
            for(int d = 0; d < Vector2Int.dirs.length; d++) {
                Vector2Int nPos = pos.Add(Vector2Int.dirs[d].Mul(2));//dirs*2 to have a pathlike structure
                if(nPos.equals(startPos))//if next Pos is start Pos
                {
                    closed = true;
                    break;
                }
                if(!inBounds(nPos))//do not us out of bounds
                    continue;

                if(AStar.FindPath(nPos, startPos, grid) != null)
                    possibleDirs.add(nPos);
            }

            Vector2Int nPos = possibleDirs.get(rnd.nextInt(possibleDirs.size()));//choos next dir at random
            SetTile(nPos, Tile.wall);
            SetTile(pos.Add(pos.Add(nPos.Mul(-1).Mul(0.5))), Tile.wall);//set intermediate wall
        }

        //TODO: generate connections within
        //TODO: invert map -> using walls as path
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