package Views;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Opciók grafikus megjelenítése.
 *
 */
public class Options {

    static int numberOfPlayers;

    /**
     * Megjelenítőfüggvény.
     *
     * @param title
     */
    public static int display(String title) {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setWidth(250);
        window.setHeight(200);

        TextField textField = new TextField();
        Label label = new Label("Set the number of players (1-5):");
        Button setButton = new Button("Set");
        setButton.setOnAction(e -> {
            try {
                numberOfPlayers = Integer.parseInt(textField.getText());
                if(1 <=  numberOfPlayers && numberOfPlayers <= 5) {
                    window.close();
                }
            } catch (Exception ignored) {

            }

        });

        HBox containerOfSettings = new HBox(10);
        containerOfSettings.getChildren().addAll(textField, setButton);
        containerOfSettings.setAlignment(Pos.CENTER);

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(label, containerOfSettings);

        Scene scene = new Scene(layout);
        scene.getStylesheets().add("style.css");
        window.setScene(scene);
        window.showAndWait();

        return numberOfPlayers;
    }
}
