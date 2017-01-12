package us.heptet.magewars.webapps.vaadin;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import us.heptet.magewars.domain.game.BaseCardSet;
import us.heptet.magewars.domain.game.CardSet;
import us.heptet.magewars.domain.game.GameElementFactory;
import us.heptet.magewars.domain.game.Player;
import us.heptet.magewars.domain.game.SpellBookManager;
import us.heptet.magewars.domain.game.mages.BeastMaster;
import us.heptet.magewars.ui.SpellBookViewer;
import us.heptet.magewars.ui.UiFactory;
import us.heptet.magewars.ui.vaadin.VaadinUiFactory;

/* Created by kay on 4/11/2014. */
/**
 *
 */
public class MageWarsApplication extends UI {
    @Override
    protected void init(VaadinRequest vaadinRequest) {
        UiFactory uiFactory = new VaadinUiFactory();
        SpellBookViewer spellBookViewer = new SpellBookViewer(uiFactory);
        CardSet cardSet = new BaseCardSet();
        BeastMaster beastMaster = new BeastMaster();
        SpellBookManager spellBookManager = new SpellBookManager(cardSet);
        spellBookManager.initializeMageSpellbook(beastMaster);
        Player player = GameElementFactory.createPlayer(0);
        spellBookViewer.initForPlayer(player, beastMaster.getSpellBook());
        //VerticalLayout layout = new VerticalLayout();
        setContent((Component)spellBookViewer.getControl().getControl());
        //layout.addComponent(new Label("Hello, world!"));
    }
}
