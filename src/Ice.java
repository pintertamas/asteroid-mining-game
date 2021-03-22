public class Ice extends Material {
    @Override
    protected void readyToMine() {
        TestLogger.functionCalled(this, "readyToMine", "void");
        asteroid.setIsHollow(true);
        TestLogger.functionReturned();
    }

    @Override
    protected boolean isCompatibleWith(Material m) {
        return false;
    }
}
