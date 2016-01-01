package Gui.GameSceneComponents;

import Data.Game;
import Gui.PopUps.SaveFile;
import javafx.scene.control.*;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class TopMenu extends MenuBar {

    public TopMenu(Game game) {
        Menu fileMenu = new Menu("File");
        MenuItem newFile = new MenuItem("Save game");
        newFile.setOnAction(e -> {
            try {
                FileOutputStream saveFile = new FileOutputStream("Saved Games\\" + SaveFile.getFileName() + ".cat");
                ObjectOutputStream save = new ObjectOutputStream(saveFile);
                save.writeObject(game);
                save.close();
                System.out.println("Game saved successfully!");
            } catch (Exception exc) {
                exc.printStackTrace();
            }
        });
        fileMenu.getItems().add(newFile);

        Menu helpMenu = new Menu("Help");
        CheckMenuItem autoSave = new CheckMenuItem("Enable AutoSave");
        autoSave.setSelected(true);
        helpMenu.getItems().addAll(autoSave);

        this.getMenus().addAll(fileMenu, helpMenu);
    }

}
