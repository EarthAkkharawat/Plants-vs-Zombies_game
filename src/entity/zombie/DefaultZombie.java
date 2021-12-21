package entity.zombie;

import entity.Zombie;
import javafx.scene.layout.Pane;

/**
 * The type Default zombie.
 */
public class DefaultZombie extends Zombie {

    /**
     * Instantiates a new Default zombie.
     *
     * @param x    the x
     * @param y    the y
     * @param lane the lane
     */
    public DefaultZombie(int x, int y, int lane) {
        super(5, 2, x, y, 68, 118, lane, "/gif/defaultzombie.gif");
    }

    @Override
    public void attacking() {
        super.eatPlant();
    }

    @Override
    public void attacking(Pane pane) {
    }
}
