package us.heptet.magewars.ui.controller;

import org.springframework.stereotype.Component;
import us.heptet.magewars.domain.game.Card;
import us.heptet.magewars.domain.game.Player;
import us.heptet.magewars.domain.game.PlayerCard;
import us.heptet.magewars.domain.game.SpellBook;
import us.heptet.magewars.ui.model.CardViewSelectionData;
import us.heptet.magewars.ui.model.SpellBookPages;
import us.heptet.magewars.ui.model.SpellSlot;
import us.heptet.magewars.ui.view.PlanSpellsView;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/* Created by kay on 6/17/2014. */
/**
 * Plan spells controller.
 */
@Component
public class PlanSpellsController implements Controller, PlanSpellsView.Controller {
    private static Logger logger = Logger.getLogger(PlanSpellsController.class.getName());
    private PlanSpellsView view;
    private int activatedPage;
    private int numPages;
    private int numRows = 2;
    private int numCols = 4;
    private List<SpellSlot> spellSlotList;
    private SpellBookPages spellBookPages;
    private boolean spellsSelectable;
    private CardViewSelectionData[][][] selectionData;

    static {
        logger.setLevel(Level.FINEST);
    }

    public PlanSpellsController() {
        /* nothing */
    }

    /**
     * Create the controller with the dependent view.
     * @param view
     */
    @Inject
    public PlanSpellsController(PlanSpellsView view) {
        this.view = view;
        view.setController(this);
    }

    @Override
    public void onPreviousPageClicked() {
        if (activatedPage > 0) {
            //
            activatedPage--;
            activatePage(activatedPage);
        }
    }

    @Override
    public void onNextPageClicked() {
        if (activatedPage + 1 < numPages)
        {
            activatedPage++;
            activatePage(activatedPage);
        }
    }

    private void activatePage(int activatedPage) {
        view.activatePage(activatedPage);
    }

    @Override
    public void onOkButtonClicked() {
        logger.fine("OK button clicked");
        // we should call an event handler but for now we can simply hide the control
        view.setVisible(false);
    }

    @Override
    public void onCardClicked(int page, int row, int col) {
        PlayerCard<? extends Card> playerCard = spellBookPages.getCard(page, row, col);
        if(!spellsSelectable)
        {
            return;
        }

        /* Determine selection state of the CardView */
        CardViewSelectionData cardSelect = getCardViewSelectionData(page, row, col);

       /* we don't need to create the Selection Data if all the spell slots are full */
        if(cardSelect == null)
        {
            cardSelect = new CardViewSelectionData();
            setCardViewSelectionData(page, row, col, cardSelect);
        }
        SpellSlot newSlot = null;
        int newSelectedIndex = 0;
        int curIndex = 0;
        /* IF the card is selected already, start at the spell slot index following
        the one its currently in
         */
        if(cardSelect.isSelected())
        {
            curIndex = cardSelect.getSpellSlotIndex() + 1;
        }

        SpellSlot slot;
        while(curIndex < spellSlotList.size())
        {
            slot = spellSlotList.get(curIndex);
            /* if the current slot will accept the card */
            if(slot.isEmpty())
            //&& (slot.castingSpellCasterCard == null || slot.castingSpellCasterCard.canCast(cv.getCard())))
            {
                newSlot = slot;
                newSelectedIndex = curIndex;
                break;
            }
            curIndex++;
        }
        /* if we're moving the card to the next available slot or deselecting it */
        // seems odd to check both predicates - what if only one of these is true?
        if(cardSelect.isSelected() && cardSelect.getSpellSlot() != null)
        {
            // Clear the currently selected slot, since we're preparing to move the card either to the next open
            // slot or deselecting it.
            view.setSpellSlotLabelText(cardSelect.getSpellSlotIndex(), "");
            cardSelect.getSpellSlot().clear();
        }
        /* if there's an available slot */
        if(newSlot != null)
        {
            cardSelect.setSpellSlot(newSlot);
            cardSelect.setSpellSlotIndex(newSelectedIndex);
            // The card could already be selected, but may not be
            cardSelect.setSelected(true);

            newSlot.setPlayerCard(playerCard);
            // Set new slot label
            view.setSpellSlotLabelText(newSelectedIndex, playerCard.getCard().getCardName());
        }
        /* if not we're unselecting, label has already been cleared */
        else if(cardSelect.isSelected())
        {
            // some redunancy here maybe
            cardSelect.setSelected(false);
            cardSelect.getSpellSlot().clear();
            cardSelect.setSpellSlot(null);
        }
    }

    private void setCardViewSelectionData(int page, int row, int col, CardViewSelectionData selectData) {
        selectionData[page][row][col] = selectData;
    }

    private CardViewSelectionData getCardViewSelectionData(int page, int row, int col) {
        return selectionData[page][row][col];
    }

    @Override
    public void showForPlayer(Player player) {
        logger.info("showing spell book viewer");


        spellsSelectable = true;

        assert player != null : "player should not be null";
        assert player.getMagePlayerCard() != null : "player magePlayerCard should not be null";
        assert player.getMagePlayerCard().getCard() != null : "player.magePlayerCard.card should not be null";

        SpellBook spellBook = player.getSpellBook();


        spellBookPages = new SpellBookPages(spellBook, numRows, numCols);
        numPages = spellBookPages.getNumPages();
        selectionData = new CardViewSelectionData[numPages][numRows][numCols];
        view.setSpellBookPages(spellBookPages);

        spellSlotList = new ArrayList<>();
        spellSlotList.add(new SpellSlot("Spell 1"));
        spellSlotList.add(new SpellSlot("Spell 2"));

        view.setSpellSlotList(spellSlotList);
        // Prepare sets up the labels for the list we just set. We could always roll them into one
        // method - prepare was "initforplayer" method that did a buncha stuff
        view.prepare();

        activatedPage = 0;
        activatePage(0);

        /*
        We should find a new place for this to live - ensure this code is integrated into javafx implementation
        Region spellChooser = (Region)spellBookViewer.getControl().getControl();

        spellChooser.setId("spell-chooser");

        spellChooser.layoutXProperty().bind(widthProperty().subtract(spellChooser.widthProperty()).divide(2.0));
        spellChooser.layoutYProperty().bind(heightProperty().subtract(spellChooser.heightProperty()).divide(2.0));
        spellChooser.setOpacity(0);
        overlay.getChildren().add(spellChooser);
        if(getPerformAnimations()) {
            FadeTransition f = new FadeTransition(Duration.millis(1000.0), spellChooser);
            f.setFromValue(0);
            f.setToValue(1);

            f.play();
        }
        else
        {
            spellChooser.setOpacity(1);
        }
        */
    }

    public PlanSpellsView getView() {
        return view;
    }
}
