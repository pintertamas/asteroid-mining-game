package Bills;

import Materials.*;
import Test.TestLogger;

import java.util.HashMap;

public abstract class BillOfMaterials {

    private final HashMap<Class<?>, Integer> bill;

    public BillOfMaterials() {
        bill = new HashMap<>();
    }

    public boolean hasEnoughMaterial(HashMap<Class<?>, Integer> inventoryMaterials) {
        TestLogger.functionCalled(this, "hasEnoughMaterial", "boolean");
        if (inventoryMaterials.isEmpty()) {
            TestLogger.functionReturned(String.valueOf(false));
            return false;
        }
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
        TestLogger.functionCalled(this, "isNeeded", "Materials.Material m", "boolean");
        TestLogger.functionReturned(String.valueOf(bill.get(m.getClass()) > 0));
        return bill.get(m.getClass()) > 0;
    }

    public void checkMaterial(Material m) {
        TestLogger.functionCalled(this, "checkMaterial", "Materials.Material m", "void");
        if (bill.get(m.getClass()) > 0)
            bill.put(m.getClass(), bill.get(m.getClass()) - 1);
        TestLogger.functionReturned();
    }

    public void pay(HashMap<Class<?>, Integer> inventoryMaterials) {
        TestLogger.functionCalled(this, "pay", "HashMap<Class<?>, Integer> inventoryMaterials", "void");
        assert hasEnoughMaterial(inventoryMaterials);
        inventoryMaterials.replaceAll((material, value) -> value - bill.get(material));
        TestLogger.functionReturned();
    }

    public HashMap<Class<?>, Integer> getBill() {
        return bill;
    }
}
