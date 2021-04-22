import Playground.Map;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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
        Group root = new Group();
        Canvas canvas = new Canvas(screenWidth, screenHeight);
        root.getChildren().add(canvas);
        Scene scene = new Scene(root);
        primaryStage.setTitle("Asteroid Mining Game");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        scene.getStylesheets().add("style.css");
        root.getStylesheets().addAll("style.css");
        Game game = new Game();
        game.run(root, screenBounds, new Map());
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
