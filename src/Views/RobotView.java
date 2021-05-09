package Views;

import Entities.Robot;
import Maths.Drawable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.image.ImageView;

import java.util.Random;

/**
 * Robotok view-ja.
 *
 */
public class RobotView extends View {
    final Robot robot;

    /**
     * Konstruktor.
     *
     * @param robot
     */
    public RobotView(Robot robot) {
        this.robot = robot;
        int robotNumber = new Random().nextInt(5) + 1;
        this.imagePath = "/figures/robots/robot" + robotNumber + ".png";
    }

    /**
     * Kirajzoláshoz megvalósítása.
     *
     * @param root
     * @param screenBounds
     */
    @Override
    public void draw(Group root, Rectangle2D screenBounds) {
        root.getChildren().remove(this.getView());
        this.getView().getChildren().clear();

        int settlerIndex = robot.getAsteroid().getAsteroidView().getContainedViews().indexOf(robot.getFigureView());
        Drawable astPos = this.robot.getAsteroid().getPosition();
        double angle = settlerIndex * (360.0 / this.robot.getAsteroid().getFigures().size());
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
