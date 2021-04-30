package Controllers;

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

    public Controller(Group root) {
        this.root = root;
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

    public void moveAllAsteroids(Group root, Rectangle2D screenBounds, float dx, float dy) {
        this.getMap().moveAllAsteroids(root, screenBounds, dx, dy);
    }

    public void drawAllViews(Rectangle2D screenBounds) {
        for (View view : views)
            view.draw(root, screenBounds);
    }

}
