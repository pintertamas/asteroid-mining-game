package Bills;

import Materials.*;
import Test.TestLogger;

import java.util.ArrayList;
import java.util.HashMap;

/** Recept osztály az építésekhez. */
public abstract class BillOfMaterials {

    /** Egy ArrayList-ben tároljuk a recepthez szükséges nyersanyagokat. */
    private final ArrayList<Material> bill;

    public BillOfMaterials() {
        bill = new ArrayList<>();
    }

    /** Egy HashMap-et ad vissza, amely tartalmazza a nyersanyagokat és a darabszámokat. */
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

    /** Leellenőrzi, hogy van-e elegendő nyersanyag a recepthez az Inventory-ban. */
    public boolean hasEnoughMaterials(ArrayList<Material> inventoryMaterials) {
        TestLogger.functionCalled(this, "hasEnoughMaterial", "boolean");
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

    /** Az Inventory-ból levonja a nyersanyagokat. */
    public void pay(ArrayList<Material> inventoryMaterials) {
        TestLogger.functionCalled(this, "pay", "inventoryMaterials", "void");
        assert hasEnoughMaterials(inventoryMaterials);

        for (Material material : bill) {
            inventoryMaterials.removeIf(inventoryMaterial -> inventoryMaterial.getClass() == material.getClass());
        }

        TestLogger.functionReturned();
    }

    /** Visszaadja a recepthez tartozó nyersanyagok listáját. */
    public ArrayList<Material> getBill() {
        return bill;
    }

    /** Hozzáad egy adott nyersanyagból count db-ot a recepthez. */
    public void addToBill(Material m, int count) {
        for (int i = 0; i < count; i++)
            bill.add(m);
    }
}
