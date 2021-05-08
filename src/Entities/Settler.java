package Entities;

import Bills.*;
import Interfaces.IDrill;
import Interfaces.IMine;
import Materials.*;
import Maths.Drawable;
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
        this.figureView = new SettlerView(this);
    }

    private Asteroid nextAsteroid() {
        return this.getAsteroid().getMap().getCurrentAsteroid();
    }

    /**
     * Mozgás.
     */
    @SuppressWarnings("SpellCheckingInspection")
    public void move() {
        ArrayList<Asteroid> neighbors = this.asteroid.getNeighbors();
        if (neighbors.size() == 0 || nextAsteroid() == this.getAsteroid() || !neighbors.contains(nextAsteroid())) {
            System.out.println("return, wont move");
            return;
        }
        this.asteroid.stepCompleted();
        Asteroid neighborChoice = this.asteroid.getMap().getCurrentAsteroid();
        this.asteroid.removeFigure(this);
        neighborChoice.addFigure(this);
        this.setAsteroid(neighborChoice);
        this.setRoundFinished(true);
    }

    /**
     * Bányászás.
     *
     * @return
     */
    @SuppressWarnings("SpellCheckingInspection")
    public void mine() {
        if (this.inventory.getMaterialCapacity() > this.inventory.getMaterials().size())
            if (asteroid.mined(this)) {
                asteroid.getMaterial().readyToMine();
                this.asteroid.stepCompleted();
                setRoundFinished(true);
            }
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
            this.asteroid.stepCompleted();
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
            this.asteroid.stepCompleted();
            billOfRobot.pay(inventory.getMaterials());
            Robot robot = new Robot(this.asteroid, false);
            asteroid.getAsteroidView().addContainedView(robot.getFigureView());
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
            this.asteroid.stepCompleted();
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
            this.getAsteroid().stepCompleted();
            Portal p = portals.get(0);
            p.setAsteroid(asteroid);
            asteroid.addPortal(p);
            asteroid.getAsteroidView().addContainedView(p.getPortalView());
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
        if (m == null)
            return;
        if (this.asteroid.setMaterial(m)) {
            this.getAsteroid().getMaterial().readyToMine();
            this.getAsteroid().stepCompleted();
            this.inventory.removeMaterial(m);
            this.setRoundFinished(true);
            this.inventory.setSelectedMaterial(null);
        }
    }

    /**
     * Reagálás robbanásra: meghal.
     */
    @SuppressWarnings("SpellCheckingInspection")
    @Override
    public void onExplosion() {
        this.die();
    }

    public void moveToSettler(Group root, Rectangle2D screenBounds) {
        double xDistance = screenBounds.getWidth() / 2 - this.asteroid.getPosition().getX();
        double yDistance = screenBounds.getHeight() / 2 - this.asteroid.getPosition().getY();
        this.getAsteroid().getMap().moveAllAsteroids(root, screenBounds, xDistance, yDistance);
    }

    /**
     * Lépés.
     */
    @SuppressWarnings("SpellCheckingInspection")
    @Override
    public void step(Group root, Rectangle2D screenBounds) {
        if (asteroid.getMap().getCurrentSettler() != this) {
            if (this.getAsteroid().isNearSun())
                for (Material material : inventory.getMaterials()) {
                    material.setNearSunCount(material.getNearSunCount() + 1);
                }
            this.getAsteroid().getMap().setCurrentSettler(this);
            moveToSettler(root, screenBounds);
        }
    }

    /**
     * Teleportkapun keresztüli mozgás.
     *
     * @return
     */
    @SuppressWarnings("SpellCheckingInspection")
    public void moveThroughPortal() {
        if (asteroid.getPortals().size() != 0) {
            for (Portal portal : getAsteroid().getPortals()) {
                if (portal.getPair().getAsteroid() == this.getAsteroid().getMap().getCurrentAsteroid()) {
                    this.asteroid.stepCompleted();
                    Asteroid neighborChoice = this.asteroid.getMap().getCurrentAsteroid();
                    this.asteroid.removeFigure(this);
                    neighborChoice.addFigure(this);
                    this.setAsteroid(neighborChoice);
                    this.setRoundFinished(true);
                }
            }
        }
    }
}
