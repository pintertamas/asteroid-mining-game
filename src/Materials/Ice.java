package Materials;

import Test.TestLogger;
import Test.UserIO;

/**
 * Vízjég osztály.
 * A Material osztály leszármazottja.
 */
@SuppressWarnings("SpellCheckingInspection")
public class Ice extends Material {
    public Ice() {
        imagePath = "/asteroids/ice.png";
    }

    /**
     * Az utolsó réteg lefúrása után, ha napközelben van az aszteroida, a jég elolvad, eltűnik.
     */
    @SuppressWarnings("SpellCheckingInspection")
    @Override
    public void readyToMine() {
        TestLogger.functionCalled(this, "readyToMine", "void");
        UserIO.addToTemporaryOutput("iceMelted");
        System.out.println("Ice sublimated");
        getAsteroid().setIsHollow(true);
        TestLogger.functionReturned();
    }
}
