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
        Drawable pos = this.settler.getAsteroid().getPosition();
        if (pos.isInside(screenBounds)) {
            ImageView imageView = ViewFunctions.image(imagePath, pos.getSize());
            imageView.setX(pos.getX());
            imageView.setY(pos.getY());

            this.getView().getChildren().clear();
            this.getView().getChildren().add(imageView);

            root.getChildren().remove(this.getView()); //TODO ez itt még lehet hogy baj lesz mert az első lefutáskor még nem lesz rajta a rooton a view
            root.getChildren().add(imageView);
        }
    }

}
