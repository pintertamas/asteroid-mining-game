package Materials;

import Test.TestLogger;
import Test.UserIO;

/**
 * Urán osztály.
 * A Material leszármazottja.
 */
@SuppressWarnings("SpellCheckingInspection")
public class Uranium extends Material {
    /**
     * Az utolsó réteg lefúrása után, ha harmadszorra napközelben van az aszteroida, az urán felrobbantja az aszteroidát.
     */
    @SuppressWarnings("SpellCheckingInspection")
    @Override
    public void readyToMine() {
        TestLogger.functionCalled(this, "readyToMine()", "void");
        if (this.getNearSunCount() >= 3) {
            this.getAsteroid().explode();
            System.out.println("Uranium exploded");
        }
        TestLogger.functionReturned();
    }
}
