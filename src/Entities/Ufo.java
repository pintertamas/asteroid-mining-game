package Entities;

import Controllers.Controller;
import Interfaces.IMine;
import Materials.Material;
import Playground.Asteroid;
import Playground.Portal;
import Views.UfoView;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;

import java.util.ArrayList;
import java.util.Random;

/**
 * Ufó osztály, amely képes mozogni és bányászni.
 * A Figure leszármazottja.
 */
@SuppressWarnings("SpellCheckingInspection")
public class Ufo extends Figure implements IMine {
    final ArrayList<Material> materials;

    /**
     * Konstruktor.
     *
     * @param asteroid
     * @param roundFinished
     */
    @SuppressWarnings("SpellCheckingInspection")
    public Ufo(Asteroid asteroid, boolean roundFinished) {
        super(asteroid, roundFinished);
        materials = new ArrayList<>();
        figureView = new UfoView(this);
    }

    /**
     * Mozgás.
     */
    @SuppressWarnings("SpellCheckingInspection")
    @Override
    public void move() {
        this.asteroid.stepCompleted();
        this.asteroid.removeFigure(this);
        this.asteroid = getNextDestination();
        this.asteroid.addFigure(this);
        this.setRoundFinished(true);
    }

    /**
     * Mozgás teleportkapun keresztül.
     *
     * @return
     */
    @SuppressWarnings("SpellCheckingInspection")
    @Override
    public void moveThroughPortal() {
        if (asteroid.getPortals().size() != 0) {
            this.getAsteroid().stepCompleted();
            Random rand = new Random();
            ArrayList<Portal> tmpArray = new ArrayList<>();
            for (Portal p : asteroid.getPortals()) {
                if (p.getPair().getAsteroid() != null) {
                    tmpArray.add(p);
                }
            }
            int nextAsteroid = rand.nextInt(tmpArray.size());
            asteroid.removeFigure(this);
            asteroid.getNeighbors().get(nextAsteroid).addFigure(this);
            setAsteroid(tmpArray.get(nextAsteroid).getAsteroid());
            setRoundFinished(true);
        }
    }

    /**
     * Lépés.
     */
    @SuppressWarnings("SpellCheckingInspection")
    @Override
    public void step(Group root, Rectangle2D screenBounds) {
        assert (!this.getRoundFinished());
        if (!this.asteroid.isHollow() && this.asteroid.getLayers() == 0)
            mine();
        else if (ufoCanMove() && !ufoCanMoveThroughPortal())
            move();
        else if (!ufoCanMove() && ufoCanMoveThroughPortal())
            moveThroughPortal();
        else if (ufoCanMove() && ufoCanMoveThroughPortal()) {
            Random rand = new Random();
            int number = rand.nextInt(2);
            if (number == 0) {
                move();
            } else {
                moveThroughPortal();
            }
        } else {
            asteroid.stepCompleted();
            setRoundFinished(true);
        }
        Controller.getController().drawAllViews(screenBounds);
        Controller.getController().redrawGUI(root, screenBounds);
    }

    private boolean ufoCanMove() {
        return this.asteroid.getNeighbors().size() > 0;
    }

    /**
     * Megmondja hogy tud-e lépni az ufó
     *
     * @return
     */
    private boolean ufoCanMoveThroughPortal() {
        if (this.asteroid.getPortals().size() > 0) {
            for (Portal p : this.asteroid.getPortals()) {
                if (p.getPair() != null)
                    return true;
            }
        }
        return false;
    }

    /**
     * Reagálás robbanásra.
     */
    @SuppressWarnings("SpellCheckingInspection")
    @Override
    public void onExplosion() {
        die();
    }

    /**
     * Bányászás.
     *
     * @return
     */
    @SuppressWarnings("SpellCheckingInspection")
    @Override
    public void mine() {
        this.getAsteroid().stepCompleted();
        this.materials.add(this.asteroid.getMaterial());
        this.asteroid.setIsHollow(true);
        this.setRoundFinished(true);
        if (this.asteroid.isNearSun())
            this.getAsteroid().getMaterial().readyToMine();
    }

    /**
     * Következő aszteroida kiválasztásra, amelyre át szeretne lépni.
     *
     * @return
     */
    @SuppressWarnings("SpellCheckingInspection")
    public Asteroid getNextDestination() {
        Random rand = new Random();
        int index = rand.nextInt(asteroid.getNeighbors().size());
        return asteroid.getNeighbors().get(index);
    }
}
