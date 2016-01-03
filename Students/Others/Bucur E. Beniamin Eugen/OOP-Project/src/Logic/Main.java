package Logic;

import Gui.MainScene;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static Stage mainWindow;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        mainWindow = primaryStage;
        mainWindow.setTitle("Settlers of Catan");
        mainWindow.setResizable(false);

        new MainScene();
        mainWindow.show();
    }


}