package us.heptet.magewars.ui.vaadin;

import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import us.heptet.magewars.ui.Control;
import us.heptet.magewars.ui.Grid;

/* Created by kay on 4/11/2014. */
/**
 *
 */
public class GridImpl implements Grid {
    GridLayout gridLayout;

    public GridImpl(int rows, int cols) {
        gridLayout = new GridLayout(cols, rows);
    }

    @Override
    public void add(Control control, int col, int row) {
        gridLayout.addComponent((Component) control.getControl(), col, row);
    }

    @Override
    public void remove(Control control) {
        gridLayout.removeComponent((Component)control.getControl());

    }

    @Override
    public void setId(String id) {

    }

    @Override
    public void setVisible(boolean visible) {
        gridLayout.setVisible(visible);
    }

    @Override
    public Object getControl() {
        return gridLayout;
    }

    @Override
    public double getWidth() {
        return 0;
    }

    @Override
    public double getHeight() {
        return 0;
    }
}
