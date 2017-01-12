package us.heptet.magewars.domain.game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/* Created by kay on 3/14/14. */
/**
 * Implementation of Arena.
 */
public class ArenaImpl implements Arena<GameObject> {
    private static Logger logger = Logger.getLogger(ArenaImpl.class.getName());
    private int numRows;
    private int numCols;
    private Zone[][] zones; // do we want a two dimensional array here?
    private Zone[] mageStart;
    private List<AddObjectHandler> addObjectHandlerList = new ArrayList<>();
    private List<Zone> zoneList = new ArrayList<>();

    static {
        logger.setLevel(Level.FINEST);
    }
    /***
     * Initialize the Arena with the default number of rows and columns.
     * @see Constants
     */
    public ArenaImpl() {
        this(Constants.ARENA_ROWS, Constants.ARENA_COLS);
    }

    /***
     * Create an arena with the specified number of rows and columns.
     * @param numRows Number of rows in the arena.
     * @param numCols Number of columns in the arena.
     */
    public ArenaImpl(int numRows, int numCols)
    {
        this.numCols = numCols;
        this.numRows = numRows;

        zones = new Zone[numRows][numCols];
        for(int i = 0; i < numRows; i++)
        {
            for(int j = 0; j < numCols; j++)
            {
                Zone curZ = GameElementFactory.createZone(j, i);
                curZ.addAddObjectListener((AddObjectHandler) gameObject ->
                    addObjectHandlerList.forEach(o -> o.addObject(gameObject)));
                zones[i][j] = curZ;
                zoneList.add(curZ);
            }
        }
        mageStart = new Zone[Constants.PLAYER_START.length];
        for(int i = 0; i < Constants.PLAYER_START.length; i++)
        {
            mageStart[i] = zones[Constants.PLAYER_START[i][1]][Constants.PLAYER_START[i][0]];
        }
    }

    @Override
    public void setMageStart(Player player, Zone start)
    {
        mageStart[player.getPlayerIndex()] = start;
    }

    @Override
    public Zone[] getMageStart() {
        return mageStart;
    }

    @Override
    public void addAddObjectListener(AddObjectHandler handler)
    {
        addObjectHandlerList.add(handler);
    }

    @Override
    public int getNumRows() {
        return numRows;
    }

    @Override
    public int getNumCols() {
        return numCols;
    }

    @Override
    public boolean containsObject(GameObject gameObject) {
        for(Zone z:zoneList)
        {
            if(z.getObjects().contains(gameObject))
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public Zone getZone(int col, int row) {
        logger.finest("getting zone [" + row + "][" + col + "] size " + zones.length + "x" + zones[row].length);
        assert zones[row][col] != null;
        return zones[row][col];
    }

    @Override
    public Collection<Object> getAllObjects() {
        List<Object> allObjects = new ArrayList<>();
        for(Zone z:zoneList)
        {
            allObjects.addAll(z.getObjects());
        }
        return allObjects;
    }

    @Override
    public void addObject(Object gameObject) {
        logger.warning("addObject called with " + gameObject.toString());
        throw new UnsupportedOperationException();
    }

    public Zone[][] getZones() {
        return zones;
    }

    @Override
    public String toString() {
        return "ArenaImpl{" +
                "numRows=" + numRows +
                ", numCols=" + numCols +
                ", zoneList=" + zoneList +
                '}';
    }
}
