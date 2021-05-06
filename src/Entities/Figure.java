package Entities;
import Interfaces.*;
import Playground.*;
import Views.View;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;

/**
 * Figura absztrakt osztály, amely megvalósítja a következő interfészeket:
 * ISteppable, IPortalMove, IMove.
 */
@SuppressWarnings("SpellCheckingInspection")
public abstract class Figure implements ISteppable, IPortalMove, IMove {
    protected Asteroid asteroid;
    protected boolean roundFinished;
    protected View figureView;

    public View getFigureView() {
        return figureView;
    }

    /**
     * Konstruktor.
     *
     * @param asteroid
     * @param roundFinished
     */
    @SuppressWarnings("SpellCheckingInspection")
    public Figure(Asteroid asteroid, boolean roundFinished) {
        this.asteroid = asteroid;
        asteroid.addFigure(this);
        this.roundFinished = roundFinished;
    }

    /**
     * Visszaadja az aszteroidát, amelyen éppen áll.
     *
     * @return
     */
    @SuppressWarnings("SpellCheckingInspection")
    public Asteroid getAsteroid() {
        return this.asteroid;
    }

    /**
     * Mozgás.
     */
    @SuppressWarnings("SpellCheckingInspection")
    public abstract void move();

    /**
     * Teleportkapun keresztül mozgás.
     *
     * @return
     */
    @SuppressWarnings("SpellCheckingInspection")
    public abstract boolean moveThroughPortal();

    /**
     * Lépés.
     */
    @SuppressWarnings("SpellCheckingInspection")
    public abstract void step(Group root, Rectangle2D screenBounds);

    /**
     * Visszaadja, hogy az adott figura körének vége van-e már.
     *
     * @return
     */
    @SuppressWarnings("SpellCheckingInspection")
    public boolean getRoundFinished(){return this.roundFinished;}

    /**
     * Fúrás.
     *
     * @return
     */
    @SuppressWarnings("SpellCheckingInspection")
    public boolean drill() {
        if (asteroid.drilled()) {
            setRoundFinished(true);
            this.asteroid.stepCompleted();
            return true;
        }
        return false;
    }

    /**
     * Az adott figura reakciója robbanásra.
     */
    @SuppressWarnings("SpellCheckingInspection")
    public abstract void onExplosion();

    /**
     * Figura halála.
     */
    @SuppressWarnings("SpellCheckingInspection")
    public void die() {
        System.out.println(this + " died");
        String figureName = this.getClass().getName().replace("Entities.", "");
        asteroid.removeFigure(this);
        this.asteroid = null;
    }

    /**
     * Visszaadja az adott figura Inventory-ját.
     *
     * @return
     */
    @SuppressWarnings("SpellCheckingInspection")
    public Inventory getInventory() {
        return new Inventory();
    }

    /**
     * Beállítja a hozzá tartozó aszteroidát.
     *
     * @param asteroid
     */
    @SuppressWarnings("SpellCheckingInspection")
    public void setAsteroid(Asteroid asteroid) {
        this.asteroid = asteroid;
    }

    /**
     * Beállítja, hogy vége van-e már a körnek.
     *
     * @param roundFinished
     */
    @SuppressWarnings("SpellCheckingInspection")
    public void setRoundFinished(boolean roundFinished) {
        this.roundFinished = roundFinished;
    }
}
