import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class Inventory {
    private ArrayList<Portal> portals;
    private HashMap<Material, Integer> materials;
    private ArrayList<Material> mats;
    private final int inventoryCapacity;

    public Inventory() {
        this.portals = new ArrayList<>();
        this.materials = new HashMap<>();
        this.inventoryCapacity = 10;
        this.mats = new ArrayList<>();
    }

    public ArrayList<Material> getMats() {
        return mats;
    }

    private boolean isInventoryFull() {
        int itemCount = 0;
        for (Material m : materials.keySet()) {
            itemCount += materials.get(m);
        }
        return itemCount >= inventoryCapacity;
    }

    /*
    public void addMaterial(Material m) {
        TestLogger.functionCalled(this, "addMaterial", "Material m", "void");
        if (!isInventoryFull()) {
            if (materials.get(m) == 0)
                materials.put(m, 1);
            else
                materials.put(m, materials.get(m) + 1);
        }
        TestLogger.functionReturned();
    }
    */

    public void addMaterial(Material m) {
        TestLogger.functionCalled(this, "addMaterial", "Material m", "void");
        mats.add(m);
        TestLogger.functionReturned();
    }

    public void removeMaterial(Material m) {
        if (materials.get(m) > 1)
            materials.put(m, materials.get(m) - 1);
        else
            materials.remove(m);
    }

    public void addPortal(Portal p) {
        portals.add(p);
    }

    public void removePortal(Portal p) {
        portals.remove(p);
    }

    public ArrayList<Portal> getPortals() {
        return portals;
    }

    public HashMap<Material, Integer> getMaterials() {
        return materials;
    }

}
