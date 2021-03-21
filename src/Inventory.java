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
        //TODO testlogger nem jó itt meg kéne oldani !!!!!!!!!!!!!!!!!!!!!!!!!
        TestLogger.functionCalled(this, "addMaterial", "Material m", "void");
        if (materials.get(m) == 0)
            materials.put(m, 1);
        else
            materials.put(m, materials.get(m) + 1);
        TestLogger.functionReturned();
    }

    public void removeMaterial(Material m) {
        TestLogger.functionCalled(this, "removeMaterial", "Material m", "void");
        if (materials.get(m) > 1)
            materials.put(m, materials.get(m) - 1);
        else
            materials.remove(m);
        TestLogger.functionReturned();
    }

    public void addPortal(Portal p) {
        TestLogger.functionCalled(this, "addPortal", "Portal p", "void");
        portals.add(p);
        TestLogger.functionReturned();
    }

    public void removePortal(Portal p) {
        TestLogger.functionCalled(this, "removePortal", "Portal p", "void");
        portals.remove(p);
        TestLogger.functionReturned();
    }

    public ArrayList<Portal> getPortals() {
        TestLogger.functionCalled(this, "getPortals",  "ArrayList<Portal>");
        TestLogger.functionReturned(portals.toString());
        return portals;
    }

    public HashMap<Material, Integer> getMaterials() {
        TestLogger.functionCalled(this, "getMaterials",  "HashMap<Material, Integer>");
        TestLogger.functionReturned(materials.toString());
        return materials;
    }
}
