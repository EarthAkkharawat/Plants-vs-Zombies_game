package logic;

import entity.zombie.BucketHeadZombie;
import entity.zombie.DefaultZombie;
import entity.zombie.FunnelHeadZombie;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Collections;

public class GameEntity {

    // Fields
    private final int level;
    private final int numDefaultZombie;
    private final int numFunnelHeadZombie;
    private final int numBucketHeadZombie;
    private final ArrayList<Integer> availableZombies;
    private final ArrayList<Integer> zombieList1;
    private final ArrayList<Integer> zombieList2;
    private final int totalZombies;

    // Methods
    public GameEntity(int level) {
        this.level = level;
        zombieList1 = new ArrayList<>();
        zombieList2 = new ArrayList<>();
        availableZombies = new ArrayList<>();
        if (level >= 5) {
            totalZombies = 30;
            numDefaultZombie = 12;
            numFunnelHeadZombie = 10;
            numBucketHeadZombie = 8;
        } else if (level >= 4) {
            totalZombies = 25;
            numDefaultZombie = 12;
            numFunnelHeadZombie = 9;
            numBucketHeadZombie = 4;
        } else if (level >= 3) {
            totalZombies = 20;
            numDefaultZombie = 10;
            numFunnelHeadZombie = 8;
            numBucketHeadZombie = 2;
        } else if (level >= 2) {
            totalZombies = 15;
            numDefaultZombie = 10;
            numFunnelHeadZombie = 5;
            numBucketHeadZombie = 0;
        } else {
            totalZombies = 10;
            numDefaultZombie = 10;
            numFunnelHeadZombie = 0;
            numBucketHeadZombie = 0;
        }
        for (int idx = 0; idx < numDefaultZombie; idx++) {
            availableZombies.add(1);
        }
        for (int idx = 0; idx < this.numFunnelHeadZombie; idx++) {
            availableZombies.add(2);
        }
        for (int idx = 0; idx < this.numBucketHeadZombie; idx++) {
            availableZombies.add(3);
        }
        Collections.shuffle(availableZombies);
        for (int idx = 0; idx < availableZombies.size(); idx++) {
            if (idx % 2 == 1) {
                zombieList1.add(availableZombies.get(idx));
            } else {
                zombieList2.add(availableZombies.get(idx));
            }
        }
    }

    public static void spawnDefaultZombie(Pane pane, int lane, int laneNumber) {
        DefaultZombie Dzombie = new DefaultZombie(1024, lane, laneNumber);
        Dzombie.buildImage(pane);
        GameController.allZombies.add(Dzombie);
        Dzombie.moveZombie();
    }

    public static void spawnFunnelHeadZombie(Pane pane, int lane, int laneNumber) {
        FunnelHeadZombie Fzombie = new FunnelHeadZombie(1024, lane, laneNumber);
        Fzombie.buildImage(pane);
        GameController.allZombies.add(Fzombie);
        Fzombie.moveZombie();
    }

    public static void spawnBucketHeadZombie(Pane pane, int lane, int laneNumber) {
        BucketHeadZombie Bzombie = new BucketHeadZombie(1024, lane, laneNumber);
        Bzombie.buildImage(pane);
        GameController.allZombies.add(Bzombie);
        Bzombie.moveZombie();
    }

    public int getLevel() {
        return level;
    }

    public int getNumDefaultZombie() {
        return numDefaultZombie;
    }

    public int getNumFunnelHeadZombie() {
        return numFunnelHeadZombie;
    }

    public int getNumBucketHeadZombie() {
        return numBucketHeadZombie;
    }

    public ArrayList<Integer> getZombieList1() {
        return zombieList1;
    }

    public ArrayList<Integer> getZombieList2() {
        return zombieList2;
    }

    public int getTotalZombies() {
        return totalZombies;
    }

    public ArrayList<Integer> getAvailableZombies() {
        return availableZombies;
    }
}
