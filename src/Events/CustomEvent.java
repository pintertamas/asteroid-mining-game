package Events;

import Controllers.ClickEventHandler;
import javafx.event.Event;
import javafx.event.EventType;


/**
 * Saját események készítéséhez absztrakt osztály
 *
 */
public abstract class CustomEvent extends Event {
    public static final EventType<CustomEvent> CUSTOM_EVENT_TYPE = new EventType(ANY);

    public CustomEvent(EventType<? extends Event> eventType) {
        super(eventType);
    }

    public abstract void invokeHandler(ClickEventHandler handler);
}