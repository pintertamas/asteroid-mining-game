package Materials;
import Entities.Settler;
import Playground.Asteroid;
import Test.TestLogger;

/**
 * Nyersanyag osztály.
 */
public abstract class Material {
    private Asteroid asteroid;
    private int nearSunCount;

    /**
     * Konstruktor.
     */
    public Material() {
    }

    /**
     * Az utolsó réteg lefúrása után a nyersanyag reakciója a napközelség függvényében.
     */
    public void readyToMine() {
        TestLogger.functionCalled(this, "readyToMine()", "void");
        TestLogger.functionReturned();
    }

    /**
     * A nyersanyag hozzáadása a Settler Inventory-jához.
     *
     * @param s
     */
    public void addToInventory(Settler s) {
        TestLogger.functionCalled(this, "addToInventory", "Entities.Settler s", "void");
        s.getInventory().addMaterial(this);
        TestLogger.functionReturned();
    }

    /**
     * Az adott nyersanyagot tartalmazó aszteroida beállítása.
     *
     * @param asteroid
     */
    public void setAsteroid(Asteroid asteroid) {
        this.asteroid = asteroid;
    }

    /**
     * Az adott nyersanyagot tartalmazó aszteroida visszaadása.
     *
     * @return
     */
    public Asteroid getAsteroid() {
        return asteroid;
    }

    /**
     * Visszaadja, hogy napközelben van-e.
     *
     * @return
     */
    public int getNearSunCount() {
        return nearSunCount;
    }
}
