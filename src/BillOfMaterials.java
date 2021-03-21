import java.util.HashMap;

public abstract class BillOfMaterials {

    HashMap<Material, Integer> materials;

    public BillOfMaterials() {
        materials = new HashMap<>();
    }

    public boolean hasEnoughMaterial(HashMap<Material, Integer> mats) {
        return true;
    }

    public boolean isNeeded(Material m) {
        return true;
    }

    public void checkMaterials(Material m) {
    }

}
