package Materials;

import Test.TestLogger;

/**
 * Urán osztály.
 * A Material leszármazottja.
 */
public class Uranium extends Material {
    /**
     * Az utolsó réteg lefúrása után, ha harmadszorra napközelben van az aszteroida, az urán felrobbantja az aszteroidát.
     */
    @Override
    public void readyToMine() {
        TestLogger.functionCalled(this, "readyToMine()", "void");
        if (this.getNearSunCount() >= 3) {
            this.getAsteroid().explode();
            System.out.println("Asteroid exploded");
        }
        TestLogger.functionReturned();
    }
}
