package entity.plant;

import entity.Plant;
import javafx.scene.layout.Pane;

/**
 * The type Wallnut.
 */
public class Wallnut extends Plant {

    /**
     * Instantiates a new Wallnut.
     *
     * @param x      the x
     * @param y      the y
     * @param column the column
     * @param row    the row
     */
    public Wallnut(int x, int y, int column, int row) {
        super(x, y, 60, 75, "/gif/walnut_full_life.gif", 400, column, row);
    }

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

