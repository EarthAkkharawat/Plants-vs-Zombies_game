package logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import entity.Plant;
import entity.Zombie;

/**
 * The type Game data.
 */
public class GameData {

    private final List<Zombie> allZombie;
    private final List<Plant> allPlants;
    private ArrayList<Integer> zombieList1;
    private ArrayList<Integer> zombieList2;
    private final boolean status;
    private final int sunCount;

    /**
     * Instantiates a new Game data.
     *
     * @param level the level
     */
    public GameData(int level) {
        sunCount = 75;
        zombieList1 = new ArrayList<>();
        zombieList2 = new ArrayList<>();
        allZombie = Collections.synchronizedList(new ArrayList<>());
        allPlants = Collections.synchronizedList(new ArrayList<>());
        GameEntity gamelevel = new GameEntity(level);
        zombieList1 = gamelevel.getZombieList1();
        zombieList2 = gamelevel.getZombieList2();
        status = LevelMenuController.status;
    }

    /**
     * Gets all zombie.
     *
     * @return the all zombie
     */
    public List<Zombie> getAllZombie() {
        return allZombie;
    }

    /**
     * Gets all plants.
     *
     * @return the all plants
     */
    public List<Plant> getAllPlants() {
        return allPlants;
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
     * Gets status.
     *
     * @return the status
     */
    public boolean getStatus() {
        return status;
    }

    /**
     * Gets sun count.
     *
     * @return the sun count
     */
    public int getSunCount() {
        return sunCount;
    }
}
