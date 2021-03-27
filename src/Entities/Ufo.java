package Entities;

import Interfaces.IMine;
import Playground.Asteroid;
import Playground.Portal;

import java.util.ArrayList;
import java.util.Random;

/**
 * Ufó osztály, amely képes mozogni és bányászni.
 * A Figure leszármazottja.
 */
public class Ufo extends Figure implements IMine {

    /**
     * Konstruktor.
     *
     * @param asteroid
     * @param roundFinished
     */
    public Ufo(Asteroid asteroid, boolean roundFinished) {
        super(asteroid, roundFinished);
    }

    /**
     * Mozgás.
     */
    @Override
    public void move() {
        this.asteroid.removeFigure(this);
        this.asteroid = getNextDestination();
        this.asteroid.addFigure(this);
    }

    /**
     * Mozgás teleportkapun keresztül.
     *
     * @return
     */
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
    @Override
    public void step() {
        if(asteroid.getLayers()==0) {
            mine();
        }
        else {
            move();
        }
    }

    /**
     * Reagálás robbanásra.
     */
    @Override
    public void onExplosion() {

    }

    /**
     * Bányászás.
     *
     * @return
     */
    @Override
    public boolean mine() {
        return false;
    }

    /**
     * Következő aszteroida kiválasztásra, amelyre át szeretne lépni.
     *
     * @return
     */
    public Asteroid getNextDestination() {
        Random rand = new Random();
        int index = rand.nextInt(asteroid.getNeighbors().size());
        return asteroid.getNeighbors().get(index);
    }
}
