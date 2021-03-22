public class Uranium extends Material {
    @Override
    protected void readyToMine() {
        TestLogger.functionCalled(this, "readyToMine()", "void");
        asteroid.explode();
        super.readyToMine();
        TestLogger.functionReturned();
    }

    @Override
    protected boolean isCompatibleWith() {
        return true;
    }

    @Override
    public void addToList(Material coal, Material iron, Material uranium, Material Ice, Settler s) {
        s.getBillOfMaterials().addMaterial(uranium);
    }


}
