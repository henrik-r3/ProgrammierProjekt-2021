import java.util.*;

public class Map {
    public static Map instance;
    
    enum Tile{ empty, wall, food; }
    
    Vector2Int size;

    Tile[] map;

    public Map(Vector2Int size){
        size.x = (size.x/2)*2+1;//make size.x odd
        size.y = (size.y/2)*2+1;//make size.y odd -> get a border
        this.size = size;
        instance = this;
        map = new Tile[size.x * size.y];
        Arrays.fill(map, Tile.empty);

        //generateMap(Game.instance.rnd, 20);

        //printMap();
    }

    public void SetTile(Vector2Int pos, Tile tile){ map[pos.x + pos.y * size.x] = tile; }
    public Tile GetTile(Vector2Int pos){ return map[pos.x + pos.y * size.x]; }
    public boolean IsCol  (Vector2Int pos){ return GetTile(pos).equals(Tile.wall); }
    public boolean IsFood(Vector2Int pos){
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

    public void generateMap(Random rnd, int minLength) {
        Vector2Int startPos = new Vector2Int(rnd.nextInt(size.x/2)*2+1, rnd.nextInt(size.y/2)*2+1);//get a random odd stating pos
        Vector2Int pos = startPos;
        int loopLength = 0;
        SetTile(pos, Tile.food);

        boolean closed = false;
        while(!closed){
            AStar.Grid grid = new AStar.Grid(size.Mul(0.5));
            ArrayList<Vector2Int> possibleDirs = new ArrayList<Vector2Int>();
            ArrayList<Integer> possibleLength = new ArrayList<Integer>();
            boolean closingPossible = false;
            for(int d = 0; d < Vector2Int.dirs.length; d++) {
                Vector2Int nPos = pos.Add(Vector2Int.dirs[d].Mul(2));//dirs*2 to have a pathlike structure
                if(nPos.equals(startPos))//if next Pos is start Pos -> loop is about to close
                {   
                    if(loopLength >= minLength){
                        closed = true;
                        break;
                    }else{
                        closingPossible = true;
                        continue;
                    }
                        
                }
                if(!inBounds(nPos))//do not us out of bounds
                    continue;
                if(IsCol(nPos))
                    continue;

                Vector2Int[] path = AStar.FindLongestPath(nPos.Mul(0.5), startPos.Mul(0.5), grid);
                if(path != null){
                    possibleDirs.add(nPos);
                    possibleLength.add(path.length);
                }//else
                    //System.out.println(Vector2Int.dirs[d] + " is not reachable");
                    
            }

            if(closingPossible && possibleDirs.size() == 0) {//prevent it from dying
                closed = true;
                System.out.println("fuck, its the last option...");
            }
                

            if(possibleDirs.size() == 0 && !closed && !closingPossible)
            {
                System.out.println("THERE IS NO WAY OUT! ARRGGGGHHH!!");
                System.out.println(loopLength);
                break;
            }

            if(!closed){
                
                //choose not at random, but rather by most length gain
                int max = 0;
                for(int i = 1; i < possibleLength.size(); i++){
                    if(possibleLength.get(max) < possibleLength.get(i))
                        max = i;
                }
                Vector2Int nPos = possibleDirs.get(max);//choose dir with most length
                


                //Vector2Int nPos = possibleDirs.get(rnd.nextInt(possibleDirs.size()));//choose next dir at random

                SetTile(nPos, Tile.wall);
                SetTile(pos.Add(nPos.Add(pos.Mul(-1)).Mul(0.5)), Tile.wall);//set intermediate wall at pos + (npos-pos)/2
                pos = nPos;
                //printMap();
                //System.out.println();
                loopLength++;
            }else{
                Vector2Int nPos = startPos;
                SetTile(nPos, Tile.wall);
                SetTile(pos.Add(nPos).Mul(0.5), Tile.wall);//set intermediate wall at (nPos+pos)/2
            }
            
        }
        

        //generate connections within
        
        double connectProb = 0.1;
        for(int x = 0; x < Math.floor(size.x*0.5); x++)
            for(int y = 0; y < Math.floor(size.y*0.5); y++){
                pos = new Vector2Int(x*2+1, y*2+1);
                if(IsCol(pos)){//if pos is path
                    for(int d = 0; d < Vector2Int.dirs.length; d++){
                        Vector2Int nPos = pos.Add(Vector2Int.dirs[d].Mul(2));
                        if(inBounds(nPos))
                            if(IsCol(nPos) && rnd.nextDouble() < connectProb)//just use certain percentage of possible connections
                                SetTile(pos.Add(nPos).Mul(0.5), Tile.wall);//set intermediate wall at (nPos+pos)/2
                    }
                }
            }


        //invert map -> using walls as path
        for(int i = 0; i < map.length; i++)
            if(map[i] == Tile.empty)
                map[i] = Tile.wall;
            else if(map[i] == Tile.wall)
                map[i] = Tile.empty;
    }

    public Vector2Int getRandomPos(){
        Random rnd = Game.instance.rnd;
        while(true){
            Vector2Int pos = new Vector2Int(rnd.nextInt(size.x), rnd.nextInt(size.y));
            if(!IsCol(pos))
                return pos;
        }
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
        System.out.println(); 
    }

}