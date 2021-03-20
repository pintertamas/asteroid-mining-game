import java.util.ArrayList;

public class Asteroid {
    ArrayList<Asteroid> neighbors;
    ArrayList<Figure> figures;
    Material material;
    ArrayList<Portal> portal;

    int layers;
    boolean isNearSun;
    boolean isHollow;

    public Asteroid(Material material, int layers, boolean isNearSun, boolean isHollow) {
        TestLogger.functionCalled(this, "Asteroid", "void");
        this.figures = new ArrayList<>();
        this.portal = new ArrayList<>();
        this.neighbors = new ArrayList<>();
        this.material = material;
        material.setAsteroid(this);
        this.layers = layers;
        this.isNearSun = isNearSun;
        this.isHollow = isHollow;
        TestLogger.functionReturned();
    }

    public void explode() {
        TestLogger.functionCalled(this, "explode", "void");
        TestLogger.functionReturned();
    }

    public void addFigure(Figure f) {
        TestLogger.functionCalled(this, "addFigure", f.getClass().getName() + " " + f, "void");
        TestLogger.functionReturned();

        this.figures.add(f);
    }

    public void removeFigure(Figure f) {
        TestLogger.functionCalled(this, "removeFigure", f.getClass().getName() + " " + f, "void");
        TestLogger.functionReturned();
    }

    public void setMaterial(Material m) {
        TestLogger.functionCalled(this, "setMaterial", m.getClass().getName() + " " + m, "void");
        TestLogger.functionReturned();
    }

    public boolean drilled() {
        TestLogger.functionCalled(this, "drilled", "boolean");
        if (this.layers > 0) {
            decreaseLayers();
            if (this.layers == 0) {
                material.readyToMine();
            }
            TestLogger.functionReturned(String.valueOf(true));
            return false;
        }
        TestLogger.functionReturned(String.valueOf(true));
        return true;
    }

    public boolean mined() {
        TestLogger.functionCalled(this, "mined", "void");
        TestLogger.functionReturned(String.valueOf(true));

        return true;
    }

    public boolean coreChanged() {
        TestLogger.functionCalled(this, "coreChanged", "void");
        TestLogger.functionReturned(String.valueOf(true));

        return true;
    }

    public void decreaseLayers() {
        TestLogger.functionCalled(this, "decreaseLayers", "void");
        TestLogger.functionReturned();
        this.layers--;
    }

    public void addPortal(Portal p) {
        TestLogger.functionCalled(this, "addPortal", p.getClass().getName() + " " + p, "void");
        TestLogger.functionReturned();

        this.portal.add(p);
    }

    public void addNeighbor(Asteroid a) {
        TestLogger.functionCalled(this, "addNeighbor", "void");
        TestLogger.functionReturned();

        this.neighbors.add(a);
    }

    public void invokeFigures() {
        TestLogger.functionCalled(this, "invokeFigures", "void");
        TestLogger.functionReturned();
    }

    public void resetStep() {
        TestLogger.functionCalled(this, "resetStep", "void");
        TestLogger.functionReturned();
    }

    public ArrayList<Asteroid> getNeighbors() {
        TestLogger.functionCalled(this, "getNeighbors", "ArrayList<Asteroid>");
        TestLogger.functionReturned(neighbors.toString());

        return neighbors;
    }
}
