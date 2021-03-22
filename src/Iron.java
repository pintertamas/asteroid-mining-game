public class Iron extends Material {

    public Iron() {
        super();
    }

    @Override
    protected boolean isCompatibleWith() {
        return false;
    }

    @Override
    public void addToList(Material coal, Material iron, Material uranium, Material ice, Settler s) {
        s.getBillOfMaterials().addMaterial(iron);
    }


}
