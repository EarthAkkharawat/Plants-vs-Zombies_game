package logic;

import entity.Plant;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class GameMenuController {

    // Fields
    @FXML
    private AnchorPane GamePlayRoot;
    @FXML
    private ImageView restartGameButton;

    @FXML
    private int levelNumber;
    private GameData data;
    public static List<Plant> allPlants;

    // Methods
    @FXML
    public void initializeData(AnchorPane gamePlayRoot, int levelNumber, GameData d, List<Plant> allPlant) {
        this.GamePlayRoot = gamePlayRoot;
        this.levelNumber = levelNumber;
        this.data = d;
        allPlants = allPlant;
    }

    @FXML
    public void restartGame() throws IOException {
        GameController.gameStatus = false;
        GameController.endAnimations();
        Stage stage = (Stage) restartGameButton.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("GamePlay.fxml"));
        AnchorPane game = fxmlLoader.load();
        GameController controller = fxmlLoader.getController();
        data = new GameData(levelNumber);
        controller.initializeData(levelNumber, data);

        GamePlayRoot.getChildren().setAll(game);
    }

    @FXML
    public void showMainMenu() throws IOException {
        GameController.gameStatus = false;
        GameController.endAnimations();
        AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainMenu.fxml")));
        GamePlayRoot.getChildren().setAll(pane);
        Stage stage = (Stage) restartGameButton.getScene().getWindow();
        stage.close();

    }
}

