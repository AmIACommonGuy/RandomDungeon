package byow.Core;
import java.util.ArrayList;
import java.util.Random;

import static byow.Core.RandomUtils.bernoulli;
import static byow.Core.RandomUtils.uniform;

/** This class is used to create linkages
 *  It does not connect the nearest neighbor,
 *  but connects rooms in a quad tree manner.
 */

public class Graph {
    ArrayList<Edge> edge = new ArrayList<>();
    Vertex root = new Vertex(null);
    class Edge<Room> {
        Room a;
        Room b;

        Edge(Room a, Room b) {
            this.a = a;
            this.b = b;
        }
    }

    /**
     *  Quad tree vertex
     */
    private class Vertex {
        Room room;
        Vertex btLeft;
        Vertex btRight;
        Vertex upLeft;
        Vertex upRight;

        private Vertex(Room room) {
            this.room = room;
            btLeft = null;
            btRight = null;
            upLeft = null;
            upRight = null;

        }

    }
    private Vertex putHelper(Vertex T, Room room, Vertex tp) {
        if (T == null) {
            edge.add(new Edge(tp.room, room));
            return new Vertex(room);
        }
        if (T.room.center().x() < room.center().x()) {
            if (T.room.center().y() < room.center().y()) {
                T.upRight = putHelper(T.upRight, room, T);
            } else {
                T.btRight = putHelper(T.btRight, room, T);
            }
        } else {
            if (T.room.center().y() < room.center().y()) {
                T.upLeft = putHelper(T.upLeft, room, T);
            } else {
                T.btLeft = putHelper(T.btLeft, room, T);
            }
        }
        return T;
    }

    public void put(Room room) {
        if (root.room == null) {
            root = new Vertex(room);
        } else {
            putHelper(root, room, null);
        }
    }

    public Position[] path(Edge<Room> edgeIn, Random random) {
        Room startRoom = edgeIn.a;
        Room endRoom = edgeIn.b;

        int x1 = startRoom.center().x();
        int y1 = startRoom.center().y();
        int x2 = endRoom.center().x();
        int y2 = endRoom.center().y();
        int l1 = startRoom.halfLength();
        int w1 = startRoom.halfWidth();
        int l2 = endRoom.halfLength();
        int w2 = endRoom.halfWidth();
        int startX = uniform(random, x1 - l1 + 2, x1 + l1 - 2);
        int endX = uniform(random, x2 - l2 + 2, x2 + l2 - 2);

        int startY = uniform(random, y1 - w1 + 2, y1 + w1 - 2);
        int endY = uniform(random, y2 - w2 + 2, y2 + w2 - 2);
        Position start = new Position(startX, startY);
        Position end = new Position(endX, endY);
        Position turning;
        if (bernoulli(random, 0.015)) {
            turning = new Position(startX, endY);
        } else {
            turning = new Position(endX, startY);
        }
        return new Position[]{start, end, turning};
    }

}
