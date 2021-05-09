package Views;

import Entities.Ufo;
import Maths.Drawable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.image.ImageView;

/**
 * Ufok view-ja.
 *
 */
public class UfoView extends View {
    final Ufo ufo;

    /**
     * Konstruktor.
     *
     * @param ufo
     */
    public UfoView(Ufo ufo) {
        this.ufo = ufo;
        this.imagePath = "/figures/ufos/ufo1.png";
    }

    /**
     * Ufo kirajzolását valósítja meg.
     *
     * @param root
     * @param screenBounds
     */
    @Override
    public void draw(Group root, Rectangle2D screenBounds) {
        root.getChildren().remove(this.getView());
        this.getView().getChildren().clear();

        int settlerIndex = ufo.getAsteroid().getAsteroidView().getContainedViews().indexOf(ufo.getFigureView());
        Drawable astPos = this.ufo.getAsteroid().getPosition();
        double angle = settlerIndex * (360.0 / this.ufo.getAsteroid().getFigures().size());
        Drawable pos = new Drawable(astPos.getX() + astPos.getSize() / 2 * Math.cos(angle), astPos.getY() + astPos.getSize() / 2 * Math.sin(angle));

        if (pos.isInside(screenBounds)) {
            ImageView imageView = ViewFunctions.image(imagePath, this.ufo.getAsteroid().getPosition().getSize() / 3);
            imageView.setX(pos.getX() - pos.getSize() / 6);
            imageView.setY(pos.getY() - pos.getSize() / 6);

            this.getView().getChildren().add(imageView);
            root.getChildren().add(this.getView());
        }
    }
}
