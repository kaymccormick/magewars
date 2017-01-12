package us.heptet.magewars.test.game;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Description;
import org.jmock.api.Action;
import org.jmock.api.Invocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.heptet.magewars.game.events.AcceptsEvent;
import us.heptet.magewars.game.events.RPCEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jade on 15/09/2016.
 */
public class ConvertAndSendAction implements Action {
    private static Logger logger = LoggerFactory.getLogger(ConvertAndSendAction.class);

    String message;
    ObjectMapper objectMapper = new ObjectMapper();
    List<AcceptsEvent> acceptsEventList = new ArrayList<>();

    @Override
    public void describeTo(Description description) {
        /**/

    }

    @Override
    public Object invoke(Invocation invocation) throws Throwable {
        RPCEvent rpcEvent = (RPCEvent)invocation.getParameter(1);
        RPCEvent newEvent = objectMapper.readValue(objectMapper.writeValueAsString(rpcEvent), RPCEvent.class);

        acceptsEventList.forEach(acceptsEvent -> {
            try {
                acceptsEvent.acceptEvent(newEvent.getEvent());
            } catch(Exception ex)
            {
                logger.error("exception: {}", ex.getMessage(), ex);
            }
        });
        return null;
    }

    /**
     * Add acceptsEvent
     * @param eventManager
     */
    public void add(AcceptsEvent eventManager) {
        acceptsEventList.add(eventManager);
    }
}
