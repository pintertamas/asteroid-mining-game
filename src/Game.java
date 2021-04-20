import Interfaces.IGameState;
import Playground.GameState;
import Playground.Map;
import Test.TestLogger;
import Test.UserIO;
import javafx.animation.AnimationTimer;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.io.IOException;
import java.util.Scanner;
import java.util.UUID;

public class Game implements IGameState {
    private GameState gameState = GameState.LOAD;

    @Override
    public void changeGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public void run(Group root, Canvas canvas, Scene scene, GraphicsContext gc, Rectangle2D screenBounds, Map map) {
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
                            map.placeAsteroids();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        gameState = GameState.IN_PROGRESS;
                    }
                    case IN_PROGRESS -> {
                        map.refreshMap(root);
                        try {
                            inProgress(root, map);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
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

    @SuppressWarnings("SpellCheckingInspection")
    public void init(Map map) throws IOException {
        Scanner in = new Scanner(System.in);

        System.out.println("Would you like to show TestLogger messages?  (1 = Yes)");
        boolean showTestLogger = in.nextLine().equals("1");
        TestLogger.setShow(showTestLogger);

        System.out.println("Would you like to show input choices? (1 = Yes)");
        boolean showInput = in.nextLine().equals("1");
        UserIO.setShowInput(showInput);

        System.out.println("Would you like to generate the map automatically? (1 = Yes)");
        boolean automaticSetup = in.nextLine().equals("1");
        UserIO.setIsAutomatic(automaticSetup);

        System.out.println("Should I check whether the game is winnable or not? (1 = Yes)");
        boolean shouldCheckIfWinnable = in.nextLine().charAt(0) == '1';
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
        UserIO.openFile();

        map.addStateListener(this);

        //Játék iniciaizálása:
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
        m.setupRound(root);
        if (m.shouldRun())
            System.out.println("\n---------ROUND ENDED----------\n");
    }
}
