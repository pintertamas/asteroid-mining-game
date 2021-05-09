package Entities;

import Controllers.Controller;
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
        this.asteroid.stepCompleted();
        this.asteroid.removeFigure(this);
        this.setAsteroid(a);
        a.addFigure(this);
        this.setRoundFinished(true);
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
        if (neighbors.size() > 0)
            return neighbors.get(new Random().nextInt(neighbors.size()));
        else return this.asteroid;
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
        if (this.asteroid.getLayers() != 0) {
            drill();
        } else if (asteroid.getNeighbors().size() > 0) {
            move();
        } else {
            asteroid.stepCompleted();
            this.setRoundFinished(true);
        }
        Controller.getController().drawAllViews(screenBounds);
        Controller.getController().redrawGUI(root, screenBounds);
    }
}
