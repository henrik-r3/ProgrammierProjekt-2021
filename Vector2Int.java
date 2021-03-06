public class Vector2Int {
    public int x;
    public int y;

    public static Vector2Int zero = new Vector2Int(0, 0);

    public static Vector2Int up = new Vector2Int(0, 1);
    public static Vector2Int right = new Vector2Int(1, 0);
    public static Vector2Int down = new Vector2Int(0, -1);
    public static Vector2Int left = new Vector2Int(-1, 0);
    public static Vector2Int[] dirs = {up, right, down, left};

    public Vector2Int(){}
    public Vector2Int(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Vector2Int Add(Vector2Int other){
        return new Vector2Int(x+other.x, y+other.y);
    }

    public Vector2Int Mul(double k){
        return new Vector2Int((int)Math.floor(k*x), (int)Math.floor(k*y));
    }

    public Vector2Int invert(Vector2Int other){
        if(other.x == -1){
            return new Vector2Int(1,0);
        }else if(other.x == 1){
            return new Vector2Int(-1, 0);
        }else if(other.y == -1){
            return new Vector2Int(0, 1);
        }else if(other.y == 1){
            return new Vector2Int(0, -1);
        }
        return new Vector2Int();
    }

    @Override
    public String toString() {
        return "(" + x + " ; " + y + ")";
    }

    @Override
    public boolean equals(Object oj){
        if(oj.getClass() != this.getClass())
            return false;
        Vector2Int other = (Vector2Int)oj;
        return (x == other.x) && (y == other.y);
    }
}