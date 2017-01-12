package us.heptet.magewars.webapp.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.TakesValue;

import com.google.gwt.user.client.ui.*;
import us.heptet.magewars.webapp.client.gwt.MyResources;

/* Created by kay on 5/15/2014. */
/**
 * View implementation for {@link LoginView}.
 *
 * @see us.heptet.magewars.webapp.client.presenter.LoginPresenter
 */
public class LoginViewImpl extends Composite implements LoginView {
    private Presenter presenter;

    interface LoginViewUiBinder extends UiBinder<FlowPanel, LoginViewImpl> {}

    private static LoginViewUiBinder uiBinder = GWT.create(LoginViewUiBinder.class);

    @UiField
    MyResources res;

    @UiField
    TextBox username;

    @UiField
    TextBox password;

    @UiField
    FlowPanel loginPanel;

    @UiField
    Label statusLabel;

    @UiField
    Button loginButton;

    /**
     * Create an instance.
     */
    public LoginViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @UiHandler("loginButton")
    void handleLoginClick(ClickEvent e)
    {
        presenter.onLoginButtonClicked();
    }

    public FlowPanel getFlowPanel()
    {
        return (FlowPanel)getWidget();
    }

    @Override
    public void setStatusVisible(boolean b) {
        statusLabel.setVisible(b);
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public Widget asWidget() {
        return this;
    }

    @Override
    public TakesValue<String> getStatus() {
        return statusLabel.asEditor();
    }

    @Override
    public HasValue<String> getUsername() {
        return username;
    }

    @Override
    public HasValue<String> getPassword() {
        return password;
    }

    @Override
    public void setId(String id) {
        /* do nothing */
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
