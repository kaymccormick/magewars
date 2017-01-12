package us.heptet.magewars.ui.view;

import us.heptet.magewars.domain.game.Player;
import us.heptet.magewars.ui.model.SpellBookPages;
import us.heptet.magewars.ui.model.SpellSlot;

import java.util.List;

/* Created by kay on 6/17/2014. */
/**
 * Plan spells view interface
 */
public interface PlanSpellsView extends View {
    /**
     * Active a page in the spell book viewer
     * @param paneIndex
     */
    void activatePage(int paneIndex);

    /**
     * set visible
     * @param b
     */
    @Override
    void setVisible(boolean b);

    /**
     * Prepare the view
     */
    void prepare();

    /**
     * Set the "Spell slot list"
     * @param spellSlotList
     */
    void setSpellSlotList(List<SpellSlot> spellSlotList);

    /**
     * set the controller for this view.
     * @param controller
     */
    void setController(Controller controller);

    /***
     * Set the model (spell book pages)
     * @param spellBookPages
     */
    void setSpellBookPages(SpellBookPages spellBookPages);

    /**
     * Set the spell slot label text
     * @param spellSlotIndex
     * @param text
     */
    void setSpellSlotLabelText(int spellSlotIndex, String text);

    /**
     * Controller interface
     */
    interface Controller
    {
        /**
         * Handler for "previous page" clicked
         */
        void onPreviousPageClicked();

        /**
         * Handler for "next page" clicked
         */
        void onNextPageClicked();

        /**
         * Handler for "ok button" clicked
         */
        void onOkButtonClicked();

        /**
         * Handler for when a card has been clicked
         * @param page
         * @param row
         * @param col
         */
        void onCardClicked(int page, int row, int col);

        /**
         * show the view for a particular player
         * @param player
         */
        void showForPlayer(Player player);
    }
}
