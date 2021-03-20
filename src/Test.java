public class Test {
    void test1() {
        System.out.println("Initializing Asteroid: ");
        Asteroid a1 = new Asteroid(new Iron(), 3, true, false);
        Asteroid a2 = new Asteroid(new Iron(), 3, true, false);
        a1.addNeighbor(a2);
        a2.addNeighbor(a1);
        Settler s = new Settler(a1, false);
        s.move();
    }

    void test2() {
        Asteroid a3 = new Asteroid(new Iron(), 2, true, false);
        Settler s2 = new Settler(a3, false);
        s2.drill();
    }

    void test3() {}
    void test4() {}
    void test5() {}
    void test6() {}
    void test7() {}
    void test8() {}
    void test9() {}
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
