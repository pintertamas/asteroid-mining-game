public class Settler extends Figure {
    private Inventory inventory;
    private BillOfMaterials billOfMaterials;

    @Override
    public void move() {
    }

    public boolean mine() {
        return true;
    }

    public void buildPortal() {
    }

    public void buildRobot() {
    }

    public void buildBase() {
    }

    public void putPortalDown() {
    }

    public boolean putMaterialBack() {
        return true;
    }

    public Material chooseMaterial() {
        return new Iron();
    }

    @Override
    public void onExplosion() {
    }

    @Override
    public void step() {
    }
}
