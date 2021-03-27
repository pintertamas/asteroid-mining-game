package Materials;

import Test.TestLogger;

public class Ice extends Material {
    @Override
    public void readyToMine() {
        TestLogger.functionCalled(this, "readyToMine", "void");
        System.out.println("Ice disappeared");
        getAsteroid().setIsHollow(true);
        TestLogger.functionReturned();
    }
}
