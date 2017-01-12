package us.heptet.magewars.ui.gwt;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Widget;
import us.heptet.magewars.ui.Control;
import us.heptet.magewars.ui.Grid;

import java.util.logging.Level;
import java.util.logging.Logger;

/* Created by kay on 4/9/2014. */
/**
 * Grid implementation
 *
 */
public class GridImpl extends ControlImpl implements Grid {
    private static final Logger logger = Logger.getLogger(GridImpl.class.getName());
    private com.google.gwt.user.client.ui.Grid grid;

    /**
     * Create an instance.
     * @param rows
     * @param cols
     */
    public GridImpl(int rows, int cols) {
        grid = new com.google.gwt.user.client.ui.Grid(rows, cols);
        grid.setCellSpacing(0);
        initWidget(grid);
    }

    static {
        logger.setLevel(Level.FINEST);
    }

    @Override
    public void setId(String id) {
        DOM.setAttribute(getElement(), "id", id);
    }

    @Override
    public Object getControl() {
        return this;
    }

    @Override
    public void add(Control control, int col, int row) {
        Widget w = (Widget)control.getControl();
        logger.info("adding widget " + w.getClass().toString());
        grid.setWidget(row, col, w);

    }

    @Override
    public void remove(Control control) {
        throw new UnsupportedOperationException();
    }
}
