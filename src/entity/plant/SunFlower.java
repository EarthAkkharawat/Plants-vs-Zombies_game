package entity.plant;

import entity.Plant;
import entity.Sun;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.effect.Glow;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import logic.GameController;

public class SunFlower extends Plant {

    // Field
    private Timeline sunProducer;

    // Constructor
    public SunFlower(int x, int y, int column, int row) {
        super(x, y, 73, 74, "/gif/sunflower.gif", 100, column, row);
    }

    // Methods
    @Override
    public void attacking(Pane pane) {
        produceSun(pane);
    }

    @Override
    public void attacking() {

    }

    public void produceSun(Pane pane) {
        Timeline stopShine = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            Glow shine = new Glow();
            getImage().setEffect(shine);
            shine.setLevel(0.0);
        }));

        Timeline sunProducer = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
            checkHealthPoint();
            if (getHealthpoint() > 0) {
                Sun sun = new Sun(getX() + 20, getY() + 40, false);
                sun.buildImage(pane);
            }
            stopShine.play();
        }));

        Timeline startShine = new Timeline(new KeyFrame(Duration.seconds(8), event -> {
            Glow shines = new Glow();
            getImage().setEffect(shines);
            shines.setLevel(0.7);
            sunProducer.play();
        }));

        startShine.setCycleCount(Timeline.INDEFINITE);
        startShine.play();
        this.sunProducer = sunProducer;
        GameController.animationTimelines.add(sunProducer);
        GameController.animationTimelines.add(startShine);
        GameController.animationTimelines.add(stopShine);
    }

    public void checkHealthPoint() {
        if (getHealthpoint() <= 0) {
            getImage().setVisible(false);
            getImage().setDisable(true);
            endAnimation(getSunProducer());
        }
    }

    public Timeline getSunProducer() {
        return sunProducer;
    }

}
