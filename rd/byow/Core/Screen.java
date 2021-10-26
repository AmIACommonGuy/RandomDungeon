package byow.Core;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;
import java.util.Date;

import java.awt.*;

public class Screen {

    private int width = 100;
    private int height = 60;
    private String currentMouse;

    public Screen() {
        //blank screen
        TERenderer ter = new TERenderer();
        ter.initialize(100, 60, 0, 0);
        currentMouse = "initial";
    }

    public void showMain() {
        String title = "CS61B: THE GAME";
        String newG = "New Game (N)";
        String loadG = "Load Game (L)";
        String replayG = "Replay Game (R)";
        String quitG = "Quit Game (Q)";
        StdDraw.setPenColor(Color.white);
        Font fontM = new Font("Helvetica", 1, 60);
        StdDraw.setFont(fontM);
        StdDraw.text(width / 2, 40, title);
        StdDraw.setPenRadius(0.0000001);
        Font fontO = new Font("Helvetica", 1, 40);
        StdDraw.setFont(fontO);
        StdDraw.text(width / 2, 25, newG);
        StdDraw.text(width / 2, 20, loadG);
        StdDraw.text(width / 2, 15, replayG);
        StdDraw.text(width / 2, 10, quitG);
        StdDraw.show();
    }

    public void drawFrame(TETile[][] worldTile, Avatar avatar) {
        StdDraw.setFont();
        StdDraw.clear(Color.black);
        // print out world
        int numXTiles =  worldTile.length;
        int numYTiles =  worldTile[0].length;
        Date date = new Date();
        StdDraw.clear(new Color(0, 0, 0));
        for (int x = 0; x < numXTiles; x += 1) {
            for (int y = 0; y < numYTiles; y += 1) {
                if (worldTile[x][y] == null) {
                    throw new IllegalArgumentException("Tile at position x=" + x + ", y=" + y
                            + " is null.");
                }
                worldTile[x][y].draw(x, y);
            }
        }
        //Take the string and display it in the center of the screen
        StdDraw.setPenColor(Color.white);
        //this draws the line
        StdDraw.filledRectangle(width / 2, height - 6, width / 2, 0.1);
        StdDraw.text(13, 59, "Current time: " + date.toString());
        StdDraw.text(8, 55, mouseTile(worldTile));
        Integer numCoin = avatar.getCoinNum();
        StdDraw.text(height - 20, 55, "Coins collected: " + numCoin.toString());
        StdDraw.show();
    }


    public String mouseTile(TETile[][] tiles) {
        // outof bound error need fix
        int x = (int) StdDraw.mouseX();
        int y = (int) StdDraw.mouseY();
        if (x > width || y >= height - 10) {
            return "";
        } else {
            String mouseTile = tiles[x][y].description();
            return mouseTile;
        }
    }

    public void seedScreen(String input) {
        StdDraw.clear(Color.black);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(width / 2, 35, "Enter seed:");
        Font fontS = new Font("Arial ", 1, 45);
        StdDraw.setFont(fontS);
        StdDraw.text(width / 2, 30, input);
        StdDraw.show();
    }
}