public class Coal extends Material {
    @Override
    protected boolean isCompatibleWith() {
        return false;
    }

    @Override
    public void addToList(Material coal, Material iron, Material uranium, Material Ice, Settler s) {
        s.getBillOfMaterials().addMaterial(coal);
    }


}
