package entity;

import entity.base.Entity;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import logic.GameController;

public class Sun extends Entity {

    // Fields
    private final int timeout_time;

    // Constructor
    public Sun(int x, int y, boolean isFalling) {
        super(x, y, 50, 50, "/images/sun.png");
        if (isFalling) {
            timeout_time = 14000;
        } else {
            timeout_time = 5000;
        }
        disappear();
    }

    // Methods
    public void disappear() {
        new Thread(() -> {
            try {
                Thread.sleep(timeout_time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            getImage().setVisible(false);
            getImage().setDisable(true);

        }).start();
    }

    @Override
    public void buildImage(Pane pane) {
        super.buildImage(pane);
        getImage().setOnMouseClicked(mouseEvent -> {
            getImage().setVisible(false);
            getImage().setDisable(true);
            GameController.updateSunCount(25);
        });
    }

    public void sunMovement() {
        if (getY() <= 550) {
            setY(getY() + 1);
        }
    }

    public void fallingSun() {
        Timeline sun_animation = new Timeline(new KeyFrame(Duration.millis(12), actionEvent -> sunMovement()));
        sun_animation.setCycleCount(550);
        sun_animation.play();
        GameController.animationTimelines.add(sun_animation);
    }

}
