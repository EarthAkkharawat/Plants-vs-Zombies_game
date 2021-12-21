package logic;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.util.Objects;

/**
 * The type Main menu controller.
 */
public class MainMenuController {

    @FXML
    private AnchorPane mainRoot;

    /**
     * Exit game.
     */
    @FXML
    public void exitGame() {
        System.exit(0);
    }

    /**
     * Show level menu.
     *
     * @throws Exception the exception
     */
    @FXML
    public void showLevelMenu() throws Exception {
        AnchorPane Apane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("LevelMenu.fxml")));
        mainRoot.getChildren().setAll(Apane);
    }

}
