package us.heptet.magewars.ui;

import us.heptet.magewars.ui.factory.UiFactory;
import us.heptet.magewars.ui.view.CardView;
import us.heptet.magewars.ui.view.MultiCardView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jade on 21/08/2016.
 */
public class MultiCardViewImpl implements MultiCardView {
    private final Grid grid;
    private final int columns;
    private final int rows;
    private UiFactory uiFactory;

    private List<CardView> cardViewList;

    /**
     * Initialize a multi card view.
     * @param uiFactory     UI Factory
     * @param columns Columns
     * @param rows Rows
     */
    public MultiCardViewImpl(UiFactory uiFactory, int columns, int rows) {

        this.uiFactory = uiFactory;

        grid = uiFactory.createGrid(4, 4);

        this.columns = columns;
        this.rows = rows;

        cardViewList = new ArrayList<>(rows * columns);

        for(int i = 0, index = 0; i < rows; i++, index++)
        {
            for(int j = 0; j < columns; j++, index++)
            {
                CardView cardView = uiFactory.createCardView();

                cardViewList.add(cardView);
                grid.add(cardView, j, i);
            }
        }
    }

    @Override
    public void setId(String id) {
        grid.setId(id);
    }

    @Override
    public void setVisible(boolean visible) {
        grid.setVisible(visible);
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

    @Override
    public CardView getCardView(int row, int column) {
        return cardViewList.get(row * columns + column);
    }
}
