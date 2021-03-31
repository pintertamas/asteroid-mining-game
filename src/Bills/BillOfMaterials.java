package Bills;

import Materials.*;
import Test.TestLogger;

import java.util.ArrayList;
import java.util.HashMap;

/** Recept osztály az építésekhez. */
public class BillOfMaterials {
    /**
     * Egy ArrayList-ben tároljuk a recepthez szükséges nyersanyagokat.
     */
    private final ArrayList<Material> bill;

    /**
     * Konstruktor.
     */
    public BillOfMaterials() {
        bill = new ArrayList<>();
    }

    /**
     * Egy HashMap-et ad vissza, amely tartalmazza a nyersanyagokat és a darabszámokat.
     *
     * @param materials
     * @return
     */
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

    /**
     * Leellenőrzi, hogy van-e elegendő nyersanyag a recepthez az Inventory-ban.
     *
     * @param inventoryMaterials
     * @return
     */
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

    /**
     * Az Inventory-ból levonja a nyersanyagokat.
     *
     * @param inventoryMaterials
     */
    public void pay(ArrayList<Material> inventoryMaterials) {
        TestLogger.functionCalled(this, "pay", "ArrayList<Material> inventoryMaterials", "void");
        assert hasEnoughMaterials(inventoryMaterials);

        for (int i = 0; i < bill.size(); i++) {
            for (int j = 0; j < inventoryMaterials.size(); j++) {
                if (inventoryMaterials.get(j).getClass() == bill.get(i).getClass()) {
                    inventoryMaterials.remove(j);
                }
            }
        }

        TestLogger.functionReturned();
    }

    /**
     * Visszaadja a recepthez tartozó nyersanyagok listáját.
     *
     * @return
     */
    public ArrayList<Material> getBill() {
        return bill;
    }

    /**
     * Hozzáad egy adott m nyersanyagból count db-ot a recepthez.
     *
     * @param m
     * @param count
     */
    public void addToBill(Material m, int count) {
        for (int i = 0; i < count; i++)
            bill.add(m);
    }
}
