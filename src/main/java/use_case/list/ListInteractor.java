package use_case.list;

import data_access.grade_api.UserRepository;

/**
 * The list display Interactor.
 */
public class ListInteractor implements ListInputBoundary {
    private final UserRepository userDataAccessObject;
    private final ListOutputBoundary listPresenter;

    public ListInteractor(UserRepository signupDataAccessInterface,
                          ListOutputBoundary listOutputBoundary) {
        this.userDataAccessObject = signupDataAccessInterface;
        this.listPresenter = listOutputBoundary;
    }

    @Override
    public void execute(ListInputData listInputData) {
        // TODO
    }
}
