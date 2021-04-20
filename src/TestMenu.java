import Test.Test;
import Test.TestLogger;

import java.util.HashMap;
import java.util.Scanner;

/**
 * Menü osztály a tesztek futtatásához.
 */
@SuppressWarnings("SpellCheckingInspection")
public class TestMenu {
    private final HashMap<Integer, Runnable> tests = new HashMap<>();
    private final HashMap<Integer, String> options = new HashMap<>();
    final Test test = new Test();

    /**
     * Konstruktor.
     */
    @SuppressWarnings("SpellCheckingInspection")
    TestMenu() {
        tests.put(1, test::test1);
        tests.put(2, test::test2);
        tests.put(3, test::test3);
        tests.put(4, test::test4);
        tests.put(5, test::test5);
        tests.put(6, test::test6);
        tests.put(7, test::test7);
        tests.put(8, test::test8);
        tests.put(9, test::test9);
        tests.put(10, test::test10);
        tests.put(11, test::test11);
        tests.put(12, test::test12);
        tests.put(13, test::test13);
        tests.put(14, test::test14);
        tests.put(15, test::test15);
        tests.put(16, test::test16);
        tests.put(17, test::test17);
        tests.put(18, test::test18);
        tests.put(19, test::test19);
        tests.put(20, test::test20);
        tests.put(21, test::test21);
        tests.put(22, test::test22);
        tests.put(23, test::test23);

        options.put(1, "(5.3.1) Move to Neighbor");
        options.put(2, "(5.3.2) Drill Asteroid (layers > 1)");
        options.put(3, "(5.3.3) Drill Asteroid (layers = 0)");
        options.put(4, "(5.3.4) Drill Asteroid with common material (layers = 1)");
        options.put(5, "(5.3.5) Drill Uranium (Settler, layers = 1, nearSun)");
        options.put(6, "(5.3.6) Drill Uranium (Robot, layers = 1, nearSun)");
        options.put(7, "(5.3.7) Drill Ice (layers = 1, nearSun)");
        options.put(8, "(5.3.8) Mine Material Layers > 0");
        options.put(9, "(5.3.9) Mine Material Layers = 0");
        options.put(10, "(5.3.10) Move Settler through Portal");
        options.put(11, "(5.3.11) Build Teleport Portal (Enough Material)");
        options.put(12, "(5.3.12) Build Teleport Portal (Not Enough Material)");
        options.put(13, "(5.3.13) Build Base (Enough Material)");
        options.put(14, "(5.3.14) Build Base (Not Enough Material)");
        options.put(15, "(5.3.15) Storm Kills Player");
        options.put(16, "(5.3.16) Storm Does Not Kill Player");
        options.put(17, "(5.3.17) Put down Portal (Settler has 0 Portals)");
        options.put(18, "(5.3.18) Put down Portal (Settler has 1 Portals)");
        options.put(19, "(5.3.19) Put down Portal (Settler has 2 Portals)");
        options.put(20, "(5.3.20) Put Back Material Asteroid Hollow");
        options.put(21, "(5.3.21) Put Back Material Asteroid Not Hollow");
        options.put(22, "(5.3.22) Build Robot Enough Material");
        options.put(23, "(5.3.23) Build Robot Not Enough Material");
    }

    /**
     * Kiírja a tesztek listáját.
     */
    @SuppressWarnings("SpellCheckingInspection")
    void show() {
        System.out.println("0. Exit");
        for (HashMap.Entry<Integer, String> entry : options.entrySet())
            System.out.printf("%d. %s%n", entry.getKey(), entry.getValue());
        System.out.print("Your choice: ");
    }

    /**
     * Beolvassa a kiválasztott teszt sorszámát,
     * majd lefuttatja a hozzá tartozó tesztesetet.
     */
    @SuppressWarnings("SpellCheckingInspection")
    void run() {
        Scanner in = new Scanner(System.in);

        boolean shouldRun = true;
        while (shouldRun) {
            show();
            int id = in.nextInt();
            if (id == 0) {
                shouldRun = false;
            } else {
                tests.get(id).run();
                System.out.print("\n\n");
                shouldRun = TestLogger.ask("Do you want to run more tests?");
            }
        }
        in.close();
    }
}
