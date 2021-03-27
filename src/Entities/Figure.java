package Entities;
import Interfaces.*;
import Playground.*;
import Test.TestLogger;

/**
 * Figura absztrakt osztály, amely megvalósítja a következő interfészeket:
 * ISteppable, IPortalMove, IMove.
 */
public abstract class Figure implements ISteppable, IPortalMove, IMove {
    protected Asteroid asteroid;
    protected boolean roundFinished;

    /**
     * Konstruktor.
     * @param asteroid
     * @param roundFinished
     */
    public Figure(Asteroid asteroid, boolean roundFinished) {
        this.asteroid = asteroid;
        asteroid.addFigure(this);
        this.roundFinished = roundFinished;
    }

    /**
     * Visszaadja az aszteroidát, amelyen éppen áll.
     * @return
     */
    public Asteroid getAsteroid() {
        return this.asteroid;
    }

    /**
     * Mozgás.
     */
    public abstract void move();

    /**
     * Teleportkapun keresztül mozgás.
     * @return
     */
    public abstract boolean moveThroughPortal();

    /**
     * Lépés.
     */
    public abstract void step();

    /**
     * Visszaadja, hogy az adott figura körének vége van-e már.
     * @return
     */
    public boolean getRoundFinished(){return this.roundFinished;}

    /**
     * Fúrás.
     * @return
     */
    public boolean drill() {
        TestLogger.functionCalled(this, "drill", "boolean");
        if (asteroid.drilled()) {
            setRoundFinished(true);
            TestLogger.functionReturned(String.valueOf(true));
            System.out.println("Drill done");
            return true;
        }
        System.out.println("Drill NOT done");
        TestLogger.functionReturned(String.valueOf(false));
        return false;
    }

    /**
     * Az adott figura reakciója robbanásra.
     */
    public abstract void onExplosion();

    /**
     * Figura halála.
     */
    //TODO Ez így jó?
    public void die() {
        TestLogger.functionCalled(this, "die", "void");
        //asteroid.removeFigure(this);
        //this = null;
        //System.out.println(this + " died");
        TestLogger.functionReturned();
    }

    /**
     * Visszaadja az adott figura Inventory-ját.
     * @return
     */
    public Inventory getInventory() {
        return new Inventory();
    }

    /**
     * Beállítja a hozzá tartozó aszteroidát.
     * @param asteroid
     */
    public void setAsteroid(Asteroid asteroid) {
        TestLogger.functionCalled(this, "setAsteroid", "Playground.Asteroid asteroid", "void");
        this.asteroid = asteroid;
        TestLogger.functionReturned();
    }

    /**
     * Beállítja, hogy vége van-e már a körnek.
     * @param roundFinished
     */
    public void setRoundFinished(boolean roundFinished) {
        TestLogger.functionCalled(this, "setRoundFinished", "boolean roundFinished", "void");
        this.roundFinished = roundFinished;
        TestLogger.functionReturned(String.valueOf(roundFinished));
    }
}
