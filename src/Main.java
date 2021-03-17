import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("Testing:");
        Asteroid a1 = new Asteroid(new Iron(), 3, true, false);
        Asteroid a2 = new Asteroid(new Iron(), 3, true, false);
        a1.addNeighbor(a2);
        a2.addNeighbor(a1);
        Settler s = new Settler(a1, false);
        s.move();





    }


    public static void main(String[] args) {
        launch(args);
    }
}
