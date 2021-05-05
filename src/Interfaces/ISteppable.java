package Interfaces;

import javafx.geometry.Rectangle2D;
import javafx.scene.Group;

import java.io.IOException;

/**
 * Interfész egy körön belüli lépéshez..
 */
@SuppressWarnings("SpellCheckingInspection")
public interface ISteppable {
    void step(Group root, Rectangle2D screenBounds) throws IOException;
}
