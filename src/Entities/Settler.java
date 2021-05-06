package Entities;

import Bills.*;
import Interfaces.IDrill;
import Interfaces.IMine;
import Materials.*;
import Playground.*;
import Views.SettlerView;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Telepes osztály, képes mozogni, fúrni és bányászni.
 * A Figure leszármazottja.
 */
@SuppressWarnings("SpellCheckingInspection")
public class Settler extends Figure implements IMine, IDrill {
    private final Inventory inventory;

    /**
     * Konstruktor.
     *
     * @param asteroid
     * @param roundFinished
     */
    @SuppressWarnings("SpellCheckingInspection")
    public Settler(Asteroid asteroid, boolean roundFinished) {
        super(asteroid, roundFinished);
        this.inventory = new Inventory();
        //-----
        inventory.addMaterial(new Iron());
        inventory.addMaterial(new Coal());
        inventory.addMaterial(new Uranium());
        inventory.addMaterial(new Ice());
        inventory.addMaterial(new Iron());
        inventory.addMaterial(new Coal());
        inventory.addMaterial(new Uranium());
        inventory.addMaterial(new Ice());
        inventory.addMaterial(new Ice());
        inventory.addMaterial(new Ice());

        inventory.addPortal(new Portal());
        inventory.addPortal(new Portal());
        inventory.addPortal(new Portal());
        //-----
        this.figureView = new SettlerView(this);
    }

    /**
     * Mozgás.
     */
    @SuppressWarnings("SpellCheckingInspection")
    public void move() {
        ArrayList<Asteroid> neighbors = this.asteroid.getNeighbors();
        if (neighbors.size() == 0) {
            return;
        }
        int neighborChoice = new Random().nextInt(asteroid.getNeighbors().size()); //TODO ezt majd a grafikus felületen kell beállítani
        this.asteroid.removeFigure(this);
        neighbors.get(neighborChoice).addFigure(this);
        this.setAsteroid(neighbors.get(neighborChoice));
        this.setRoundFinished(true);
    }

    /**
     * Bányászás.
     *
     * @return
     */
    @SuppressWarnings("SpellCheckingInspection")
    public boolean mine() {
        if (asteroid.mined(this)) {
            setRoundFinished(true);
            return true;
        }
        return false;
    }

    /**
     * Visszaadja az Inventory-t.
     *
     * @return
     */
    @SuppressWarnings("SpellCheckingInspection")
    @Override
    public Inventory getInventory() {
        return inventory;

    }

    /**
     * Teleportkapu-pár építés.
     *
     * @return
     */
    @SuppressWarnings("SpellCheckingInspection")
    public void buildPortal() {
        BillOfPortal billOfPortal = new BillOfPortal();
        if (billOfPortal.hasEnoughMaterials(this.inventory.getMaterials())
                && this.inventory.getPortals().size() < this.inventory.getPortalCapacity()) {
            billOfPortal.pay(inventory.getMaterials());
            Portal p1 = new Portal();
            Portal p2 = new Portal();
            p1.setPair(p2);
            p2.setPair(p1);
            this.inventory.addPortal(p1);
            this.inventory.addPortal(p2);
            this.setRoundFinished(true);
        }
    }

    /**
     * Robot építés.
     *
     * @return
     */
    @SuppressWarnings("SpellCheckingInspection")
    public void buildRobot() {
        BillOfRobot billOfRobot = new BillOfRobot();

        if (billOfRobot.hasEnoughMaterials(this.inventory.getMaterials())
                && this.inventory.getPortals().size() < this.inventory.getPortalCapacity()) {
            billOfRobot.pay(inventory.getMaterials());
            this.asteroid.addFigure(new Robot(this.asteroid, true));
            this.setRoundFinished(true);
        }
    }

    /**
     * Bázis építés.
     *
     * @return
     */
    @SuppressWarnings("SpellCheckingInspection")
    public void buildBase() {
        BillOfBase billOfBase = new BillOfBase();
        if (billOfBase.hasEnoughMaterials(this.asteroid.summarizeMaterials())) {
            this.setRoundFinished(true);
            this.asteroid.getMap().gameEnd(true);
            this.asteroid.getMap().switchGameState(GameState.WON);
        }
    }

    /**
     * Teleportkapu lerakása.
     *
     * @return
     */
    @SuppressWarnings("SpellCheckingInspection")
    public void putPortalDown() {
        ArrayList<Portal> portals = inventory.getPortals();
        if (portals.size() >= 1) {
            this.asteroid.addPortal(portals.get(0));
            this.inventory.removePortal(portals.get(0));
            this.setRoundFinished(true);
        }
    }

    /**
     * Nyersanyag visszarakása üres aszteroidába.
     *
     * @param m
     * @return
     */
    @SuppressWarnings("SpellCheckingInspection")
    public void putMaterialBack(Material m) {
        if (this.asteroid.setMaterial(m)) {
            this.inventory.removeMaterial(m);
            this.setRoundFinished(true);
        }
    }

    /**
     * Nyersanyag kiválasztása az Inventory-ból.
     *
     * @return
     */
    @SuppressWarnings("SpellCheckingInspection")
    public Material chooseMaterial() {
        return new Iron(); //TODO át kell írni mert az eddigi megoldást nem lehet megcsinálni
    }

    /**
     * Reagálás robbanásra: meghal.
     */
    @SuppressWarnings("SpellCheckingInspection")
    @Override
    public void onExplosion() {
        this.die();
    }

    /**
     * Lépés.
     */
    @SuppressWarnings("SpellCheckingInspection")
    @Override
    public void step(Group root, Rectangle2D screenBounds) {
        this.getAsteroid().getMap().setCurrentSettler(this);
        // TODO ide jön a cucc
        //setRoundFinished(true);
    }

    /**
     * Teleportkapun keresztüli mozgás.
     *
     * @return
     */
    @SuppressWarnings("SpellCheckingInspection")
    public boolean moveThroughPortal() {
        ArrayList<Asteroid> tmpArray = new ArrayList<>();
        if (asteroid.getPortals().size() != 0) {
            for (Portal p : asteroid.getPortals()) {
                tmpArray.add(p.getPair().getAsteroid());
            }
            int portalChoice = new Random().nextInt(asteroid.getPortals().size()); //TODO grafikus felületen kell megcsinálni
            asteroid.removeFigure(this);
            tmpArray.get(portalChoice).addFigure(this);
            setAsteroid(tmpArray.get(portalChoice));
            this.setRoundFinished(true);
            return true;
        } else {
            return false;
        }
    }
}
