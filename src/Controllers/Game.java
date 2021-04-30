package Controllers;

import Interfaces.IGameState;
import Playground.GameState;
import Test.TestLogger;
import Test.UserIO;
import javafx.animation.AnimationTimer;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;

import java.io.IOException;
import java.util.Scanner;
import java.util.UUID;

public class Game implements IGameState {
    private GameState gameState = GameState.LOAD;
    private final Map map;
    public Controller controller;

    Game(Controller controller) {
        this.controller = controller;
        this.map = controller.map;
    }

    /**
     * Setter a GameState megváltoztatásához
     */
    @Override
    public void changeGameState(GameState gameState) {
        this.gameState = gameState;
    }


    /**
     * A játék futtatását felügyeli, az alapján, hogy melyik GameState-ben vagyunk
     *
     * @param root
     * @param gc
     * @param screenBounds
     */
    public void run(Group root, GraphicsContext gc, Rectangle2D screenBounds) {
        boolean shouldCheckGameEnd = UserIO.checkIfWinnable();
        controller.getMap().addStateListener(this);
        // itt lehet hozzáadogatni a listenereket amikor kellenek majd
        new AnimationTimer() {
            @Override
            public void handle(long l) {
                switch (gameState) {
                    case LOAD -> {
                        controller.getMap().initGame(screenBounds);
                        controller.drawAllViews(screenBounds);
                        gameState = GameState.IN_PROGRESS;
                    }
                    case IN_PROGRESS -> {
                        if (!(shouldCheckGameEnd && controller.getMap().checkGameEnd())) {
                            controller.getMap().reset();
                            try {
                                controller.getMap().setupRound(root, screenBounds);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    case LOST -> {
                        map.gameEnd(false);
                        if (UserIO.readFromFile())
                            UserIO.closeFile();
                    }
                    case WON -> {
                        map.gameEnd(true);
                        if (UserIO.readFromFile())
                            UserIO.closeFile();
                    }
                }
            }
        }.start();
    }
}
