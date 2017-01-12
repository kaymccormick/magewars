package us.heptet.magewars.atmosphere;

import org.atmosphere.client.TrackMessageSizeInterceptor;
import org.atmosphere.config.service.Disconnect;
import org.atmosphere.config.service.Get;
import org.atmosphere.config.service.ManagedService;
import org.atmosphere.config.service.Message;
import org.atmosphere.config.service.Post;
import org.atmosphere.config.service.Ready;
import org.atmosphere.cpr.*;
import org.atmosphere.gwt20.managed.AtmosphereMessageInterceptor;
import org.atmosphere.gwt20.server.GwtRpcInterceptor;
import org.atmosphere.gwt20.shared.Constants;
import org.atmosphere.interceptor.AtmosphereResourceLifecycleInterceptor;
import org.atmosphere.interceptor.BroadcastOnPostAtmosphereInterceptor;
import org.atmosphere.interceptor.IdleResourceInterceptor;
import org.atmosphere.interceptor.SuspendTrackerInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.RequestContextUtils;
import us.heptet.magewars.game.events.BaseEvent;
import us.heptet.magewars.game.events.GameEvent;
import us.heptet.magewars.game.events.RPCEvent;
import us.heptet.magewars.game.events.SpellbookSelectionChange;
import us.heptet.magewars.gameservice.core.events.games.UpdateGamePlayerEvent;
import us.heptet.magewars.gameservice.core.service.CombinedGameService;
import us.heptet.magewars.gameservice.persistence.services.GamePersistenceService;

/**
 * Prototype managed service implementation for atmosphere handling.
 *
 * Created by kay on 6/13/2014.
 */


@ManagedService(path = "/*",
        interceptors = {
                AtmosphereResourceLifecycleInterceptor.class,
                //TrackMessageSizeInterceptor.class,
                MyGwtRpcInterceptor.class,
                SuspendTrackerInterceptor.class,
              MyInterceptor.class,
//                AtmosphereMessageInterceptor.class,

                //MyBroadcastOnPostAtmosphereInterceptor.class,
                IdleResourceInterceptor.class
}
 /*       interceptors = {
                AtmosphereResourceLifecycleInterceptor.class,
                TrackMessageSizeInterceptor.class,
                GwtRpcInterceptor.class,
                SuspendTrackerInterceptor.class,
                //AtmosphereMessageInterceptor.class,
                //BroadcastOnPostAtmosphereInterceptor.class,
                IdleResourceInterceptor.class
        }*/)
public class ManagedTestService {
    private static Logger logger = LoggerFactory.getLogger(ManagedTestService.class);
    private CombinedGameService gameService;
    //private static java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ManagedTestService.class.getName());



//    @Get
    public void onGet(final AtmosphereResource r)
    {

        AtmosphereRequest req = r.getRequest();
        if(req.getHeader("Upgrade") != null)
        {
            return;
        }
        logger.info("in GET");

        AtmosphereResponse response = r.getResponse();
        response.setContentType("text/html");
        response.write("Hello from " + ManagedTestService.class.getName());
    }

    @Ready
    public void onReady(final AtmosphereResource r) {
        if(r == null)
        {
            logger.warn("atmosphere resource is null.");
        } else {
            if(r.uuid() == null)
            {
                logger.warn("atmosphere resource uuid is null for {}", r);
            }
        }

        logger.debug("setting broadcaster for {} to /{}", r.uuid(), r.uuid());
        r.setBroadcaster(r.getAtmosphereConfig().getBroadcasterFactory().lookup("/" + r.uuid(), true));

        r.addEventListener(new AtmosphereResourceEventListener() {
            @Override
            public void onPreSuspend(AtmosphereResourceEvent event) {
                logger.info("pre suspend {}", event.getResource().uuid());
            }

            @Override
            public void onSuspend(AtmosphereResourceEvent event) {
                logger.info("suspend {}", event.getResource().uuid());
            }

            @Override
            public void onResume(AtmosphereResourceEvent event) {
                logger.info("resume {}", event.getResource().uuid());

            }

            @Override
            public void onDisconnect(AtmosphereResourceEvent event) {
                logger.info("disconnect {}", event.getResource().uuid());
            }

            @Override
            public void onBroadcast(AtmosphereResourceEvent event) {
                logger.info("broadcast {}", event.getResource().uuid());
            }

            @Override
            public void onThrowable(AtmosphereResourceEvent event) {
                logger.info("throwable {}", event.getResource().uuid());
            }

            @Override
            public void onClose(AtmosphereResourceEvent event) {
                logger.info("close {}", event.getResource().uuid());
            }

            @Override
            public void onHeartbeat(AtmosphereResourceEvent event) {
                logger.info("heartbeat {}", event.getResource().uuid());
            }
        });
        logger.info("Received RPC GET");
        //r.getAtmosphereConfig().resourcesFactory().
        // Look up a new Broadcaster used for pushing who is connected.
        //BroadcasterFactory.getDefault().lookup("Connected users", true).addAtmosphereResource(r)


        Broadcaster myBroadcaster = r.getAtmosphereConfig().getBroadcasterFactory().lookup("MyBroadcaster", true);
        logger.info("myBroadcaster = " + myBroadcaster);
        logger.info("adding " + r + " to " + myBroadcaster);
        myBroadcaster.addAtmosphereResource(r);
        //        .broadcast("Browser UUID: " + r.uuid() + " connected.");
    }

    @Disconnect
    public void disconnected(AtmosphereResourceEvent event){
        // isCancelled == true. means the client didn't send the close event, so an unexpected network glitch or browser
        // crash occurred.
        if (event.isCancelled()) {
            logger.info("User:" + event.getResource().uuid() + " unexpectedly disconnected");
        } else if (event.isClosedByClient()) {
            logger.info("User:" + event.getResource().uuid() + " closed the connection");
        }
    }

    //ServerSerializationStreamReader is something we might be able to use to
    // deserialize a payload

    @Post
    public void post(AtmosphereResource r) {
        // Don't need to do anything, the interceptor took care of it for us.
        logger.info("POST received with transport + " + r.transport());
        Object messageObject = r.getRequest().getAttribute(Constants.MESSAGE_OBJECT);
        logger.info("mo = {}", messageObject);
        if(messageObject instanceof RPCEvent)
        {
            RPCEvent rpcEvent = (RPCEvent)messageObject;
            BaseEvent baseEvent = rpcEvent.getEvent();
            logger.info("event type = {}", baseEvent.getEventType());

            if(gameService == null)
            {
                try {
                    WebApplicationContext webApplicationContext =
                            RequestContextUtils.findWebApplicationContext(r.getRequest(), r.getRequest().getServletContext());
                    gameService = (CombinedGameService) webApplicationContext.getBean("gameService");
                } catch(Exception ex)
                {
                    logger.error("{}", ex.toString());
                }
            }

            // FIXME these events need to be properly handled by webservice code, hopefully in a way that makes
            // it possible to use atmosphere in the future
            if(baseEvent.getEventType() == GameEvent.SPELLBOOK_SELECTED) {
                GameEvent gameEvent = (GameEvent) baseEvent;
                gameService.updateGamePlayer(new UpdateGamePlayerEvent(gameEvent.getGameId(),
                        gameEvent.getUsername(), gameEvent.getSpellbookId()));
                r.getAtmosphereConfig().getBroadcasterFactory().lookup("MyBroadcaster").broadcast(rpcEvent);
            }
            if(baseEvent.getEventType() == GameEvent.VIEW_TABLE)
            {
                GameEvent gameEvent = (GameEvent) baseEvent;
                logger.debug("got view table event");
                String o = "/game" + gameEvent.getGameId().toString();
                logger.debug("subscribing atmosphere resource {} to {}", r.uuid(), o);
                logger.info("Suspending AR {}", r.uuid());

                r.suspend();
                r.getAtmosphereConfig().getBroadcasterFactory().lookup(o, true).addAtmosphereResource(r);
            }
            if(baseEvent.getEventType() == GameEvent.START_GAME)
            {
                GameEvent gameEvent = (GameEvent) baseEvent;

            }
            if(baseEvent.getEventType() == GameEvent.GAME_JOINED)
            {
                GameEvent gameEvent = (GameEvent) baseEvent;
                logger.debug("got game joined event");
                String o = "/game" + gameEvent.getGameId().toString();

                r.getAtmosphereConfig().getBroadcasterFactory().lookup(o, true).broadcast(messageObject);
            }
        }
    }


    @Message
    public void message(Object o)
    {
        logger.info("got @Message {} {}", o.getClass().getName(), o);
    }

}
