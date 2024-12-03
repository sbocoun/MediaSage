package interface_adapter.search;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import interface_adapter.ViewModel;
import interface_adapter.note.NoteState;

/**
 * The ViewModel for the SearchView.
 */
public class SearchViewModel extends ViewModel<NoteState> {

    private String searchResults;
    private String errorMessage;
    private final PropertyChangeSupport propertyChangeSupport;

    public SearchViewModel() {
        super("search");
        this.setState(new NoteState());
        this.propertyChangeSupport = new PropertyChangeSupport(this);
    }

    /**
     * Sets the search results and notifies observers of the change.
     * @param searchResults the search results to display
     */
    public void setSearchResults(String searchResults) {
        final String oldResults = this.searchResults;
        this.searchResults = searchResults;
        notifyObservers("searchResults", oldResults, searchResults);
    }

    /**
     * Sets an error message and notifies observers of the change.
     * @param errorMessage the error message to display
     */
    public void setErrorMessage(String errorMessage) {
        final String oldMessage = this.errorMessage;
        this.errorMessage = errorMessage;
        notifyObservers("errorMessage", oldMessage, errorMessage);
    }

    /**
     * Clears the error message.
     */
    private void clearErrorMessage() {
        setErrorMessage("");
    }

    /**
     * Gets the error message.
     * @return the error message
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Notifies all registered observers about a property change.
     * @param propertyName the name of the property that changed
     * @param oldValue the old value of the property
     * @param newValue the new value of the property
     */
    public void notifyObservers(String propertyName, Object oldValue, Object newValue) {
        propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
    }

    /**
     * Adds a PropertyChangeListener to observe state changes.
     * @param listener the PropertyChangeListener to add
     */
    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }
}
