package us.heptet.magewars.ui;

import com.google.inject.Singleton;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import us.heptet.magewars.ui.factory.UiFactory;
import us.heptet.magewars.ui.model.SpellBookPages;
import us.heptet.magewars.ui.model.SpellSlot;
import us.heptet.magewars.ui.view.CardView;
import us.heptet.magewars.ui.view.PlanSpellsView;

import javax.inject.Inject;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/* Created by kay on 4/8/2014. */
/**
 * Implementation of {@link PlanSpellsView}
 */
@Component
@Lazy
@Singleton
public class SpellBookViewer implements PlanSpellsView  {
    private static Logger logger = Logger.getLogger(SpellBookViewer.class.getName());

    private Controller controller;
    private UiFactory uiFactory;
    private Button okButton;
    private int curPane;
    private List<SpellSlot> spellSlotList;
    private Container control;
    private Grid outerGrid;
    private SpellBookPages spellBookPages;
    private static final int NUM_ROWS = 2;
    private static final int NUM_COLS = 4;
    private CardView[][] cardViews;
    private List<Label> spellSlotLabels = new ArrayList<>();

    static {
        logger.setLevel(Level.FINEST);
    }

    /* We won't necessarily instantiate this object every time spells must be selected,
        and our "spell slots" will change as the game progresses.
     */

    /**
     * Create the spell book viewer.
     * @param uiFactory
     */
    @Inject
    public SpellBookViewer(UiFactory uiFactory) {
        super();
        logger.info("In SpellBookViewer constructor");
        this.uiFactory = uiFactory;

        control = uiFactory.createControlContainer();
        // This is the wrong number of rows!!!
        outerGrid = uiFactory.createGrid(1, 4);
        control.add(outerGrid);
        control.setId("spell-book-viewer");

        logger.finer("control = " + control.toString());
        logger.finer("control.control = " + control.getControl().toString());

        /* Initialize paging buttons */
        Button leftButton = uiFactory.createButton("<");
        outerGrid.add(leftButton, 0, 0);
        Button rightButton = uiFactory.createButton(">");

        // We need to have a controller or presenter here.
        leftButton.setOnActionHandler(new EventHandler() {
            @Override
            public void handle() {
                controller.onPreviousPageClicked();
            }
        });
        rightButton.setOnActionHandler(new EventHandler() {
            @Override
            public void handle() {
                controller.onNextPageClicked();
            }
        });

        outerGrid.add(rightButton, 2, 0);

        /* initialize ok button */
        okButton = uiFactory.createButton("OK");
        okButton.setOnActionHandler(new EventHandler() {
            @Override
            public void handle() {

                logger.info("Calling controller.onOkButtonClicked");
                controller.onOkButtonClicked();
            }
        });


        Grid grid = uiFactory.createGrid(NUM_ROWS, NUM_COLS);
        grid.setId("spellbook-page-1-gridpane");

        outerGrid.add(grid, 1, 0);
        cardViews = new CardView[NUM_ROWS][NUM_COLS];
        for(int i = 0; i < NUM_ROWS; i++)
        {
            for(int j = 0; j < NUM_COLS; j++)
            {
                final CardView cv = uiFactory.createCardView();
                cardViews[i][j] = cv;
                grid.add(cv, j, i);
                cv.setId("cardview-1-" + (j + 1) + "-" + (i + 1));
                final int row = i;
                final int col = j;
                cv.setOnMouseClicked(new EventHandler() {
                    @Override
                    public void handle() {
                        controller.onCardClicked(curPane, row, col);
                    }
                });
            }
        }
    }

    @Override
    public void prepare()
    {

        logger.fine("entering prepare");
        /* begin player specific stuff */
        this.setVisible(true);

        Deque<SpellSlot> openSpellSlots = new LinkedList<>();
        assert spellSlotList != null : "spellSlotList should not be null";
        openSpellSlots.addAll(spellSlotList);

        /* Initialize Selected Spell Labels */
        final VerticalBox chosenSpells = uiFactory.createVerticalBox();
        // do we need to remove the old contents??
        outerGrid.add(chosenSpells, 3, 0);
        Label selectedSpellsLabel  = uiFactory.createLabel("Selected Spells");
        selectedSpellsLabel.setId("selected-spells-label");
        chosenSpells.add(selectedSpellsLabel);
        spellSlotLabels.clear();
        for(final SpellSlot s:spellSlotList)
        {
            Label label = uiFactory.createLabel(s.getSpellSlotName());
            String id = s.getSpellSlotName().toLowerCase().replace(' ', '-').concat("-slot-label");
            label.setId(id);
            logger.fine("Id is " + id);
            Label selectedLabel = uiFactory.createLabel("");
            selectedLabel.setId("selected-" + id);
            spellSlotLabels.add(selectedLabel);

            chosenSpells.add(label);
            chosenSpells.add(selectedLabel);
        }
        chosenSpells.add(okButton);
    }

    @Override
    public Object getControl() {
        return control.getControl();
    }

    @Override
    public double getWidth() {
        return 0;
    }

    @Override
    public double getHeight() {
        return 0;
    }

    public List<SpellSlot> getSpellSlotList() {
        return spellSlotList;
    }

    @Override
    public void setSpellSlotList(List<SpellSlot> spellSlotList) {
        this.spellSlotList = spellSlotList;
    }
    public Controller getController() {
        return controller;
    }

    //@Autowired
    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void setSpellBookPages(SpellBookPages spellBookPages) {

        this.spellBookPages = spellBookPages;
    }

    @Override
    public void setSpellSlotLabelText(int spellSlotIndex, String text) {
        spellSlotLabels.get(spellSlotIndex).setText(text);
    }

    @Override
    public void setId(String id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setVisible(boolean b) {
        control.setVisible(b);
    }

    @Override
    public void activatePage(int paneIndex) {
        curPane = paneIndex;
        for(int i = 0; i < NUM_ROWS; i++)
        {
            for(int j = 0; j < NUM_COLS; j++)
            {
                cardViews[i][j].setPlayerCard(spellBookPages.getCard(curPane, i, j));
            }
        }
    }
}