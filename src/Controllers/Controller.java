package Controllers;

import Views.AsteroidView;
import Views.BackgroundView;
import Views.View;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;

import java.util.ArrayList;

public class Controller {
    Game game;
    Map map;
    Group root;
    ArrayList<View> views;

    public Controller() {
        root = new Group();
        game = new Game(this);
        map = new Map();
        views = new ArrayList<>();
        this.views.add(new BackgroundView());
    }

    public Game getGame() {
        return game;
    }

    public Map getMap() {
        return map;
    }

    public void moveAllAsteroids(Group root, Rectangle2D screenBounds, double dx, double dy) {

    }

    public void drawAllViews(Rectangle2D screenBounds) {
        for (View view : views)
            view.draw(root, screenBounds);
    }

}
