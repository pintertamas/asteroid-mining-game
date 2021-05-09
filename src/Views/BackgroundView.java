package Views;

import Controllers.KeyEventHandler;
import Controllers.Map;
import Events.BackgroundEvent;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;

/**
 * Háttér view-ja
 */
public class BackgroundView extends View {
    final Map map;

    /**
     * Konstruktor.
     *
     * @param map
     */
    public BackgroundView(Map map) {
        this.map = map;
        this.imagePath = "/background.png";
    }

    /**
     * Kirajzolás.
     *
     * @param root
     * @param screenBounds
     */
    @Override
    public void draw(Group root, Rectangle2D screenBounds) {
        root.getChildren().remove(this.getView());
        this.getView().getChildren().clear();

        ImageView imageView = ViewFunctions.image(imagePath, screenBounds.getWidth());
        imageView.setX(0);
        imageView.setY(0);

        this.getView().getChildren().clear();
        this.getView().getChildren().add(imageView);

        root.getChildren().add(this.getView());

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
    }
}
