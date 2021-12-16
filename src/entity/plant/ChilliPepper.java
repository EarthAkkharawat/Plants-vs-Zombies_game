package entity.plant;

import entity.Plant;
import entity.Zombie;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import logic.GameController;

import java.util.ArrayList;
import java.util.Objects;

public class ChilliPepper extends Plant {

    // Fields
    private ArrayList<Zombie> roastedZombies;
    private final ImageView[] fires;

    // Constructor
    public ChilliPepper(int x, int y, int column, int row) {
        super(x, y, 100, 100, "/gif/chillipepper.gif", 4, column, row);
        fires = new ImageView[9];
    }

    // Methods
    @Override
    public void attacking() {
    }

    @Override
    public void buildImage(GridPane lawn) {
        super.buildImage(lawn);
        for (int idx = 0; idx < 9; idx++) {
            fires[idx] = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/gif/chillipepperFire.gif")).toString(), 100, 100, false, false));
            fires[idx].setDisable(true);
            fires[idx].setVisible(false);
            lawn.add(fires[idx], idx, row, 1, 1);
        }
        roastedZombies = new ArrayList<>();
    }

    @Override
    public void attacking(Pane pane) {
        new Thread(() -> {
            try {
                Thread.sleep(1650);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            image.setVisible(false);
            image.setDisable(true);
            Media blast = new Media(Objects.requireNonNull(getClass().getResource("/sounds/chillipepper.wav")).toString());
            MediaPlayer mediaPlayer = new MediaPlayer(blast);
            mediaPlayer.setAutoPlay(true);
            mediaPlayer.play();
            for (int idx = 0; idx < 9; idx++) {
                fires[idx].setVisible(true);
            }
            synchronized (GameController.allZombies) {
                for (Zombie zombie : GameController.allZombies) {
                    if (row == zombie.getLane()) {
                        zombie.burntZombie();
                    }
                }
            }
            GameController.allPlants.removeIf(plant -> this == plant);
            for (Zombie roastedZombie : getRoastedZombies()) {
                for (int idx = 0; idx < GameController.allZombies.size(); idx++) {
                    if (roastedZombie == GameController.allZombies.get(idx)) {
                        GameController.allZombies.remove(idx);
                    }
                }
            }
            removeFire();
        }).start();
    }

    public void removeFire() {
        new Thread(() -> {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int idx = 0; idx < 9; idx++) {
                fires[idx].setVisible(false);
                fires[idx].setDisable(true);
            }
        }).start();
        setHealthpoint(0);
    }

    @Override
    public void checkHealthPoint() {
    }

    public ArrayList<Zombie> getRoastedZombies() {
        return roastedZombies;
    }

}
