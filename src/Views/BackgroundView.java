package Views;

import Controllers.Controller;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.image.ImageView;

public class BackgroundView extends View {
    Controller controller;
    boolean moving, goNorth, goSouth, goEast, goWest;

    public void handleMouseActions(Group root, Rectangle2D screenBounds) {
        int speed = 30;

        root.getScene().setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP:
                    goNorth = true;
                    break;
                case DOWN:
                    goSouth = true;
                    break;
                case LEFT:
                    goWest = true;
                    break;
                case RIGHT:
                    goEast = true;
                    break;
                case SHIFT:
                    moving = true;
                    break;
            }
        });

        root.getScene().setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case UP:
                    goNorth = false;
                    break;
                case DOWN:
                    goSouth = false;
                    break;
                case LEFT:
                    goWest = false;
                    break;
                case RIGHT:
                    goEast = false;
                    break;
                case SHIFT:
                    moving = false;
                    break;
            }
        });

        int dx = 0, dy = 0;

        if (goNorth) dy += speed;
        if (goSouth) dy -= speed;
        if (goEast) dx -= speed;
        if (goWest) dx += speed;

        controller.moveAllAsteroids(root, screenBounds, dx, dy);
    }

    public void draw(Group root, Rectangle2D screenBounds) {
        String img = "/background.png";
        ImageView imageView = ViewFunctions.image(img, screenBounds.getHeight());
        imageView.setX(0);
        imageView.setY(0);

        this.getView().getChildren().clear();
        this.getView().getChildren().add(imageView);

        root.getChildren().remove(this.getView());
        root.getChildren().add(imageView);
    }
}
