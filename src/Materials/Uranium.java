package Materials;

import Test.TestLogger;

public class Uranium extends Material {
    @Override
    public void readyToMine() {
        TestLogger.functionCalled(this, "readyToMine()", "void");
        asteroid.explode();
        TestLogger.functionReturned();
    }
}
