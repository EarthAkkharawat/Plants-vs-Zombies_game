package entity.zombie;

import entity.Zombie;
import javafx.scene.layout.Pane;

public class BucketHeadZombie extends Zombie {

    // Constructor
    public BucketHeadZombie(int x, int y, int lane) {
        super(22, 4, x, y, 65, 120, lane, "/assets/gif/bucketheadzombie.gif");
    }

    @Override
    public void attacking(Pane pane) {
    }

    @Override
    public void attacking() {
       super.eatPlant();
    }
}
