package byow.Core;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;

public class GUI {
    /**
     * The width of the window of this game.
     */
    private int width;
    /**
     * The height of the window of this game.
     */
    private int height;

    public GUI(int width, int height) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
    }

    public void drawFrame(String s) {

        StdDraw.clear(Color.black);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(width / 2, height / 2, s);
        StdDraw.line(0, height - 4, width, height - 4);
        StdDraw.show();

    }
}
