package Controllers;

import Entities.Figure;
import Playground.Asteroid;
import Playground.Portal;
import Views.BackgroundView;
import Views.View;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;

import java.util.ArrayList;

/**
 * Controller osztály a grafikus elemek kezeléséhez.
 */
public class Controller {
    private static Controller singletonController = null;
    Game game;
    Map map;
    Group root;
    final ArrayList<View> views;

    /**
     * Konstruktor.
     *
     * @param root
     */
    private Controller(Group root) {
        this.root = root;
        game = new Game(this);
        map = new Map();
        views = new ArrayList<>();
    }

    /**
     * A Controller inicializálása
     *
     * @param root
     */
    public static Controller initializeController(Group root) {
        if (singletonController == null)
            singletonController = new Controller(root);
        return singletonController;
    }


    /**
     * A Controller resetelése
     *
     */
    public void resetController() {
        game = new Game(this);
        map = new Map();
        views.clear();
    }

    public void setRoot(Group root) {
        this.root = root;
    }

    public static Controller getController() {
        return singletonController;
    }

    public Game getGame() {
        return game;
    }

    public Map getMap() {
        return map;
    }


    /**
     * Minden grafikus elem view-jának összegyűjtése.
     */
    public void addAllViews() {
        this.views.add(map.getMapView());
        for (Asteroid asteroid : map.getAsteroids()) {
            this.views.add(asteroid.getAsteroidView());
            for (Portal portal : asteroid.getPortals())
                asteroid.getAsteroidView().addContainedView(portal.getPortalView());
            for (Figure figure : asteroid.getFigures())
                asteroid.getAsteroidView().addContainedView(figure.getFigureView());
        }
        this.views.add(map.getGuiView());
    }

    public void drawBackground(Rectangle2D screenBounds) {
        new BackgroundView(map).draw(root, screenBounds);
    }

    /**
     * Az összes view kirajzolása.
     *
     * @param screenBounds
     */
    public void drawAllViews(Rectangle2D screenBounds) {
        for (View view : views)
            view.draw(root, screenBounds);
    }

    /**
     * Figurák elhelyezése a közeépponthoz legközelebbi aszteroidára
     *
     * @param screenBounds
     */
    public void placeFigures(Rectangle2D screenBounds) {
        for (int i = 0; i < map.getAsteroids().get(1).getFigures().size(); i++) {
            Asteroid closest = map.findClosestAsteroidToCenter(screenBounds);
            Figure figure = map.getAsteroids().get(1).getFigures().get(i);
            figure.getAsteroid().removeFigure(figure);
            figure.setAsteroid(closest);
            closest.addFigure(figure);
        }
    }

    /**
     * A paraméterben megadott aszteroida újrarajzolása
     *
     * @param asteroid
     * @param screenBounds
     */
    public void redrawAsteroid(Asteroid asteroid, Rectangle2D screenBounds) {
        this.views.remove(asteroid.getAsteroidView());
        this.views.add(asteroid.getAsteroidView());
        asteroid.getAsteroidView().draw(root, screenBounds);
    }

    /**
     * Adott aszteroida tartalmazott view-jainak újrarajozolása
     *
     * @param asteroid
     * @param screenBounds
     */
    public void redrawAsteroidContainedViews(Asteroid asteroid, Rectangle2D screenBounds) {
        this.views.removeAll(asteroid.getAsteroidView().getContainedViews());
        this.views.addAll(asteroid.getAsteroidView().getContainedViews());
        asteroid.getAsteroidView().drawContainedViews(root, screenBounds);
    }

    /**
     * kamera mozgatása Settlerhez
     *
     * @param root
     * @param screenBounds
     */
    public static void moveToSettler(Group root, Rectangle2D screenBounds) {
        double xDistance = screenBounds.getWidth() / 2 - getController().getMap().getCurrentSettler().getAsteroid().getPosition().getX();
        double yDistance = screenBounds.getHeight() / 2 - getController().getMap().getCurrentSettler().getAsteroid().getPosition().getY();
        getController().getMap().moveAllAsteroids(root, screenBounds, xDistance, yDistance);
        Controller.getController().drawAllViews(screenBounds);
    }

    /**
     * GUI újrarajzolása
     *
     * @param root
     * @param screenBounds
     */
    public void redrawGUI(Group root, Rectangle2D screenBounds) {
        map.getGuiView().draw(root, screenBounds);
    }
}
