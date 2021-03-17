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
    }

    public void addFigure(Figure f, int index) {
        new TestLogger().functionCalled(this.getClass().getName(), this.toString(), "addFigure", f.getClass().getName() + " " + f, "void", index);
        new TestLogger().functionReturned(this.getClass().getName(), this.toString(), "addFigure", f.getClass().getName() + " " + f, "", index);

        this.figures.add(f);
    }

    public void removeFigure(Figure f, int index) {
        index++;
        for (int i = 0; i < index; i++)
            System.out.print("\t");

        System.out.print(index + " ");
        System.out.println(this.getClass().getName() + " " + this + " removeFigure(" + f.getClass().getName() + " " + f + "): void");

        for (int i = 0; i < index; i++)
            System.out.print("\t");
        System.out.print(index + " ");

        System.out.println(this.getClass().getName() + " " + this + " removeFigure(" + f.getClass().getName() + " " + f + ") returned");
    }

    public void setMaterial(Material m) {
    }

    public boolean drilled() {
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

    public ArrayList<Asteroid> getNeighbors(int index) {
        index++;
        for (int i = 0; i < index; i++)
            System.out.print("\t");

        System.out.print(index + " ");
        System.out.println(this.getClass().getName() + " " + this + " getNeighbors(): ArrayList<Asteroid>");

        for (int i = 0; i < index; i++)
            System.out.print("\t");
        System.out.print(index + " ");

        System.out.println(this.getClass().getName() + " " + this + " getNeighbors() returned: " + neighbors);

        return neighbors;
    }
}
