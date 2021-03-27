package Materials;

import Test.TestLogger;

public class Uranium extends Material {
    @Override
    public void readyToMine() {
        TestLogger.functionCalled(this, "readyToMine()", "void");
        if (this.getNearSunCount() >= 3)
            this.getAsteroid().explode();
        TestLogger.functionReturned();
    }
}
