import java.util.HashMap;

public abstract class BillOfMaterials {

    HashMap<Material, Integer> bill;

    public BillOfMaterials() {
        bill = new HashMap<>();
    }

    public boolean hasEnoughMaterial(HashMap<Material, Integer> inventoryMaterials) {
        for (Material m : inventoryMaterials.keySet()) {
            if (bill.get(m) > inventoryMaterials.get(m))
                return false;
        }
        return true;
    }

    public boolean isNeeded(Material m) {
        return bill.get(m) > 0;
    }

    public void checkMaterial(Material m) {
        if (bill.get(m) > 0)
            bill.put(m, bill.get(m) - 1);
    }

}
