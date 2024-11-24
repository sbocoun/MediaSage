package use_case.filter_list;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import data_access.grade_api.UserRepository;
import entity.AbstractMedia;
import entity.MediaCollection;
import interface_adapter.filter_list.FilterListOutputBoundary;
import interface_adapter.filter_list.FilterListOutputData;
import interface_adapter.filter_list.FilterPresenter;
import use_case.filter_list.filter_strategies.FilterStrategy;
import use_case.filter_list.filter_strategies.MovieFilterStrategy;
import use_case.filter_list.filter_strategies.TelevisionFilterStrategy;


/**
 * Interactor for the filter panel.
 */
public class FilterInteractor implements FilterInputBoundary {

    private final UserRepository userDAO;
    private final FilterListOutputBoundary filterPresenter;
    private final Map<String, FilterStrategy> strategies = new HashMap<>();

    public FilterInteractor(FilterPresenter filterPresenter, UserRepository userDataAccessObject) {
        this.filterPresenter = filterPresenter;
        this.userDAO = userDataAccessObject;
        strategies.put("entity.Movie", new MovieFilterStrategy());
        strategies.put("entity.Television", new TelevisionFilterStrategy());
    }

    @Override
    public void execute(FilterListInputData filterListInputData) {
        final FilterStrategy strategy = strategies.get(filterListInputData.getCollectionType());
        if (strategy == null) {
            filterPresenter.prepareFailView("Invalid collection type: " + filterListInputData.getCollectionType());
        }
        filterPresenter.prepareSuccessView(filterMedia(filterListInputData, strategy));
    }

    private <T extends AbstractMedia> FilterListOutputData filterMedia(FilterListInputData filterListInputData, FilterStrategy strategy) {
        final MediaCollection<T> collection = userDAO.getNamedCollection(filterListInputData.getCollectionName(), filterListInputData.getCollectionType());
        final List<T> mediaList = collection.getMediaList();
        final List<String> filteredMediaNames = collection.getMediaNames();
        for (T media : mediaList) {
            if (!strategy.meetsCriteria(media, filterListInputData.getFilters())) {
                filteredMediaNames.remove(media.getName());
            }
        }
        return new FilterListOutputData(filteredMediaNames);
    }
}