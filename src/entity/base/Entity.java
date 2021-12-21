package entity.base;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.Objects;

/**
 * The type Entity.
 */
public class Entity {

    /**
     * The X.
     */
    protected int x;
    /**
     * The Y.
     */
    protected int y;
    /**
     * The Width.
     */
    protected int width;
    /**
     * The Height.
     */
    protected int height;
    /**
     * The Image.
     */
    protected ImageView image;
    /**
     * The Path.
     */
    protected String path;

    /**
     * Instantiates a new Entity.
     *
     * @param x      the x
     * @param y      the y
     * @param width  the width
     * @param height the height
     * @param path   the path
     */
    public Entity(int x, int y, int width, int height, String path) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.path = Objects.requireNonNull(getClass().getResource(path)).toString(); // Url of image
    }

    /**
     * Build image.
     *
     * @param pane the pane
     */
// Methods
    public void buildImage(Pane pane) {
        image = new ImageView();
        image.setImage(new Image(path, width, height, false, false));
        image.setX(x);
        image.setY(y);
        pane.getChildren().add(image);
    }

    /**
     * Gets x.
     *
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * Sets x.
     *
     * @param x the x
     */
    public void setX(int x) {
        this.x = x;
        image.setX(x);
    }

    /**
     * Gets y.
     *
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * Sets y.
     *
     * @param y the y
     */
    public void setY(int y) {
        this.y = y;
        image.setY(y);
    }

    /**
     * Gets image.
     *
     * @return the image
     */
    public ImageView getImage() {
        return image;
    }

    /**
     * Sets image.
     *
     * @param image the image
     */
    public void setImage(ImageView image) {
        this.image = image;
    }

    /**
     * Gets path.
     *
     * @return the path
     */
    public String getPath() {
        return path;
    }

}
