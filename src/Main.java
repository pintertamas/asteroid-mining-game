import Controllers.Controller;
import Interfaces.IGameState;
import Interfaces.IPlayerNumber;
import Views.Options;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.ArrayList;

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

    @Override
    public void start(Stage primaryStage) {
        IPlayerNumber listener;

        Scene menuScene;

        Group root = new Group();
        Scene scene = new Scene(root);

        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        System.out.println(screenBounds);
        double screenWidth = screenBounds.getWidth();
        double screenHeight = screenBounds.getHeight();
        primaryStage.setX(screenBounds.getMinX());
        primaryStage.setY(screenBounds.getMinY());
        primaryStage.setWidth(screenWidth);
        primaryStage.setHeight(screenHeight);
        primaryStage.setMaximized(true);
        primaryStage.setTitle("Asteroid Mining Game");
        //primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        //primaryStage.show();
        scene.getStylesheets().add("style.css");

        Controller controller = new Controller(root);
        listener = controller.getMap();

        Button button1 = new Button("Play");
        Button button2 = new Button(("Options"));
        Button button3 = new Button("Quit");
        Font font = Font.font("verdana", FontWeight.BOLD, 36);
        button1.setFont(font);

        VBox vBox = new VBox(20);

        StackPane stackPane1 = new StackPane();
        stackPane1.getChildren().addAll(button1);
        StackPane stackPane2 = new StackPane();
        stackPane2.getChildren().addAll(button2);
        StackPane stackPane3 = new StackPane();
        stackPane3.getChildren().addAll(button3);

        Image image = new Image("background.png");
        ImageView imageView = new ImageView(image);
        Group group = new Group();
        vBox.getChildren().addAll(stackPane1, stackPane2, stackPane3);
        vBox.setPrefWidth(screenBounds.getWidth()/5);
        vBox.setPrefHeight(screenBounds.getHeight()/5);
        vBox.setLayoutX((screenBounds.getWidth()-vBox.getPrefWidth())/2);
        vBox.setLayoutY((screenBounds.getHeight()-vBox.getPrefHeight())/3);
        group.getChildren().addAll(imageView, vBox);

        menuScene = new Scene(group, 200, 200);
        menuScene.getStylesheets().add("style.css");

        button1.setOnAction(e -> {
            if (controller.getMap().getNumberOfPlayers() >= 1 && controller.getMap().getNumberOfPlayers() <= 5) {
                primaryStage.setScene(scene);
                controller.getGame().run(root, screenBounds);
            }
        });
        button2.setOnAction((e -> {
        int numOfPlayers = Options.display("Options");
        System.out.println(numOfPlayers);
            listener.changePlayerNumber(numOfPlayers);
        }));
        button3.setOnAction(e -> System.exit(0));

        button1.setId("playButton");
        button2.setId("optionsButton");
        button3.setId("quitButton");

        primaryStage.setScene(menuScene);
        primaryStage.setTitle("Asteroid mining game");
        primaryStage.show();
    }
}
