package Materials;

import Test.TestLogger;

public class Ice extends Material {
    @Override
    public void readyToMine() {
        TestLogger.functionCalled(this, "readyToMine", "void");
        asteroid.setIsHollow(true);
        TestLogger.functionReturned();
    }
}
