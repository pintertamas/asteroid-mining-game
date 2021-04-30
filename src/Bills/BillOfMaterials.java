package Bills;

import Materials.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Recept osztály az építésekhez.
 */
@SuppressWarnings("SpellCheckingInspection")
public class BillOfMaterials {
    /**
     * Egy ArrayList-ben tároljuk a recepthez szükséges nyersanyagokat.
     */
    @SuppressWarnings("SpellCheckingInspection")
    private final ArrayList<Material> bill;

    /**
     * Konstruktor.
     */
    @SuppressWarnings("SpellCheckingInspection")
    public BillOfMaterials() {
        bill = new ArrayList<>();
    }

    /**
     * Egy HashMap-et ad vissza, amely tartalmazza a nyersanyagokat és a darabszámokat.
     *
     * @param materials
     * @return
     */
    @SuppressWarnings("SpellCheckingInspection")
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
    @SuppressWarnings("SpellCheckingInspection")
    public boolean hasEnoughMaterials(ArrayList<Material> inventoryMaterials) {
        HashMap<Class<?>, Integer> allMaterials = countMaterials(inventoryMaterials);
        HashMap<Class<?>, Integer> billMaterials = countMaterials(bill);

        if (allMaterials.isEmpty()) {
            return false;
        }
        for (Class<?> m : billMaterials.keySet()) {
            if (!allMaterials.containsKey(m))
                return false;
            else if (billMaterials.get(m) > allMaterials.get(m)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Az Inventory-ból levonja a nyersanyagokat.
     *
     * @param inventoryMaterials
     */
    @SuppressWarnings("SpellCheckingInspection")
    public void pay(ArrayList<Material> inventoryMaterials) {
        assert hasEnoughMaterials(inventoryMaterials);

        for (Material billMaterial : bill) {
            for (Material inventoryMaterial : inventoryMaterials) {
                if (billMaterial.getClass() == inventoryMaterial.getClass()) {
                    inventoryMaterials.remove(inventoryMaterial);
                    break;
                }
            }
        }
    }

    /**
     * Hozzáad egy adott m nyersanyagból count db-ot a recepthez.
     *
     * @param m
     * @param count
     */
    @SuppressWarnings("SpellCheckingInspection")
    public void addToBill(Material m, int count) {
        for (int i = 0; i < count; i++)
            bill.add(m);
    }
}
