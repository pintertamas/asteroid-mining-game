package Controllers;

import Events.CustomEvent;
import javafx.event.EventHandler;

public abstract class ClickEventHandler implements EventHandler<CustomEvent> {
    public abstract void onAsteroidClicked();

    @Override
    public void handle(CustomEvent event) {
        event.invokeHandler(this);
    }
}
