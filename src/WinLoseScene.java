import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class WinLoseScene {

    public static void WinLose(String title, String winorlose) {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setWidth(250);
        window.setHeight(200);

        Label label = new Label("The settlers " + winorlose + " the game!");
        Button setButton = new Button("Go back to the main menu");
        setButton.setOnAction(e -> {
        window.close();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, setButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }
}
