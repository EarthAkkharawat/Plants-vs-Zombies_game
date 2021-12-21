package entity;

import entity.base.Attackable;
import entity.base.Entity;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import logic.GameController;

import java.util.Objects;


/**
 * The type Zombie.
 */
public abstract class Zombie extends Entity implements Attackable {

    private int health;
    /**
     * The Attack power.
     */
    protected int attackPower;
    private int lane;
    /**
     * The Dx.
     */
    protected int dx;
    private Timeline zombieAnimation;

    /**
     * Instantiates a new Zombie.
     *
     * @param health      the health
     * @param attackPower the attack power
     * @param x           the x
     * @param y           the y
     * @param width       the width
     * @param height      the height
     * @param lane        the lane
     * @param path        the path
     */
    public Zombie(int health, int attackPower, int x, int y, int width, int height, int lane, String path) {
        super(x, y, width, height, path);
        this.health = health;
        setAttackPower(attackPower);
        setLane(lane);
        setDx(-1);
    }

    /**
     * Sets health point.
     *
     * @param health the health
     */
    public void setHealthPoint(int health) {
        this.health = health;
        Platform.runLater(() -> {
            if (health <= 0) {
                ++GameController.numKilledZombies;
                getImage().setVisible(false);
                getImage().setDisable(true);
                getZombieAnimation().stop();
                for (Object zombie : GameController.allZombies) {
                    if (this == zombie) {
                        Media yuckSound = new Media(Objects.requireNonNull(getClass().getResource("/sounds/yuck.wav")).toString());
                        MediaPlayer mediaPlayer = new MediaPlayer(yuckSound);
                        mediaPlayer.setAutoPlay(true);
                        mediaPlayer.play();
                        GameController.allZombies.remove(zombie);
                        break;
                    }
                }
            }
            if (health <= 7) {
                getImage().setImage(new Image(Objects.requireNonNull(getClass().getResource("/gif/defaultzombie.gif")).toString(), 65, 115, false, false));
                width = 65;
                height = 115;
            }
        });
    }

    /**
     * Burnt zombie.
     */
    public void burntZombie() {
        getImage().setImage(new Image(Objects.requireNonNull(getClass().getResource("/gif/burntZombie.gif")).toString(), 65, 115, false, false));
        health = 0;
        dx = 0;
        ++GameController.numKilledZombies;
        new Thread(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            getImage().setVisible(false);
            getImage().setDisable(true);
        }).start();

    }

    /**
     * Reached house.
     */
    public void ReachedHouse() {
        if (getImage().getX() <= 220) {
            Media brainzSound = new Media(Objects.requireNonNull(getClass().getResource("/sounds/brainz.wav")).toString());
            MediaPlayer mediaPlayer = new MediaPlayer(brainzSound);
            mediaPlayer.setAutoPlay(true);
            mediaPlayer.play();
            GameController.wonGame = -1;
        }
    }

    /**
     * Move zombie.
     */
    public void moveZombie() {
        Timeline animation = new Timeline(new KeyFrame(Duration.millis(70), e -> zombieWalk()));
        animation.setCycleCount(Animation.INDEFINITE);
        animation.play();
        zombieAnimation = animation;
        GameController.animationTimelines.add(animation);
    }

    /**
     * Zombie walk.
     */
    public void zombieWalk() {
        if (getX() > 220 && health > 0) {
            setX(getX() + dx);
            try {
                attacking();
            } catch (java.util.ConcurrentModificationException e) {
                // e.printStackTrace();
            }
            ReachedHouse();
        }
    }

    /**
     * Act eat.
     *
     * @param plant the plant
     */
    public void actEat(Plant plant) {
        dx = 0;
        plant.setHealthpoint(plant.getHealthpoint() - attackPower);
        if (plant.getHealthpoint() <= 0) {
            plant.setHealthpoint(0);
            GameController.allPlants.remove(plant);
            dx = -1;
        }
    }

    /**
     * Eat plant.
     */
    public void eatPlant() {
        synchronized (GameController.allPlants) {
            for (Plant plant : GameController.allPlants) {
                if (plant.getRow() == getLane()) {
                    if (Math.abs(plant.getX() - getImage().getX()) <= 50) {
                        actEat(plant);
                    }
                }
            }
        }
    }

    /**
     * Gets health.
     *
     * @return the health
     */
    public int getHealth() {
        return health;
    }

    /**
     * Gets attack power.
     *
     * @return the attack power
     */
    public int getAttackPower() {
        return attackPower;
    }

    /**
     * Gets lane.
     *
     * @return the lane
     */
    public int getLane() {
        return lane;
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
     * Sets attack power.
     *
     * @param attackPower the attack power
     */
    public void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }

    /**
     * Sets dx.
     *
     * @param dx the dx
     */
    public void setDx(int dx) {
        this.dx = dx;
    }

    /**
     * Gets zombie animation.
     *
     * @return the zombie animation
     */
    public Timeline getZombieAnimation() {
        return zombieAnimation;
    }
}
