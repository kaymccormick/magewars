package us.heptet.magewars.ui.generic;

import org.springframework.stereotype.Component;
import us.heptet.magewars.domain.game.Arena;
import us.heptet.magewars.domain.game.Zone;
import us.heptet.magewars.ui.Grid;
import us.heptet.magewars.ui.factory.UiFactory;
import us.heptet.magewars.ui.ZoneView;
import us.heptet.magewars.ui.ArenaView;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/* Created by kay on 4/30/2014. */
/**
 * Arena View implementation, the generic version
 */
@Component
public class ArenaViewImpl implements ArenaView {
    private int numRows;
    private int numCols;
    private Grid grid;
    private List<ZoneView> zoneViewList;

    /**
     * Construct an instance
     * @param uiFactory
     * @param arena
     */
    @Inject
    public ArenaViewImpl(UiFactory uiFactory, Arena arena)
    {
        zoneViewList = new ArrayList<>();

        numRows = arena.getNumRows();
        numCols = arena.getNumCols();

        grid = uiFactory.createGrid(numRows, numCols);
        grid.setId("boardpane");

        for(int row = 0; row < numRows; row++)
        {
            for(int col = 0; col < numCols; col++)
            {
                Zone zone = arena.getZone(col, row);
                assert zone != null;
                ZoneView zoneView = uiFactory.createZoneView(zone);

                zoneViewList.add(zoneView);
                grid.add(zoneView, col, row);
            }
        }
    }


    @Override
    public ZoneView getZoneView(int col, int row)
    {
        return zoneViewList.get(row * numCols + col);
    }

    @Override
    public void setId(String id) {
        grid.setId(id);

    }

    @Override
    public void setVisible(boolean visible) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object getControl() {
        return grid.getControl();
    }

    @Override
    public double getWidth() {
        return grid.getWidth();
    }

    @Override
    public double getHeight() {
        return grid.getHeight();
    }
}
