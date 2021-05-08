package Controllers;

import Entities.Figure;
import Playground.Asteroid;
import Playground.Portal;
import Views.BackgroundView;
import Views.View;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;

import java.util.ArrayList;

public class Controller {
    private static Controller singletonController = null;
    Game game;
    Map map;
    Group root;
    ArrayList<View> views;

    private Controller(Group root) {
        this.root = root;
        game = new Game(this);
        map = new Map();
        views = new ArrayList<>();
    }

    public static Controller initializeController(Group root) {
        if (singletonController == null)
            singletonController = new Controller(root);
        return singletonController;
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

    public void addAllViews() {
        this.views.add(new BackgroundView(map));
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

    public void drawAllViews(Rectangle2D screenBounds) {
        for (View view : views)
            view.draw(root, screenBounds);
    }

    public void placeFigures(Rectangle2D screenBounds) {
        for (int i = 0; i < map.getAsteroids().get(1).getFigures().size(); i++) {
            Asteroid closest = map.findClosestAsteroidToCenter(screenBounds);
            Figure figure = map.getAsteroids().get(1).getFigures().get(i);
            figure.getAsteroid().removeFigure(figure);
            figure.setAsteroid(closest);
            closest.addFigure(figure);
        }
    }
}
