package logic;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class EndGameController {
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
    @FXML
    private Label plantName;


    @FXML
    void ToMainMenu() throws IOException {
        AnchorPane Apane = FXMLLoader.load(getClass().getResource("MainPage.fxml"));
        endGame.getChildren().setAll(Apane);
    }

    @FXML
    void ToNextLevel() throws IOException {
        AnchorPane Apane = FXMLLoader.load(getClass().getResource("LevelMenu.fxml"));
        endGame.getChildren().setAll(Apane);
    }

    @FXML
    public void initialize() {
        zombiesAteYourBrains.setVisible(false);
        youAteZombiesBrains.setVisible(false);
        youWon.setVisible(false);
        plantImage.setVisible(false);
        plantName.setVisible(false);
        nextLevelButton.setVisible(false);
        nextLevelButton.setDisable(true);
    }

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
                plantName.setVisible(true);
                plantImage.setVisible(true);
                if (level == 1) {
                    plantName.setText("Walnut");
                    plantImage.setImage(new Image("/images/Level2.png"));
                } else if (level == 2) {
                    plantName.setText("CherryBomb");
                    plantImage.setImage(new Image("/images/Level3.png"));
                } else if (level == 3) {
                    plantName.setText("Repeater");
                    plantImage.setImage(new Image("/images/Level4.png"));
                } else if (level == 4) {
                    plantName.setText("ChilliPepper");
                    plantImage.setImage(new Image("/images/Level5.png"));
                }
            }

        }

    }

}

