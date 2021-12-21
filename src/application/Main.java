package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Objects;

/**
 * The type Main.
 */
public class Main extends Application {

    /**
     * The constant mediaPlayer.
     */
    public static MediaPlayer mediaPlayer;

    @Override
    public void start(Stage primaryStage) throws Exception {
        addMusic();
        Parent mainPage = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/logic/MainMenu.fxml")));
        Scene scene = new Scene(mainPage, 1024, 600);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Plants VS Zombies");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Add music.
     */
    public void addMusic() {
        Media sound = new Media(Objects.requireNonNull(getClass().getResource("/sounds/background.wav")).toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setStartTime(Duration.seconds(0));
        mediaPlayer.setStopTime(Duration.seconds(50));
        mediaPlayer.play();
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}