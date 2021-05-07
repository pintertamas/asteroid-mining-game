package Events;

import Controllers.ClickEventHandler;
import javafx.event.EventType;

public class BackgroundEvent extends CustomEvent {

    public static final EventType<CustomEvent> BACKGROUND_KEY_PRESSED = new EventType(CUSTOM_EVENT_TYPE, "BackgroundKeyEvent");

    public BackgroundEvent() {
        super(BACKGROUND_KEY_PRESSED);
    }

    @Override
    public void invokeHandler(ClickEventHandler handler) {
        handler.onItemClicked();
    }
}
