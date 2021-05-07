package Views;

import Maths.Drawable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;

public class View {
    private final Group view;
    protected Drawable pos;
    protected String imagePath;

    View() {
        view = new Group();
    }

    protected Group getView() {
        return view;
    }

    public void draw(Group root, Rectangle2D screenBounds) {

    }

    public void setPos(Drawable pos) {
        this.pos = pos;
    }
}
