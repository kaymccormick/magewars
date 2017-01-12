package us.heptet.magewars.uitest.uiTest.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import javafx.beans.property.SimpleIntegerProperty;
import us.heptet.magewars.domain.game.BaseCardSet;
import us.heptet.magewars.domain.game.CardSet;
import us.heptet.magewars.domain.game.GameElementFactory;
import us.heptet.magewars.domain.game.Player;
import us.heptet.magewars.domain.game.SpellBook;
import us.heptet.magewars.domain.game.mages.BeastMaster;
import us.heptet.magewars.ui.SpellBookViewer;
import us.heptet.magewars.ui.UiFactory;
import us.heptet.magewars.ui.gwt.GwtUiFactory;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Entry point classes define <code>onModuleLoad()</code>
 */
public class UiTest implements EntryPoint {
    private static Logger logger = Logger.getLogger("UiTestLogger");
    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
            @Override
            public void onUncaughtException(Throwable throwable) {
                String s = "";
                Throwable cause = throwable.getCause();
                for (int i = 0; i < cause.getStackTrace().length; i++) {
                    s = s + cause.getStackTrace()[i].toString() + "<br>\r\n";

                }
                logger.log(Level.SEVERE, cause.toString() + "\r\n" + s);
            }
        });

        logger.info("here");
        //TestClass testClass = new TestClass();
        //RootPanel.get("slot1").add((Widget)testClass.createButton("foo").getControl());
        UiFactory uiFactory = new GwtUiFactory();
        //simpleIntegerProperty.set(5);

        CardSet cardSet = new BaseCardSet();
        Player player = GameElementFactory.createPlayer(0);

        BeastMaster beastMaster = new BeastMaster(cardSet);
        beastMaster.initializeSpellBook();
        SpellBook spellBook = beastMaster.getSpellBook();
        SpellBookViewer spellBookViewer = new SpellBookViewer(uiFactory);
        spellBookViewer.initForPlayer(player, spellBook);
        Widget widget = (Widget)spellBookViewer.getControl().getControl();
        logger.info("widget = " + widget.toString());

        SimpleIntegerProperty i = new SimpleIntegerProperty();
        i.set(5);
        logger.info("I = " + i.toString());
        RootPanel.get().add(widget);
    }

    private static class MyAsyncCallback implements AsyncCallback<String> {
        private Label label;

        public MyAsyncCallback(Label label) {
            this.label = label;
        }

        public void onSuccess(String result) {
            label.getElement().setInnerHTML(result);
        }

        public void onFailure(Throwable throwable) {
            label.setText("Failed to receive answer from server!");
        }
    }
}
