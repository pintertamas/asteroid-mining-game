import Controllers.Controller;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * Main osztály.
 */
@SuppressWarnings("SpellCheckingInspection")
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        System.out.println(screenBounds);
        double screenWidth = screenBounds.getWidth();
        double screenHeight = screenBounds.getHeight();
        primaryStage.setX(screenBounds.getMinX());
        primaryStage.setY(screenBounds.getMinY());
        primaryStage.setWidth(screenWidth);
        primaryStage.setHeight(screenHeight);
        primaryStage.setMaximized(true);
        Group root = new Group();

        Scene scene = new Scene(root);
        primaryStage.setTitle("Asteroid Mining Controllers.Game");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        scene.getStylesheets().add("style.css");

        Controller controller = new Controller(root);
        controller.getGame().run(root, screenBounds);
    }

    /**
     * main függvény.
     *
     * @param args
     */
    @SuppressWarnings("SpellCheckingInspection")
    public static void main(String[] args) {
        launch();
    }
}
