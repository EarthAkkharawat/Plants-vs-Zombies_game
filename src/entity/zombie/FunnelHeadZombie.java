package entity.zombie;


import entity.Plant;
import entity.Zombie;
import javafx.scene.layout.Pane;
import logic.GameController;

/**
 * The type Funnel head zombie.
 */
public class FunnelHeadZombie extends Zombie {

    /**
     * Instantiates a new Funnel head zombie.
     *
     * @param x    the x
     * @param y    the y
     * @param lane the lane
     */
    public FunnelHeadZombie(int x, int y, int lane) {
        super(12, 2, x, y, 134, 124, lane, "/gif/funnelheadzombie.gif");
    }

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
