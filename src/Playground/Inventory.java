package Playground;

import Materials.Material;
import Test.TestLogger;

import java.util.ArrayList;
import java.util.HashMap;

public class Inventory {
    private final ArrayList<Portal> portals;
    private final HashMap<Class<?>, Integer> materials;
    private final int materialCapacity;
    private final int portalCapacity;

    public Inventory() {
        this.portals = new ArrayList<>();
        this.materials = new HashMap<>();
        this.materialCapacity = 10;
        this.portalCapacity = 3;
    }

    private boolean isMaterialInventoryFull() {
        TestLogger.functionCalled(this, "isMaterialInventoryFull", "boolean");
        int itemCount = 0;
        for (Class<?> m : materials.keySet()) {
            itemCount += materials.get(m);
        }
        TestLogger.functionReturned(String.valueOf(itemCount >= materialCapacity));
        return itemCount >= materialCapacity;
    }

    public void addMaterial(Material m) {
        TestLogger.functionCalled(this, "addMaterial", "Materials.Material m", "void");
        if (!isMaterialInventoryFull()) {
            if (materials.containsKey(m.getClass())) {
                if (materials.get(m.getClass()) > 0)
                    materials.put(m.getClass(), materials.get(m.getClass()) + 1);
            }
            else materials.put(m.getClass(), 1);
        }
        TestLogger.functionReturned();
    }

    public void removeMaterial(Material m) {
        TestLogger.functionCalled(this, "removeMaterial", "Materials.Material m", "void");
        if (materials.containsKey(m.getClass())) {
            if (materials.get(m.getClass()) > 1)
                materials.put(m.getClass(), materials.get(m.getClass()) - 1);
            else
                materials.remove(m.getClass());
        }
        TestLogger.functionReturned();
    }

    private boolean isPortalInventoryFull() {
        TestLogger.functionCalled(this, "isPortalInventoryFull", "boolean");
        int itemCount = portals.size();
        TestLogger.functionReturned(String.valueOf(itemCount >= portalCapacity));
        return itemCount >= portalCapacity;
    }

    public void addPortal(Portal p) {
        TestLogger.functionCalled(this, "addPortal", "Playground.Portal p", "void");
        if (!isPortalInventoryFull())
            portals.add(p);
        TestLogger.functionReturned();
    }

    public void removePortal(Portal p) {
        TestLogger.functionCalled(this, "removePortal", "Playground.Portal p", "void");
        if (portals.size() != 0 && p != null)
            portals.remove(p);
        TestLogger.functionReturned();
    }

    public ArrayList<Portal> getPortals() {
        return portals;
    }

    public HashMap<Class<?>, Integer> getMaterials() {
        return materials;
    }
}
