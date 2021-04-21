import Interfaces.IGameState;
import Playground.GameState;
import Playground.Map;
import Test.TestLogger;
import Test.UserIO;
import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.Scanner;
import java.util.UUID;

public class Game implements IGameState {
    private GameState gameState = GameState.LOAD;

    @Override
    public void changeGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public void run(Group root, Rectangle2D screenBounds, Map map) {
        //double screenWidth = screenBounds.getWidth();
        //double screenHeight = screenBounds.getHeight();
        //MainMenu menu = new MainMenu();
        // itt lehet hozzáadogatni a listenereket amikor kellenek majd
        new AnimationTimer() {
            @Override
            public void handle(long l) {
                switch (gameState) {
                    //case menu -> menu.showMenu(root, canvas, gc);
                    case LOAD -> {
                        try {
                            init(map);
                            map.placeAsteroids(screenBounds);
                            map.drawWholeMap(root, screenBounds);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        gameState = GameState.IN_PROGRESS;
                    }
                    case IN_PROGRESS -> {
                        drawBackground(root, screenBounds);
                        map.handleMouseActions(root, screenBounds);
                        try {
                            inProgress(root, map);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        drawPlayground(root, screenBounds);
                    }
                    case LOST -> {
                        try {
                            gameEnd();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        map.gameEnd(false);
                        if (UserIO.readFromFile())
                            UserIO.closeFile();
                    }
                    case WON -> {
                        try {
                            gameEnd();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        map.gameEnd(true);
                        if (UserIO.readFromFile())
                            UserIO.closeFile();
                    }
                }
            }
        }.start();
    }

    public void init(Map map) throws IOException {
        Scanner in = new Scanner(System.in);

        boolean showTestLogger = false;
        if (UserIO.isConsole()) {
            System.out.println("Would you like to show TestLogger messages?  (1 = Yes)");
            showTestLogger = in.nextLine().equals("1");
        }
        TestLogger.setShow(showTestLogger);

        boolean showInput = false;
        if (UserIO.isConsole()) {
            System.out.println("Would you like to show input choices? (1 = Yes)");
            showInput = in.nextLine().equals("1");
        }
        UserIO.setShowInput(showInput);

        boolean automaticSetup = true;
        if (UserIO.isConsole()) {
            System.out.println("Would you like to generate the map automatically? (1 = Yes)");
            automaticSetup = in.nextLine().equals("1");
        }
        UserIO.setIsAutomatic(automaticSetup);

        boolean shouldCheckIfWinnable = true;
        if (UserIO.isConsole()) {
            System.out.println("Should I check whether the game is winnable or not? (1 = Yes)");
            shouldCheckIfWinnable = in.nextLine().charAt(0) == '1';
        }
        UserIO.setCheckIfWinnable(shouldCheckIfWinnable);

        boolean loadFromFiles = false;
        if (!UserIO.isAutomatic()) {
            System.out.println("Would you like to load initializations by hand or from files? (1 = From files)");
            if (in.nextLine().charAt(0) == '1') {
                UserIO.setReadFromFile(true);
                loadFromFiles = true;
            }
            if (loadFromFiles) {
                UserIO.choosePath(UserIO.Phase.INIT);
            }
        }
        if (UserIO.isConsole())
            UserIO.openFile();


        map.addStateListener(this);
        map.initGame();

        if (!UserIO.readFromFile() && !UserIO.isAutomatic()) {
            System.out.println("Would you like to save this input as a custom init file? (1 = Yes)");
            if (in.nextLine().charAt(0) == '1') {
                System.out.println("What file name would you like to call it?");
                String filename = UserIO.readString();
                UserIO.saveCustomIO(UserIO.Phase.INIT, filename);
            }
        }

        UserIO.clear();

        if (UserIO.isConsole()) {
            System.out.println("Would you like to load test cases manually or from files? (1 = From files)");
            if (UserIO.readString().charAt(0) == '1') {
                UserIO.setReadFromFile(true);
                UserIO.clearTemporaryInput();
                UserIO.choosePath(UserIO.Phase.TEST);
            } else {
                UserIO.clearTemporaryInput();
                UserIO.setReadFromFile(false);
            }
            UserIO.openFile();
        }
    }

    private void gameEnd() throws IOException {
        UserIO.addToResultOutput();
        String resultFileName;
        if (UserIO.readFromFile())
            resultFileName = UserIO.getPathName();
        else resultFileName = "CustomInput_" + UUID.randomUUID() + ".txt";
        UserIO.saveCustomIO(UserIO.Phase.RESULT, resultFileName);
    }

    @SuppressWarnings("SpellCheckingInspection")
    private void inProgress(Group root, Map m) throws IOException {
        //A játék menete
        m.reset();
        //m.setupRound(root);
        if (m.shouldRun())
            System.out.println("\n---------ROUND ENDED----------\n");
    }

    public void drawBackground(Group root, Rectangle2D screenBounds) {
        root.getChildren().removeIf((o) -> root.getChildren().get(0) != o);
        if (root.getChildren().size() == 1) {
            Image image = new Image("/background.png", screenBounds.getWidth(), screenBounds.getHeight(), true, true);
            ImageView imageView = new ImageView(image);
            imageView.setX(0);
            imageView.setY(0);
            root.getChildren().add(imageView);
        }
    }

    private void drawSideBar(Group root, Rectangle2D screenBounds) {
        double width = screenBounds.getWidth() / 5;
        double height = screenBounds.getHeight();
        double posX = 4 * screenBounds.getWidth() / 5;
        double posY = 0;

        VBox mainContainer = new VBox();
        mainContainer.setAlignment(Pos.TOP_CENTER);
        mainContainer.setLayoutX(posX);
        mainContainer.setLayoutY(posY);
        mainContainer.setPrefWidth(width);
        mainContainer.setPrefHeight(height);
        mainContainer.setBackground(new Background(new BackgroundFill(Color.rgb(100, 250, 250), CornerRadii.EMPTY, Insets.EMPTY)));

        Button btn = new Button();
        btn.setLayoutX(mainContainer.getLayoutX());
        btn.setLayoutY(mainContainer.getLayoutY());
        btn.setStyle("-fx-text-inner-color: red;");
        //mainContainer.getChildren().add(btn);
        root.getChildren().add(mainContainer);


    }

    /**
     * @param root
     */
    private void drawPlayground(Group root, Rectangle2D screenBounds) {
        drawSideBar(root, screenBounds);
    }
}
