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


public abstract class Zombie extends Entity implements Attackable {

    // Fields
    private int health;
    protected int attackPower;
    private int lane;
    protected int dx;
    private Timeline zombieAnimation;

    // Constructor
    public Zombie(int health, int attackPower, int x, int y, int width, int height, int lane, String path) {
        super(x, y, width, height, path);
        this.health = health;
        setAttackPower(attackPower);
        setLane(lane);
        setDx(-1);
    }

    // Methods
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

    public void ReachedHouse() {
        if (getImage().getX() <= 220) {
            Media brainzSound = new Media(Objects.requireNonNull(getClass().getResource("/sounds/brainz.wav")).toString());
            MediaPlayer mediaPlayer = new MediaPlayer(brainzSound);
            mediaPlayer.setAutoPlay(true);
            mediaPlayer.play();
            GameController.wonGame = -1;
        }
    }

    public void moveZombie() {
        Timeline animation = new Timeline(new KeyFrame(Duration.millis(70), e -> zombieWalk()));
        animation.setCycleCount(Animation.INDEFINITE);
        animation.play();
        zombieAnimation = animation;
        GameController.animationTimelines.add(animation);
    }

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

    public void actEat(Plant plant) {
        dx = 0;
        plant.setHealthpoint(plant.getHealthpoint() - attackPower);
        if (plant.getHealthpoint() <= 0) {
            plant.setHealthpoint(0);
            GameController.allPlants.remove(plant);
            dx = -1;
        }
    }

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

    public int getHealth() {
        return health;
    }

    public int getAttackPower() {
        return attackPower;
    }

    public int getLane() {
        return lane;
    }

    public void setLane(int lane) {
        this.lane = lane;
    }

    public void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public Timeline getZombieAnimation() {
        return zombieAnimation;
    }
}
