package interface_adapter.list;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A factory for creating ListTableModel depending on the type of AbstractMedia displayed.
 */
public class ListTableModelFactory {
    private final Map<String, List<String>> mediaColumns;
    private final String name = "name";
    private final String runtime = "runtime";
    private final String description = "description";
    private final String genres = "genres";
    private final String cast = "cast";
    private final String userRating = "user-rating";
    private final String externalRating = "external-rating";
    private final String entityMovie = "entity.Movie";
    private final String entityTelevision = "entity.Television";

    /**
     * Creates a new ListTableModelFactory that creates the corresponding ListTableModel for the media type.
     */
    public ListTableModelFactory() {
        final List<String> movieColumns = new ArrayList<>(List.of(
                name, runtime, description, genres, cast, userRating, externalRating));
        final List<String> televisionColumns = new ArrayList<>(List.of(
                name, "seasons", "total episodes", description, genres, cast,
                userRating, externalRating));
        this.mediaColumns = new HashMap<>();
        mediaColumns.put(entityMovie, movieColumns);
        mediaColumns.put(entityTelevision, televisionColumns);
    }

    /**
     * Create a ListTableModel for the relevant mediaType.
     *
     * @param mediaType the type of AbstractMedia (e.g. entity.Movie)
     * @param mediaList the list of AbstractMedia
     * @return a ListTableModel used to display the list of AbstractMedia in a JTable
     * @throws UnsupportedOperationException if the AbstractMedia type is unknown
     */
    public ListTableModel createListTableModel(String mediaType,
                                               List<Map<String, Object>> mediaList)
            throws UnsupportedOperationException {
        final ListTableModel listTableModel;
        if (entityMovie.equals(mediaType)) {
            listTableModel = new ListTableModel(mediaColumns.get(entityMovie),
                    createMovieTable(mediaList));
        }
        else if (entityTelevision.equals(mediaType)) {
            listTableModel = new ListTableModel(mediaColumns.get(entityTelevision),
                    createTelevisionTable(mediaList));
        }
        else {
            throw new UnsupportedOperationException("Unknown media type.");
        }
        return listTableModel;
    }

    /**
     * Creates a table used for ListTableModel, storing movie data.
     * @param mediaList the list of movie data to store into the table
     * @return a table for movie data
     */
    private List<List<Object>> createMovieTable(List<Map<String, Object>> mediaList) {
        final List<List<Object>> table = new ArrayList<>();
        for (final Map<String, Object> media : mediaList) {
            final List<Object> row = new ArrayList<>();
            row.add(media.get(name));
            row.add(media.get(runtime));
            row.add(media.get(description));
            row.add(media.get(genres));
            row.add(media.get(cast));
            row.add(media.get(userRating));
            row.add(media.get(externalRating));
            table.add(row);
        }
        return table;
    }

    /**
     * Creates a table used for ListTableModel, storing television data.
     * @param mediaList the list of television data to store into the table
     * @return a table for television data
     */
    private List<List<Object>> createTelevisionTable(List<Map<String, Object>> mediaList) {
        final List<List<Object>> table = new ArrayList<>();
        for (final Map<String, Object> media : mediaList) {
            final List<Object> row = new ArrayList<>();
            row.add(media.get(name));
            row.add(media.get("seasons"));
            row.add(media.get("total episodes"));
            row.add(media.get(description));
            row.add(media.get(genres));
            row.add(media.get(cast));
            row.add(media.get(userRating));
            row.add(media.get(externalRating));
            table.add(row);
        }
        return table;
    }
}
