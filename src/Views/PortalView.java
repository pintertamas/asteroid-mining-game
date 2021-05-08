package Views;

import Maths.Drawable;
import Playground.Portal;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.image.ImageView;

public class PortalView extends View {
    Portal portal;

    public PortalView(Portal portal) {
        this.portal = portal;
        this.imagePath = "/portals/portal.png";
    }

    @Override
    public void draw(Group root, Rectangle2D screenBounds) {
        root.getChildren().remove(this.getView());
        this.getView().getChildren().clear();

        Drawable astPos = this.portal.getAsteroid().getPosition();

        if (pos.isInside(screenBounds)) {
            ImageView imageView = ViewFunctions.image(imagePath, astPos.getSize() / 4);
            imageView.setX(pos.getX() - pos.getSize() / 8);
            imageView.setY(pos.getY() - pos.getSize() / 8);

            this.getView().getChildren().add(imageView);
            root.getChildren().add(this.getView());
        }
    }
}
