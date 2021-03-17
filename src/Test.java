import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Test {
    public static void test() {
        System.out.println("Testing:");
        switch (1) {
            case 1:
                Asteroid a1 = new Asteroid(new Iron(), 3, true, false);
                Asteroid a2 = new Asteroid(new Iron(), 3, true, false);
                a1.addNeighbor(a2);
                a2.addNeighbor(a1);
                Settler s = new Settler(a1, false);
                s.move();
                break;
            case 2:
                break;
            case 3:
                break;
            default:
                break;
        }
    }
}
