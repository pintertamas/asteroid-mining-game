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
        //TODO: build tp portal (enough material)
    }
    void test12() {
        //TODO: build tp portal (not enough material)
    }
    void test13() {
        //TODO: build base (enough material)
    }
    void test14() {
        //TODO: build base (not enough material)
    }
    void test15() {
        //TODO: storm kills player
    }
    void test16() {
        //TODO: storm does not kill the player
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
        //TODO: put back material asteroid is hollow
    }
    void test21() {
        //TODO: put back material asteroid is not hollow
    }
    void test22() {
        //TODO: build robot enough material
    }
    void test23() {
        //TODO: build robot enough material
    }
}
