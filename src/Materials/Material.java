package Materials;
import Entities.Settler;
import Playground.Asteroid;
import Views.MaterialView;

/**
 * Nyersanyag osztály.
 */
@SuppressWarnings("SpellCheckingInspection")
public abstract class Material {
    private Asteroid asteroid;
    private int nearSunCount;
    protected final MaterialView materialView = new MaterialView(this);

    public MaterialView getMaterialView() {
        return materialView;
    }

    /**
     * Az utolsó réteg lefúrása után a nyersanyag reakciója a napközelség függvényében.
     */
    @SuppressWarnings("SpellCheckingInspection")
    public void readyToMine() {
    }

    /**
     * A nyersanyag hozzáadása a Settler Inventory-jához.
     *
     * @param s
     */
    @SuppressWarnings("SpellCheckingInspection")
    public void addToInventory(Settler s) {
        s.getInventory().addMaterial(this);
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
