package Materials;

import Test.TestLogger;

/**
 * Vízjég osztály.
 * A Material osztály leszármazottja.
 */
@SuppressWarnings("SpellCheckingInspection")
public class Ice extends Material {
    /**
     * Az utolsó réteg lefúrása után, ha napközelben van az aszteroida, a jég elolvad, eltűnik.
     */
    @SuppressWarnings("SpellCheckingInspection")
    @Override
    public void readyToMine() {
        TestLogger.functionCalled(this, "readyToMine", "void");
        System.out.println("Ice disappeared");
        getAsteroid().setIsHollow(true);
        TestLogger.functionReturned();
    }
}
