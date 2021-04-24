package Playground;

import Controllers.DrawFunctions;
import Entities.Figure;
import Entities.Settler;
import Interfaces.IDrawable;
import Materials.Material;
import Test.TestLogger;
import Test.UserIO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import Maths.Drawable;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Aszteroida osztály.
 */
@SuppressWarnings("SpellCheckingInspection")
public class Asteroid implements IDrawable {
    private Drawable position = new Drawable();
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
    @SuppressWarnings("SpellCheckingInspection")
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
    @SuppressWarnings("SpellCheckingInspection")
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
        TestLogger.functionCalled(this, "explode", "void");

        while (!figures.isEmpty()) {
            figures.get(0).onExplosion();
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
        UserIO.addToTemporaryOutput("asteroidExploded");
        TestLogger.functionReturned();
    }

    /**
     * Az aszteroidán álló figurák listájához hozzáad egy újat.
     *
     * @param f
     */
    @SuppressWarnings("SpellCheckingInspection")
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
    @SuppressWarnings("SpellCheckingInspection")
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
    @SuppressWarnings("SpellCheckingInspection")
    public boolean drilled() {
        TestLogger.functionCalled(this, "drilled", "boolean");
        if (this.layers > 0) {
            decreaseLayers();
            if (this.layers == 0) {
                if (isNearSun) {
                    material.readyToMine();
                }
            }
            System.out.println("Asteroid has been drilled");
            UserIO.addToTemporaryOutput("successful");
            TestLogger.functionReturned(String.valueOf(true));
            return true;
        }
        System.out.println("Drill could not be done");
        UserIO.addToTemporaryOutput("unsuccessful");
        TestLogger.functionReturned(String.valueOf(false));
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
        TestLogger.functionCalled(this, "mined", "boolean");
        if (this.layers == 0 && !this.isHollow && this.material != null) {
            material.addToInventory(settler);
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
     * @param newMaterial
     * @return
     */
    @SuppressWarnings("SpellCheckingInspection")
    public boolean setMaterial(Material newMaterial) {
        TestLogger.functionCalled(this, "coreChanged", "void");
        if (this.isHollow && newMaterial != null) {
            this.material = newMaterial;
            isHollow = false;
            TestLogger.functionReturned(String.valueOf(true));
            return true;
        }
        TestLogger.functionReturned(String.valueOf(false));
        return false;
    }

    /**
     * Csökkenti az aszteroida köpenyeit eggyel.
     */
    @SuppressWarnings("SpellCheckingInspection")
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
    @SuppressWarnings("SpellCheckingInspection")
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
    @SuppressWarnings("SpellCheckingInspection")
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
    @SuppressWarnings("SpellCheckingInspection")
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
    @SuppressWarnings("SpellCheckingInspection")
    public void invokeFigures(Group root, Rectangle2D screenBounds) throws IOException {
        TestLogger.functionCalled(this, "invokeFigures", "void");
        Figure f = pickNextFigure();
        if (this.getMap().shouldRun() && f != null) {
            System.out.println(f + " is going to step now.");
            this.getMap().setCurrentFigure(f);
            f.step(root, screenBounds);
        }
        TestLogger.functionReturned();
    }

    /**
     * Még nem fejezte be a körét a figura.
     */
    @SuppressWarnings("SpellCheckingInspection")
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
    @SuppressWarnings("SpellCheckingInspection")
    public ArrayList<Asteroid> getNeighbors() {
        TestLogger.functionCalled(this, "getNeighbors", "ArrayList<Playground.Asteroid>");
        TestLogger.functionReturned(neighbors.toString());
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
        TestLogger.functionCalled(this, "setIsHollow", "boolean b", "void");
        this.isHollow = isHollow;
        TestLogger.functionReturned();
    }

    /**
     * bu
     * Kezeli a figurákat napviharban.
     */
    @SuppressWarnings("SpellCheckingInspection")
    public void handleFigures() {
        TestLogger.functionCalled(this, "handleFigures", "void");
        assert (!isHollow);
        for (int i = 0; i < figures.size(); i++)
            figures.get(i).die();
        TestLogger.functionReturned();
    }

    /**
     * Ez a függvény rajzolja ki az aszteroida adatait
     */
    public void printAsteroidDetails() {
        System.out.println("Current asteroid properties:");
        System.out.println("\tCurrent asteroid: " + this);
        System.out.println("\tLayers: " + this.getLayers());
        if (this.isHollow()) {
            System.out.println("\tHollow: " + this.isHollow());
        } else {
            System.out.println("\tMaterial: " + this.getMaterial().getClass().toString().replace("class Materials.", ""));
        }
        if (this.getPortals().size() > 0)
            System.out.println("\tPortals: " + this.getPortals().toString());
    }

    /**
     * Ez a függvény az aszeroida állapota alapján visszaad egy image-t
     */
    public String getImage() {
        return this.layers > 0 ? "asteroids/rock.png" : this.isHollow ? "/asteroids/hollow.png" : this.material.getImagePath();
    }

    /**
     * Ez a függvény rajzolja ki az aszteroidákat
     * @param root
     * @param screenBounds
     */
    public void draw(Group root, Rectangle2D screenBounds) {
        if (getPosition().isInside(screenBounds)) {
            String img = getImage();
            ImageView imageView = DrawFunctions.image(img, this.position.getSize());
            imageView.setX(this.position.getX());
            imageView.setY(this.position.getY());
            root.getChildren().add(imageView);
        }
    }

    /**
     * Ez a függvény frissíti az aktuális  pozíciót
     * @param x
     * @param y
     */
    public void updatePosition(float x, float y) {
        this.position.move(new Drawable(x, y));
    }

    /**
     * Ez a függvény frissíti a portálokat és figurákat
     * @param root
     * @param screenBounds
     */
    public void refresh(Group root, Rectangle2D screenBounds) {
        this.draw(root, screenBounds);
        for (Portal portal : this.portals)
            portal.draw(root, screenBounds);
        for (Figure figure : this.figures)
            figure.draw(root, screenBounds);
    }

    // ez csak a teszteléshez kell, mert így a settler is tud solarstormot generálni
    public Map getMap() {
        return this.map;
    }

    /**
     * Getter a pozicíóhoz
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
}
