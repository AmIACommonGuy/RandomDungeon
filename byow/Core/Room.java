package byow.Core;

public class Room {
    private Position center;
    private int halfWidth;
    private int halfLength;

    public Room(Position proposedC, int halfLength, int halfWidth) {
        this.center = proposedC;
        this.halfLength = halfLength;
        this.halfWidth = halfWidth;
    }
    public Position center() {
        return this.center;
    }
    public int halfWidth() {
        return halfWidth;
    }
    public int halfLength() {
        return halfLength;
    }
}

