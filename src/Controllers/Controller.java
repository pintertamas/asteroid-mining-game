package Controllers;

import Entities.Figure;
import Playground.Asteroid;
import Playground.Portal;
import Views.BackgroundView;
import Views.GUIView;
import Views.MapView;
import Views.View;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;

import java.util.ArrayList;

public class Controller {
    Game game;
    Map map;
    Group root;
    ArrayList<View> views;

    public Controller(Group root) {
        this.root = root;
        game = new Game(this);
        map = new Map();
        views = new ArrayList<>();
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
            for (Figure figure : asteroid.getFigures())
                this.views.add(figure.getFigureView());
            for (Portal portal : asteroid.getPortals())
                this.views.add(portal.getPortalView());
        }
        this.views.add(new GUIView(map));
    }

    public void drawAllViews(Rectangle2D screenBounds) {
        for (View view : views)
            view.draw(root, screenBounds);
        // TODO itt adjuk hozzá a figurákat is meg a portálokat is
    }

}
