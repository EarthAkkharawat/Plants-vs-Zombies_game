package entity;

import entity.base.Attackable;
import entity.base.Entity;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

/**
 * The type Plant.
 */
public abstract class Plant extends Entity implements Attackable {

    /**
     * The Row.
     */
    protected int row;
    /**
     * The Column.
     */
    protected int column;
    /**
     * The Healthpoint.
     */
    protected int healthpoint;

    /**
     * Instantiates a new Plant.
     *
     * @param x           the x
     * @param y           the y
     * @param width       the width
     * @param height      the height
     * @param path        the path
     * @param healthpoint the healthpoint
     * @param column      the column
     * @param row         the row
     */
    public Plant(int x, int y, int width, int height, String path, int healthpoint, int column, int row) {
        super(x, y, width, height, path);
        this.healthpoint = healthpoint;
        this.column = column;
        this.row = row;
    }

    /**
     * Build image.
     *
     * @param lawn the lawn
     */
    public void buildImage(GridPane lawn) {
        image = new ImageView();
        image.setImage(new Image(path, width, height, false, false));
        lawn.add(image, column, row, 1, 1);

    }

    /**
     * Gets healthpoint.
     *
     * @return the healthpoint
     */
    public int getHealthpoint() {
        return healthpoint;
    }

    /**
     * Sets healthpoint.
     *
     * @param healthpoint the healthpoint
     */
    public void setHealthpoint(int healthpoint) {
        this.healthpoint = healthpoint;
        checkHealthPoint();
    }

    /**
     * Check health point.
     */
    public abstract void checkHealthPoint();

    /**
     * End animation.
     *
     * @param timeline the timeline
     */
    public void endAnimation(Timeline timeline) {
        timeline.stop();
    }

    /**
     * Gets row.
     *
     * @return the row
     */
    public int getRow() {
        return row;
    }

    /**
     * Gets column.
     *
     * @return the column
     */
    public int getColumn() {
        return column;
    }


}
