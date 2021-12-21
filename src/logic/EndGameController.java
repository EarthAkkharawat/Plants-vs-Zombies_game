package logic;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Objects;

/**
 * The type End game controller.
 */
public class EndGameController {

    /**
     * The Main menu button.
     */
    @FXML
    public Button mainMenuButton;
    @FXML
    private AnchorPane endGame;
    @FXML
    private ImageView zombiesAteYourBrains;
    @FXML
    private ImageView youAteZombiesBrains;
    @FXML
    private ImageView youWon;
    @FXML
    private ImageView nextLevelButton;
    @FXML
    private ImageView plantImage;

    /**
     * To main menu.
     *
     * @throws IOException the io exception
     */
    @FXML
    public void ToMainMenu() throws IOException {
        AnchorPane Apane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainMenu.fxml")));
        endGame.getChildren().setAll(Apane);
    }

    /**
     * To next level.
     *
     * @throws IOException the io exception
     */
    @FXML
    public void ToNextLevel() throws IOException {
        AnchorPane Apane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("LevelMenu.fxml")));
        endGame.getChildren().setAll(Apane);
    }

    /**
     * Initialize.
     */
    @FXML
    public void initialize() {
        zombiesAteYourBrains.setVisible(false);
        youAteZombiesBrains.setVisible(false);
        youWon.setVisible(false);
        plantImage.setVisible(false);
        nextLevelButton.setVisible(false);
        nextLevelButton.setDisable(true);
    }

    /**
     * End game ui.
     *
     * @param level   the level
     * @param gameWin the game win
     */
    @FXML
    public void endGameUI(int level, boolean gameWin) {
        if (!gameWin) {
            zombiesAteYourBrains.setVisible(true);
        } else {
            if (level == 5) {
                youAteZombiesBrains.setVisible(true);
            } else {
                youWon.setVisible(true);
                nextLevelButton.setVisible(true);
                nextLevelButton.setDisable(false);
                plantImage.setVisible(true);
                if (level == 1) {
                    plantImage.setImage(new Image("/images/Level2.png"));
                } else if (level == 2) {
                    plantImage.setImage(new Image("/images/Level3.png"));
                } else if (level == 3) {
                    plantImage.setImage(new Image("/images/Level4.png"));
                } else if (level == 4) {
                    plantImage.setImage(new Image("/images/Level5.png"));
                }
            }

        }

    }

}

