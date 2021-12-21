package entity.zombie;

import entity.Zombie;
import javafx.scene.layout.Pane;

/**
 * The type Bucket head zombie.
 */
public class BucketHeadZombie extends Zombie {

    /**
     * Instantiates a new Bucket head zombie.
     *
     * @param x    the x
     * @param y    the y
     * @param lane the lane
     */
    public BucketHeadZombie(int x, int y, int lane) {
        super(22, 3, x, y, 65, 120, lane, "/gif/bucketheadzombie.gif");
    }

    @Override
    public void attacking(Pane pane) {
    }

    @Override
    public void attacking() {
        super.eatPlant();
    }
}
