import Playground.Map;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main osztály.
 */
@SuppressWarnings("SpellCheckingInspection")
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double screenWidth = 800;//screenBounds.getWidth();
        double screenHeight = 600;//screenBounds.getHeight();
        primaryStage.setX(screenBounds.getMinX());
        primaryStage.setY(screenBounds.getMinY());
        primaryStage.setWidth(screenWidth);
        primaryStage.setHeight(screenHeight);
        Group root = new Group();
        Canvas canvas = new Canvas(screenWidth, screenHeight);
        root.getChildren().add(canvas);
        Scene scene = new Scene(root);
        GraphicsContext gc;
        primaryStage.setTitle("Asteroid Mining Game");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        gc = canvas.getGraphicsContext2D();
        primaryStage.show();
        Game game = new Game();
        game.run(root, canvas, gc, screenBounds, new Map());
    }

    /**
     * main függvény.
     * @param args
     */
    @SuppressWarnings("SpellCheckingInspection")
    public static void main(String[] args) {
        launch();
    }
}
