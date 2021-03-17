import java.util.ArrayList;

public class Asteroid {
    ArrayList<Asteroid> neighbors;
    ArrayList<Figure> figures;
    Material material;
    Portal portal;

    int layers;
    boolean isNearSun;
    boolean isHollow;

    public Asteroid(Material material, int layers, boolean isNearSun, boolean isHollow) {
        this.figures = new ArrayList<>();
        this.portal = null;
        this.neighbors = new ArrayList<>();
        this.material = material;
        material.setAsteroid(this);
        this.layers = layers;
        this.isNearSun = isNearSun;
        this.isHollow = isHollow;
    }

    public void explode() {
        TestLogger.functionCalled(this.getClass().getName(), this.toString(), "explode", "", "void");
        TestLogger.functionReturned();
    }

    public void addFigure(Figure f) {
        TestLogger.functionCalled(this.getClass().getName(), this.toString(), "addFigure", f.getClass().getName() + " " + f, "void");
        TestLogger.functionReturned();

        this.figures.add(f);
    }

    public void removeFigure(Figure f) {
        TestLogger.functionCalled(this.getClass().getName(), this.toString(), "removeFigure", f.getClass().getName() + " " + f, "void");
        TestLogger.functionReturned();
    }

    public void setMaterial(Material m) {
        TestLogger.functionCalled(this.getClass().getName(), this.toString(), "setMaterial", m.getClass().getName() + " " + m, "void");
        TestLogger.functionReturned();
    }

    public boolean drilled() {
        TestLogger.functionCalled(this.getClass().getName(), this.toString(), "drilled", "", "void");
        TestLogger.functionReturned(String.valueOf(true));

        return true;
    }

    public boolean mined() {
        return true;
    }

    public boolean coreChanged() {
        return true;
    }

    public void decreaseLayers() {
    }

    public void addPortal(Portal p) {
    }

    public void addNeighbor(Asteroid a) {
        this.neighbors.add(a);
    }

    public void invokeFigures() {
    }

    public void resetStep() {
    }

    public ArrayList<Asteroid> getNeighbors() {
        TestLogger.functionCalled(this.getClass().getName(), this.toString(), "getNeighbors", "", "ArrayList<Asteroid>");
        TestLogger.functionReturned(neighbors.toString());

        return neighbors;
    }
}
