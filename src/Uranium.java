public class Uranium extends Material {
    @Override
    protected void readyToMine() {
        TestLogger.functionCalled(this, "readyToMine()", "void");
        asteroid.explode();
        TestLogger.functionReturned();
    }
}
