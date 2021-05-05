package Views;

import Entities.Ufo;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;

public class UfoView extends View {
    Ufo ufo;
    public UfoView(Ufo ufo) {
        this.ufo = ufo;
    }

    @Override
    public void draw(Group root, Rectangle2D screenBounds) {
        super.draw(root, screenBounds);
    }
}
