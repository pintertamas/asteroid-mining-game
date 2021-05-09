package Controllers;

import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.input.KeyEvent;

/**
 * Billentyűgombok lenyomását kezelő EventHandler osztály
 *
 */
public class KeyEventHandler implements EventHandler<KeyEvent> {
    final Map map;
    final Group root;
    final Rectangle2D screenBounds;
    final int speed = 10;

    /**
     * Konstruktor
     *
     * @param root
     * @param screenBounds
     * @param map
     */
    protected KeyEventHandler(Group root, Rectangle2D screenBounds, Map map) {
        this.map = map;
        this.root = root;
        this.screenBounds = screenBounds;
    }

    /**
     * W,S,A,D gombok lenyomásával keletkező eventek kezelése
     *
     * @param event
     */
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
