package us.heptet.magewars.domain.game;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/* Created by kay on 3/14/14. */
/**
 * Implementation for Zone game element.
 */
@JsonIgnoreProperties({"gameElementType", "controllingPlayer"})
public class ZoneImpl extends GameElementBase implements Zone {
    private static transient Logger logger = Logger.getLogger(ZoneImpl.class.getName());
    public static final transient Zone NOWHERE = GameElementFactory.createZone(-1, -1);
    private int row;
    private int col;
    private List<GameObject> objects = new ArrayList<>();
    private List<AddObjectHandler> addObjectHandlerList = new ArrayList<>();

    static {
        logger.setLevel(Level.FINEST);
    }
    /***
     * Default constructor.
     */
    public ZoneImpl() {
        /*Default Constructor */
    }

    /***
     * Create a zone based on the row and column. Use GameElementFactory.createZone instead.
     * @param col   column
     * @param row   row
     */
    ZoneImpl(int col, int row)
    {
        super(GameElementType.ZONE);
        logger.finest("Creating ZoneImpl: col = " + col + "; row = " + row);
        this.col = col;
        this.row = row;
    }

    @Override
    public void addObject(GameObject gameObject)
    {
        logger.fine("adding " + gameObject.toString() + " to zone");
        gameObject.moveObject(this);
        objects.add(gameObject);
        for(AddObjectHandler addObjectHandler:addObjectHandlerList)
        {
            logger.finest("calling add object handler.");
            addObjectHandler.addObject(gameObject);
        }
    }

    @Override
    public int getRow() {
        return row;
    }

    @Override
    public int getCol() {
        return col;
    }

    @Override
    public List<GameObject> getObjects() {
        return objects;
    }

    @Override
    public void addAddObjectListener(AddObjectHandler addObjectHandler)
    {
        logger.finest("adding handler " + addObjectHandler + " to " + this);
        addObjectHandlerList.add(addObjectHandler);
        logger.finest("now have " + addObjectHandlerList.size() + " handlers");
    }

    /* Called by move action */
    @Override
    public void moveObject(Object gameObject, Zone destZone) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int distanceFromZone(Zone sourceZone) {
        return Math.abs(getCol() - sourceZone.getCol()) + Math.abs(getRow() - sourceZone.getRow());
    }

    @JsonIgnore
    @Override
    public Zone getLocation() {
        return this;
    }

    @Override
    public String toString() {
        return "ZoneImpl{" +
                "row=" + row +
                ", col=" + col +
                ", objects=" + objects +
                '}';
    }
}
