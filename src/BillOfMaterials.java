import java.util.ArrayList;

public class BillOfMaterials {

    ArrayList<Material> materials;

    public BillOfMaterials() {
        materials = new ArrayList<>();
    }

    public boolean hasEnoughMaterial(ArrayList<Material> mats) {
        return true;
    }

    public boolean isNeeded(Material m) {
        return true;
    }

    public void checkMaterials(Material m) {
    }

}
