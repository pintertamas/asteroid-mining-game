import java.util.ArrayList;

public class Test {
    void test1() {
        Asteroid a1 = new Asteroid(new Iron(), 3, true, false);
        Asteroid a2 = new Asteroid(new Iron(), 3, true, false);
        a1.addNeighbor(a2);
        a2.addNeighbor(a1);
        Settler s = new Settler(a1, false);
        s.move();
    }

    void test2() {
        Asteroid a = new Asteroid(new Iron(), 2, true, false);
        Settler s = new Settler(a, false);
        a.addFigure(s);
        s.setAsteroid(a);
        s.drill();
    }

    void test3() {
        Asteroid a = new Asteroid(new Iron(), 0, true, false);
        Settler s = new Settler(a, false);
        a.addFigure(s);
        s.drill();
    }
    void test4() {
        Asteroid a = new Asteroid(new Iron(), 1, true, false);
        Settler s = new Settler(a, false);
        a.addFigure(s);
        s.drill();
    }

    void test5() {
        Asteroid a = new Asteroid(new Uranium(), 1, true, false);
        Settler s = new Settler(a, false);
        a.addFigure(s);
        s.drill();
    }

    void test6() {
        Asteroid a = new Asteroid(new Uranium(), 1, true, false);
        Robot r = new Robot(a, false);
        a.addFigure(r);
        r.drill();
    }

    void test7() {
        Asteroid a = new Asteroid(new Ice(), 1, true, false);
        Settler s = new Settler(a, false);
        a.addFigure(s);
        s.drill();
    }
    void test8() {
        Asteroid a = new Asteroid(new Iron(), 1, false, false);
        Settler s = new Settler(a, false);
        a.addFigure(s);
        s.mine();
    }
    void test9() {
        Asteroid a = new Asteroid(new Uranium(), 1, false, false);
        Settler s = new Settler(a, false);
        a.addFigure(s);
        s.mine();
    }
    void test10() {
        Asteroid a1 = new Asteroid(new Iron(), 3, true, false);
        Asteroid a2 = new Asteroid(new Uranium(), 0, false, false);
        Portal p1 = new Portal();
        Portal p2 = new Portal();
        a1.addPortal(p1);
        a2.addPortal(p2);
        Settler s = new Settler(a1, false);
        a1.addFigure(s);
        s.move();
    }
    void test11() {
        Asteroid a1 = new Asteroid(new Iron(), 3, true, false);
        Settler s = new Settler(a1, false);
        s.getInventory().addMaterial(new Iron());
        s.getInventory().addMaterial(new Iron());
        s.getInventory().addMaterial(new Ice());
        s.getInventory().addMaterial(new Uranium());
        s.buildPortal();
    }
    void test12() {
        Asteroid a1 = new Asteroid(new Iron(), 3, true, false);
        Settler s = new Settler(a1, false);
        s.buildPortal();
    }
    void test13() {
        //TODO: build base (enough material)
    }
    void test14() {
        //TODO: build base (not enough material)
    }
    void test15() {
        Map map = new Map();
        Asteroid a1 = new Asteroid(new Iron(), 0, true, false);
        Settler s = new Settler(a1, false);
        a1.addFigure(s);
        map.addAsteroid(a1);
        map.solarStorm();
    }
    void test16() {
        Map map = new Map();
        Asteroid a1 = new Asteroid(new Iron(), 0, true, true);
        Settler s = new Settler(a1, false);
        a1.addFigure(s);
        map.addAsteroid(a1);
        map.solarStorm();
    }
    void test17() {
        Asteroid a1 = new Asteroid(new Iron(), 3, true, false);
        Settler s = new Settler(a1, false);
        a1.addFigure(s);
        s.putPortalDown();
    }
    void test18() {
        Asteroid a1 = new Asteroid(new Iron(), 3, true, false);
        Asteroid a2 = new Asteroid(new Uranium(), 0, false, false);
        Portal p1 = new Portal();
        Portal p2 = new Portal();
        a2.addPortal(p2);
        Settler s = new Settler(a1, false);
        a1.addFigure(s);
        s.getInventory().addPortal(p1);
        s.putPortalDown();
    }
    void test19() {
        Asteroid a1 = new Asteroid(new Iron(), 3, true, false);
        Portal p1 = new Portal();
        Portal p2 = new Portal();
        Settler s = new Settler(a1, false);
        a1.addFigure(s);
        s.getInventory().addPortal(p1);
        s.getInventory().addPortal(p2);
        s.putPortalDown();
    }
    void test20() {
        Asteroid a1 = new Asteroid(new Iron(), 3, true, true);
        Settler s = new Settler(a1, false);
        a1.addFigure(s);
        s.putMaterialBack();
    }
    void test21() {
        Asteroid a1 = new Asteroid(new Iron(), 3, true, false);
        Settler s = new Settler(a1, false);
        a1.addFigure(s);
        s.putMaterialBack();
    }
    void test22() {
        Asteroid a1 = new Asteroid(new Iron(), 3, true, false);
        Settler s = new Settler(a1, false);
        s.getInventory().addMaterial(new Iron());
        s.getInventory().addMaterial(new Coal());
        s.getInventory().addMaterial(new Uranium());
        s.buildRobot();
    }
    void test23() {
        Asteroid a1 = new Asteroid(new Iron(), 3, true, false);
        Settler s = new Settler(a1, false);
        s.buildRobot();
    }
}
