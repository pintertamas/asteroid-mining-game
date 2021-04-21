package Interfaces;

import javafx.geometry.Rectangle2D;
import javafx.scene.Group;

public interface IDrawable {
    void draw(Group root, Rectangle2D screenBounds);
}
