package us.heptet.magewars.ui.model;

import us.heptet.magewars.domain.game.Card;
import us.heptet.magewars.domain.game.PlayerCard;
import us.heptet.magewars.domain.game.SpellCaster;

/**
* Created by kay on 6/18/2014.
*/
public class SpellSlot
{

    private String spellSlotName;
    private SpellCaster spellCaster;
    //1.8 Predicate<_Card> predicate;
    private PlayerCard<? extends Card> playerCard;
    //SpellCasterCard castingSpellCasterCard;

    /**
     *
     * @param s
     */
    public SpellSlot(String s) {
        this.setSpellSlotName(s);
    }

    /**
     *
     * @param spellSlotName
     * @param spellCaster
     */
    public SpellSlot(String spellSlotName, SpellCaster spellCaster)
    {
        this.setSpellCaster(spellCaster);
        this.setSpellSlotName(spellSlotName);
    }


    public void setPlayerCard(PlayerCard<? extends Card> playerCard)
    {
        this.playerCard = playerCard;
    }

    /**
     * Clear the slot
     */
    public void clear() {
        setPlayerCard(null);
    }

    public String getSpellSlotName() {
        return spellSlotName;
    }

    public void setSpellSlotName(String spellSlotName) {
        this.spellSlotName = spellSlotName;
    }

    public SpellCaster getSpellCaster() {
        return spellCaster;
    }

    public void setSpellCaster(SpellCaster spellCaster) {
        this.spellCaster = spellCaster;
    }

    public PlayerCard<? extends Card> getPlayerCard() {
        return playerCard;
    }

    public boolean isEmpty() { return getPlayerCard() == null; }

}
