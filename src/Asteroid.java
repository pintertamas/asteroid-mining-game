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

        for (Figure f : figures) {
            f.onExplosion();
        }
    }

    public void addFigure(Figure f) {
        TestLogger.functionCalled(this, "addFigure", f.getClass().getName() + " " + f, "void");
        TestLogger.functionReturned();

        this.figures.add(f);
    }

    public void removeFigure(Figure f) {
        TestLogger.functionCalled(this, "removeFigure", f.getClass().getName() + " " + f, "void");
        TestLogger.functionReturned();

        this.figures.remove(f);
    }

    public void setMaterial(Material m) {
        TestLogger.functionCalled(this, "setMaterial", m.getClass().getName() + " " + m, "void");
        this.material = m;
        TestLogger.functionReturned();
    }

    public boolean drilled() {
        TestLogger.functionCalled(this, "drilled", "boolean");
        if (this.layers > 0) {
            decreaseLayers();
            if (this.layers == 0) {
                if (isNearSun) {
                    material.readyToMine();
                }

            }
            TestLogger.functionReturned(String.valueOf(true));
            return false;
        }
        TestLogger.functionReturned(String.valueOf(true));
        return true;
    }

    public boolean mined(Settler s) {
        TestLogger.functionCalled(this, "mined", "boolean");
        if (this.layers == 0) {
            material.addToInventory(s);
            setIsHollow(true);
            TestLogger.functionReturned(String.valueOf(true));
            return true;
        }
        TestLogger.functionReturned(String.valueOf(false));
        return false;

    }

    public boolean coreChanged(Material m) {
        TestLogger.functionCalled(this, "coreChanged", "void");
        TestLogger.functionReturned(String.valueOf(true));
        if (this.isHollow) {
            setMaterial(m);
            return true;
        }
        return false;
    }

    public void decreaseLayers() {
        TestLogger.functionCalled(this, "decreaseLayers", "void");
        TestLogger.functionReturned();
        this.layers--;
    }

    public void addPortal(Portal p) {
        TestLogger.functionCalled(this, "addPortal", p.getClass().getName() + " " + p, "void");
        this.portal.add(p);
        TestLogger.functionReturned();
    }

    public void addNeighbor(Asteroid a) {
        TestLogger.functionCalled(this, "addNeighbor", "void");
        this.neighbors.add(a);
        TestLogger.functionReturned();
    }

    public void invokeFigures() {
        TestLogger.functionCalled(this, "invokeFigures", "void");
        for (Figure f : figures) {
            f.step();
        }
        TestLogger.functionReturned();
    }

    public void resetStep() {
        TestLogger.functionCalled(this, "resetStep", "void");
        for (Figure f : figures) {
            f.setRoundFinished(false);
        }
        TestLogger.functionReturned();
    }

    public ArrayList<Asteroid> getNeighbors() {
        TestLogger.functionCalled(this, "getNeighbors", "ArrayList<Asteroid>");
        TestLogger.functionReturned(neighbors.toString());

        return neighbors;
    }

    public void setIsHollow(boolean b) {
        TestLogger.functionCalled(this, "setIsHollow", "void");
        isHollow = b;
        TestLogger.functionReturned();
    }

    //TODO Ez maf?
    public void handleFigures(Figure f) {
        TestLogger.functionCalled(this, "handleFigures", "Figure f", "void");

        TestLogger.functionReturned();
    }
}
