package byow.TileEngine;

import java.awt.Color;

/**
 * Contains constant tile objects, to avoid having to remake the same tiles in different parts of
 * the code.
 *
 * You are free to (and encouraged to) create and add your own tiles to this file. This file will
 * be turned in with the rest of your code.
 *
 * Ex:
 *      world[x][y] = Tileset.FLOOR;
 *
 * The style checker may crash when you try to style check this file due to use of unicode
 * characters. This is OK.
 */

public class Tileset {
    public static final TETile AVATAR = new TETile('@', Color.white, Color.black, "you");
    public static final TETile WALL = new TETile('#', new Color(141, 141, 141), Color.darkGray,
            "wall");
    public static final TETile FLOOR = new TETile('·', new Color(74, 78, 74), Color.black,
            "floor");
    public static final TETile NOTHING = new TETile(' ', Color.black, Color.black, "nothing");
    public static final TETile GRASS = new TETile('"', Color.green, Color.black, "grass");
    public static final TETile WATER = new TETile('≈', Color.blue, Color.black, "water");
    public static final TETile FLOWER = new TETile('❀', Color.magenta, Color.pink, "flower");
    public static final TETile SAND = new TETile('▒', Color.yellow, Color.black, "sand");
    public static final TETile MOUNTAIN = new TETile('▲', Color.gray, Color.black, "mountain");
    public static final TETile TREE = new TETile('T', Color.green, Color.black, "tree", "tree.png");
    public static final TETile COIN = new TETile('O', Color.yellow, Color.darkGray, "coin", "coin.png");
    public static final TETile STONEFLOOR = new TETile('▒', Color.darkGray, Color.darkGray, "stone floor");
    public static final TETile YOU = new TETile('F', Color.pink, Color.darkGray, "Oink oink! Get me coins!", "piggy.png");
    public static final TETile BRICK = new TETile('B', Color.orange, Color.black, "brick wall", "brick.png");
    public static final TETile DIRT = new TETile('D', Color.orange, Color.black, "dirt floor", "dirt.png");
    public static final TETile BUSH = new TETile('B', Color.green, Color.black, "bush", "bush.png");
}


