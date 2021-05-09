package Views;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Scene, ami a játék végén jelenik meg: nyeréskor/vesztéskor.
 *
 */
public class WinLoseScene {

    /**
     * Konstruktor.
     *
     * @param primaryStage
     * @param winOrLose
     */
    public static void WinLose(Stage primaryStage, String winOrLose) {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Game Over");
        window.setWidth(250);
        window.setHeight(200);

        Label label = new Label("The settlers " + winOrLose + " the game!");
        Button setButton = new Button("Go back to the main menu");
        setButton.setOnAction(e -> {
            window.close();
            MenuScene.loadMenu(primaryStage);
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, setButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        scene.getStylesheets().add("style.css");
        window.setScene(scene);
        window.show();
    }
}
