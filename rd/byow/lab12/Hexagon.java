package byow.lab12;
import org.junit.Test;
import static org.junit.Assert.*;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

public class Hexagon {
    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;
    /**
     * Three inputs:
     * 1. Color
     * 2. Position of first tile
     * 3. Size
     *
     * It puts a hexagon to the wanted position
     */
    TETile[][] paintedArea;
    public Hexagon(TETile color, Integer x, Integer y, Integer size, TETile[][] map) {
        for (int j = 0; j < size; j++) {
            for (int i = 0; i < size + 2 * j; i++) {
                map[x - j + i][y + j] = color;
                paintedArea = map;
            }
        }
    }
}
