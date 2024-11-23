package interface_adapter.generate_recommendations;

import interface_adapter.list.ListViewModel;
import use_case.generate_recommendations.GenOutputBoundary;

/**
 * Presenter for the output of TasteDive recommendations.
 */
public class GenPresenter implements GenOutputBoundary {

    private final ListViewModel listViewModel;

    public GenPresenter(ListViewModel listViewModel) {
        this.listViewModel = listViewModel;
    }

    /**
     * Prepares the success view for the generate recommendations use case.
     *
     * @param recommendations the output data
     */
    @Override
    public void prepareSuccessView(String recommendations) {
        listViewModel.getState().setGeneratedRecommendations(recommendations);
        listViewModel.firePropertyChanged("recommendation");
    }

    /**
     * Prepares the failure view for the generate recommendations Use Case.
     *
     * @param errorMessage the explanation of the failure
     */
    @Override
    public void prepareFailView(String errorMessage) {
        listViewModel.getState().setErrorMessage(errorMessage);
        listViewModel.firePropertyChanged("recommendation");
    }
}
