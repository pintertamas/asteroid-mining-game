import java.util.ArrayList;

public class Inventory {
    ArrayList<Portal> portals;
    ArrayList<Material> materials;

    public void addMaterial(Material m) {
    }

    public void removeMaterial(Material m) {
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

    public ArrayList<Material> getMaterials() {
        return materials;
    }
}
