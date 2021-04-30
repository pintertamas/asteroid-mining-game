package Views;

import javafx.geometry.Rectangle2D;
import javafx.scene.Group;

public class View {
    private final Group view;

    View() {
        view = new Group();
    }

    public Group getView() {
        return view;
    }

    public void draw(Group root, Rectangle2D screenBounds) {

    }
}
