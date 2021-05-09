package Controllers;

import Events.CustomEvent;
import javafx.event.EventHandler;

public abstract class ClickEventHandler implements EventHandler<CustomEvent> {
    public abstract void onItemClicked();

    /**
     * Lekezeli azt az eventet amikor egy objectre kattintanak.
     *
     * @param event
     */
    @Override
    public void handle(CustomEvent event) {
        event.invokeHandler(this);
    }
}
