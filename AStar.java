import java.util.ArrayList;
import java.util.HashSet;

public class AStar {
    private static float compareSign = -1;//-1 => smaller is better => gets the shortest path;
                                         // 1 => bigger is better => gets the longest path;

    public static class Grid
    {
        public Node[] grid;
        public Vector2Int size;
        public Vector2Int offset;

        public Grid()
        {
            size = Map.instance.size;

            grid = new Node[size.x * size.y];

            for(int x = 0; x < size.x; x++)
                for(int y = 0; y < size.y; y++)
                {   
                    Vector2Int pos = new Vector2Int(x, y);
                    boolean walkable = !Map.instance.IsCol(pos);
                    grid[Index(pos)] = new Node(pos, walkable);
                }
        }

        public Grid(Vector2Int smallerSize)//load only half the size
        {
            size = smallerSize;

            grid = new Node[size.x * size.y];

            for(int x = 0; x < size.x; x++){
                for(int y = 0; y < size.y; y++)
                {   
                    Vector2Int pos = new Vector2Int(x, y);
                    boolean walkable = !Map.instance.IsCol(pos.Mul(2).Add(new Vector2Int(1, 1)));//check collision at pos*2 + 1
                    grid[Index(pos)] = new Node(pos, walkable);
                }
            }
        }

        public boolean InBounds(Vector2Int pos) { return pos.x >= 0 && pos.x < size.x && pos.y >= 0 && pos.y < size.y; }
        public int Index(Vector2Int pos) { return pos.x + pos.y * size.x; }
        public Node get(Vector2Int pos) { return grid[Index(pos)]; }
    }



    public static class Node extends HeapItem<Node>
    {
        public Vector2Int pos;
        public boolean walkable;

        public Node parent;

        public int g_cost;//dist to start node (a)
        public int h_cost;//dist to end node (b)
        public int f_cost() { return g_cost + h_cost; }//g_cost + h_cost
        public int HeapIndex;

        public Node(Vector2Int pos, boolean walkable)
        {
            this.pos = pos;
            this.walkable = walkable;

            g_cost = compareSign < 0 ? Integer.MAX_VALUE : Integer.MIN_VALUE;//compareSign = -1: use maxVal ...
            h_cost = compareSign < 0 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
        }

        public Heap<Node> UpdateCosts(Node initiator, Vector2Int targetPos, Heap<Node> openSet)
        {
            //check if the initiator opens a new path, that is shorter and will lower the cost
            int newMoveCostToNeighb = initiator.g_cost + GetDistance(initiator.pos, pos);
            if ((int)Math.signum(compareSign)*(g_cost-newMoveCostToNeighb) < 0 || !openSet.Contains(this))//compareSign = -1: if(newMoveCostToNeighb < g_cost...        compareSign = 1: if(newMoveCostToNeighb > g_cost... 
            {
                g_cost = newMoveCostToNeighb;
                h_cost = GetDistance(pos, targetPos);
                parent = initiator;

                if (!openSet.Contains(this))
                    openSet.Add(this);
                else
                    openSet.UpdateItem(this);
            }
            return openSet;
        }

        public Heap<Node> Close(Grid grid, Vector2Int targetPos, Heap<Node> openSet, HashSet<Node> closedSet)
        {
            //update all neighbour's costs
            ArrayList<Node> neighbours = GetNeighbours(grid);
            for(int n = 0; n < neighbours.size(); n++)
            {
                if (!neighbours.get(n).walkable || closedSet.contains(neighbours.get(n)))
                    continue;

                openSet = neighbours.get(n).UpdateCosts(this, targetPos, openSet);
            }
            return openSet;
        }

        public ArrayList<Node> GetNeighbours(Grid grid)
        {
            ArrayList<Node> neighbours = new ArrayList<Node>();

            for (int x = -1; x <= 1; x++)
                for (int y = -1; y <= 1; y++)
                {
                    //if (x == 0 && y == 0)//not the center -> 8 surounding cells

                    if ((x + y == 0) || Math.abs(x + y) == 2)//if point is in the center or the corners, then skip -> just uses 4 orthogonal adjacent cells
                        continue;

                    Vector2Int Npos = pos.Add(new Vector2Int(x, y));

                    if (grid.InBounds(Npos))//if neighbour exists
                        neighbours.add(grid.get(Npos));
                }

            return neighbours;
        }

        @Override
        public int compareTo(Node other) {
            int compare = f_cost() - other.f_cost();
            if(compare == 0) {
                compare = h_cost - other.h_cost;
            }
            return compare * (int)Math.signum(compareSign);//get positive if it is better according to the compareSign
        }
    }

    public static Vector2Int[] FindShortestPath(Vector2Int start, Vector2Int target, Grid grid){
        compareSign = -1;
        return FindPath(start, target, grid);
    }
    public static Vector2Int[] FindLongestPath(Vector2Int start, Vector2Int target, Grid grid){//not longest but maximal distance path
        compareSign = 1;
        return FindPath(start, target, grid);
    }

    private static Vector2Int[] FindPath(Vector2Int start, Vector2Int target, Grid grid)
    {   
        Node startNode = grid.get(start);
        Node targetNode = grid.get(target);

        Heap<Node> openSet = new Heap<Node>(grid.size.x * grid.size.y);
        HashSet<Node> closedSet = new HashSet<Node>();
        openSet.Add(startNode);

        while (openSet.size() > 0)
        {   
            //Selects the next currentNode from the openSet, that has the lowest cost
            Node currentNode = openSet.RemoveFirst();
            closedSet.add(currentNode);

            //target is reached
            if (currentNode == targetNode)
                return Retrace(startNode, targetNode).toArray(new Vector2Int[0]);
                

            openSet = currentNode.Close(grid, targetNode.pos, openSet, closedSet);
        }

        //there is no way of getting there
        return null;
    }

    static ArrayList<Vector2Int> Retrace(Node startNode, Node endNode)
    {
        ArrayList<Vector2Int> path = new ArrayList<Vector2Int>();
        Node currentNode = endNode;

        while(currentNode != startNode)
        {
            path.add(currentNode.pos);
            currentNode = currentNode.parent;
        }

        //reverse path
        int i = 0;
        while(true){
            if(2*i >= path.size())
                break;
            Vector2Int front = path.get(i);
            path.set(i, path.get(path.size()-1-i));
            path.set(path.size()-1-i, front);
            i++;
        }

        return path;
    }

    static int GetDistance(Vector2Int a, Vector2Int b)
    {
        Vector2Int dist = new Vector2Int(Math.abs(a.x - b.x), Math.abs(a.y - b.y));
        if (dist.x < dist.y)
            return 14 * dist.x + 10 * (dist.y - dist.x);
        else
            return 14 * dist.y + 10 * (dist.x - dist.y);
    }
}