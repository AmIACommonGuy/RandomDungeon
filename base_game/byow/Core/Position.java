package byow.Core;

public class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position shiftPos(int dx, int dy) {
        return new Position(x + dx, y + dy);
    }

    public int x() {
        return this.x;
    }
    public int y() {
        return this.y;
    }
}
