package Entities;
import Playground.Asteroid;
import Views.RobotView;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;

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
        this.figureView = new RobotView(this);
    }

    /**
     * Mozgás.
     */
    @SuppressWarnings("SpellCheckingInspection")
    public void move() {
        Asteroid a = chooseNextDestination();
        //if the current asteroid has no neighbors the robot can't move
        if(a == null){
            System.out.println("Robot move NOT done, no neighbors");
            return;
        }
        this.asteroid.stepCompleted();
        this.asteroid.removeFigure(this);
        setAsteroid(a);
        a.addFigure(this);
        this.setRoundFinished(true);
        System.out.println("Robot move done");
    }

    /**
     * Reagálás a robbanásra: átkerül egy szomszédos aszteroidára.
     */
    @SuppressWarnings("SpellCheckingInspection")
    @Override
    public void onExplosion() {
        this.move();
    }

    /**
     * Következő aszteroida kiválasztásra, amelyre át szeretne lépni.
     *
     * @return
     */
    @SuppressWarnings("SpellCheckingInspection")
    public Asteroid chooseNextDestination() {
        ArrayList<Asteroid> neighbors = this.asteroid.getNeighbors();
        if (neighbors.size() == 0) {
            return null;
        }
        return neighbors.get(new Random().nextInt(neighbors.size()));
    }

    /**
     * A teleportkapun át való mozgás.
     *
     * @return
     */
    @SuppressWarnings("SpellCheckingInspection")
    public void moveThroughPortal() {
    }

    /**
     * Lépés.
     */
    @SuppressWarnings("SpellCheckingInspection")
    public void step(Group root, Rectangle2D screenBounds) {
        if(this.asteroid.getLayers() != 0) {
            drill();
        }
        else {
            move();
        }
        this.setRoundFinished(true);
    }
}
