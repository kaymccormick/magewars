package us.heptet.magewars.game;

/* Created by kay on 2/15/14. */
/**
 * Parent interface of SelectableRegion.
 */
public interface Selectable {
    /***
     * Retrieve selection state,
     * @return SelectionState instance
     */
    SelectionState getSelectionState();

    /***
     * Set selection state.
     * @param selectionState SelectionState instance
     */
    void setSelectionState(SelectionState selectionState);
}
