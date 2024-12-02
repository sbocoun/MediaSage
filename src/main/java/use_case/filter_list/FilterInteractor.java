package use_case.filter_list;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.AbstractMedia;
import entity.MediaCollection;
import use_case.filter_list.filter_strategies.FilterStrategy;
import use_case.filter_list.filter_strategies.MovieFilterStrategy;
import use_case.filter_list.filter_strategies.TelevisionFilterStrategy;

/**
 * Interactor for the filter panel.
 */
public class FilterInteractor implements FilterInputBoundary {

    private final FilterDataAccessInterface userDAO;
    private final FilterListOutputBoundary filterPresenter;
    private final Map<String, FilterStrategy> strategies = new HashMap<>();

    public FilterInteractor(FilterListOutputBoundary filterPresenter, FilterDataAccessInterface userDataAccessObject) {
        this.filterPresenter = filterPresenter;
        this.userDAO = userDataAccessObject;
        strategies.put("entity.Movie", new MovieFilterStrategy());
        strategies.put("entity.Television", new TelevisionFilterStrategy());
    }

    /**
     * Executes the filter list use case.
     *
     * @param filterListInputData input data containing the collection name, type, and filters
     */
    @Override
    public void execute(FilterListInputData filterListInputData) {
        final FilterStrategy strategy = strategies.get(filterListInputData.getCollectionType());
        if (userDAO.getCurrentUsername().isEmpty()) {
            filterPresenter.prepareFailView("User is not logged in.");
        }
        else if (strategy == null) {
            filterPresenter.prepareFailView("Unsupported collection type: "
                    + filterListInputData.getCollectionType());
        }
        else {
            filterPresenter.prepareSuccessView(filterMedia(filterListInputData, strategy));
        }
    }

    /**
     * Filters the media based on the given filters.
     *
     * @param filterListInputData input data containing the collection name, type, and filters
     * @param strategy the strategy to use for filtering
     * @param <T> the type of media to filter
     * @return the filtered media names
     */
    private <T extends AbstractMedia> FilterListOutputData filterMedia(
        FilterListInputData filterListInputData, FilterStrategy strategy) {
        final MediaCollection<T> collection = userDAO.getNamedCollection(
                     filterListInputData.getCollectionName(),
                     filterListInputData.getCollectionType());
        final List<T> mediaList = collection.getMediaList();
        final List<String> filteredMediaNames = new ArrayList<>();
        for (T media : mediaList) {
            if (strategy.meetsCriteria(media, filterListInputData.getFilters())) {
                filteredMediaNames.add(media.getName());
            }
        }
        return new FilterListOutputData(filteredMediaNames);
    }
}
