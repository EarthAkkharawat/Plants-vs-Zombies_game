package entity.plant;

import entity.Plant;
import javafx.scene.layout.Pane;

public class Wallnut extends Plant {

    // Constructor
    public Wallnut(int x, int y, int column, int row) {
        super(x, y, 60, 75, "/gif/walnut_full_life.gif", 400, column, row);
    }

    // Methods
    public void checkHealthPoint() {
        if (getHealthpoint() <= 0) {
            getImage().setVisible(false);
            getImage().setDisable(true);
        }
    }

    @Override
    public void attacking(Pane pane) {
    }

    @Override
    public void attacking() {
    }
}

