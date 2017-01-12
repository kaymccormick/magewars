package us.heptet.magewars.ui.factory;

import us.heptet.magewars.ui.MultiCardViewImpl;
import us.heptet.magewars.ui.view.MultiCardView;

/**
 * Created by jade on 22/08/2016.
 */
public abstract class GenericUiFactory implements UiFactory {
    @Override
    public MultiCardView createMultiCardView(int cols, int rows) {
        return new MultiCardViewImpl(this, cols, rows);
    }
}
