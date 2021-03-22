import java.util.HashMap;

public abstract class BillOfMaterials {

    HashMap<Class<?>, Integer> bill;

    public BillOfMaterials() {
        bill = new HashMap<>();
    }

    public boolean hasEnoughMaterial(HashMap<Class<?>, Integer> inventoryMaterials) {
        TestLogger.functionCalled(this, "hasEnoughMaterial", "boolean");
        if (inventoryMaterials.isEmpty())
            return false;
        for (Class<?> m : inventoryMaterials.keySet()) {
            if (bill.get(m) > inventoryMaterials.get(m)) {
                TestLogger.errorMessage("Insufficient materials!");
                TestLogger.functionReturned(String.valueOf(false));
                return false;
            }
        }
        TestLogger.functionReturned(String.valueOf(true));
        return true;
    }

    public boolean isNeeded(Material m) {
        return bill.get(m.getClass()) > 0;
    }

    public void checkMaterial(Material m) {
        if (bill.get(m.getClass()) > 0)
            bill.put(m.getClass(), bill.get(m.getClass()) - 1);
    }

    public void pay(HashMap<Class<?>, Integer> inventoryMaterials) {
        assert hasEnoughMaterial(inventoryMaterials);
        inventoryMaterials.replaceAll((material, value) -> value - bill.get(material));
    }
}
