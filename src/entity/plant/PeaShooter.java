package entity.plant;

/**
 * The type Pea shooter.
 */
public class PeaShooter extends Shooter {

    /**
     * Instantiates a new Pea shooter.
     *
     * @param x      the x
     * @param y      the y
     * @param column the column
     * @param row    the row
     */
    public PeaShooter(int x, int y, int column, int row) {
        super(x, y, "/gif/peashooter.gif", 100, 60, 62, column, row);
    }

}

