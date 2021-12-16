package logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import entity.Plant;
import entity.Zombie;

public class GameData {

    // Fields
    private final List<Zombie> allZombie;
    private final List<Plant> allPlants;
    private ArrayList<Integer> zombieList1;
    private ArrayList<Integer> zombieList2;
    private final boolean status;
    private final int sunCount;

    // Methods
    public GameData(int level) {
        sunCount = 50;
        zombieList1 = new ArrayList<>();
        zombieList2 = new ArrayList<>();
        allZombie = Collections.synchronizedList(new ArrayList<>());
        allPlants = Collections.synchronizedList(new ArrayList<>());
        GameEntity gamelevel = new GameEntity(level);
        zombieList1 = gamelevel.getZombieList1();
        zombieList2 = gamelevel.getZombieList2();
        status = LevelMenuController.status;
    }

    public List<Zombie> getAllZombie() {
        return allZombie;
    }

    public List<Plant> getAllPlants() {
        return allPlants;
    }

    public ArrayList<Integer> getZombieList1() {
        return zombieList1;
    }

    public ArrayList<Integer> getZombieList2() {
        return zombieList2;
    }

    public boolean getStatus() {
        return status;
    }

    public int getSunCount() {
        return sunCount;
    }
}
