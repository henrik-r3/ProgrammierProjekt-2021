import java.util.*;

public class Map {
    public static Map instance;
    
    enum Tile{ empty, wall, food; }
    
    Vector2Int size;

    Tile[] map;

    int foodCount = 0;

    public Map(Vector2Int size){
        size.x = (size.x/2)*2+1;//make size.x odd
        size.y = (size.y/2)*2+1;//make size.y odd -> get a border
        this.size = size;
        instance = this;
        map = new Tile[size.x * size.y];
        //Arrays.fill(map, Tile.empty);
        Arrays.fill(map, Tile.wall);

        //generateMap(Game.instance.rnd, 20);

        //printMap();
    }

    public void SetTile(Vector2Int pos, Tile tile){ 
        if(tile.equals(Tile.food))//food get added
            foodCount++;
        if(GetTile(pos).equals(Tile.food))
            foodCount--;

        map[pos.x + pos.y * size.x] = tile; 
    }

    public Tile GetTile(Vector2Int pos){ return map[pos.x + pos.y * size.x]; }
    public boolean IsCol  (Vector2Int pos){ return GetTile(pos).equals(Tile.wall); }
    public boolean RemoveFood(Vector2Int pos){
        if(GetTile(pos).equals(Tile.food)){
            SetTile(pos, Tile.empty);
            return true;
        }
        return false;
    }


    private boolean inBounds(Vector2Int pos){
        return (pos.x >= 0 && pos.x < size.x) && (pos.y >= 0 && pos.y < size.y);
    }

    static int[] Random_Order(Random rnd) {//Generates Random Order
        int[] order = new int[4];
        ArrayList<Integer> available = new ArrayList<Integer>();
        available.add(0); available.add(1); available.add(2); available.add(3);
        for (int i = 0; i < order.length; i++) {
            int pick = rnd.nextInt(available.size());
            order[i] = available.get(pick);
            available.remove(pick);
        }

        return order;
    }


    public void generateMap(Random rnd, int minLength){
        Tile path = Tile.food;

        Vector2Int start = new Vector2Int(5, 5);
        IterativeDFS(start, path, rnd, 10000);
        //dfs(start, start, -1, Tile.empty, Game.instance.rnd, 50000);
        ConnectDeadEnd(start, path, Game.instance.rnd);

        //generate additional connections in map
        double connectProb = 0.05;
        for(int x = 0; x < Math.floor(size.x*0.5); x++)
            for(int y = 0; y < Math.floor(size.y*0.5); y++){
                Vector2Int pos = new Vector2Int(x*2+1, y*2+1);
                if(!IsCol(pos)){//if pos is path
                    for(int d = 0; d < Vector2Int.dirs.length; d++){
                        Vector2Int nPos = pos.Add(Vector2Int.dirs[d].Mul(2));
                        if(inBounds(nPos)){
                            Vector2Int intermediate = pos.Add(nPos).Mul(0.5);
                            if(!IsCol(nPos) && IsCol(intermediate) && rnd.nextDouble() < connectProb)//just use certain percentage of possible connections
                                SetTile(intermediate, path);//set intermediate path at (nPos+pos)/2
                        }   
                    }
                }
            }
    }

    /*public void dfs(Vector2Int pos, Vector2Int lastP, int lastD, Tile path, Random rnd, int TimeConst) {
        try{
            Thread.sleep(Math.max(1, TimeConst / (size.x * size.y)));//scale sleep by map size
        }catch(Exception e){

        }
        Game.instance.frame.repaint();//draw map to show update


        //connect node to the one before
        SetTile(pos, path);

        Vector2Int conWall = pos.Add(lastP.Add(pos.Mul(-1)).Mul(0.5));//pos + (lastP - pos) / 2
        SetTile(conWall, path);

        boolean deadEnd = true;

        int[] order = Random_Order(rnd);
        for (int d = 0; d < Vector2Int.dirs.length; d++) {
            if(order[d] == lastD && d < Vector2Int.dirs.length-1){//if we've got lastD and it is not in the last pos
                //swap this element with the last -> less straight paths
                int swap = order[Vector2Int.dirs.length-1];
                order[Vector2Int.dirs.length-1] = order[d];
                order[d] = swap;
            }

            Vector2Int nPos = pos.Add(Vector2Int.dirs[order[d]].Mul(2));
            if (!inBounds(nPos))//if tile is not on map ...
                continue;//... skip
            
            if (!IsCol(nPos))//if tile was visited before ...
                continue;//... skip

            dfs(nPos, pos, d, path, rnd, TimeConst);
            deadEnd = false;
        }

        if(deadEnd){
            ConnectDeadEnd(pos, path, rnd);//connect dead end to somwhere -> no dead ends  (not part of original DFS)
        }
    }*/

    void ConnectDeadEnd(Vector2Int pos, Tile path, Random rnd){
        int[] order = Random_Order(rnd);
        for (int d = 0; d < Vector2Int.dirs.length; d++) {
            Vector2Int nPos = pos.Add(Vector2Int.dirs[order[d]].Mul(2));
            
            if (!inBounds(nPos))//if tile is not on map ...
                continue;//... skip

            if (IsCol(nPos))//if tile was not visited before ...
                continue;//... skip

            Vector2Int nWall = pos.Add(Vector2Int.dirs[order[d]]);
            if(!IsCol(nWall))//if connection was already made
                continue;

            SetTile(nWall, path);
            break;
        }
    }

    void IterativeDFS(Vector2Int start, Tile path, Random rnd, int TimeConst){
        Stack<Vector2Int> posStack = new Stack<Vector2Int>();
        Stack<Vector2Int> lastStack = new Stack<Vector2Int>();

        posStack.add(start);
        lastStack.add(start);

        while(!posStack.empty()){
            Vector2Int pos = posStack.pop();
            Vector2Int lastP = lastStack.pop();


            if (!IsCol(pos))//if tile was visited before ...
                continue;//... skip

            try{
                Thread.sleep(Math.max(1, TimeConst / (size.x * size.y)));//scale sleep by map size
            }catch(Exception e){
            }
            Game.instance.frame.repaint();//draw map to show update
    
            //connect node to the one before
            SetTile(pos, path);
    
            Vector2Int conWall = pos.Add(lastP.Add(pos.Mul(-1)).Mul(0.5));//pos + (lastP - pos) / 2
            SetTile(conWall, path);
    
            boolean deadEnd = true;
    
            int[] order = Random_Order(rnd);
            for (int d = 0; d < Vector2Int.dirs.length; d++) {
    
                Vector2Int nPos = pos.Add(Vector2Int.dirs[order[d]].Mul(2));
                if (!inBounds(nPos))//if tile is not on map ...
                    continue;//... skip
                
                if (!IsCol(nPos))//if tile was visited before ...
                    continue;//... skip
    
                posStack.add(nPos);
                lastStack.add(pos);
                deadEnd = false;
            }
    
            if(deadEnd){
                ConnectDeadEnd(pos, path, rnd);//connect dead end to somwhere -> no dead ends  (not part of original DFS)
            }
        }
    }


    /*
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
        
        double connectProb = 0.15;
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
    }*/

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