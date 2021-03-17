public class Uranium extends Material{
    @Override
    protected void readyToMine() {
        super.readyToMine();
    }

    @Override
    protected boolean isCompatibleWith() {
        return true;
    }
}
