package Controllers;

import Interfaces.IGameState;
import Playground.GameState;
import Views.WinLoseScene;
import javafx.animation.AnimationTimer;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.stage.Stage;

public class Game implements IGameState {
    private GameState gameState = GameState.LOAD;
    public final Controller controller;

    /**
     * Konstruktor
     *
     * @param controller
     */
    Game(Controller controller) {
        this.controller = controller;
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
     * @param screenBounds
     */
    public void run(Stage primaryStage, Group root, Rectangle2D screenBounds) {
        controller.getMap().addStateListener(this);
        controller.setRoot(root);
        // itt lehet hozzáadogatni a listenereket amikor kellenek majd
        new AnimationTimer() {
            @Override
            public void handle(long l) {
                switch (gameState) {
                    case LOAD:
                        controller.getMap().initGame(screenBounds);
                        controller.placeFigures(screenBounds);
                        controller.getMap().resetRound();
                        controller.drawBackground(screenBounds);
                        controller.addAllViews();
                        controller.drawAllViews(screenBounds);
                        gameState = GameState.IN_PROGRESS;
                        break;
                    case IN_PROGRESS:
                        if (!controller.getMap().checkGameEnd()) {
                            controller.getMap().setupRound(root, screenBounds);
                        }
                        break;
                    case LOST:
                        WinLoseScene.WinLose(primaryStage, "lost");
                        gameState = GameState.RESET;
                        break;
                    case WON:
                        WinLoseScene.WinLose(primaryStage, "won");
                        gameState = GameState.RESET;
                        break;
                    case RESET:
                        Controller.getController().resetController();
                        controller.getMap().changePlayerNumber(0);
                        gameState = GameState.IDLE;
                        break;
                    case IDLE:
                }
            }
        }.start();
    }
}
