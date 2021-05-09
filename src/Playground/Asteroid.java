package Playground;

import Controllers.Map;
import Entities.Figure;
import Entities.Settler;
import Materials.Material;
import Views.AsteroidView;
import javafx.geometry.Rectangle2D;
import Maths.Drawable;
import javafx.scene.Group;

import java.util.ArrayList;

/**
 * Aszteroida osztály.
 */
@SuppressWarnings("SpellCheckingInspection")
public class Asteroid {
    private Drawable position = new Drawable();
    private Map map;
    private final ArrayList<Asteroid> neighbors;
    private final ArrayList<Figure> figures;
    private Material material;
    private final ArrayList<Portal> portals;
    private int stepsLeft;

    int layers;
    final boolean isNearSun;
    boolean isHollow;

    private final AsteroidView asteroidView;

    /**
     * Konstruktor.
     *
     * @param map
     * @param material
     * @param layers
     * @param isNearSun
     * @param isHollow
     */
    @SuppressWarnings("SpellCheckingInspection")
    public Asteroid(Map map, Material material, int layers, boolean isNearSun, boolean isHollow) {
        this.map = map;
        this.figures = new ArrayList<>();
        this.portals = new ArrayList<>();
        this.neighbors = new ArrayList<>();
        this.material = material;
        this.material.setAsteroid(this);
        this.layers = layers;
        this.isNearSun = isNearSun;
        this.isHollow = isHollow;
        this.asteroidView = new AsteroidView(this);
        this.stepsLeft = this.getFigures().size();
    }

    /**
     * Map nélküli konstruktor.
     *
     * @param material
     * @param layers
     * @param isNearSun
     * @param isHollow
     */
    @SuppressWarnings("SpellCheckingInspection")
    public Asteroid(Material material, int layers, boolean isNearSun, boolean isHollow) {
        this.figures = new ArrayList<>();
        this.portals = new ArrayList<>();
        this.neighbors = new ArrayList<>();
        this.material = material;
        this.material.setAsteroid(this);
        this.layers = layers;
        this.isNearSun = isNearSun;
        this.isHollow = isHollow;
        this.asteroidView = new AsteroidView(this);
        this.stepsLeft = this.getFigures().size();
    }


    /**
     * Eggyel csökkenti a stepsLeft paraméter értékét. Ezt használja az összes figure, hogy jelezze az aszteroida felé ő már lépett.
     */
    public void stepCompleted() {
        this.stepsLeft--;
    }


    /**
     * Getter az asteroidView paraméterhez
     */
    public AsteroidView getAsteroidView() {
        return asteroidView;
    }

    /**
     * Visszaadja az aszteroidán lévő teleportkapuk listáját.
     *
     * @return
     */
    @SuppressWarnings("SpellCheckingInspection")
    public ArrayList<Portal> getPortals() {
        return portals;
    }

    /**
     * Visszaadja a benne lévő nyersanyagot.
     *
     * @return
     */
    @SuppressWarnings("SpellCheckingInspection")
    public Material getMaterial() {
        return this.material;
    }

    /**
     * Visszaadja az aszteroidán álló figurák listáját.
     *
     * @return
     */
    @SuppressWarnings("SpellCheckingInspection")
    public ArrayList<Figure> getFigures() {
        return this.figures;
    }

    /**
     * Visszaadja az aszteroida köpenyeinek számát.
     *
     * @return
     */
    @SuppressWarnings("SpellCheckingInspection")
    public int getLayers() {
        return layers;
    }

    /**
     * Az aszteroida felrobbanása.
     */
    @SuppressWarnings("SpellCheckingInspection")
    public void explode() {
        System.out.println("this asteroid exploded: " + this);
        this.material = null;
        while (figures.size() > 0) {
            if (figures.get(0) != null)
                figures.get(0).onExplosion();
        }

        for (Asteroid neighbor : getNeighbors()) {
            neighbor.removeNeighbor(this);
        }
        this.neighbors.clear();

        for (Portal portal : getPortals()) {
            if (portal.getPair() != null) {
                this.asteroidView.removeContainedView(portal.getPair().getPortalView());
                portal.getPair().getAsteroid().removePortal(portal.getPair());
                portal.getPair().setPair(null);
                portal.setPair(null);
                this.asteroidView.removeContainedView(portal.getPortalView());
                this.removePortal(portal);
            }
        }
    }

    /**
     * Az aszteroidán álló figurák listájához hozzáad egy újat.
     *
     * @param f
     */
    @SuppressWarnings("SpellCheckingInspection")
    public void addFigure(Figure f) {
        this.figures.add(f);
    }

    /**
     * Kivesz egyet az aszteroidán álló figurák listájából.
     *
     * @param f
     */
    @SuppressWarnings("SpellCheckingInspection")
    public void removeFigure(Figure f) {
        this.figures.remove(f);
    }

    /**
     * Az aszteroida fúrása.
     *
     * @return
     */
    @SuppressWarnings("SpellCheckingInspection")
    public boolean drilled() {
        if (this.layers > 0) {
            decreaseLayers();
            if (this.layers == 0) {
                if (isNearSun) {
                    material.readyToMine();
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Az aszteroida kibányászása.
     *
     * @param settler
     * @return
     */
    @SuppressWarnings("SpellCheckingInspection")
    public boolean mined(Settler settler) {
        if (this.layers == 0 && !this.isHollow && this.material != null) {
            material.addToInventory(settler);
            setIsHollow(true);
            return true;
        }
        return false;
    }

    /**
     * Beállítja az aszteroida nyersanyagát.
     *
     * @param newMaterial
     * @return
     */
    @SuppressWarnings("SpellCheckingInspection")
    public boolean setMaterial(Material newMaterial) {
        if (this.isHollow && newMaterial != null) {
            this.material = newMaterial;
            this.material.setAsteroid(this);
            isHollow = false;
            return true;
        }
        return false;
    }

    /**
     * Csökkenti az aszteroida köpenyeit eggyel.
     */
    @SuppressWarnings("SpellCheckingInspection")
    public void decreaseLayers() {
        this.layers--;
    }

    /**
     * Hozzáad egy teleportkaput az aszteroidához.
     *
     * @param p
     */
    @SuppressWarnings("SpellCheckingInspection")
    public void addPortal(Portal p) {
        this.portals.add(p);
    }


    /**
     * Kitörölök egy portált.
     *
     * @param p
     */
    public void removePortal(Portal p) {
        this.portals.remove(p);
    }

    /**
     * Hozzáad egy másik aszteroidát a szomszédai listájához.
     *
     * @param a
     */
    @SuppressWarnings("SpellCheckingInspection")
    public void addNeighbor(Asteroid a) {
        this.neighbors.add(a);
    }

    /**
     * Kitörli az egyik szomszédját az aszteroidának
     *
     * @param neighbor
     */
    @SuppressWarnings("SpellCheckingInspection")
    public void removeNeighbor(Asteroid neighbor) {
        neighbors.remove(neighbor);
    }

    /**
     * Összegyűjti az aszteroidán álló figurák Inventory-jában lévő összes nyersanyagot egy listába.
     *
     * @return
     */
    @SuppressWarnings("SpellCheckingInspection")
    public ArrayList<Material> summarizeMaterials() {
        ArrayList<Material> materials = new ArrayList<>();
        for (Figure f : this.figures) {
            materials.addAll(f.getInventory().getMaterials());
        }
        return materials;
    }

    /**
     * Kiválasztja a következő figurát, aki lépni fog.
     *
     * @return
     */
    @SuppressWarnings("SpellCheckingInspection")
    public Figure pickNextFigure() {
        for (Figure figure : figures) {
            if (!figure.getRoundFinished()) {
                return figure;
            }
        }
        return null;
    }

    /**
     * @return
     */
    public boolean noMoreStepsLeft() {
        return stepsLeft == 0;
    }

    /**
     * A következő figurát lépteti.
     */
    @SuppressWarnings("SpellCheckingInspection")
    public void invokeFigures(Group root, Rectangle2D screenBounds) {
        Figure f = pickNextFigure();
        if (this.getMap().shouldRun() && f != null) {
            f.step(root, screenBounds);
        }
    }

    /**
     * Setter a stepsLeft paraméterhez, kör elején beállítjuk mindig az azon az aszteroidán tartozkodó figurek számára.
     *
     * */
    public void setStepsLeft(int stepsLeft) {
        this.stepsLeft = stepsLeft;
    }

    /**
     * Még nem fejezte be a körét a figura.
     */
    @SuppressWarnings("SpellCheckingInspection")
    public void resetStep() {
        for (Figure f : figures) {
            f.setRoundFinished(false);
        }
    }

    /**
     * Visszaadja a szomszédos aszteroidák listáját.
     *
     * @return
     */
    @SuppressWarnings("SpellCheckingInspection")
    public ArrayList<Asteroid> getNeighbors() {
        return neighbors;
    }

    /**
     * isHollow getter
     *
     * @return
     */
    public boolean isHollow() {
        return isHollow;
    }

    /**
     * Üregesre állítja az aszteroidát.
     *
     * @param isHollow
     */
    @SuppressWarnings("SpellCheckingInspection")
    public void setIsHollow(boolean isHollow) {
        this.isHollow = isHollow;
    }

    /**
     * Ez a függvény frissíti az aktuális  pozíciót
     *
     * @param x
     * @param y
     */
    public void updatePosition(double x, double y) {
        this.position.move(new Drawable(x, y));
    }

    /**
     * Kezeli a figurákat napviharban.
     */
    @SuppressWarnings("SpellCheckingInspection")
    public void handleFigures() {
        assert (!isHollow);
        for (int i = 0; i < figures.size(); i++)
            figures.get(i).die();
    }

    /**
     * Visszaadj a map paramétert
     */
    public Map getMap() {
        return this.map;
    }

    /**
     * Getter a pozicíóhoz
     * @return
     */
    public Drawable getPosition() {
        return position;
    }

    /**
     * Setter a pocícióhoz
     */
    public void setPosition(Drawable position) {
        this.position = position;
    }

    /**
     * Visszaadja, hogy az aszteroida napközelben van-e vagy sem.
     * @return
     */
    public boolean isNearSun() {
        return isNearSun;
    }
}
