package Entities;

import Interfaces.IMine;
import Materials.Material;
import Playground.Asteroid;
import Playground.Portal;
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
    ArrayList<Material> materials;

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
        this.imagePath = "/figures/ufos/ufo1.png";
    }

    /**
     * Mozgás.
     */
    @SuppressWarnings("SpellCheckingInspection")
    @Override
    public void move() {
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
    public boolean moveThroughPortal() {
        if(asteroid.getPortals().size() != 0) {
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
        else {
            return false;
        }
        return false;
    }

    /**
     * Lépés.
     */
    @SuppressWarnings("SpellCheckingInspection")
    @Override
    public void step(Group root, Rectangle2D screenBounds) {
        if(asteroid.getLayers()==0) {
            mine();
        }
        else if (ufoCanStep()){
            Random rand = new Random();
            int number = rand.nextInt(2);
            if (number == 0) {
                move();
            }
            else {
                moveThroughPortal();
            }
        }
        this.setRoundFinished(true);
    }

    /**
     * Megmondja hogy tud-e lépni az ufó
     * @return
     */
    private boolean ufoCanStep() {
        if (this.asteroid.getNeighbors().size() > 0)
            return true;
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
    public boolean mine() {
        if (!this.asteroid.isHollow() && this.asteroid.getLayers() == 0) {
            this.materials.add(this.asteroid.getMaterial());
            this.asteroid.setIsHollow(true);
            this.setRoundFinished(true);
            System.out.println("Ufo Mine done");
            return true;
        }
        System.out.println("Ufo Mine NOT done");
        return false;
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
