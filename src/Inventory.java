import java.util.ArrayList;
import java.util.HashMap;

public class Inventory {
    private final ArrayList<Portal> portals;
    private final HashMap<Class<?>, Integer> materials;
    private final int inventoryCapacity;

    public Inventory() {
        this.portals = new ArrayList<>();
        this.materials = new HashMap<>();
        this.inventoryCapacity = 10;
    }

    private boolean isInventoryFull() {
        int itemCount = 0;
        for (Class<?> m : materials.keySet()) {
            itemCount += materials.get(m);
        }
        return itemCount >= inventoryCapacity;
    }

    public void addMaterial(Material m) {
        TestLogger.functionCalled(this, "addMaterial", "Material m", "void");
        if (!isInventoryFull()) {
            if (materials.containsKey(m.getClass())) {
                if (materials.get(m.getClass()) > 0)
                    materials.put(m.getClass(), materials.get(m.getClass()) + 1);
            }
            else materials.put(m.getClass(), 1);
        }
        TestLogger.functionReturned();
    }

    public void removeMaterial(Material m) {
        if (materials.get(m.getClass()) > 1)
            materials.put(m.getClass(), materials.get(m.getClass()) - 1);
        else
            materials.remove(m.getClass());
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

    public HashMap<Class<?>, Integer> getMaterials() {
        return materials;
    }
}
