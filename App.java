package indy;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import static indy.Constants.*;

/**
 * This is the App class for my Indy project, Bloons Tower Defense 15! It is a
 * recreation of the Bloons TD video game series. This app class instantiates the
 * PaneOrganizer and Scene and sets up the Stage.
 */

public class App extends Application {

    @Override
    public void start(Stage stage) {
        PaneOrganizer organizer = new PaneOrganizer();
        Scene scene = new Scene(organizer.getRoot(),
                SCENE_WIDTH, SCENE_HEIGHT);

        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Bloons TD 15");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}