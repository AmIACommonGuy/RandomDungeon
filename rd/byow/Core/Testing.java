package byow.Core;

import org.junit.Test;

public class Testing {

    String[] args = {"a"};
    @Test
    public void testSeedWorld() {
        Main.main(args);
    }

    public static void main(String[] args) {
        Engine eng = new Engine();
        //eng.interactWithInputString("n12345s");
        eng.interactWithKeyboard();
    }
}
