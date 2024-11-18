package interface_adapter.list;

import use_case.list.ListOutputBoundary;

/**
 * The presenter for displaying the media collection list.
 */
public class ListPresenter implements ListOutputBoundary {

    /**
     * Prepares the success view for the Note related Use Cases.
     *
     * @param note the output data
     */
    @Override
    public void prepareSuccessView(String note) {
        // TODO
    }

    /**
     * Prepares the failure view for the Note related Use Cases.
     *
     * @param errorMessage the explanation of the failure
     */
    @Override
    public void prepareFailView(String errorMessage) {
        // TODO
    }
}
