package Controllers;

import Playground.Asteroid;
import Events.ClickEvent;
import javafx.event.EventHandler;

public abstract class ClickEventHandler implements EventHandler<ClickEvent> {
    public abstract void onAsteroidClicked(Asteroid asteroid);

    @Override
    public void handle(ClickEvent event) {
        event.invokeHandler(this);
    }
}
