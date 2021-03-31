import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Main osztály.
 */
public class Main {

    /**
     * main függvény.
     * @param args
     */
    public static void main(String[] args) throws IOException {
        Game game = new Game();
        game.run();
    }
}
