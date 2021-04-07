package Entities;

import Interfaces.IMine;
import Materials.Material;
import Playground.Asteroid;
import Playground.Portal;
import Test.TestLogger;

import java.lang.reflect.Array;
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
    public void step() {
        if(asteroid.getLayers()==0) {
            mine();
        }
        else {
            Random rand = new Random();
            int number = rand.nextInt(2);
            if (number == 0) {
                move();
            }
            else {
                moveThroughPortal();
            }
        }
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
        TestLogger.functionCalled(this, "mine", "boolean");
        if (!this.asteroid.isHollow() && this.asteroid.getLayers() == 0) {
            this.materials.add(this.asteroid.getMaterial());
            this.asteroid.setIsHollow(true);
            this.setRoundFinished(true);
            System.out.println("Mine done");
            TestLogger.functionReturned(String.valueOf(true));
            return true;
        }
        System.out.println("Mine NOT done");
        TestLogger.functionReturned(String.valueOf(false));
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

    public Asteroid getNextDestinationIfPortal() {
        Random rand = new Random();
        ArrayList<Integer> invalidPortalIndex = new ArrayList<>();
        int n = 0;
        for(Portal p : asteroid.getPortals()) {
            if(p.getPair().getAsteroid() != null) {
                invalidPortalIndex.add(n);
                n++;
            }
        }
        int randomNumber = -1;
        if(n != asteroid.getPortals().size()) {
            while (invalidPortalIndex.contains(randomNumber)) {
                randomNumber = rand.nextInt(asteroid.getPortals().size());
            }
            return asteroid.getPortals().get(randomNumber).getAsteroid();
        }
        return null;
    }
}
