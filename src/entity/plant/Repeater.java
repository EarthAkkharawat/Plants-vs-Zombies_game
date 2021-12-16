package entity.plant;

import entity.Pea;
import entity.Zombie;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import logic.GameController;


public class Repeater extends Shooter {

    // Constructor
    public Repeater(int x, int y, int column, int row) {
        super(x, y, "/gif/repeater.gif", 150, 60, 62, column, row);
    }

    // Methods
    @Override
    public void attacking(Pane pane) {
        Timeline peaShooter = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
            synchronized (GameController.allZombies) {
                for (Zombie zombie : GameController.allZombies) {
                    if (zombie.getLane() == getShooterLane() && getX() <= zombie.getX()) {
                        int pea1StartX = getX() + 50;
                        int pea2StartX = getX() - 20;
                        int peaStartY = getY() + 26;
                        Pea p1 = new Pea(pea1StartX, peaStartY, getX() + 50, row);
                        Pea p2 = new Pea(pea2StartX, peaStartY, getX() + 50, row);
                        p1.buildImage(pane);
                        p2.buildImage(pane);
                        p1.shootPea();
                        p2.shootPea();
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
}
