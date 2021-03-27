package Bills;

import Materials.*;
import Test.TestLogger;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class BillOfMaterials {

    private final ArrayList<Material> bill;

    public BillOfMaterials() {
        bill = new ArrayList<>();
    }

    public HashMap<Class<?>, Integer> countMaterials(ArrayList<Material> materials) {
        HashMap<Class<?>, Integer> result = new HashMap<>();
        for (Material m : materials) {
            if (result.containsKey(m.getClass())) {
                if (result.get(m.getClass()) > 0)
                    result.put(m.getClass(), result.get(m.getClass()) + 1);
            } else result.put(m.getClass(), 1);
        }
        return result;
    }

    public boolean hasEnoughMaterials(ArrayList<Material> inventoryMaterials) {
        TestLogger.functionCalled(this, "hasEnoughMaterial", "ArrayList<Material> inventoryMaterials", "boolean");
        HashMap<Class<?>, Integer> allMaterials = countMaterials(inventoryMaterials);

        if (allMaterials.isEmpty()) {
            TestLogger.functionReturned(String.valueOf(false));
            return false;
        }
        for (Class<?> m : allMaterials.keySet()) {
            if (!allMaterials.containsKey(m))
                return false;
            if (countMaterials(bill).get(m) > allMaterials.get(m)) {
                TestLogger.errorMessage("Insufficient materials!");
                TestLogger.functionReturned(String.valueOf(false));
                return false;
            }
        }
        TestLogger.functionReturned(String.valueOf(true));
        return true;
    }

    public void pay(ArrayList<Material> inventoryMaterials) {
        TestLogger.functionCalled(this, "pay", "ArrayList<Material> inventoryMaterials", "void");
        assert hasEnoughMaterials(inventoryMaterials);

        for (Material material : bill) {
            inventoryMaterials.removeIf(inventoryMaterial -> inventoryMaterial.getClass() == material.getClass());
        }

        TestLogger.functionReturned();
    }

    public ArrayList<Material> getBill() {
        return bill;
    }

    public void addToBill(Material m, int count) {
        for (int i = 0; i < count; i++)
            bill.add(m);
    }
}
