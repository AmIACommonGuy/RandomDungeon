package byow.Core;

import byow.TileEngine.TETile;

import edu.princeton.cs.introcs.StdDraw;

import static byow.Core.FileUtils.*;

public class Engine {
    World newWorld;
    Avatar avatar;

    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
        //must display main menu with at least options to start a new world
        //screen has load prev world, and quit


        // 1. initialize
        Screen screen = new Screen();
        screen.showMain();
        String input = "";
        TETile[][] game;


        // first command:
        while (input.equals("")) {
            if (StdDraw.hasNextKeyTyped()) {
                char command = StdDraw.nextKeyTyped();
                switch (command) {
                    case 'N':
                    case 'n':
                        //start new game, wait for user to input seed inputs
                        input += 'n';
                        boolean seedEnd = true;
                        while (seedEnd) {
                            screen.seedScreen(input.substring(1));
                            if (StdDraw.hasNextKeyTyped()) {
                                char inputC = StdDraw.nextKeyTyped();
                                if (isDigit(inputC)) {
                                    input += inputC;
                                    if (inputC == 's' | inputC == 'S') {
                                        seedEnd = false;
                                    }
                                } else {
                                    continue;
                                }
                            }
                        }
                        System.out.println(input);
                        game = interactWithInputString(input);
                        break;
                    case 'L':
                    case 'l':
                        input = readContentsAsString(GAME);
                        game = interactWithInputString(input);
                        break;
                    case 'r':
                    case 'R':
                        String savedGame = readContentsAsString(GAME);
                        System.out.println(savedGame);
                        int operationPos = savedGame.split("s", 0)[0].length() + 1;
                        System.out.println(savedGame.substring(0, operationPos));
                        game = interactWithInputString(savedGame.substring(0, operationPos));
                        for (int i = operationPos; i < savedGame.length(); i++) {
                            StdDraw.pause(20);
                            screen.drawFrame(avatar.solicitNCharsInput(savedGame.charAt(i)), avatar);
                            StdDraw.pause(45);
                            if (StdDraw.hasNextKeyTyped()) {
                                break;
                            }
                        }
                        input = savedGame;
                        game = interactWithInputString("l");
                        break;
                    case 'q':
                    case 'Q':
                        System.exit(0);
                    default:
                        return;
                }
                while (!StdDraw.hasNextKeyTyped()) {
                    screen.drawFrame(game, avatar);
                }
                operation(input, screen);
                System.exit(0);
            }
        }


    }

    private boolean isDigit(char input) {
        if (input == 's' | input == 'S' |
                input == '0' |
                input == '1' |
                input == '2' |
                input == '3' |
                input == '4' |
                input == '5' |
                input == '6' |
                input == '7' |
                input == '8' |
                input == '9' ) {
            return true;
        }
        return false;
    }


    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     * <p>
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     * <p>
     * In other words, both of these calls:
     * - interactWithInputString("n123sss:q")
     * - interactWithInputString("lww")
     * <p>
     * should yield the exact same world state as:
     * - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {
        TETile[][] rt;
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.

        //takes series of keyboard inputs
        //parse through the string input

        String rInput = input.toLowerCase();
        char firstChar = rInput.charAt(0);
        String saveInput;
        String update;
        switch (firstChar) {
            case 'n':
                String seed = rInput.substring(1).split("s", 0)[0];
                newWorld = new World(Long.parseLong(seed));
                rt = newWorld.makeWorld();
                avatar = new Avatar(rt, newWorld.createAvatar());
                String operation = rInput.substring(2 + seed.length());

                if (!operation.contains(":q")) {
                    System.out.print("no save");
                    for (int i = 0; i < operation.length(); i++) {
                        rt = avatar.moveAvatar(operation.charAt(i));
                    }
                } else {
                    System.out.print("yes save");
                    for (int i = 0; i < operation.length() - 2; i++) {
                        rt = avatar.moveAvatar(operation.charAt(i));
                    }
                    saveInput = rInput.split(":q", 0)[0];
                    saveGame(saveInput);
                }
                break;
            case 'l':
                update = rInput.substring(1);
                saveInput = readContentsAsString(GAME) + update;
                System.out.println(saveInput);
                rt = interactWithInputString(saveInput);
                break;
            default:
                return null;
        }
        return rt;
    }

    private void saveGame(String input) {
        GAME.delete();
        FileUtils.writeContents(GAME, input);
    }

    private void operation(String inputSave, Screen screen) {
        boolean ecs = false;
        String is = inputSave;
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char current = StdDraw.nextKeyTyped();
                if (current == ':') {
                    System.out.println("quit?");
                    ecs = true;
                } else if (ecs && (current == 'q' | current == 'Q')) {
                    interactWithInputString(is + ":q");
                    return;
                } else if (ecs != true &&
                        (current == 'w' | current == 'a' | current == 's' | current == 'd'
                        | current == 'W' | current == 'A' | current == 'S' | current == 'D')) {
                    is += current;
                    System.out.println(is);
                    while (!StdDraw.hasNextKeyTyped()) {
                        screen.drawFrame(interactWithInputString(is), avatar);
                    }
                }
            }
        }
    }
}





