public class Ice extends Material {
    @Override
    protected void readyToMine() {
        super.readyToMine();
    }

    @Override
    protected boolean isCompatibleWith() {
        return false;
    }
}
