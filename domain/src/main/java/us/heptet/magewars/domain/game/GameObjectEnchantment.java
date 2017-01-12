package us.heptet.magewars.domain.game;

/* Created by kay on 3/27/14. */
/**
 * Represents a game object enchantment.
 */
public class GameObjectEnchantment extends GameObjectBase {
    private GameObject gameObject;
    private boolean revealed;

    /***
     *  Default constructor for deserialization - to construct, use {@link GameElementFactory}
     */
    public GameObjectEnchantment() {
        /* For serialization */
    }

    GameObjectEnchantment(GameObject gameObject, PlayerCard enchantment)
    {
        super(enchantment);
        this.gameObject = gameObject;
    }

    @Override
    public String toString() {
        return "GameObjectEnchantment{" +
                "playerCard=" + getPlayerCard() +
                ", gameObject=" + gameObject +
                ", revealed=" + revealed +
                '}';
    }

    public GameObject getGameObject() {
        return gameObject;
    }

    public void setGameObject(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    public boolean isRevealed() {
        return revealed;
    }

    public void setRevealed(boolean revealed) {
        this.revealed = revealed;
    }


}
