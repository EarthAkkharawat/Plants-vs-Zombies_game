package entity;

import entity.base.Entity;
import javafx.scene.effect.Glow;
import javafx.scene.layout.Pane;

/**
 * The type Shovel.
 */
public class Shovel extends Entity {

    private static boolean isDisabled = true;
    private static Shovel shovel;

    private Shovel() {
        super(680, 10, 58, 58, "/images/Shovel.png");
    }

    /**
     * Is disabled boolean.
     *
     * @return the boolean
     */
    public boolean IsDisabled() {
        return isDisabled;
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static Shovel getInstance() {
        if (shovel == null) {
            shovel = new Shovel();
        }
        return shovel;
    }

    @Override
    public void buildImage(Pane pane) {
        super.buildImage(pane);
        shovel.getImage().setOnMouseClicked(e -> {
            isDisabled = false;
            shovel.enable();
            SideElement.setCardSelectedToNull();
        });
    }

    /**
     * Enable.
     */
    public void enable() {
        Glow glow = new Glow();
        shovel.getImage().setEffect(glow);
        glow.setLevel(0.4);
    }

    /**
     * Disable.
     */
    public void disable() {
        if (!isDisabled) {
            Glow glow = (Glow) shovel.getImage().getEffect();
            glow.setLevel(0.0);
            isDisabled = true;
        }
    }

}