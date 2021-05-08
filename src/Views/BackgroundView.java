package Views;

import Controllers.KeyEventHandler;
import Controllers.Map;
import Events.BackgroundEvent;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;

public class BackgroundView extends View {
    Map map;

    public BackgroundView(Map map) {
        this.map = map;
        this.imagePath = "/background.png";
    }
    public void draw(Group root, Rectangle2D screenBounds) {
        ImageView imageView = ViewFunctions.image(imagePath, screenBounds.getWidth());
        imageView.setX(0);
        imageView.setY(0);

        this.getView().getChildren().clear();
        this.getView().getChildren().add(imageView);

        root.getScene().setOnKeyPressed((KeyEvent event) -> imageView.fireEvent(new BackgroundEvent()));

        root.getScene().addEventHandler(KeyEvent.KEY_PRESSED, new KeyEventHandler(root, screenBounds, map) {
            @Override
            public void handle(KeyEvent event) {
                super.handle(event);
            }
        });

        root.getScene().addEventHandler(KeyEvent.KEY_RELEASED, new KeyEventHandler(root, screenBounds, map) {
            @Override
            public void handle(KeyEvent event) {
                super.handle(event);
            }
        });

        root.getChildren().remove(this.getView());
        root.getChildren().add(imageView);
    }
}
