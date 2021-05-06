package Views;

import Entities.Settler;
import Maths.Drawable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.image.ImageView;

import java.util.Random;

public class SettlerView extends View {
    Settler settler;
    String imagePath;

    public SettlerView(Settler settler) {
        this.settler = settler;
        int settlerNumber = new Random().nextInt(5) + 1;
        this.imagePath = "/figures/spaceships/spaceship" + settlerNumber + ".png";
    }

    @Override
    public void draw(Group root, Rectangle2D screenBounds) {
        root.getChildren().remove(this.getView());
        this.getView().getChildren().clear();

        Drawable pos = this.settler.getAsteroid().getPosition();
        if (pos.isInside(screenBounds)) {
            ImageView imageView = ViewFunctions.image(imagePath, this.settler.getAsteroid().getPosition().getSize() / 3);
            imageView.setX(pos.getX() - pos.getSize() / 6);
            imageView.setY(pos.getY() - pos.getSize() / 6);

            this.getView().getChildren().add(imageView);
            root.getChildren().add(this.getView());
        }
    }

}
