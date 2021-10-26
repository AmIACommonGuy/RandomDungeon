package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.Random;


import static byow.Core.RandomUtils.*;

public class World {

    private static final int WIDTH = 100;
    private static final int HEIGHT = 50;

//    private int num_Bombs;
    private TETile[][] world;
    private ArrayList<Room> rooms;
    private Graph bridges;

    private long SEED;
    private Random RANDOM;


    public World(Long seed) {
        this.RANDOM = new Random(seed);
        this.bridges = new Graph();
        this.world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                this.world[x][y] = randomTile();
            }
        }
        this.rooms = new ArrayList();
    }

    //sets the seed and random object to world
    public static void setSeed(World world, long s) {
        world.SEED = s;
        world.RANDOM = new Random(world.SEED);
    }


    public TETile[][] makeWorld() {
//        TERenderer ter = new TERenderer();
//        ter.initialize(WIDTH, HEIGHT);
        for (int num = 0; num < 150; num++) {
            roomGenerator(RANDOM);
        }
        removeOverlap();

        for (Room i : rooms) {
            roomTiles(i);
        }
        for (Room i : rooms) {
            bridges.put(i);
        }
        for (Graph.Edge i : bridges.edge) {
            Position[] hallway = bridges.path(i, RANDOM);
            hallwayTiles(hallway[0], hallway[1], hallway[2]);
        }
        for (Room i : rooms) {
            if (bombPos(i) != null) {
                Position bp = bombPos(i);
                world[bp.x()][bp.y()] = Tileset.COIN;
            }
        }

//        ter.renderFrame(world);
        return world;
    }


    public Position createAvatar() {
        return rooms.get(uniform(RANDOM, 0,  rooms.size() - 1)).center();
    }


    private Position bombPos(Room room) {
        Position cp = room.center();
        int hl = room.halfLength();
        int hw = room.halfWidth();
        int bpx = RandomUtils.uniform(RANDOM, cp.x() - hl + 1, cp.x() + hl - 1);
        int bpy = RandomUtils.uniform(RANDOM, cp.y() - hw + 1, cp.y() + hw - 1);
        return new Position(bpx, bpy);
    }



    private void roomTiles(Room newRoom) {
        Position center = newRoom.center();
        int halfLength = newRoom.halfLength();
        int halfWidth = newRoom.halfWidth();
        int lbc = center.x() - halfLength;
        int luc = center.x() + halfLength;
        int rbc = center.y() - halfWidth;
        int ruc = center.y() + halfWidth;
        for (int i = 0; i < 2 * halfLength + 1; i++) {
            this.world[lbc + i][rbc] = hallwayReplacer(this.world[lbc + i][rbc]);
        }
        for (int i = 1; i < 2 * halfWidth; i++) {
            this.world[lbc][rbc + i] = hallwayReplacer(this.world[lbc][rbc + i]);
        }
        for (int i = 1; i < 2 * halfWidth; i++) {
            this.world[luc][rbc + i] = hallwayReplacer(this.world[luc][rbc + i]);
        }
        for (int i = 0; i < 2 * halfLength + 1; i++) {
            this.world[lbc + i][ruc] = hallwayReplacer(this.world[lbc + i][ruc]);
        }
        for (int i = 1; i < 2 * halfLength; i++) {
            for (int j = 1; j < 2 * halfWidth; j++) {
                this.world[lbc + i][rbc + j] = Tileset.DIRT;
            }
        }
        return;
    }

    public void roomGenerator(Random rSeed) {
        int halfLength = uniform(rSeed, 5, 8);
        int halfWidth = uniform(rSeed, 5, 6);
        Position center = centerToss(halfLength, halfWidth, rSeed);
        this.rooms.add(new Room(center, halfLength, halfWidth));
    }

    private static Position centerToss(int halfLength, int halfWidth, Random rSeed) {
        int x = uniform(rSeed, halfLength, WIDTH - halfLength);
        int y = uniform(rSeed, halfWidth, HEIGHT - halfWidth);
        return new Position(x, y);
    }

    private void removeOverlap() {
        ArrayList<Room> eligibleRoom = new ArrayList();
        for (int i = 0; i < this.rooms.size(); i++) {
            Boolean overlap = false;
            Room ithRoom = this.rooms.get(i);
            for (int j = i + 1; j < this.rooms.size(); j++) {
                Room jthRoom = this.rooms.get(j);
                int xDist = Math.abs(ithRoom.center().x() - jthRoom.center().x());
                int yDist = Math.abs(ithRoom.center().y() - jthRoom.center().y());
                if (xDist <= (ithRoom.halfLength() + jthRoom.halfLength())
                        && yDist <= (ithRoom.halfWidth() + jthRoom.halfWidth())) {
                    overlap = true;
                    continue;
                }
            }
            if (!overlap | bernoulli(RANDOM, 0.01)) {
                eligibleRoom.add(ithRoom);
            }
        }
        this.rooms = eligibleRoom;
    }

    private void hallwayTiles(Position start, Position end, Position turn) {

        int minX = Math.min(start.x(), end.x());
        int minY = Math.min(start.y(), end.y());
        int maxX = Math.max(start.x(), end.x());
        int maxY = Math.max(start.y(), end.y());

        //Hallway
        for (int i = minX - 1; i <= maxX + 1; i++) {
            this.world[i][turn.y() - 1] = hallwayReplacer(this.world[i][turn.y() - 1]);
            this.world[i][turn.y() + 1] = hallwayReplacer(world[i][turn.y() + 1]);
        }
        for (int i = minY - 1; i <= maxY + 1; i++) {
            this.world[turn.x() - 1][i] = hallwayReplacer(this.world[turn.x() - 1][i]);
            this.world[turn.x() + 1][i] = hallwayReplacer(this.world[turn.x() + 1][i]);
        }

        //Floor
        for (int i = minX; i < maxX; i++) {
            this.world[i][turn.y()] = Tileset.DIRT;
        }
        for (int i = minY; i < maxY; i++) {
            this.world[turn.x()][i] = Tileset.DIRT;
        }
        this.world[turn.x()][turn.y()] = Tileset.DIRT;
    }

    private TETile hallwayReplacer(TETile t) {
        if (t.equals(Tileset.NOTHING) | t.equals(Tileset.BRICK) | t.equals(Tileset.TREE) | t.equals(Tileset.MOUNTAIN) | t.equals(Tileset.BUSH)) {
            return Tileset.BRICK;
        }
        return Tileset.DIRT;
    }

    private TETile randomTile() {
        int tileNum = RANDOM.nextInt(20);
        switch (tileNum) {
            case 0: return Tileset.BUSH;
            case 1: return Tileset.BUSH;
            case 2: return Tileset.TREE;
            case 3: return Tileset.MOUNTAIN;
            default: return Tileset.NOTHING;
        }
    }
}


