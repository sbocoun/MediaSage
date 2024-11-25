package view.filter_panels;

import java.awt.CardLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import interface_adapter.filter_list.FilterViewModel;

/**
 * Manages the filter panels for the different media types.
 */
public class FilterPanelManager {
    private final JPanel filterPanelContainer = new JPanel(new CardLayout());
    private final CardLayout cardLayout = (CardLayout) filterPanelContainer.getLayout();
    private final Map<String, Filter> filterPanels = new HashMap<>();
    // Assigned a default value to prevent null pointer exceptions.
    private String displayedFilterPanel = "entity.Movie";

    public FilterPanelManager(FilterViewModel filterViewModel) {
        this.addFilterPanel("entity.Movie", new MovieFilter(filterViewModel));
        this.addFilterPanel("entity.Television", new TelevisionFilter(filterViewModel));
    }

    /**
     * Clears the currently viewed filter panel.
     */
    public void clearFilterPanel() {
        filterPanels.get(displayedFilterPanel).clearFilter();
    }

    /**
     * Adds a filter panel to the manager.
     *
     * @param name The type of entity the filter panel is for.
     * @param panel The filter panel to add.
     */
    public void addFilterPanel(String name, Filter panel) {
        filterPanels.put(name, panel);
        if (panel instanceof JPanel jPanel) {
            filterPanelContainer.add(jPanel, name);
        }
    }

    /**
     * Switches the filter panel to the one corresponding to the given media type.
     *
     * @param mediaType The media type to switch to.
     */
    public void updateFilterPanel(String mediaType) {
        cardLayout.show(filterPanelContainer, mediaType);
        displayedFilterPanel = mediaType;
    }

    /**
     * Retrieves the container for the filter panels.
     * @return The container for the filter panels.
     */
    public JPanel getFilterPanelContainer() {
        return filterPanelContainer;
    }
}
