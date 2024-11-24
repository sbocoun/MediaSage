package use_case.list;

import data_access.InMemoryUserDAO;
import entity.AbstractMedia;
import entity.MediaCollection;
import entity.PropMedia;
import entity.Rating;
import entity.User;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.fail;

public class ListInteractorTest {
    private InMemoryUserDAO userRepo;

    @Before
    public void setup() {
        userRepo = new InMemoryUserDAO();
        userRepo.loadUserFromFile("grade-api-sample-response.json");
        userRepo.get("test");
    }

    @Test
    public void testLogin() {
        final ListOutputBoundary listOB = new ListOutputBoundary() {
            @Override
            public void prepareSuccessView(ListOutputData outputData) {
                // Should display the first media collection stored in the user's list
                final List<List<Object>> collectionData = new ArrayList<>(List.of(
                        getTheMatrixData(),
                        getBlueBrothersData()
                ));
                final List<String> availableCollections = new ArrayList<>(List.of("Movie Watchlist", "watchlist 2", "Movie testlist"));
                assertEquals(collectionData, outputData.getCollectionData());
                assertEquals("Movie Watchlist", outputData.getCollectionName());
                assertEquals("entity.Movie", outputData.getMediaType());
                assertEquals(availableCollections, outputData.getAvailableCollections());

            }

            @Override
            public void prepareLogoutView() {
                fail("Logout View should not have been reached.");
            }

            @Override
            public void prepareFailView(ListOutputData listOutputData) {
                fail("Fail View should not have been reached.");
            }
        };
        final ListInteractor listInteractor = new ListInteractor(userRepo, listOB);
        listInteractor.executeOnLogin(userRepo.get(userRepo.getCurrentUsername()).getAllMediaCollections());
    }

    @Test
    public void testNotLoggedIn() {
        final InMemoryUserDAO emptyDAO = new InMemoryUserDAO();
        final ListOutputBoundary listOB = new ListOutputBoundary() {
            @Override
            public void prepareSuccessView(ListOutputData outputData) {
                fail("Should not have been reached.");
            }

            @Override
            public void prepareLogoutView() {
                fail("Should not have been reached.");
            }

            @Override
            public void prepareFailView(ListOutputData listOutputData) {
                fail("Should not have been reached.");
            }
        };
        final ListInteractor listInteractor = new ListInteractor(emptyDAO, listOB);
        final ListInputData listInputData = new ListInputData();
        assertThrows(RuntimeException.class, () -> listInteractor.execute(listInputData));
    }

    @Test
    public void testCollectionNameNotFound() {
        final ListOutputBoundary listOB = new ListOutputBoundary() {
            @Override
            public void prepareSuccessView(ListOutputData outputData) {
                fail("Collection should not have been found.");
            }

            @Override
            public void prepareLogoutView() {
                fail("Logout View should not have been reached.");
            }

            @Override
            public void prepareFailView(ListOutputData listOutputData) {
                final String errorMessage = "Collection with the name non-existent collection not found.";
                assertEquals(errorMessage, listOutputData.getErrorMessage());
            }
        };
        final ListInputBoundary listInteractor = new ListInteractor(userRepo, listOB);
        final ListInputData listInputData = new ListInputData();
        listInputData.setNameOfDesiredCollection("non-existent collection");
        listInteractor.execute(listInputData);
    }

    @Test
    public void testMovieCollectionFound() {
        final ListOutputBoundary listOB = new ListOutputBoundary() {
            @Override
            public void prepareSuccessView(ListOutputData outputData) {
                final List<Object> blueBrothers = getBlueBrothersData();
                final List<String> availableCollections = new ArrayList<>(List.of("Movie Watchlist", "watchlist 2", "Movie testlist"));
                assertEquals(blueBrothers, outputData.getCollectionData().get(0));
                assertEquals("Movie testlist", outputData.getCollectionName());
                assertEquals(availableCollections, outputData.getAvailableCollections());
                assertEquals("entity.Movie", outputData.getMediaType());
            }

            @Override
            public void prepareLogoutView() {
                fail("Logout View should not have been reached.");
            }

            @Override
            public void prepareFailView(ListOutputData listOutputData) {
                fail("Collection should have been found.");
            }
        };
        final ListInputBoundary listInteractor = new ListInteractor(userRepo, listOB);
        final ListInputData listInputData = new ListInputData();
        listInputData.setNameOfDesiredCollection("Movie testlist");
        listInteractor.execute(listInputData);
    }

    @Test
    public void testTelevisionCollectionFound() {
        final ListOutputBoundary listOB = new ListOutputBoundary() {
            @Override
            public void prepareSuccessView(ListOutputData outputData) {
                final List<Object> poI = getPoIData();
                final List<String> availableCollections = new ArrayList<>(List.of("Movie Watchlist", "watchlist 2", "Movie testlist"));
                assertEquals(poI, outputData.getCollectionData().get(0));
                assertEquals("watchlist 2", outputData.getCollectionName());
                assertEquals(availableCollections, outputData.getAvailableCollections());
                assertEquals("entity.Television", outputData.getMediaType());
            }

            @Override
            public void prepareLogoutView() {
                fail("Logout View should not have been reached.");
            }

            @Override
            public void prepareFailView(ListOutputData listOutputData) {
                fail("Collection should have been found.");
            }
        };
        final ListInputBoundary listInteractor = new ListInteractor(userRepo, listOB);
        final ListInputData listInputData = new ListInputData();
        listInputData.setNameOfDesiredCollection("watchlist 2");
        listInteractor.execute(listInputData);
    }

    @Test
    public void testUnknownCollection() {
        final ListOutputBoundary listOB = new ListOutputBoundary() {
            @Override
            public void prepareSuccessView(ListOutputData outputData) {
                fail("Success View should not have been reached.");
            }

            @Override
            public void prepareLogoutView() {
                fail("Logout View should not have been reached.");
            }

            @Override
            public void prepareFailView(ListOutputData listOutputData) {
                fail("Collection should have been found.");
            }
        };
        addUnknownData();
        final ListInputBoundary listInteractor = new ListInteractor(userRepo, listOB);
        final ListInputData listInputData = new ListInputData();
        listInputData.setNameOfDesiredCollection("prop collection");
        assertThrows(UnsupportedOperationException.class, () -> listInteractor.execute(listInputData));
        removeUnknownData();
    }

    @Test
    public void testNewUserWithDefaultCollection() {
        final User newUser = new User("prop user", "password");
        final InMemoryUserDAO emptyRepo = new InMemoryUserDAO();
        emptyRepo.save(newUser);
        emptyRepo.get("prop user");

        final ListOutputBoundary listOB = new ListOutputBoundary() {
            @Override
            public void prepareSuccessView(ListOutputData outputData) {
                final List<List<Object>> defaultCollection = new ArrayList<>();
                final List<String> availableCollections = new ArrayList<>(List.of("movie night"));
                assertEquals(defaultCollection, outputData.getCollectionData());
                assertEquals("movie night", outputData.getCollectionName());
                assertEquals(availableCollections, outputData.getAvailableCollections());
                assertEquals("entity.Movie", outputData.getMediaType());
            }

            @Override
            public void prepareLogoutView() {
                fail("Logout View should not have been reached.");
            }

            @Override
            public void prepareFailView(ListOutputData listOutputData) {
                fail("The Default collection should have been found.");
            }
        };
        final ListInteractor listInteractor = new ListInteractor(emptyRepo, listOB);
        assertNotNull(emptyRepo.getCurrentUser());
        listInteractor.executeOnLogin(emptyRepo.getCurrentUser().getAllMediaCollections());
    }

    private List<Object> getBlueBrothersData() {
        final List<Object> result = new ArrayList<>();
        result.add("The Blues Brothers");
        result.add(133);
        result.add("Jake Blues, just released from prison, puts his old band back together to save the Catholic home where he and his brother Elwood were raised.");
        result.add(new ArrayList<>(List.of("Music",
                "Comedy",
                "Action",
                "Crime")));
        result.add(new ArrayList<>(List.of("Dan Aykroyd",
                "John Belushi",
                "James Brown",
                "Cab Calloway",
                "Ray Charles",
                "Aretha Franklin")));
        result.add(-1);
        result.add(77);
        return result;
    }

    private List<Object> getTheMatrixData() {
        final List<Object> result = new ArrayList<>();
        result.add("The Matrix");
        result.add(136);
        result.add("A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.");
        // genres
        result.add(new ArrayList<>(List.of("Action",
                "Sci-Fi")));
        // cast
        result.add(new ArrayList<>(List.of("Keanu Reeves",
                "Lawrence Fishburn",
                "Carrie-Anne Moss",
                "Hugo Weaving",
                "Gloria Foster")));
        result.add(-1);
        result.add(87);
        return result;
    }

    private List<Object> getPoIData() {
        final List<Object> result = new ArrayList<>();
        result.add("Person of Interest");
        result.add(5);
        result.add(103);
        result.add("John Reese, former CIA paramilitary operative, is presumed dead and teams up with reclusive billionaire Finch to prevent violent crimes in New York City by initiating their own type of justice. With the special training that Reese has had in Covert Operations and Finch's genius software inventing mind, the two are a perfect match for the job that they have to complete. With the help of surveillance equipment, they work \"outside the law\" and get the right criminal behind bars. ");
        // genres
        result.add(new ArrayList<>(List.of("Drama",
                "Action & Adventure",
                "Sci-Fi & Fantasy",
                "Crime")));
        // cast
        result.add(new ArrayList<>(List.of("Jim Caviezel",
                "Michael Emerson",
                "Kevin Chapman",
                "Taraji P. Henson",
                "Amy Acker")));
        result.add(-1);
        result.add(81);
        return result;
    }

    private void addUnknownData() {
        final List<MediaCollection<? extends AbstractMedia>> mediaCollections =
                userRepo.get(userRepo.getCurrentUsername()).getAllMediaCollections();
        mediaCollections.add(createPropCollection());
        userRepo.saveMediaCollections(mediaCollections);
    }

    private void removeUnknownData() {
        final List<MediaCollection<? extends AbstractMedia>> mediaCollections =
                userRepo.get(userRepo.getCurrentUsername()).getAllMediaCollections();
        mediaCollections.remove(createPropCollection());
        userRepo.saveMediaCollections(mediaCollections);
    }

    private MediaCollection<PropMedia> createPropCollection() {
        final PropMedia propMedia = new PropMedia(
                "prop 1",
                new ArrayList<>(List.of("prop genre")),
                new Rating(15),
                new Rating(40)
        );
        return new MediaCollection<>(
                "prop collection",
                "watched",
                PropMedia.class,
                new ArrayList<>(List.of(propMedia))
        );
    }
}
