package us.heptet.magewars.ui.javafx;

import org.springframework.beans.factory.InitializingBean;
import us.heptet.magewars.game.events.EventManagerImpl;

/* Created by kay on 7/7/2014. */
/**
 * TODO Never used, to be deleted.
 */
public class InitializingEventManager extends EventManagerImpl implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {
        connectChannels();
    }
}
