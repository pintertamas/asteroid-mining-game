package Controllers;

import Interfaces.IGameState;
import Playground.Asteroid;
import Playground.GameState;
import javafx.animation.AnimationTimer;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;

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
        controller.getMap().addStateListener(this);
        // itt lehet hozzáadogatni a listenereket amikor kellenek majd
        new AnimationTimer() {
            @Override
            public void handle(long l) {
                switch (gameState) {
                    case LOAD -> {
                        controller.getMap().initGame(screenBounds);
                        controller.getMap().sunflower(controller.getMap().getAsteroids(), screenBounds);
                        controller.addAllViews();
                        controller.drawAllViews(screenBounds);
                        gameState = GameState.IN_PROGRESS;
                    }
                    case IN_PROGRESS -> {
                        if (!controller.getMap().checkGameEnd()) {
                            controller.getMap().resetRound();
                            controller.getMap().setupRound(root, screenBounds);
                        }
                    }
                    case LOST -> {
                        controller.getMap().gameEnd(false);
                    }
                    case WON -> {
                        controller.getMap().gameEnd(true);
                    }
                }
            }
        }.start();
    }
}
