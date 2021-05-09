package Views;

import Controllers.Controller;
import Interfaces.IPlayerNumber;
import Playground.GameState;
import Views.Options;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * Scene ami a menüt tartalmazza.
 *
 */
public class MenuScene {

    /**
     * Menü betöltése
     *
     * @param primaryStage
     */
    public static void loadMenu(Stage primaryStage) {
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
        primaryStage.setFullScreen(true);
        primaryStage.setTitle("Asteroid Mining Game");
        primaryStage.setResizable(false);
        scene.getStylesheets().add("style.css");

        Controller controller = Controller.initializeController(root);
        listener = controller.getMap();

        Button playButton = new Button("Play");
        Button optionsButton = new Button(("Options"));
        Button quitButton = new Button("Quit");
        Font font = Font.font("verdana", FontWeight.BOLD, 36);
        playButton.setFont(font);

        VBox vBox = new VBox(20);

        StackPane playButtonStack = new StackPane();
        playButtonStack.getChildren().addAll(playButton);
        StackPane optionsButtonStack = new StackPane();
        optionsButtonStack.getChildren().addAll(optionsButton);
        StackPane quitButtonStack = new StackPane();
        quitButtonStack.getChildren().addAll(quitButton);

        Image image = new Image("background.png");
        ImageView imageView = new ImageView(image);
        Group group = new Group();
        vBox.getChildren().addAll(playButtonStack, optionsButtonStack, quitButtonStack);
        vBox.setPrefWidth(screenBounds.getWidth() / 5);
        vBox.setPrefHeight(screenBounds.getHeight() / 5);
        vBox.setLayoutX((screenBounds.getWidth() - vBox.getPrefWidth()) / 2);
        vBox.setLayoutY((screenBounds.getHeight() - vBox.getPrefHeight()) / 3);
        group.getChildren().addAll(imageView, vBox);

        menuScene = new Scene(group, 200, 200);
        menuScene.getStylesheets().add("style.css");

        playButton.setOnAction(e -> {
            if (controller.getMap().getNumberOfPlayers() >= 1 && controller.getMap().getNumberOfPlayers() <= 5) {
                System.out.println(controller);
                controller.getGame().changeGameState(GameState.LOAD);
                primaryStage.setScene(scene);
                primaryStage.setFullScreen(true);
                controller.getGame().run(primaryStage, root, screenBounds);
            }
        });
        optionsButton.setOnAction((e -> {
            int numOfPlayers = Options.display("Options");
            listener.changePlayerNumber(numOfPlayers);
        }));
        quitButton.setOnAction(e -> System.exit(0));

        playButton.setId("playButton");
        optionsButton.setId("optionsButton");
        quitButton.setId("quitButton");

        primaryStage.setScene(menuScene);
        primaryStage.setTitle("Asteroid mining game");
        primaryStage.show();
    }
}
