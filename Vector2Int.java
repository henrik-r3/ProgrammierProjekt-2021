public class Vector2Int {
    public int x;
    public int y;

    public static Vector2Int up = new Vector2Int(0, 1);
    public static Vector2Int right = new Vector2Int(1, 0);
    public static Vector2Int down = new Vector2Int(0, -1);
    public static Vector2Int left = new Vector2Int(-1, 0);

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

    @Override
    public String toString() {
        return "(" + x + " ; " + y + ")";
    }
}