package Views;

import Maths.Drawable;
import Playground.Portal;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.image.ImageView;

/**
 * Teleportkapukhoz view.
 *
 */
public class PortalView extends View {
    final Portal portal;

    /**
     * Konstruktor.
     *
     * @param portal
     */
    public PortalView(Portal portal) {
        this.portal = portal;
        this.imagePath = "/portals/portal.png";
    }

    /**
     * Rajzolófüggvény.
     *
     * @param root
     * @param screenBounds
     */
    @Override
    public void draw(Group root, Rectangle2D screenBounds) {
        root.getChildren().remove(this.getView());
        this.getView().getChildren().clear();

        Drawable pos = this.portal.getAsteroid().getPosition();

        if (pos.isInside(screenBounds)) {
            ImageView imageView = ViewFunctions.image(imagePath, pos.getSize() / 4);
            imageView.setX(pos.getX() - pos.getSize() / 8);
            imageView.setY(pos.getY() - pos.getSize() / 8);

            this.getView().getChildren().add(imageView);
            root.getChildren().add(this.getView());
        }
    }
}
