package entity;

import entity.base.Entity;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import logic.GameController;

import java.util.Objects;

/**
 * The type Pea.
 */
public class Pea extends Entity {

    private int lane;
    private int plantPosition;
    private Timeline peaAnimation;
    private boolean bombed;

    /**
     * Instantiates a new Pea.
     *
     * @param x             the x
     * @param y             the y
     * @param plantPosition the plant position
     * @param lane          the lane
     */
    public Pea(int x, int y, int plantPosition, int lane) {
        super(x, y, 20, 20, "/images/pea.png");
        this.path = Objects.requireNonNull(getClass().getResource("/images/pea.png")).toString();
        setPlantPosition(plantPosition);
        setLane(lane);
        setBombed(false);
    }

    /**
     * Move pea.
     */
    public void movePea() {
        if (x <= 1050) {
            setX(getX() + 1);
        }
        image.setVisible(plantPosition <= getX());
        checkZombieCollision();
    }

    /**
     * Shoot pea.
     */
    public void shootPea() {
        Timeline animation = new Timeline(new KeyFrame(Duration.millis(5), e -> movePea()));
        animation.setCycleCount(1050);
        animation.play();
        this.peaAnimation = animation;
        GameController.animationTimelines.add(animation);
    }

    /**
     * Check zombie collision.
     */
    public void checkZombieCollision() {
        synchronized (GameController.allZombies) {
            for (Zombie zombie : GameController.allZombies) {
                if (zombie.getLane() == lane && !bombed) {
                    if (Math.abs(zombie.getX() - getX()) <= 3 && !bombed) {
                        bombed = true;
                        zombie.setHealthPoint(zombie.getHealth() - 1);
                        getImage().setVisible(false);
                        getImage().setDisable(true);
                        peaAnimation.stop();
                        Media splat = new Media(Objects.requireNonNull(getClass().getResource("/sounds/splat3.wav")).toString());
                        MediaPlayer mediaPlayer = new MediaPlayer(splat);
                        mediaPlayer.setAutoPlay(true);
                        mediaPlayer.play();
                    }
                }
            }
        }
    }

    /**
     * Sets lane.
     *
     * @param lane the lane
     */
    public void setLane(int lane) {
        this.lane = lane;
    }

    /**
     * Sets plant position.
     *
     * @param plantPosition the plant position
     */
    public void setPlantPosition(int plantPosition) {
        this.plantPosition = plantPosition;
    }

    /**
     * Sets bombed.
     *
     * @param bombed the bombed
     */
    public void setBombed(boolean bombed) {
        this.bombed = bombed;
    }
}

