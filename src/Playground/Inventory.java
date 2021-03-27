package Playground;

import Materials.Material;
import Test.TestLogger;

import java.util.ArrayList;

/**
 * Inventory osztály, amelyben a játékosok a nyersanyagaikat tárolják.
 */
public class Inventory {
    private final ArrayList<Portal> portals;
    private final ArrayList<Material> materials;
    private final int materialCapacity;
    private final int portalCapacity;

    /**
     * Konstruktor.
     */
    public Inventory() {
        this.portals = new ArrayList<>();
        this.materials = new ArrayList<>();
        this.materialCapacity = 10;
        this.portalCapacity = 3;
    }

    /**
     * Megnézi, hogy a nyersanyagok Inventory-ja tele van-e.
     *
     * @return
     */
    private boolean isMaterialInventoryFull() {
        TestLogger.functionCalled(this, "isMaterialInventoryFull", "boolean");
        TestLogger.functionReturned(String.valueOf(materials.size() >= materialCapacity));
        return materials.size() >= materialCapacity;
    }

    /**
     * Hozzáad egy nyersanyagot az Inventory-hoz.
     *
     * @param m
     */
    public void addMaterial(Material m) {
        TestLogger.functionCalled(this, "addMaterial", "Materials.Material m", "void");
        if (!isMaterialInventoryFull()) {
            materials.add(m);
        }
        TestLogger.functionReturned();
    }

    /**
     * Kivesz egy nyersanyagot az Inventory-ból.
     *
     * @param m
     */
    public void removeMaterial(Material m) {
        TestLogger.functionCalled(this, "removeMaterial", "Materials.Material m", "void");
        for (int i = 0; i < materials.size(); i++) {
            if (materials.get(i).equals(m))
                materials.remove(m);
        }
        TestLogger.functionReturned();
    }

    /**
     * Megnézi, hogy a teleportkapuk Inventory-ja tele van-e.
     *
     * @return
     */
    private boolean isPortalInventoryFull() {
        TestLogger.functionCalled(this, "isPortalInventoryFull", "boolean");
        int itemCount = portals.size();
        TestLogger.functionReturned(String.valueOf(itemCount >= portalCapacity));
        return itemCount >= portalCapacity;
    }

    /**
     * Hozzáad egy teleportkaput az Inventory-hoz.
     *
     * @param p
     */
    public void addPortal(Portal p) {
        TestLogger.functionCalled(this, "addPortal", "Playground.Portal p", "void");
        if (!isPortalInventoryFull())
            portals.add(p);
        TestLogger.functionReturned();
    }

    /**
     * Kivesz eg yteleportkaput az Inventory-ból.
     *
     * @param p
     */
    public void removePortal(Portal p) {
        TestLogger.functionCalled(this, "removePortal", "Playground.Portal p", "void");
        if (portals.size() != 0 && p != null)
            portals.remove(p);
        TestLogger.functionReturned();
    }

    /**
     * Visszaadja az Inventory-ban lévő teleportkapuk listáját.
     *
     * @return
     */
    public ArrayList<Portal> getPortals() {
        return portals;
    }

    /**
     * Visszaadja az Inventory-ban lévő nyersanyagok listáját.
     *
     * @return
     */
    public ArrayList<Material> getMaterials() {
        return materials;
    }
}
