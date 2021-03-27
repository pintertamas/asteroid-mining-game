package Playground;

import Entities.Figure;
import Entities.Settler;
import Materials.Material;
import Test.TestLogger;

import java.util.ArrayList;

/**
 * Aszteroida osztály.
 */
public class Asteroid {
    private Map map;
    private final ArrayList<Asteroid> neighbors;
    private final ArrayList<Figure> figures;
    private Material material;
    private final ArrayList<Portal> portals;

    int layers;
    final boolean isNearSun;
    boolean isHollow;

    /**
     * Konstruktor.
     *
     * @param map
     * @param material
     * @param layers
     * @param isNearSun
     * @param isHollow
     */
    public Asteroid(Map map, Material material, int layers, boolean isNearSun, boolean isHollow) {
        TestLogger.functionCalled(this, "Playground.Asteroid", "void");
        this.map = map;
        this.figures = new ArrayList<>();
        this.portals = new ArrayList<>();
        this.neighbors = new ArrayList<>();
        this.material = material;
        material.setAsteroid(this);
        this.layers = layers;
        this.isNearSun = isNearSun;
        this.isHollow = isHollow;
        TestLogger.functionReturned();
    }

    /**
     * Map nélküli konstruktor.
     *
     * @param material
     * @param layers
     * @param isNearSun
     * @param isHollow
     */
    public Asteroid(Material material, int layers, boolean isNearSun, boolean isHollow) {
        TestLogger.functionCalled(this, "Playground.Asteroid", "void");
        this.figures = new ArrayList<>();
        this.portals = new ArrayList<>();
        this.neighbors = new ArrayList<>();
        this.material = material;
        material.setAsteroid(this);
        this.layers = layers;
        this.isNearSun = isNearSun;
        this.isHollow = isHollow;
        TestLogger.functionReturned();
    }

    /**
     * Visszaadja az aszteroidán lévő teleportkapuk listáját.
     *
     * @return
     */
    public ArrayList<Portal> getPortals() {
        return portals;
    }

    /**
     * Visszaadja a benne lévő nyersanyagot.
     *
     * @return
     */
    public Material getMaterial() {
        return this.material;
    }

    /**
     * Visszaadja az aszteroidán álló figurák listáját.
     *
     * @return
     */
    public ArrayList<Figure> getFigures() {
        return this.figures;
    }

    /**
     * Visszaadja az aszteroida köpenyeinek számát.
     *
     * @return
     */
    public int getLayers() {
        return layers;
    }

    /**
     * Az aszteroida felrobbanása.
     */
    public void explode() {
        TestLogger.functionCalled(this, "explode", "void");
        for (Figure figure : figures) {
            figure.onExplosion();
            if (figures.isEmpty())
                break;
        }
        for (Asteroid neighbor : getNeighbors()) {
            neighbor.removeNeighbor(this);
        }
        for (Portal portal : getPortals()) {
            portal.getPair().getAsteroid().removePortal(portal.getPair());
            portal.getPair().setPair(null);
            portal.setPair(null);
            this.removePortal(portal);
        }
        this.map.removeAsteroid(this);
        System.out.println("Asteroid exploded");
        TestLogger.functionReturned();
    }

    /**
     * Az aszteroidán álló figurák listájához hozzáad egy újat.
     *
     * @param f
     */
    public void addFigure(Figure f) {
        TestLogger.functionCalled(this, "addFigure", f.getClass().getName() + " " + f, "void");
        this.figures.add(f);
        TestLogger.functionReturned();
    }

    /**
     * Kivesz egyet az aszteroidán álló figurák listájából.
     *
     * @param f
     */
    public void removeFigure(Figure f) {
        TestLogger.functionCalled(this, "removeFigure", f.getClass().getName() + " " + f, "void");
        this.figures.remove(f);
        TestLogger.functionReturned();
    }

    /**
     * Az aszteroida fúrása.
     *
     * @return
     */
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
            return true;
        }
        TestLogger.functionReturned(String.valueOf(false));
        return false;
    }

    /**
     * Az aszteroida kibányászása.
     *
     * @param s
     * @return
     */
    public boolean mined(Settler s) {
        TestLogger.functionCalled(this, "mined", "boolean");
        if (this.layers == 0 && !this.isHollow) {
            material.addToInventory(s);
            setIsHollow(true);
            TestLogger.functionReturned(String.valueOf(true));
            return true;
        }
        TestLogger.functionReturned(String.valueOf(false));
        return false;

    }

    /**
     * Beállítja az aszteroida nyersanyagát.
     *
     * @param m
     * @return
     */
    public boolean setMaterial(Material m) {
        TestLogger.functionCalled(this, "coreChanged", "void");
        if (this.isHollow) {
            this.material = m;
            isHollow = false;
            TestLogger.functionReturned(String.valueOf(false));
            return false;
        }
        TestLogger.functionReturned(String.valueOf(true));
        return true;
    }

    /**
     * Csökkenti az aszteroida köpenyeit eggyel.
     */
    public void decreaseLayers() {
        TestLogger.functionCalled(this, "decreaseLayers", "void");
        this.layers--;
        TestLogger.functionReturned();
    }

    /**
     * Hozzáad egy teleportkaput az aszteroidához.
     *
     * @param p
     */
    public void addPortal(Portal p) {
        TestLogger.functionCalled(this, "addPortal", p.getClass().getName() + " " + p, "void");
        this.portals.add(p);
        TestLogger.functionReturned();
    }

    public void removePortal(Portal p) {
        TestLogger.functionCalled(this, "removePortal", p.getClass().getName() + " " + p, "void");
        this.portals.remove(p);
        TestLogger.functionReturned();
    }

    /**
     * Hozzáad egy másik aszteroidát a szomszédai listájához.
     *
     * @param a
     */
    public void addNeighbor(Asteroid a) {
        TestLogger.functionCalled(this, "addNeighbor", "Playground.Asteroid a", "void");
        this.neighbors.add(a);
        TestLogger.functionReturned();
    }

    /**
     * Kitörli az egyik szomszédját az aszteroidának
     *
     * @param neighbor
     */
    public void removeNeighbor(Asteroid neighbor) {
        neighbors.remove(neighbor);
    }

    /**
     * Összegyűjti az aszteroidán álló figurák Inventory-jában lévő összes nyersanyagot egy listába.
     *
     * @return
     */
    public ArrayList<Material> summarizeMaterials() {
        TestLogger.functionCalled(this, "summarizeMaterials", "HashMap<Class<?>, Integer>");
        ArrayList<Material> materials = new ArrayList<>();
        for (Figure f : this.figures) {
            materials.addAll(f.getInventory().getMaterials());
        }
        TestLogger.functionReturned(materials.toString());
        return materials;
    }

    /**
     * Kiválasztja a következő figurát, aki lépni fog.
     *
     * @return
     */
    public Figure pickNextFigure() {
        for (Figure figure : figures) {
            if (!figure.getRoundFinished())
                return figure;
        }
        return null;
    }

    /**
     * A következő figurát lépteti.
     */
    public void invokeFigures() {
        TestLogger.functionCalled(this, "invokeFigures", "void");
        Figure f = pickNextFigure();
        while (f != null) {
            System.out.println(f + " is going to step now.");
            f.step();
            f = pickNextFigure();
        }
        TestLogger.functionReturned();
    }

    /**
     * Még nem fejezte be a körét a figura.
     */
    public void resetStep() {
        TestLogger.functionCalled(this, "resetStep", "void");
        for (Figure f : figures) {
            f.setRoundFinished(false);
        }
        TestLogger.functionReturned();
    }

    /**
     * Visszaadja a szomszédos aszteroidák listáját.
     *
     * @return
     */
    public ArrayList<Asteroid> getNeighbors() {
        TestLogger.functionCalled(this, "getNeighbors", "ArrayList<Playground.Asteroid>");
        TestLogger.functionReturned(neighbors.toString());
        return neighbors;
    }

    /**
     * Üregesre állítja az aszteroidát.
     *
     * @param b
     */
    public void setIsHollow(boolean b) {
        TestLogger.functionCalled(this, "setIsHollow", "boolean b", "void");
        isHollow = b;
        TestLogger.functionReturned();
    }

    /**
     * Kezeli a figurákat napviharban.
     */
    public void handleFigures() {
        TestLogger.functionCalled(this, "handleFigures", "Entities.Figure f", "void");
        if (!isHollow)
            for (Figure f : figures)
                f.onExplosion();
        TestLogger.functionReturned();
    }
}
