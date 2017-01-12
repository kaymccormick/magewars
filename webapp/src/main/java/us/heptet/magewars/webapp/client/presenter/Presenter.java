package us.heptet.magewars.webapp.client.presenter;

import us.heptet.magewars.ui.Container;

/* Created by kay on 5/14/2014. */
/**
 * Generic presenter interface.
 */
public interface Presenter {
     /**
      * Navigate to the view represented by the presenter.
      * @param container
      */
     void go(final Container container);

     /**
      * Handler for when the view is navigated away from, to remove event handlers.
      */
     void onNavigateAway();
}

