package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class Avatar {
    Position pos;
    TETile[][] tiles;
    // Game parameter
    int coinNum = 0;

    public Avatar(TETile[][] tiles, Position pos) {
        this.pos = pos;
        this.tiles = tiles;
        tiles[pos.x()][pos.y()] = Tileset.YOU;
    }


    public TETile[][] solicitNCharsInput(Character move) {
        if (move != null) {
            return moveAvatar(move);
        }
        return tiles;
    }



    public TETile[][] moveAvatar(char direction) {
        tiles[pos.x()][pos.y()] = Tileset.DIRT;
        switch (direction) {
            case 'w':
                if (moveHelper(pos.shiftPos(0, 1))) {
                    pos = pos.shiftPos(0, 1);
                }
                break;
            case 's':
                if (moveHelper(pos.shiftPos(0, -1))) {
                    pos = pos.shiftPos(0, -1);
                }
                break;
            case 'a':
                if (moveHelper(pos.shiftPos(-1, 0))) {
                    pos = pos.shiftPos(-1, 0);
                }
                break;
            case 'd':
                if (moveHelper(pos.shiftPos(1, 0))) {
                    pos = pos.shiftPos(1, 0);
                }
                break;
            default:
                break;

        }
        tiles[pos.x()][pos.y()] = Tileset.YOU;
        return tiles;
    }

    public boolean moveHelper(Position posN) {
        String proposedTile = tiles[posN.x()][posN.y()].description();
        if ((!proposedTile.equals("dirt floor")) & (!proposedTile.equals("coin"))) {
            return false;
        } else if (proposedTile.equals("coin")) {
            coinNum++;
            return true;
        } else {
            return true;
        }
    }

    public int getCoinNum() {
        return coinNum;
    }
}
