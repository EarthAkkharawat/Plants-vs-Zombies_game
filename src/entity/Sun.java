package entity;

import entity.base.Entity;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import logic.GameController;

/**
 * The type Sun.
 */
public class Sun extends Entity {

    private final int timeout_time;

    /**
     * Instantiates a new Sun.
     *
     * @param x         the x
     * @param y         the y
     * @param isFalling the is falling
     */
    public Sun(int x, int y, boolean isFalling) {
        super(x, y, 50, 50, "/images/sun.png");
        if (isFalling) {
            timeout_time = 14000;
        } else {
            timeout_time = 5000;
        }
        disappear();
    }

    /**
     * Disappear.
     */
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

    /**
     * Sun movement.
     */
    public void sunMovement() {
        if (getY() <= 550) {
            setY(getY() + 1);
        }
    }

    /**
     * Falling sun.
     */
    public void fallingSun() {
        Timeline sun_animation = new Timeline(new KeyFrame(Duration.millis(12), actionEvent -> sunMovement()));
        sun_animation.setCycleCount(550);
        sun_animation.play();
        GameController.animationTimelines.add(sun_animation);
    }

}
