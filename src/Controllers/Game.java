package Controllers;

import Interfaces.IGameState;
import Playground.GameState;
import Views.WinLoseScene;
import javafx.animation.AnimationTimer;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;

public class Game implements IGameState {
    private GameState gameState = GameState.LOAD;
    public Controller controller;

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
    public void run(Group root, Rectangle2D screenBounds) {
        controller.getMap().addStateListener(this);
        // itt lehet hozzáadogatni a listenereket amikor kellenek majd
        new AnimationTimer() {
            @Override
            public void handle(long l) {
                switch (gameState) {
                    case MENU:

                        break;
                    case LOAD:
                        controller.getMap().initGame(screenBounds);
                        controller.placeFigures(screenBounds);
                        controller.getMap().resetRound();
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
                        root.getChildren().clear();
                        WinLoseScene.WinLose("lost");
                        controller.getMap().gameEnd(false);
                        break;
                    case WON:
                        root.getChildren().clear();
                        WinLoseScene.WinLose("won");
                        controller.getMap().gameEnd(true);
                        break;
                }
            }
        }.start();
    }
}
