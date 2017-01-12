package us.heptet.magewars.atmosphere;

import java.io.IOException;
import java.util.logging.Logger;
import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.DefaultBroadcasterFactory;
import org.atmosphere.gwt20.shared.Constants;
import org.atmosphere.handler.AbstractReflectorAtmosphereHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.RequestContextUtils;
import us.heptet.magewars.gameservice.core.events.games.UpdateGamePlayerEvent;
import us.heptet.magewars.gameservice.core.service.CombinedGameService;
import us.heptet.magewars.game.events.SpellbookSelectionChange;

/*
This class should be obsolete relative to the Managed Handler. It currently handles the spell book selection
changed event.
 */


public class GwtRpcAtmosphereHandler extends AbstractReflectorAtmosphereHandler {

    static final Logger logger = Logger.getLogger(GwtRpcAtmosphereHandler.class.getName());
    @Override
    public void onRequest(AtmosphereResource ar) throws IOException {
        if (ar.getRequest().getMethod().equals("GET") ) {
            doGet(ar);
        } else if (ar.getRequest().getMethod().equals("POST") ) {
            doPost(ar);
        }
    }

    public void doGet(AtmosphereResource ar) {

        // lookup the broadcaster, if not found create it. Name is arbitrary
        ar.setBroadcaster(ar.getAtmosphereConfig().getBroadcasterFactory().lookup("MyBroadcaster", true));

        ar.getAtmosphereConfig().getBroadcasterFactory().lookup("/test", true).addAtmosphereResource(ar);

        ar.suspend();
    }

    /**
     * receive push message from client
     **/
    public void doPost(AtmosphereResource ar) {
        Object msg = ar.getRequest().getAttribute(Constants.MESSAGE_OBJECT);

        if (msg != null) {
            logger.info("received RPC post: " + msg.toString());
            // for demonstration purposes we will broadcast the message to all connections
            if(msg instanceof SpellbookSelectionChange)
            {

                if(gameService == null)
                {
                    WebApplicationContext webApplicationContext =
                            RequestContextUtils.findWebApplicationContext(ar.getRequest(), ar.getRequest().getServletContext());
                    gameService = (CombinedGameService)webApplicationContext.getBean("gameService");
                }
                SpellbookSelectionChange spellbookSelectionChange = (SpellbookSelectionChange)msg;
                getGameService().updateGamePlayer(new UpdateGamePlayerEvent(spellbookSelectionChange.getGameId(),
                        spellbookSelectionChange.getUsername(), spellbookSelectionChange.getSpellbookId()));
            }
            ar.getAtmosphereConfig().getBroadcasterFactory().lookup("MyBroadcaster").broadcast(msg);
        }
    }


    @Override
    public void destroy() {

    }

    CombinedGameService gameService;

    public CombinedGameService getGameService() {
        return gameService;
    }

    @Autowired
    @Required
    public void setGameService(CombinedGameService gameService) {
        this.gameService = gameService;
    }
}
