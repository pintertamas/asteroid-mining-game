package Materials;

import Test.TestLogger;

/**
 * Vízjég osztály.
 * A Material osztály leszármazottja.
 */
public class Ice extends Material {
    /**
     * Az utolsó réteg lefúrása után, ha napközelben van az aszteroida, a jég elolvad, eltűnik.
     */
    @Override
    public void readyToMine() {
        TestLogger.functionCalled(this, "readyToMine", "void");
        System.out.println("Ice disappeared");
        getAsteroid().setIsHollow(true);
        TestLogger.functionReturned();
    }
}
