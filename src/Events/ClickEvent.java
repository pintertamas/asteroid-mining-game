package Events;

import Controllers.ClickEventHandler;
import javafx.event.Event;
import javafx.event.EventType;

public abstract class ClickEvent extends Event {
    public static final EventType<ClickEvent> CUSTOM_EVENT_TYPE = new EventType(ANY);

    public ClickEvent(EventType<? extends Event> eventType) {
        super(eventType);
    }

    public abstract void invokeHandler(ClickEventHandler handler);
}