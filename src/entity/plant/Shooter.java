package entity.plant;

import entity.Pea;
import entity.Plant;

import entity.Zombie;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import logic.GameController;

/**
 * The type Shooter.
 */
public class Shooter extends Plant {

    /**
     * The Shooter timeline.
     */
    protected Timeline shooterTimeline;
    /**
     * The Lane.
     */
    protected int lane;

    /**
     * Instantiates a new Shooter.
     *
     * @param x           the x
     * @param y           the y
     * @param path        the path
     * @param healthpoint the healthpoint
     * @param width       the width
     * @param height      the height
     * @param column      the column
     * @param row         the row
     */
    public Shooter(int x, int y, String path, int healthpoint, int width, int height, int column, int row) {
        super(x, y, width, height, path, healthpoint, column, row);
        this.lane = row;
    }

    @Override
    public void attacking(Pane pane) {
        Timeline peaShooter = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
            synchronized (GameController.allZombies) {
                for (Zombie zombie : GameController.allZombies) {
                    if (zombie.getLane() == getShooterLane() && getX() <= zombie.getX()) {
                        int peaStartX = getX() + 50;
                        int peaStartY = getY() + 25;
                        Pea pea = new Pea(peaStartX, peaStartY, getX() + 50, row);
                        pea.buildImage(pane);
                        pea.shootPea();
                        checkHealthPoint();
                    }
                }
            }
        }));
        peaShooter.setCycleCount(Timeline.INDEFINITE);
        peaShooter.play();
        shooterTimeline = peaShooter;
        GameController.animationTimelines.add(peaShooter);
    }

    @Override
    public void attacking() {
    }

    /**
     * Gets shooter timeline.
     *
     * @return the shooter timeline
     */
    public Timeline getShooterTimeline() {
        return shooterTimeline;
    }

    /**
     * Gets shooter lane.
     *
     * @return the shooter lane
     */
    public int getShooterLane() {
        return lane;
    }

    public void checkHealthPoint() {
        if (getHealthpoint() <= 0) {
            getImage().setVisible(false);
            getImage().setDisable(true);
            endAnimation(getShooterTimeline());
        }
    }

}

