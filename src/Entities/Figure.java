package Entities;
import Interfaces.*;
import Maths.Drawable;
import Playground.*;
import Test.TestLogger;
import Test.UserIO;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;

/**
 * Figura absztrakt osztály, amely megvalósítja a következő interfészeket:
 * ISteppable, IPortalMove, IMove.
 */
@SuppressWarnings("SpellCheckingInspection")
public abstract class Figure implements ISteppable, IPortalMove, IMove, IDrawable {
    protected Asteroid asteroid;
    protected boolean roundFinished;
    protected String imagePath;

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
    public abstract void step(Group root, Rectangle2D screenBounds) throws IOException;

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
        TestLogger.functionCalled(this, "drill", "boolean");
        if (asteroid.drilled()) {
            setRoundFinished(true);
            TestLogger.functionReturned(String.valueOf(true));
            return true;
        }
        TestLogger.functionReturned(String.valueOf(false));
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
        TestLogger.functionCalled(this, "die", "void");
        System.out.println(this + " died");
        String figureName = this.getClass().getName().replace("Entities.", "");
        UserIO.addToTemporaryOutput(figureName + "Died");
        UserIO.addToResultOutput();
        asteroid.removeFigure(this);
        this.asteroid = null;
        TestLogger.functionReturned();
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
        TestLogger.functionCalled(this, "setAsteroid", "Playground.Asteroid asteroid", "void");
        this.asteroid = asteroid;
        TestLogger.functionReturned();
    }

    /**
     * Beállítja, hogy vége van-e már a körnek.
     *
     * @param roundFinished
     */
    @SuppressWarnings("SpellCheckingInspection")
    public void setRoundFinished(boolean roundFinished) {
        TestLogger.functionCalled(this, "setRoundFinished", "boolean roundFinished", "void");
        this.roundFinished = roundFinished;
        TestLogger.functionReturned(String.valueOf(roundFinished));
    }

    @Override
    public void draw(Group root, Rectangle2D screeBounds) {

    }
}
