public class Ice extends Material {
    @Override
    protected void readyToMine() {
        TestLogger.functionCalled(this, "readyToMine()", "void");
        asteroid.setIsHollow(true);
        super.readyToMine();
        TestLogger.functionReturned();
    }

    @Override
    protected boolean isCompatibleWith() {
        return false;
    }
}
