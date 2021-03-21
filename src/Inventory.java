import java.util.ArrayList;

public class Inventory {
    ArrayList<Portal> portals;
    ArrayList<Material> materials;

    public Inventory() {
        this.portals = new ArrayList<>();
        this.materials = new ArrayList<>();
    }

    public void addMaterial(Material m) {
        //TODO testlogger nem jó itt meg kéne oldani
        TestLogger.functionCalled(this, "addMaterial", "Material m", "void");
        materials.add(m);
        TestLogger.functionReturned();
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
