package logic;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Objects;

public class LevelMenuController {

    // Fields
    @FXML
    private AnchorPane levelRoot;
    @FXML
    private ImageView nightTheme;
    @FXML
    public ImageView nightMode;
    @FXML
    public ImageView dayMode;
    public static boolean status = true;

    // Methods
    public void initialize() {
        if (status) {
            nightTheme.setVisible(false);
            nightMode.setVisible(false);
            nightMode.setDisable(true);
            dayMode.setVisible(true);
            dayMode.setDisable(false);
        } else {
            nightTheme.setVisible(true);
            nightMode.setVisible(true);
            nightMode.setDisable(false);
            dayMode.setVisible(false);
            dayMode.setDisable(true);
            GameController.theme = "night";
        }
    }

    @FXML
    public void shineImage(MouseEvent event) {
        Node source = (Node) event.getSource();
        Glow shine = new Glow();
        source.setEffect(shine);
        shine.setLevel(0.3);
    }

    @FXML
    public void stopShining(MouseEvent event) {
        Node source = (Node) event.getSource();
        Glow shine = (Glow) source.getEffect();
        source.setEffect(shine);
        shine.setLevel(0.0);
    }

    @FXML
    public void startLevel1() throws IOException {
        FXMLLoader gamePlayfxml = new FXMLLoader(getClass().getResource("GamePlay.fxml"));
        AnchorPane Apane = gamePlayfxml.load();
        GameController controller = gamePlayfxml.getController();
        controller.initializeData(1, new GameData(1));
        levelRoot.getChildren().setAll(Apane);

    }

    @FXML
    public void startLevel2() throws IOException {
        FXMLLoader gamePlayfxml = new FXMLLoader(getClass().getResource("GamePlay.fxml"));
        AnchorPane Apane = gamePlayfxml.load();
        GameController controller = gamePlayfxml.getController();
        controller.initializeData(2, new GameData(2));
        levelRoot.getChildren().setAll(Apane);

    }

    @FXML
    public void startLevel3() throws IOException {
        FXMLLoader gamePlayfxml = new FXMLLoader(getClass().getResource("GamePlay.fxml"));
        AnchorPane Apane = gamePlayfxml.load();
        GameController controller = gamePlayfxml.getController();
        controller.initializeData(3, new GameData(3));
        levelRoot.getChildren().setAll(Apane);

    }

    @FXML
    public void startLevel4() throws IOException {
        FXMLLoader gamePlayfxml = new FXMLLoader(getClass().getResource("GamePlay.fxml"));
        AnchorPane Apane = gamePlayfxml.load();
        GameController controller = gamePlayfxml.getController();
        controller.initializeData(4, new GameData(4));
        levelRoot.getChildren().setAll(Apane);

    }

    @FXML
    public void startLevel5() throws IOException {
        FXMLLoader gamePlayfxml = new FXMLLoader(getClass().getResource("GamePlay.fxml"));
        AnchorPane Apane = gamePlayfxml.load();
        GameController controller = gamePlayfxml.getController();
        controller.initializeData(5, new GameData(5));
        levelRoot.getChildren().setAll(Apane);
    }

    @FXML
    public void PrevMenuLoader() throws IOException {
        AnchorPane Apane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainMenu.fxml")));
        levelRoot.getChildren().setAll(Apane);
    }

    @FXML
    public void changeGameTheme() {
        if (!dayMode.isVisible()) {
            nightMode.setVisible(false);
            nightMode.setDisable(true);
            nightTheme.setVisible(false);
            dayMode.setVisible(true);
            dayMode.setDisable(false);
            status = true;
        } else if (!nightMode.isVisible()) {
            dayMode.setVisible(false);
            dayMode.setDisable(true);
            nightMode.setVisible(true);
            nightMode.setDisable(false);
            nightTheme.setVisible(true);
            status = false;
        }
    }

    public static boolean getDayMode() {
        return status;
    }

}
