package entity.zombie;


import entity.Plant;
import entity.Zombie;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import logic.GameController;

public class FunnelHeadZombie extends Zombie {

    // Constructor
    public FunnelHeadZombie(int x, int y, int lane) {
        super(12, 2, x, y, 134, 124, lane, "/gif/funnelheadzombie.gif");
    }

    // Methods
    @Override
    public void attacking(Pane pane) {
    }

    @Override
    public void attacking() {
        eatPlant();
    }

    @Override
    public void eatPlant() {
        synchronized (GameController.allPlants) {
            for (Plant plant : GameController.allPlants) {
                if (plant.getRow() == getLane()) {
                    if (Math.abs(plant.getX() - getImage().getX()) <= 20) {
                        super.actEat(plant);
                    }
                }
            }
        }
    }
}
