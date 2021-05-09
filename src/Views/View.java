package Views;

import Maths.Drawable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;

/**
 * Grafikus megjelenítéshez View osztály
 *
 */
public class View {
    private final Group view;
    protected Drawable pos;
    protected String imagePath;

    /**
     * Konstruktor.
     *
     */
    View() {
        view = new Group();
    }

    protected Group getView() {
        return view;
    }

    /**
     * Rajzolófüggvény.
     *
     * @param root
     * @param screenBounds
     */
    public void draw(Group root, Rectangle2D screenBounds) {

    }

    public void setPos(Drawable pos) {
        this.pos = pos;
    }
}
