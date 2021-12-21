package logic;

import entity.zombie.BucketHeadZombie;
import entity.zombie.DefaultZombie;
import entity.zombie.FunnelHeadZombie;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Collections;

/**
 * The type Game entity.
 */
public class GameEntity {

    private final int level;
    private final int numDefaultZombie;
    private final int numFunnelHeadZombie;
    private final int numBucketHeadZombie;
    private final ArrayList<Integer> availableZombies;
    private final ArrayList<Integer> zombieList1;
    private final ArrayList<Integer> zombieList2;
    private final int totalZombies;

    /**
     * Instantiates a new Game entity.
     *
     * @param level the level
     */
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

    /**
     * Spawn default zombie.
     *
     * @param pane       the pane
     * @param lane       the lane
     * @param laneNumber the lane number
     */
    public static void spawnDefaultZombie(Pane pane, int lane, int laneNumber) {
        DefaultZombie Dzombie = new DefaultZombie(1024, lane, laneNumber);
        Dzombie.buildImage(pane);
        GameController.allZombies.add(Dzombie);
        Dzombie.moveZombie();
    }

    /**
     * Spawn funnel head zombie.
     *
     * @param pane       the pane
     * @param lane       the lane
     * @param laneNumber the lane number
     */
    public static void spawnFunnelHeadZombie(Pane pane, int lane, int laneNumber) {
        FunnelHeadZombie Fzombie = new FunnelHeadZombie(1024, lane, laneNumber);
        Fzombie.buildImage(pane);
        GameController.allZombies.add(Fzombie);
        Fzombie.moveZombie();
    }

    /**
     * Spawn bucket head zombie.
     *
     * @param pane       the pane
     * @param lane       the lane
     * @param laneNumber the lane number
     */
    public static void spawnBucketHeadZombie(Pane pane, int lane, int laneNumber) {
        BucketHeadZombie Bzombie = new BucketHeadZombie(1024, lane, laneNumber);
        Bzombie.buildImage(pane);
        GameController.allZombies.add(Bzombie);
        Bzombie.moveZombie();
    }

    /**
     * Gets level.
     *
     * @return the level
     */
    public int getLevel() {
        return level;
    }

    /**
     * Gets num default zombie.
     *
     * @return the num default zombie
     */
    public int getNumDefaultZombie() {
        return numDefaultZombie;
    }

    /**
     * Gets num funnel head zombie.
     *
     * @return the num funnel head zombie
     */
    public int getNumFunnelHeadZombie() {
        return numFunnelHeadZombie;
    }

    /**
     * Gets num bucket head zombie.
     *
     * @return the num bucket head zombie
     */
    public int getNumBucketHeadZombie() {
        return numBucketHeadZombie;
    }

    /**
     * Gets zombie list 1.
     *
     * @return the zombie list 1
     */
    public ArrayList<Integer> getZombieList1() {
        return zombieList1;
    }

    /**
     * Gets zombie list 2.
     *
     * @return the zombie list 2
     */
    public ArrayList<Integer> getZombieList2() {
        return zombieList2;
    }

    /**
     * Gets total zombies.
     *
     * @return the total zombies
     */
    public int getTotalZombies() {
        return totalZombies;
    }

    /**
     * Gets available zombies.
     *
     * @return the available zombies
     */
    public ArrayList<Integer> getAvailableZombies() {
        return availableZombies;
    }
}
