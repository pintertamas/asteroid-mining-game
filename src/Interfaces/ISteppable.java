package Interfaces;

import javafx.scene.Group;

import java.io.IOException;

/**
 * Interfész egy körön belüli lépéshez..
 */
@SuppressWarnings("SpellCheckingInspection")
public interface ISteppable {
    void step(Group root) throws IOException;
}
