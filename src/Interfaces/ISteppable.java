package Interfaces;

import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;

import java.io.IOException;

/**
 * Interfész egy körön belüli lépéshez..
 */
@SuppressWarnings("SpellCheckingInspection")
public interface ISteppable {
    void step(Group root) throws IOException;
}
