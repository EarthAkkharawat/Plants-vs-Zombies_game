package entity;

import entity.base.Attackable;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import logic.GameController;


public abstract class Zombie extends Entity implements Attackable {

    // Fields
    private int health;
    private int attackPower;
    private int lane;
    private int dx;
    private Timeline zombieAnimation;
    private Timeline eating;
    private boolean reachedPlant = false;
    private boolean isEating = false;

    // Constructor
    public Zombie(int health, int attackPower, int x, int y, int width, int height, int lane, String path) {
        super(x, y, width, height, path);
        setHealth(health);
        this.attackPower = attackPower;
        this.lane = lane;
        this.dx = -1;
        this.eating = new Timeline();
    }

    // Methods
    public void setHealth(int health) {
        this.health = health;
        if (health <= 0) {
            ++GameController.numKilledZombies;
            this.image.setVisible(false);
            this.image.setDisable(true);
            this.zombieAnimation.stop();
            if (this.eating != null) {
                this.eating.stop();
            }
            for (Object zombie : GameController.allZombies) {
                if (this == zombie) {
                    Media yuckSound = new Media(getClass().getResource("/sounds/yuck.wav").toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(yuckSound);
                    mediaPlayer.setAutoPlay(true);
                    mediaPlayer.play();
                    GameController.allZombies.remove(zombie);
                    break;
                }
            }
        }
        if (health <= 5) {
            Image img = new Image(getClass().getResource("/gif/normalzombie.gif").toString());
            image.setImage(img);
            image.setFitHeight(115);
            image.setFitWidth(65);
            this.width = 65;
            this.height = 115;
        }
    }

    public void burntZombie() {
        Image img = new Image(getClass().getResource("/gif/burntZombie.gif").toString());
        image.setImage(img);
        image.setFitHeight(115);
        image.setFitWidth(65);
        this.health = 0;
        this.eating.stop();
        this.dx = 0;
        ++GameController.numKilledZombies;
        new Thread(() -> {
            try {
                Thread.sleep(4500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            image.setVisible(false);
            image.setDisable(true);
        }).start();

    }

    public void ReachedHouse() {
        if (image.getX() <= 220) {
            Media brainzSound = new Media(getClass().getResource("/sounds/brainz.wav").toString());
            MediaPlayer mediaPlayer = new MediaPlayer(brainzSound);
            mediaPlayer.setAutoPlay(true);
            mediaPlayer.play();
            GameController.wonGame -= 1;
        }
    }

    public void chompPlant() {
        Media chomp = new Media(getClass().getResource("/sounds/chomp.wav").toString());
        MediaPlayer mediaPlayer = new MediaPlayer(chomp);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setStartTime(Duration.seconds(0));
        mediaPlayer.setStopTime(Duration.seconds(1));
        mediaPlayer.setCycleCount(1);
        mediaPlayer.play();
    }

    public void moveZombie() {
        Timeline animation = new Timeline(new KeyFrame(Duration.millis(75), e -> zombieWalk()));
        animation.setCycleCount(Animation.INDEFINITE);
        animation.play();
        this.zombieAnimation = animation;
        GameController.animationTimelines.add(animation);
    }

    public void zombieWalk() {
        if (getX() > 220 && this.health > 0) {
            setX(getX() + this.dx);
            try {
                attacking();
            } catch (java.util.ConcurrentModificationException e) {
                e.printStackTrace();
            }
            ReachedHouse();
        }
    }


    public void eatPlant() {
        boolean foundPlant = false;
        synchronized (GameController.allPlants) {
            for (Object p : GameController.allPlants) {
                Plant plant = (Plant) p;
                if (plant.getRow() == getLane()) {
                    if (Math.abs(((Plant) plant).getX() - image.getX()) <= 50) {
                        foundPlant = true;
                        if (!reachedPlant) {
                            reachedPlant = true;
                            isEating = true;
                        }
                        if (isEating) {
                            Timeline eat = new Timeline(new KeyFrame(Duration.millis(1000), e -> chompPlant()));
                            eat.setCycleCount(1000);
                            eat.play();
                            this.dx = 0;
                            this.eating = eat;
                            GameController.animationTimelines.add(eat);
                            isEating = false;
                        }
                        this.dx = 0;
                        plant.setHealthpoint(plant.getHealthpoint() - this.attackPower);
                        if (plant.getHealthpoint() <= 0) {
                            plant.setHealthpoint(0);
                            GameController.allPlants.remove(plant);
                            plant.getImage().setVisible(false);
                            plant.getImage().setDisable(true);
                            this.dx = -1;
                            this.reachedPlant = false;
                            this.eating.stop();
                        }
                    } else {
                        this.dx = -1;
                        this.reachedPlant = false;
                        if (this.eating != null) {
                            this.eating.stop();
                        }
                    }
                } else {
                    this.dx = -1;
                }
            }
        }
        if (!foundPlant) {
            this.dx = -1;
            if (this.eating != null) {
                this.eating.stop();
            }
            this.reachedPlant = false;
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

    public Timeline getZombieAnimation() {
        return zombieAnimation;
    }
}
