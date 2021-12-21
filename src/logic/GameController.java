package logic;

import entity.*;
import entity.plant.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.*;

/**
 * The type Game controller.
 */
public class GameController {

    @FXML
    private AnchorPane GamePlayRoot;
    @FXML
    private ImageView lawnImage;
    @FXML
    private Label sunCountLabel;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private int levelNumber;
    @FXML
    private GridPane lawn_grid;

    /**
     * The constant gameStatus.
     */
    public static boolean gameStatus;
    /**
     * The constant sunTimeline.
     */
    public static Timeline sunTimeline;
    /**
     * The constant spawnZombies1.
     */
    public static Timeline spawnZombies1;
    /**
     * The constant spawnZombies2.
     */
    public static Timeline spawnZombies2;
    private static Label sunCountDisplay;
    private double timeElapsed;
    private static int sunCount;
    /**
     * The constant LANE1.
     */
    public static final int LANE1 = 50;
    /**
     * The constant LANE2.
     */
    public static final int LANE2 = 150;
    /**
     * The constant LANE3.
     */
    public static final int LANE3 = 250;
    /**
     * The constant LANE4.
     */
    public static final int LANE4 = 350;
    /**
     * The constant LANE5.
     */
    public static final int LANE5 = 450;
    private GameEntity level;
    /**
     * The All zombies.
     */
    public static List<Zombie> allZombies;
    /**
     * The All plants.
     */
    public static List<Plant> allPlants;
    /**
     * The Zombie list 1.
     */
    public static ArrayList<Integer> zombieList1;
    /**
     * The Zombie list 2.
     */
    public static ArrayList<Integer> zombieList2;
    private GameData data;
    /**
     * The constant wonGame.
     */
    public static int wonGame;
    /**
     * The constant numKilledZombies.
     */
    public static double numKilledZombies = 0;
    /**
     * The Animation timelines.
     */
    public static ArrayList<Timeline> animationTimelines;
    /**
     * The constant theme.
     */
    public static String theme = "day";
    private Shovel shovel;
    private int spawnedZombies = 0;

    /**
     * Initialize.
     */
    public void initialize() {
        Media wave = new Media(Objects.requireNonNull(getClass().getResource("/sounds/zombies_are_coming.wav")).toString());
        MediaPlayer mediaPlayer = new MediaPlayer(wave);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setStartTime(Duration.seconds(0));
        mediaPlayer.setStopTime(Duration.seconds(5));
        mediaPlayer.play();
        sunCountDisplay = sunCountLabel;
        allZombies = Collections.synchronizedList(new ArrayList<>());
        allPlants = Collections.synchronizedList(new ArrayList<>());
        gameStatus = true;
    }

    /**
     * Initialize data.
     *
     * @param level    the level
     * @param gameData the game data
     */
    @FXML
    public void initializeData(int level, GameData gameData) {
        wonGame = 0;
        animationTimelines = new ArrayList<>();
        zombieList1 = gameData.getZombieList1();
        zombieList2 = gameData.getZombieList2();
        allPlants = gameData.getAllPlants();
        allZombies = gameData.getAllZombie();
        sunCount = gameData.getSunCount();
        LevelMenuController.status = gameData.getStatus();
        levelNumber = level;
        this.level = new GameEntity(level);
        Random rand = new Random();
        startAnimations();
        shovel = Shovel.getInstance();
        shovel.buildImage(GamePlayRoot);
        sunCountDisplay.setText(String.valueOf(sunCount));
        this.data = gameData;
        SideElement.getSideElements(level, GamePlayRoot);
        gameProgress();
        if (LevelMenuController.status) {
            fallingSuns(rand);
        } else {
            String lawnPath = Objects.requireNonNull(getClass().getResource("/images/lawn_night.png")).toString();
            Image lawn = new Image(lawnPath, 1068, 600, false, false);
            lawnImage.setImage(lawn);
        }
        zombieSpawner1(rand, 25);
        zombieSpawner2(rand, 40);
    }

    /**
     * Start animations.
     */
    public void startAnimations() {
        synchronized (allPlants) {
            for (Plant plant : allPlants) {
                plant.buildImage(lawn_grid);
                plant.attacking(GamePlayRoot);
            }
        }
        synchronized (allZombies) {
            for (Zombie zombie : allZombies) {
                zombie.buildImage(GamePlayRoot);
                zombie.moveZombie();
            }
        }
        numKilledZombies = level.getTotalZombies() * timeElapsed;
        progressBar.setProgress(timeElapsed);
    }

    /**
     * Game progress.
     */
    public void gameProgress() {
        Timeline timelineStatus = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            try {
                timeElapsed = (numKilledZombies / level.getTotalZombies());
                progressBar.setProgress(timeElapsed);
                if (wonGame == -1) {
                    numKilledZombies = 0;
                    endAnimations();
                    gameLost();
                } else if (wonGame == 0 && allZombies.size() == 0 && level.getTotalZombies() == spawnedZombies) {
                    numKilledZombies = 0;
                    endAnimations();
                    gameWon();
                } else if (progressBar.getProgress() >= 1) {
                    spawnZombies1.stop();
                    spawnZombies2.stop();
                    endAnimations();
                    gameWon();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
        timelineStatus.setCycleCount(Timeline.INDEFINITE);
        timelineStatus.play();
        animationTimelines.add(timelineStatus);
    }

    /**
     * Game lost.
     *
     * @throws IOException the io exception
     */
    public void gameLost() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EndGame.fxml"));
        AnchorPane Apane = fxmlLoader.load();
        EndGameController endController = fxmlLoader.getController();
        endController.endGameUI(levelNumber, false);
        GamePlayRoot.getChildren().setAll(Apane);

    }

    /**
     * Game won.
     *
     * @throws IOException the io exception
     */
    public void gameWon() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EndGame.fxml"));
        AnchorPane Apane = fxmlLoader.load();
        EndGameController controller = fxmlLoader.getController();
        controller.endGameUI(levelNumber, true);
        GamePlayRoot.getChildren().setAll(Apane);

    }

    /**
     * End animations.
     */
    public static void endAnimations() {
        for (Timeline animationTimeline : animationTimelines) {
            animationTimeline.stop();
        }
    }

    /**
     * End zombie spawn 1.
     */
    public void endZombieSpawn1() {
        spawnZombies1.stop();
    }

    /**
     * End zombie spawn 2.
     */
    public void endZombieSpawn2() {
        spawnZombies2.stop();
    }

    /**
     * Game menu loader.
     *
     * @throws IOException the io exception
     */
    @FXML
    public void GameMenuLoader() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("GameMenu.fxml"));
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setTitle("Game Menu");
        stage.setScene(new Scene(fxmlLoader.load()));
        GameMenuController controller = fxmlLoader.getController();
        controller.initializeData(GamePlayRoot, levelNumber, data, allPlants);
        stage.show();
    }

    /**
     * Update sun count.
     *
     * @param val the val
     */
    public static void updateSunCount(int val) {
        sunCount += val;
        getSunCountLabel().setText(Integer.toString(sunCount));
    }

    /**
     * Gets sun count label.
     *
     * @return the sun count label
     */
    public static Label getSunCountLabel() {
        return sunCountDisplay;
    }

    /**
     * Remove plant.
     *
     * @param plant the plant
     */
    public static void removePlant(Plant plant) {
        plant.setHealthpoint(0);
        allPlants.remove(plant);
    }

    /**
     * Falling suns.
     *
     * @param ran the ran
     */
    public void fallingSuns(Random ran) {
        Timeline sunDrop = new Timeline(new KeyFrame(Duration.seconds(12), event -> {
            int sunPosition = ran.nextInt(950);
            Sun sun = new Sun(sunPosition, 0, true);
            sun.buildImage(GamePlayRoot);
            sun.fallingSun();
        }));
        sunDrop.setCycleCount(Timeline.INDEFINITE);
        sunDrop.play();
        sunTimeline = sunDrop;
        animationTimelines.add(sunDrop);
    }

    private int laneSelect(int lane) {
        switch (lane) {
            case 0 -> {
                return LANE1;
            }
            case 1 -> {
                return LANE2;
            }
            case 2 -> {
                return LANE3;
            }
            case 3 -> {
                return LANE4;
            }
            default -> {
                return LANE5;
            }
        }
    }

    /**
     * Spawn zombies.
     *
     * @param zombies    the zombies
     * @param lane       the lane
     * @param laneNumber the lane number
     */
    public void spawnZombies(ArrayList<Integer> zombies, int lane, int laneNumber) {
        switch (zombies.get(0)) {
            case 1 -> {
                GameEntity.spawnDefaultZombie(GamePlayRoot, lane, laneNumber);
                zombies.remove(0);
                spawnedZombies += 1;
            }
            case 2 -> {
                GameEntity.spawnFunnelHeadZombie(GamePlayRoot, lane, laneNumber);
                zombies.remove(0);
                spawnedZombies += 1;
            }
            case 3 -> {
                GameEntity.spawnBucketHeadZombie(GamePlayRoot, lane, laneNumber);
                zombies.remove(0);
                spawnedZombies += 1;
            }
        }
    }

    /**
     * Zombie spawner 1.
     *
     * @param rand the rand
     * @param time the time
     */
    public void zombieSpawner1(Random rand, double time) {
        Timeline spawnZombie1 = new Timeline(new KeyFrame(Duration.seconds(time), event -> {
            int laneNumber = rand.nextInt(5);
            int lane = laneSelect(laneNumber);
            try {
                spawnZombies(zombieList1, lane, laneNumber);
            } catch (IndexOutOfBoundsException e) {
                endZombieSpawn1();
            }
        }));

        spawnZombie1.setCycleCount(Timeline.INDEFINITE);
        spawnZombie1.play();
        spawnZombies1 = spawnZombie1;
        animationTimelines.add(spawnZombie1);
    }

    /**
     * Zombie spawner 2.
     *
     * @param rand the rand
     * @param time the time
     */
    public void zombieSpawner2(Random rand, double time) {
        Timeline spawnZombie2 = new Timeline(new KeyFrame(Duration.seconds(time), event -> {
            int laneNumber = rand.nextInt(5);
            int lane = laneSelect(laneNumber);
            try {
                spawnZombies(zombieList2, lane, laneNumber);
            } catch (IndexOutOfBoundsException e) {
                endZombieSpawn2();
            }
        }));
        spawnZombie2.setCycleCount(Timeline.INDEFINITE);
        spawnZombie2.play();
        animationTimelines.add(spawnZombie2);
        spawnZombies2 = spawnZombie2;
    }

    /**
     * Gets grid position.
     *
     * @param event the event
     */
    @FXML
    public void getGridPosition(MouseEvent event) {
        Node source = (Node) event.getSource();
        Integer rowIndex = GridPane.getRowIndex(source);
        Integer colIndex = GridPane.getColumnIndex(source);
        if (!shovel.IsDisabled()) {
            if (colIndex != null && rowIndex != null) {
                Media jostle = new Media(Objects.requireNonNull(getClass().getResource("/sounds/plant.wav")).toString());
                MediaPlayer mediaPlayer = new MediaPlayer(jostle);
                mediaPlayer.setAutoPlay(true);
                mediaPlayer.play();
                synchronized (allPlants) {
                    for (Plant plant : allPlants) {
                        if (plant.getColumn() == colIndex && plant.getRow() == rowIndex) {
                            removePlant(plant);
                            break;
                        }
                    }
                }
                shovel.disable();
            }
        }
        if (SideElement.getCardSelected() != -1) {
            if (colIndex != null && rowIndex != null) {
                boolean drop = true;
                synchronized (allPlants) {
                    for (Plant plant : allPlants) {
                        if (plant.getColumn() == colIndex && plant.getRow() == rowIndex) {
                            drop = false;
                            break;
                        }
                    }
                }
                if (drop) {
                    if (SideElement.getElement(SideElement.getCardSelected()).getCost() <= sunCount) {
                        dropPlant(SideElement.getCardSelected(), (int) (source.getLayoutX() + source.getParent().getLayoutX()), (int) (source.getLayoutY() + source.getParent().getLayoutY()), colIndex, rowIndex);
                        updateSunCount((-1) * SideElement.getElement(SideElement.getCardSelected()).getCost());
                        SideElement.getElement(SideElement.getCardSelected()).setDisabledOn(GamePlayRoot);
                    }
                }
            }
            SideElement.setCardSelectedToNull();
        }

    }

    /**
     * Drop plant.
     *
     * @param value the value
     * @param x     the x
     * @param y     the y
     * @param col   the col
     * @param row   the row
     */
    public void dropPlant(int value, int x, int y, int col, int row) {
        Plant plant;
        Media plantSound = new Media(Objects.requireNonNull(getClass().getResource("/sounds/plant.wav")).toString());
        MediaPlayer mediaPlayer = new MediaPlayer(plantSound);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.play();
        switch (value) {
            case 1 -> {
                plant = new SunFlower(x, y, col, row);
                allPlants.add(plant);
                plant.buildImage(lawn_grid);
                plant.attacking(GamePlayRoot);
            }
            case 2 -> {
                plant = new PeaShooter(x, y, col, row);
                allPlants.add(plant);
                plant.buildImage(lawn_grid);
                plant.attacking(GamePlayRoot);
            }
            case 3 -> {
                plant = new Wallnut(x, y, col, row);
                allPlants.add(plant);
                plant.buildImage(lawn_grid);
                plant.attacking(GamePlayRoot);
            }
            case 4 -> {
                plant = new CherryBomb(x, y, col, row);
                allPlants.add(plant);
                plant.buildImage(lawn_grid);
                plant.attacking(GamePlayRoot);
            }
            case 5 -> {
                plant = new Repeater(x, y, col, row);
                allPlants.add(plant);
                plant.buildImage(lawn_grid);
                plant.attacking(GamePlayRoot);
            }
            case 6 -> {
                plant = new ChilliPepper(x, y, col, row);
                allPlants.add(plant);
                plant.buildImage(lawn_grid);
                plant.attacking(GamePlayRoot);
            }
        }

    }


}
