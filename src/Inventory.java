import java.util.ArrayList;
import java.util.HashMap;

public class Inventory {
    ArrayList<Portal> portals;
    HashMap<Material, Integer> materials;

    public Inventory() {
        this.portals = new ArrayList<>();
        this.materials = new HashMap<>();
    }

    public void addMaterial(Material m) {
        //TODO testlogger nem jó itt meg kéne oldani
        TestLogger.functionCalled(this, "addMaterial", "Material m", "void");
        if (materials.get(m) == 0)
            materials.put(m, 1);
        else
            materials.put(m, materials.get(m) + 1);
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
