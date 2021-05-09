package Controllers;

import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.input.KeyEvent;

public class KeyEventHandler implements EventHandler<KeyEvent> {
    Map map;
    Group root;
    Rectangle2D screenBounds;
    int speed = 10;

    protected KeyEventHandler(Group root, Rectangle2D screenBounds, Map map) {
        this.map = map;
        this.root = root;
        this.screenBounds = screenBounds;
    }

    @Override
    public void handle(KeyEvent event) {
        if (event.getEventType() == KeyEvent.KEY_PRESSED) {
            switch (event.getCode()) {
                case W:
                    map.setGoNorth(true);
                    break;
                case S:
                    map.setGoSouth(true);
                    break;
                case A:
                    map.setGoWest(true);
                    break;
                case D:
                    map.setGoEast(true);
                    break;
            }
        }

        if (event.getEventType() == KeyEvent.KEY_RELEASED) {
            switch (event.getCode()) {
                case W:
                    map.setGoNorth(false);
                    break;
                case S:
                    map.setGoSouth(false);
                    break;
                case A:
                    map.setGoWest(false);
                    break;
                case D:
                    map.setGoEast(false);
            }
        }
        int dx = 0, dy = 0;

        if (map.isGoNorth()) dy += speed;
        if (map.isGoSouth()) dy -= speed;
        if (map.isGoEast()) dx -= speed;
        if (map.isGoWest()) dx += speed;

        map.moveAllAsteroids(root, screenBounds, dx, dy);
    }
}
