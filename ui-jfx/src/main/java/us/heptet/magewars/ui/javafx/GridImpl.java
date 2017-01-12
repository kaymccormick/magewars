package us.heptet.magewars.ui.javafx;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;

import us.heptet.magewars.ui.Control;
import us.heptet.magewars.ui.Grid;


import java.util.logging.Level;
import java.util.logging.Logger;

/* Created by kay on 4/9/2014. */
/**
 * Our wrapper class for a JavaFX GridPane
 */
public class GridImpl implements Grid {
    private static Logger logger = Logger.getLogger(GridImpl.class.getName());
    private GridPane gridPane;
    Integer rows;
    Integer cols;

    static {
        logger.setLevel(Level.FINEST);
    }

    /**
     * Construct an instance.
     */
    public GridImpl() {
        gridPane = new GridPane();
        gridPane.setGridLinesVisible(true);

    }

    /**
     * Construct an instance.
     * @param rows
     * @param cols
     */
    public GridImpl(int rows, int cols) {
        this();
        this.rows = rows;
        this.cols = cols;
    }

    @Override
    public void add(Control control, int col, int row) {
        assert control != null;
        logger.fine("adding " + control.getControl() + " to grid cell (" + row + ", " + col + ") grid is " + rows + " x " + cols);
        if(col >= cols || row >= rows)
        {
            logger.warning("adding control to cell (" + row + ", " + col + ") outside of initial bounds (" + rows + ", " + cols + ")");
        }

        gridPane.add((Node)control.getControl(), col, row);
    }

    @Override
    public void remove(Control control) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void setId(String id) {
        gridPane.setId(id);
    }

    @Override
    public void setVisible(boolean visible) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object getControl() {
        return gridPane;
    }

    @Override
    public double getWidth() {
        return gridPane.getWidth();
    }

    @Override
    public double getHeight() {
        return gridPane.getHeight();
    }
}
