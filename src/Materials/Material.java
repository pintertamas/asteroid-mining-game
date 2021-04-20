package Materials;
import Entities.Settler;
import Playground.Asteroid;
import Test.TestLogger;

/**
 * Nyersanyag osztály.
 */
@SuppressWarnings("SpellCheckingInspection")
public abstract class Material {
    private Asteroid asteroid;
    private int nearSunCount;
    protected String imagePath;

    /**
     * Az utolsó réteg lefúrása után a nyersanyag reakciója a napközelség függvényében.
     */
    @SuppressWarnings("SpellCheckingInspection")
    public void readyToMine() {
        TestLogger.functionCalled(this, "readyToMine()", "void");
        TestLogger.functionReturned();
    }

    /**
     * A nyersanyag hozzáadása a Settler Inventory-jához.
     *
     * @param s
     */
    @SuppressWarnings("SpellCheckingInspection")
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
    @SuppressWarnings("SpellCheckingInspection")
    public void setAsteroid(Asteroid asteroid) {
        this.asteroid = asteroid;
    }

    /**
     * Az adott nyersanyagot tartalmazó aszteroida visszaadása.
     *
     * @return
     */
    @SuppressWarnings("SpellCheckingInspection")
    public Asteroid getAsteroid() {
        return asteroid;
    }

    /**
     * Visszaadja, hogy hányszor volt napközelben.
     *
     * @return
     */
    @SuppressWarnings("SpellCheckingInspection")
    public int getNearSunCount() {
        return nearSunCount;
    }

    /**
     * Beállítja, hogy hányszor volt napközelben a nyersanyag.
     *
     * @return
     */
    public void setNearSunCount(int nearSunCount) {
        this.nearSunCount = nearSunCount;
    }
}
