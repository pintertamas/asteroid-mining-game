package Entities;
import Playground.Asteroid;
import Test.TestLogger;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.Random;

/**
 * Robot osztály, amely képes mozogni és fúrni.
 * A Figure leszármazottja.
 */
@SuppressWarnings("SpellCheckingInspection")
public class Robot extends Figure {

    /**
     * Konstruktor.
     *
     * @param asteroid
     * @param roundFinished
     */
    @SuppressWarnings("SpellCheckingInspection")
    public Robot(Asteroid asteroid, boolean roundFinished) {
        super(asteroid, roundFinished);
        int robotNumber = new Random().nextInt(5) + 1;
        this.imagePath = "/figures/robots/robot" + robotNumber + ".png";
    }

    /**
     * Mozgás.
     */
    @SuppressWarnings("SpellCheckingInspection")
    public void move() {
        TestLogger.functionCalled(this, "move", "void");
        Asteroid a = chooseNextDestination();
        //if the current asteroid has no neighbors the robot can't move
        if(a == null){
            System.out.println("Robot move NOT done, no neighbors");
            TestLogger.functionReturned();
            this.setRoundFinished(true);
            return;
        }
        this.asteroid.removeFigure(this);
        setAsteroid(a);
        a.addFigure(this);
        this.setRoundFinished(true);
        System.out.println("Robot move done");
        TestLogger.functionReturned();
    }

    /**
     * Reagálás a robbanásra: átkerül egy szomszédos aszteroidára.
     */
    @SuppressWarnings("SpellCheckingInspection")
    @Override
    public void onExplosion() {
        TestLogger.functionCalled(this, "onExplosion", "void");
        this.move();
        TestLogger.functionReturned();
    }

    /**
     * Következő aszteroida kiválasztásra, amelyre át szeretne lépni.
     *
     * @return
     */
    @SuppressWarnings("SpellCheckingInspection")
    public Asteroid chooseNextDestination() {
        TestLogger.functionCalled(this, "chooseNextDestination", "Asteroid");
        ArrayList<Asteroid> neighbors = this.asteroid.getNeighbors();
        if (neighbors.size() == 0) {
            return null;
        }
        int nextDestination = 0;
        Random rand = new Random();
        nextDestination = rand.nextInt(neighbors.size());
        Asteroid nextAsteroid = neighbors.get(nextDestination);
        TestLogger.functionReturned(nextAsteroid.toString());
        return nextAsteroid;
    }

    /**
     * A teleportkapun át való mozgás.
     *
     * @return
     */
    @SuppressWarnings("SpellCheckingInspection")
    public boolean moveThroughPortal() {
        return false;
    }

    /**
     * Lépés.
     */
    @SuppressWarnings("SpellCheckingInspection")
    public void step(Group root, Rectangle2D screenBounds) {
        TestLogger.functionCalled(this, "step", "void");
        if(this.asteroid.getLayers() == 0) {
            move();
        }
        else {
            drill();
        }
        TestLogger.functionReturned();
    }
}
