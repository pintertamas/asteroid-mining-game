package Interfaces;

import java.io.IOException;

/**
 * Interfész egy körön belüli lépéshez..
 */
@SuppressWarnings("SpellCheckingInspection")
public interface ISteppable {
    void step() throws IOException;
}
