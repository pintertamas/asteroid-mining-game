package Views;

import Entities.Robot;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;

import java.util.Random;

public class RobotView extends View {
    Robot robot;
    String imagePath;

    public RobotView(Robot robot) {
        this.robot = robot;
        int robotNumber = new Random().nextInt(5) + 1;
        this.imagePath = "/figures/robots/robot" + robotNumber + ".png";
    }

    @Override
    public void draw(Group root, Rectangle2D screenBounds) {
        super.draw(root, screenBounds);
    }
}
