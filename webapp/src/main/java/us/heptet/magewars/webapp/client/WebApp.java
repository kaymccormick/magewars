package us.heptet.magewars.webapp.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.logging.client.ConsoleLogHandler;
import com.google.gwt.logging.client.HasWidgetsLogHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;
import us.heptet.magewars.game.events.EventChannel;
import us.heptet.magewars.ui.Container;
import us.heptet.magewars.webapp.client.gwt.MyResources;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Entry for point for MageWars GWT WebApp.
 *
 * Created by kay on 3/13/14.
 *
 * @see us.heptet.magewars.webapp.client.AppController
 * @see us.heptet.magewars.webapp.client.Injector
 * @see us.heptet.magewars.webapp.client.InjectorModule
 * @see us.heptet.magewars.webapp.client.gwt.MyResources
 */
public class WebApp implements EntryPoint {
    class ErrorState {
        boolean inErrorState = false;

        public boolean isInErrorState() {
            return inErrorState;
        }

        public void setInErrorState(boolean inErrorState) {
            this.inErrorState = inErrorState;
        }
    }

    ErrorState errorState = new ErrorState();

    private static Logger logger = Logger.getLogger(WebApp.class.getName());
    private boolean atmosphereEnabled = false;
    private boolean webSocketsEnabled = false;
    private boolean stompEnabled = true;

    static {
        logger.setLevel(Level.FINEST);
    }

    /**
     * This is the entry point method.
     */
    @Override
    public void onModuleLoad() {
        FlowPanel logPanel = null;
        ScrollPanel scrollPanel = null;

        boolean logPane = false;

        ConsoleLogHandler consoleLogHandler = new ConsoleLogHandler();
        consoleLogHandler.setLevel(Level.ALL);
        logger.getLogger("").addHandler(consoleLogHandler);
        logger.info("test");
        if(logPane) {
            scrollPanel = new ScrollPanel();
            scrollPanel.setHeight("250px");
            final ScrollPanel sp = scrollPanel;
            logPanel = new FlowPanel() {
                @Override
                public void add(Widget w) {
                    super.add(w);
                    sp.scrollToBottom();
                }
            };
            logPanel.ensureDebugId("logPanel");
            scrollPanel.add(logPanel);
            HasWidgetsLogHandler logHandler = new HasWidgetsLogHandler(logPanel);
            logHandler.setLevel(Level.ALL);
            Logger.getLogger("").addHandler(logHandler);
        }

        String url1 = Document.get().getURL();
        logger.severe("url = " + url1);
        if(url1.contains("?"))
        {

            String qs = url1.substring(url1.indexOf('?') + 1, url1.lastIndexOf('#') == -1 ? url1.length() : url1.lastIndexOf('#'));

            while(qs.length() > 0) {
                String key;
                String val = null;
                int amp = qs.indexOf('&');
                if (amp == -1) {
                    amp = qs.length();
                }
                int eq = qs.indexOf('=');
                int stop = eq < amp ? eq : amp;
                key = qs.substring(0, stop);
                if (eq < amp) {
                    val = qs.substring(stop + 1, amp);
                }
                qs = qs.substring(amp);
                if(key.compareTo("logLevel") == 0)

                {
                    logger.info("setting loglevel to " + val);
                    if(val != null) {
                        Logger.getGlobal().setLevel(Level.parse(val));
                    }
                }
            }
        }

        logger.finest("in WebApp.onModuleLoad");

        final Injector injector = GWT.create(Injector.class);

        MyResources.INSTANCE.css().ensureInjected();
        MyResources.INSTANCE.gss().ensureInjected();

        boolean fSetUncaughtExceptionHandler = false;
        if(fSetUncaughtExceptionHandler) {
            GWT.setUncaughtExceptionHandler(throwable -> ExceptionLogger.logException(throwable));
        }

        final AppController appViewer = injector.getAppController();

        Set<EventChannel> channels = new LinkedHashSet<>();

        if(webSocketsEnabled)
        {
            String url = GWT.getModuleBaseURL() + "ws";
            url = url.replace("http://", "ws://").replace("/webapp", "");
            channels.add(new WebSocketEventChannel(url));
        }
        if(stompEnabled)
        {
            String url = GWT.getModuleBaseURL() + "msg";
            url = url.replace("http://", "ws://").replace("/webapp", "");

            channels.add(new StompEventChannel(url));
        }

        appViewer.getEventManager().addAndConnectChannels(channels);

        final RootPanel rootPanel = RootPanel.get("maindiv");
        rootPanel.ensureDebugId("rootPanel");

        final Container container = injector.getUiFactory().createControlContainer();
        rootPanel.add((Widget)container.getControl());

        if(logPane) {
            appViewer.setLogPanel(logPanel);
            rootPanel.add(scrollPanel);
            rootPanel.setWidgetPosition(scrollPanel, 0, 300);
        }

        appViewer.go(container);

    }

    public void setAtmosphereEnabled(boolean atmosphereEnabled) {
        this.atmosphereEnabled = atmosphereEnabled;
    }

    public boolean isAtmosphereEnabled() {
        return atmosphereEnabled;
    }

    public boolean isWebSocketsEnabled() {
        return webSocketsEnabled;
    }

    public void setWebSocketsEnabled(boolean webSocketsEnabled) {
        this.webSocketsEnabled = webSocketsEnabled;
    }

    public boolean isStompEnabled() {
        return stompEnabled;
    }

    public void setStompEnabled(boolean stompEnabled) {
        this.stompEnabled = stompEnabled;
    }

    public ErrorState getErrorState() {
        return errorState;
    }
}

