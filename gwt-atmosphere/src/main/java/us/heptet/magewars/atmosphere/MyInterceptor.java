package us.heptet.magewars.atmosphere;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.atmosphere.cpr.Action;
import org.atmosphere.cpr.AtmosphereConfig;
import org.atmosphere.cpr.AtmosphereInterceptorAdapter;
import org.atmosphere.cpr.AtmosphereRequest;
import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.gwt20.server.GwtRpcUtil;
import org.atmosphere.gwt20.shared.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.heptet.magewars.game.events.BaseEvent;
import us.heptet.magewars.game.events.GameEvent;
import us.heptet.magewars.game.events.RPCEvent;

import java.io.IOException;

/* Created by kay on 6/14/2014. */
/**
 *
 *
 * * This class appepars to be used for non-gwt-rpc cloents in order to process their json messages and place them into the message_object attribute of the request
 *  (gwt_deserialized_object). Should be renamed to something better than MyInterceptor.
 */
public class MyInterceptor extends AtmosphereInterceptorAdapter {
    private static Logger logger = LoggerFactory.getLogger(MyInterceptor.class);
    private ObjectMapper mapper = new ObjectMapper();//.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
    MyBroadcastFilter filter = new MyBroadcastFilter();

    @Override
    public void configure(AtmosphereConfig config) {
        config.framework().broadcasterFilters(filter);
    }

    @Override
    public Action inspect(AtmosphereResource r) {
        AtmosphereRequest request = r.getRequest();
        if (request.getMethod().equals("POST")) {
            String ctype = request.getContentType();
            if(ctype == null || ctype.contains("x-gwt-rpc") == false)
            {
                try {
                    logger.info("!!! reading json value");
                    String s = GwtRpcUtil.readerToString(request.getReader());
                    logger.info("got json value {}", s);
                    logger.info("{}", GameEvent.class.getResource("GameEvent.class"));
                    logger.info("{}", BaseEvent.class.getResource("BaseEvent.class"));
                    logger.info("{}", BaseEvent.class.isAssignableFrom(GameEvent.class));
                    Object object = mapper.readValue(s, RPCEvent.class);
                    r.getRequest().setAttribute(Constants.MESSAGE_OBJECT, object);
                } catch(IOException ex)
                {
                    logger.info("!!! exception " + ex);
                }

            }
            //if(ctype.contains("x-gwt-rpc"))
	    /*            try {
                String data = GwtRpcUtil.readerToString(r.getRequest().getReader());
                if (logger.isDebugEnabled()) {
                    logger.debug("Received message from client: " + data);
                }
		//                Object object = mapper.setTypeFactory(new )
		//                r.getRequest().setAttribute(Constants.MESSAGE_OBJECT, object);
            } catch (IOException ex) {
                logger.error("Failed to read request data", ex);
            } catch (SerializationException ex) {
                logger.error("Failed to deserialize GWT RPC data");
		}*/
        }
        return Action.CONTINUE;
    }
}
