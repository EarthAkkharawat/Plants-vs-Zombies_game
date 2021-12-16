package entity.plant;

import entity.Pea;
import entity.Plant;

import entity.Zombie;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import logic.GameController;

public class Shooter extends Plant {

    // Fields
    protected Timeline shooterTimeline;
    protected int lane;

    // Constructor
    public Shooter(int x, int y, String path, int healthpoint, int width, int height, int column, int row) {
        super(x, y, width, height, path, healthpoint, column, row);
        this.lane = row;
    }

    // Methods
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

    public Timeline getShooterTimeline() {
        return shooterTimeline;
    }

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

