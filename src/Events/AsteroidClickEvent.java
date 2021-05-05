package Events;

import Controllers.ClickEventHandler;
import Playground.Asteroid;
import javafx.event.EventType;

public class AsteroidClickEvent extends ClickEvent {

    public static final EventType<ClickEvent> CLICK_ON_ASTEROID = new EventType(CUSTOM_EVENT_TYPE, "AsteroidClickEvent");

    private final Asteroid asteroid;

    public AsteroidClickEvent(Asteroid asteroid) {
        super(CLICK_ON_ASTEROID);
        this.asteroid = asteroid;
    }

    @Override
    public void invokeHandler(ClickEventHandler handler) {
        handler.onAsteroidClicked(asteroid);
    }
}
