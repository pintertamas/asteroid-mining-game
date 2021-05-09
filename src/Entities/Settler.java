package Entities;

import Bills.*;
import Controllers.Controller;
import Interfaces.IDrill;
import Interfaces.IMine;
import Materials.*;
import Playground.*;
import Views.SettlerView;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;

import java.util.ArrayList;

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
        this.asteroid.removeFigure(this);
        nextAsteroid().addFigure(this);
        this.setAsteroid(nextAsteroid());
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
                setRoundFinished(true);
                this.asteroid.stepCompleted();
                if (this.asteroid.isNearSun())
                    asteroid.getMaterial().readyToMine();
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
            billOfPortal.pay(inventory.getMaterials());
            Portal p1 = new Portal();
            Portal p2 = new Portal();
            p1.setPair(p2);
            p2.setPair(p1);
            this.inventory.addPortal(p1);
            this.inventory.addPortal(p2);
            this.asteroid.stepCompleted();
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
            if (this.asteroid.isNearSun())
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
            Controller.moveToSettler(root, screenBounds);
        }
    }

    /**
     * Teleportkapun keresztüli mozgás.
     *
     * @return
     */
    @SuppressWarnings("SpellCheckingInspection")
    public void moveThroughPortal() {
        int portalCount = asteroid.getPortals().size();
        if (portalCount != 0) {
            Asteroid neighborChoice = null;
            if (portalCount == 1 && asteroid.getPortals().get(0).getPair() != null) {
                this.asteroid.stepCompleted();
                neighborChoice = asteroid.getPortals().get(0).getPair().getAsteroid();
            } else {
                for (Portal portal : getAsteroid().getPortals()) {
                    if (portal.getPair().getAsteroid() == this.getAsteroid().getMap().getCurrentAsteroid()) {
                        this.asteroid.stepCompleted();
                        neighborChoice = this.asteroid.getMap().getCurrentAsteroid();
                    }
                }
            }
            this.asteroid.removeFigure(this);
            assert neighborChoice != null;
            neighborChoice.addFigure(this);
            this.setAsteroid(neighborChoice);
            this.setRoundFinished(true);
        }
    }
}
