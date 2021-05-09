import Views.MenuScene;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main osztály.
 */
@SuppressWarnings("SpellCheckingInspection")
public class Main extends Application {

    /**
     * main függvény.
     *
     * @param args
     */
    @SuppressWarnings("SpellCheckingInspection")
    public static void main(String[] args) {
        launch();
    }

    /**
     * A játék elindítása.
     *
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) {
        MenuScene.loadMenu(primaryStage);
    }
}
