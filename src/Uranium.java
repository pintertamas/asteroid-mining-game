public class Uranium extends Material {
    @Override
    protected void readyToMine() {
        TestLogger.functionCalled(this, "readyToMine()", "void");
        asteroid.explode();
        super.readyToMine();
        TestLogger.functionReturned();
    }

    @Override
    protected boolean isCompatibleWith(Material m) {
        return true;
    }
}
