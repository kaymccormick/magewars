package us.heptet.magewars.ui.gwt;

import com.google.gwt.user.client.ui.InlineLabel;
import us.heptet.magewars.ui.Label;

/* Created by kay on 4/10/2014. */
/**
 * Label implementation.
 */
public class LabelImpl extends ControlImpl implements Label {
    private InlineLabel label;

    /**
     * Create a label.
     * @param text
     */
    public LabelImpl(String text) {
        label = new InlineLabel(text);
        initWidget(label);
    }

    @Override
    public void setId(String id) {
        getElement().setAttribute("id", id);
    }

    @Override
    public void setVisible(boolean visible) {
        throw new UnsupportedOperationException();

    }

    @Override
    public Object getControl() {
        return this;
    }

    @Override
    public void setText(String text) {
        label.setText(text);
    }
}
