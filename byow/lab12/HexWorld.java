package byow.lab12;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

/**
 * =
 */


/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 60;
    private static final int HEIGHT = 60;

    private static final long SEED = 2873123;
    private static final Random RANDOM = new Random(SEED);


    public static void drawRow(TETile[][] tiles, Position pos, TETile color, int length) {
//        for (int i = 0; i < length; i++) {
//            tiles[pos.x + i][pos.y] = color;
//        }

        for (int j = 0; j < length; j++) {
            for (int i = 0; i < length + 2 * j; i++) {
                tiles[pos.x - j + i][pos.y - j] = color;
            }
        }
        for (int j = 0; j < length; j++) {
            for (int i = 0; i < length + 2 * j; i++) {
                tiles[pos.x - j + i][pos.y + j - 2 * length +1] = color;
            }
        }
    }

    public static void addHexagon(TETile[][] tiles, Position p, TETile color, int size) {
        if (size < 2) return;
        drawRow(tiles, p, color, size);
    }

    /**
     * Fills the given 2D array of tiles with RANDOM tiles.
     * @param tiles
     */
    public static void fillWithNothing(TETile[][] tiles) {
        int height = tiles[0].length;
        int width = tiles.length;
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                tiles[x][y] = Tileset.NOTHING;
            }
        }
    }

    private static Position getBottoms(Position p, int size) {
        return new Position(p.x, p.y - 2 * size);
    }
    private static Position getUpRights(Position p, int size) {
        return new Position(p.x + 2 * size - 1, p.y + size);
    }
    private static Position getDownRights(Position p, int size) {
        return new Position(p.x + 2 * size - 1, p.y - size);
    }

    public static void addHexColumn(TETile[][] tiles, Position p, int size, int num) {
        if (num < 1) return;

        addHexagon(tiles, p, randomTile(), size);
        if (num > 1) {
            Position bottomN = getBottoms(p, size);
            addHexColumn(tiles, bottomN, size, num - 1);

        }
    }


    private static class Position {
        int x;
        int y;

        Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }


    public static void drawWorld(TETile[][] world, int hexSize, int tessSize) {
        fillWithNothing(world);
        Position p = new Position(hexSize, hexSize * tessSize * 3);
        addHexColumn(world, p, hexSize, tessSize);
        for (int i = 1; i < tessSize; i++) {
            p = getUpRights(p, hexSize);
            addHexColumn(world, p, hexSize, tessSize + i);
        }

        for (int i = tessSize - 2; i >= 0; i--) {
            p = getDownRights(p, hexSize);
            addHexColumn(world, p, hexSize, tessSize + i);
        }
    }

    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(5);
        switch (tileNum) {
            case 0: return Tileset.GRASS;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.WATER;
            case 3: return Tileset.MOUNTAIN;
            case 4: return Tileset.SAND;
            default: return Tileset.NOTHING;
        }
    }




    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] world = new TETile[WIDTH][HEIGHT];
        drawWorld(world, 5, 3);

        ter.renderFrame(world);
    }

}