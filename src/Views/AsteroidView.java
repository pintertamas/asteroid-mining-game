package Views;

import Playground.Asteroid;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class AsteroidView extends View {
    private final Asteroid asteroid;
    private ArrayList<View> containedViews;

    public AsteroidView(Asteroid asteroid) {
        this.asteroid = asteroid;
    }

    public String getImage() {
        if (asteroid.getLayers() > 0)
            return "asteroids/rock.png";
        if (asteroid.isHollow())
            return "/asteroids/hollow.png";
        else return asteroid.getMaterial().getMaterialView().getImagePath();
    }

    public void draw(Group root, Rectangle2D screenBounds) {
        if (asteroid.getPosition().isInside(screenBounds)) {
            String img = getImage();
            ImageView imageView = ViewFunctions.image(img, asteroid.getPosition().getSize());
            imageView.setX(asteroid.getPosition().getX());
            imageView.setY(asteroid.getPosition().getY());

            this.getView().getChildren().clear();
            this.getView().getChildren().add(imageView);

            root.getChildren().remove(this.getView()); //TODO ez itt még lehet hogy baj lesz mert az első lefutáskor még nem lesz rajta a rooton a view
            root.getChildren().add(imageView);
        }
    }
}
