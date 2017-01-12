package us.heptet.magewars.webapp.client;

import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import us.heptet.magewars.game.events.PhaseEvent;
import us.heptet.magewars.game.phase.InitiativePhase;
import us.heptet.magewars.game.phase.PhaseInterface;

import java.util.logging.Logger;

/**
 * Created by jade on 16/09/2016.
 */
public class GwtTestPhaseEvent extends GWTTestCase {
    private static Logger logger = Logger.getLogger(GwtTestPhaseEvent.class.getName());
    static interface EventMapper extends ObjectMapper<PhaseEvent>
    {
    }

    @Override
    public String getModuleName() {
        return "us.heptet.magewars.webapp.WebApp";
    }

    @Override
    protected void gwtSetUp() throws Exception {

    }

    public void testSerialize() throws Exception {
        PhaseInterface phase = new InitiativePhase();
        PhaseEvent phaseEvent = new PhaseEvent(PhaseEvent.NEW_PHASE, phase);
        EventMapper eventMapper = GWT.create(EventMapper.class);
        String write = eventMapper.write(phaseEvent);
        logger.info("Serialize = " + write);
    }

    public void testSerializeDeserialize() throws Exception {
        PhaseInterface phase = new InitiativePhase();
        PhaseEvent phaseEvent = new PhaseEvent(PhaseEvent.NEW_PHASE, phase);
        EventMapper eventMapper = GWT.create(EventMapper.class);
        String write = eventMapper.write(phaseEvent);
        PhaseEvent event2 = eventMapper.read(write);
        logger.info("DeSerialize = " + event2.toString());
    }
}
