import java.awt.List;

public static class AStar {
    public class Grid
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

        public bool InBounds(Vector2Int pos) { return pos.x >= 0 && pos.x < size.x && pos.y >= 0 && pos.y < size.y; }
        public int Index(Vector2Int pos) { return pos.x + pos.y * size.x; }
        public Node get(Vector2Int pos) { return grid[Index(pos)]; }
    }



    public class Node extends HeapItem<Node>
    {
        public Vector2Int pos;
        public bool walkable;

        public Node parent;

        public int g_cost;//dist to start node (a)
        public int h_cost;//dist to end node (b)
        public int f_cost() { return g_cost + h_cost; }//g_cost + h_cost
        public int HeapIndex;

        public Node(Vector2Int pos, bool walkable)
        {
            this.pos = pos;
            this.walkable = walkable;

            g_cost = Integer.MaxValue;
            h_cost = Integer.MaxValue;
        }

        public Heap<Node> UpdateCosts(Node initiator, Vector2Int targetPos, Heap<Node> openSet)
        {
            //check if the initiator opens a new path, that is shorter and will lower the cost
            int newMoveCostToNeighb = initiator.g_cost + GetDistance(initiator.pos, pos);
            if (newMoveCostToNeighb < g_cost || !openSet.Contains(this))
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
            List<Node> neighbours = GetNeighbours(grid);
            for(int n = 0; n < neighbours.getSize(); n++)
            {
                if (!neighbours[n].walkable || closedSet.Contains(neighbours[n]))
                    continue;

                openSet = neighbours[n].UpdateCosts(this, targetPos, openSet);
            }
            return openSet;
        }

        public List<Node> GetNeighbours(Grid grid)
        {
            List<Node> neighbours = new List<Node>();

            for (int x = -1; x <= 1; x++)
                for (int y = -1; y <= 1; y++)
                {
                    //if (x == 0 && y == 0)//not the center -> 8 surounding cells

                    if ((x + y == 0) || Mathf.Abs(x + y) == 2)//if point is in the center or the corners, then skip -> just uses 4 orthogonal adjacent cells
                        continue;

                    Vector2Int Npos = pos.Add(new Vector2Int(x, y));

                    if (grid.InBounds(Npos))//if neighbour exists
                        neighbours.Add(grid.get(Npos));
                }

            return neighbours;
        }

        @Override
        public int compareTo(Node other) {
            int compare = compare(f_cost(), other.f_cost());
            if(compare == 0) {
                compare = compare(h_cost, other.h_cost);
            }
            return -compare;//get positive if it is smaller -> smaller -> nearer -> better
        }
    }


    public static Vector2Int[] FindPath(Vector2Int start, Vector2Int target, Tilemap tilemap, Tilemap collision) {
        Grid grid = new Grid(tilemap, collision);
        return FindPath(start, target, grid);
    }

    public static Vector2Int[] FindPath(Vector2Int start, Vector2Int target, Grid grid)
    {
        Node startNode = grid.get(start);
        Node targetNode = grid.get(target);

        Heap<Node> openSet = new Heap<Node>(grid.size.x * grid.size.y);
        HashSet<Node> closedSet = new HashSet<Node>();
        openSet.Add(startNode);

        while (openSet.getSize() > 0)
        {   
            //Selects the next currentNode from the openSet, that has the lowest cost
            Node currentNode = openSet.RemoveFirst();
            closedSet.Add(currentNode);

            //target is reached
            if (currentNode == targetNode)
                return Retrace(startNode, targetNode).ToArray();

            openSet = currentNode.Close(grid, targetNode.pos, openSet, closedSet);
        }

        //there is no way of getting there
        return null;
    }

    static List<Vector2Int> Retrace(Node startNode, Node endNode)
    {
        List<Vector2Int> path = new List<Vector2Int>();
        Node currentNode = endNode;

        while(currentNode != startNode)
        {
            path.Add(currentNode.pos);
            currentNode = currentNode.parent;
        }

        path.Reverse();
        return path;
    }

    static int GetDistance(Vector2Int a, Vector2Int b)
    {
        Vector2Int dist = new Vector2Int(Mathf.Abs(a.x - b.x), Mathf.Abs(a.y - b.y));
        if (dist.x < dist.y)
            return 14 * dist.x + 10 * (dist.y - dist.x);
        else
            return 14 * dist.y + 10 * (dist.x - dist.y);
    }
}