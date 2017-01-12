package us.heptet.magewars.webapp.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.TakesValue;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/* Created by kay on 6/1/2014. */
/**
 * Our implementation of the GWT Signup view.
 */
public class SignupViewImpl extends Composite implements SignupView {
    @UiTemplate("SignupView.ui.xml")
    interface SignupViewUiBinder extends UiBinder<FlowPanel, SignupViewImpl>
    {

    }
    private Presenter presenter;
    private static SignupViewUiBinder uiBinder = GWT.create(SignupViewUiBinder.class);

    @UiField
    TextBox signupUsername;

    @UiField
    PasswordTextBox signupPassword;

    @UiField
    PasswordTextBox confirmPassword;

    @UiField
    TextBox email;

    @UiField
    Button signupButton;

    @UiField
    Label statusLabel;


    /**
     * Create an instance.
     */
    public SignupViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public Widget asWidget() {
        return this;
    }


    @UiHandler("signupButton")
    void handleSignupClick(ClickEvent e)
    {
        presenter.onSignupButtonClicked();
    }


    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setStatusVisible(boolean b) {
        statusLabel.setVisible(b);
    }

    @Override
    public TakesValue<String> getStatus() {
        return statusLabel.asEditor();
    }

    @Override
    public HasValue<String> getUsername() {
        return signupUsername;
    }

    @Override
    public HasValue<String> getPassword() {
        return signupPassword;
    }

    @Override
    public HasValue<String> getConfirmPassword() {
        return confirmPassword;
    }

    @Override
    public HasValue<String> getEmail() {
        return email;
    }

    @Override
    public void setId(String id) {
        /* no op */

    }

    @Override
    public Object getControl() {
        return this;
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
