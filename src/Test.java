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
    void test10() {}
    void test11() {}
    void test12() {}
    void test13() {}
    void test14() {}
    void test15() {}
    void test16() {}
    void test17() {}
    void test18() {}
    void test19() {}
    void test20() {}
    void test21() {}
    void test22() {}
    void test23() {}
}
