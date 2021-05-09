package Events;

import Controllers.ClickEventHandler;
import javafx.event.EventType;

/**
 * Aszteroidára kattintás esemény elkapása és lekezelése
 *
 */
public class AsteroidCustomEvent extends CustomEvent {

    public static final EventType<CustomEvent> CLICK_ON_ASTEROID = new EventType(CUSTOM_EVENT_TYPE, "AsteroidClickEvent");

    public AsteroidCustomEvent() {
        super(CLICK_ON_ASTEROID);
    }

    @Override
    public void invokeHandler(ClickEventHandler handler) {
        handler.onItemClicked();
    }
}
