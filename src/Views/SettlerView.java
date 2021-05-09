package Views;

import Entities.Settler;
import Maths.Drawable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.image.ImageView;

import java.util.Random;

/**
 * Telepesek view-ja
 *
 */
public class SettlerView extends View {
    final Settler settler;

    /**
     * Konstruktor.
     *
     * @param settler
     */
    public SettlerView(Settler settler) {
        this.settler = settler;
        int settlerNumber = new Random().nextInt(5) + 1;
        this.imagePath = "/figures/spaceships/spaceship" + settlerNumber + ".png";
    }

    /**
     * Telepes kirajzolása.
     *
     * @param root
     * @param screenBounds
     */
    @Override
    public void draw(Group root, Rectangle2D screenBounds) {
        root.getChildren().remove(this.getView());
        this.getView().getChildren().clear();

        int settlerIndex = settler.getAsteroid().getAsteroidView().getContainedViews().indexOf(settler.getFigureView());
        Drawable astPos = this.settler.getAsteroid().getPosition();
        double angle = settlerIndex * (360.0 / this.settler.getAsteroid().getFigures().size());
        Drawable pos = new Drawable(astPos.getX() + astPos.getSize() / 2 * Math.cos(angle), astPos.getY() + astPos.getSize() / 2 * Math.sin(angle));

        if (pos.isInside(screenBounds)) {
            ImageView imageView = ViewFunctions.image(imagePath, astPos.getSize() / 3);
            imageView.setX(pos.getX() - pos.getSize() / 6);
            imageView.setY(pos.getY() - pos.getSize() / 6);

            this.getView().getChildren().add(imageView);
            root.getChildren().add(this.getView());
        }
    }
}
