package Views;

import Entities.Ufo;
import Maths.Drawable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.image.ImageView;

public class UfoView extends View {
    Ufo ufo;
    String imagePath;

    public UfoView(Ufo ufo) {
        this.ufo = ufo;
        this.imagePath = "/figures/ufos/ufo1.png";
    }

    @Override
    public void draw(Group root, Rectangle2D screenBounds) {
        root.getChildren().remove(this.getView());
        this.getView().getChildren().clear();

        Drawable pos = this.ufo.getAsteroid().getPosition();
        if (pos.isInside(screenBounds)) {
            ImageView imageView = ViewFunctions.image(imagePath, this.ufo.getAsteroid().getPosition().getSize() / 3);
            imageView.setX(pos.getX() - pos.getSize() / 6);
            imageView.setY(pos.getY() - pos.getSize() / 6);

            this.getView().getChildren().add(imageView);
            root.getChildren().add(this.getView());
        }
    }
}
