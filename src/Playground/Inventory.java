package Playground;

import Materials.Material;

import java.util.ArrayList;

/**
 * Inventory osztály, amelyben a játékosok a nyersanyagaikat tárolják.
 */
@SuppressWarnings("SpellCheckingInspection")
public class Inventory {
    private final ArrayList<Portal> portals;
    private final ArrayList<Material> materials;
    private final int materialCapacity;
    private final int portalCapacity;
    private Material selectedMaterial;

    /**
     * Konstruktor.
     */
    @SuppressWarnings("SpellCheckingInspection")
    public Inventory() {
        this.portals = new ArrayList<>();
        this.materials = new ArrayList<>();
        this.materialCapacity = 10;
        this.portalCapacity = 3;
        this.selectedMaterial = null;
    }

    public void setSelectedMaterial(Material selectedMaterial) {
        this.selectedMaterial = selectedMaterial;
    }

    public Material getSelectedMaterial() {
        return selectedMaterial;
    }

    /**
     * Megnézi, hogy a nyersanyagok Inventory-ja tele van-e.
     *
     * @return
     */
    @SuppressWarnings("SpellCheckingInspection")
    private boolean isMaterialInventoryFull() {
        return materials.size() >= materialCapacity;
    }

    /**
     * Hozzáad egy nyersanyagot az Inventory-hoz.
     *
     * @param m
     */
    @SuppressWarnings("SpellCheckingInspection")
    public void addMaterial(Material m) {
        if (!isMaterialInventoryFull()) {
            materials.add(m);
        }
    }

    /**
     * Kivesz egy nyersanyagot az Inventory-ból.
     *
     * @param m
     */
    @SuppressWarnings("SpellCheckingInspection")
    public void removeMaterial(Material m) {
        for (int i = 0; i < materials.size(); i++) {
            if (materials.get(i).equals(m))
                materials.remove(m);
        }
    }

    /**
     * Megnézi, hogy a teleportkapuk Inventory-ja tele van-e.
     *
     * @return
     */
    @SuppressWarnings("SpellCheckingInspection")
    private boolean isPortalInventoryFull() {
        int itemCount = portals.size();
        return itemCount >= portalCapacity;
    }

    /**
     * Hozzáad egy teleportkaput az Inventory-hoz.
     *
     * @param p
     */
    @SuppressWarnings("SpellCheckingInspection")
    public void addPortal(Portal p) {
        if (!isPortalInventoryFull())
            portals.add(p);
    }

    /**
     * Kivesz eg yteleportkaput az Inventory-ból.
     *
     * @param p
     */
    @SuppressWarnings("SpellCheckingInspection")
    public void removePortal(Portal p) {
        if (portals.size() != 0 && p != null)
            portals.remove(p);
    }

    /**
     * Visszaadja az Inventory-ban lévő teleportkapuk listáját.
     *
     * @return
     */
    @SuppressWarnings("SpellCheckingInspection")
    public ArrayList<Portal> getPortals() {
        return portals;
    }

    /**
     * Visszaadja az Inventory-ban lévő nyersanyagok listáját.
     *
     * @return
     */
    @SuppressWarnings("SpellCheckingInspection")
    public ArrayList<Material> getMaterials() {
        return materials;
    }

    /**
     * Getter a materialok kapacitásához
     */
    public int getMaterialCapacity() {
        return materialCapacity;
    }

    /**
     * Getter a portálok kapacitásához
     */
    public int getPortalCapacity() {
        return portalCapacity;
    }
}
